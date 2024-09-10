package com.library.bookwave.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.library.bookwave.config.ApiConfig;
import com.library.bookwave.dto.PaymentDTO;
import com.library.bookwave.repository.model.Payment;
import com.library.bookwave.service.AdminService;
import com.library.bookwave.service.PaymentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final ApiConfig apiConfig;
	private final AdminService adminService;
	private final PaymentService paymentService;
	private final HttpSession session;

	// 충전 페이지
	@GetMapping("/charge")
	public String chargePage() {
		return "payment/charge";
	}

	// 결제 페이지
	@GetMapping("/checkout")
	public String checkoutPage(@RequestParam(name = "amount") Long amount, @RequestParam(name = "orderName") String orderName, Model model) {
		// TODO User principal = session.getAttribute("principal");
		String customerName = "석지웅"; // TODO principal.getName();
		String customerEmail = "slowman918@gmail.com"; // TODO principal.getEmail();
		String customerMobilePhone = "01027203220"; // TODO principal.getPhone();
		String customerKey = UUID.randomUUID().toString();
		String orderId = UUID.randomUUID().toString();

		PaymentDTO paymentDTO = PaymentDTO.builder().clientKey(apiConfig.getClientKey()).customerName(customerName).customerEmail(customerEmail).customerMobilePhone(customerMobilePhone)
				.orderName(orderName).orderId(orderId).price(amount).customerKey(customerKey).build();

		model.addAttribute("payment", paymentDTO);

		return "payment/checkout";
	}

	// 결제 요청 성공
	@GetMapping("/success")
	public String successPage(@RequestParam(name = "paymentType") String paymentType, @RequestParam(name = "orderId") String orderId, @RequestParam(name = "paymentKey") String paymentKey,
			@RequestParam(name = "amount") Long amount, Model model) {

		return "payment/success";
	}

	// 결제 요청 실패
	@GetMapping("/fail")
	public String failPage(@RequestParam(name = "message") String message, @RequestParam(name = "code") String code) {
		System.out.println("fail 메핑");
		System.out.println("message" + message);
		System.out.println("code" + code);
		return "payment/fail";
	}

	// 결제 승인
	@PostMapping("/confirm")
	public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

		JSONParser parser = new JSONParser();
		String orderId;
		String approvedAmount;
		String paymentKey;
		try {
			// 클라이언트에서 받은 JSON 요청 바디입니다.
			JSONObject requestData = (JSONObject) parser.parse(jsonBody);
			paymentKey = (String) requestData.get("paymentKey");
			orderId = (String) requestData.get("orderId");
			approvedAmount = (String) requestData.get("amount");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		;
		JSONObject obj = new JSONObject();
		obj.put("orderId", orderId);
		obj.put("amount", approvedAmount);
		obj.put("paymentKey", paymentKey);

		// 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
		// 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((apiConfig.getSecretKey() + ":").getBytes(StandardCharsets.UTF_8));
		String authorizations = "Basic " + new String(encodedBytes);

		// 결제를 승인하면 결제수단에서 금액이 차감돼요.
		URL url = (new URI("https://api.tosspayments.com/v1/payments/confirm")).toURL();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", authorizations);
		connection.setRequestProperty("Content-Type", "application/json");
		//		connection.setRequestProperty("TossPayments-Test-Code", "REJECT_CARD_PAYMENT");
		//		connection.setRequestProperty("TossPayments-Test-Code", "INCORRECT_SUCCESS_URL_FORMAT");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(obj.toString().getBytes("UTF-8"));

		int code = connection.getResponseCode();
		boolean isSuccess = code == 200;

		InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

		// 결제 성공 및 실패 비즈니스 로직을 구현하세요.
		Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);

		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		responseStream.close();
		if (isSuccess) {
			// 결제 승인 성공시
			Gson gson = new Gson();
			Payment payment = gson.fromJson(jsonObject.toString(), Payment.class);
			payment.setUserId(1); // TODO payment.setUserId(principal.getId());
			paymentService.createPayment(payment, Long.parseLong(approvedAmount));
		} else {
			// 결제승인 실패시
			System.out.println("결제승인실패함");
		}
		System.out.println(ResponseEntity.status(code).body(jsonObject).toString());

		return ResponseEntity.status(code).body(jsonObject);
	}

	//	status
	//	결제 처리 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다. 상태 변화 흐름이 궁금하다면 흐름도를 살펴보세요.
	//	- READY: 결제를 생성하면 가지게 되는 초기 상태입니다. 인증 전까지는 READY 상태를 유지합니다.
	//	- IN_PROGRESS: 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다. 결제 승인 API를 호출하면 결제가 완료됩니다.
	//	- WAITING_FOR_DEPOSIT: 가상계좌 결제 흐름에만 있는 상태로, 결제 고객이 발급된 가상계좌에 입금하는 것을 기다리고 있는 상태입니다.
	//	- DONE: 인증된 결제수단 정보, 고객 정보로 요청한 결제가 승인된 상태입니다.
	//	- CANCELED: 승인된 결제가 취소된 상태입니다.
	//	- PARTIAL_CANCELED: 승인된 결제가 부분 취소된 상태입니다.
	//	- ABORTED: 결제 승인이 실패한 상태입니다.
	//	- EXPIRED: 결제 유효 시간 30분이 지나 거래가 취소된 상태입니다. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.

	// cancel_status
	// - REQUEST_CANCEL: 결제 취소 요청

	// 결제 취소 승인
	@GetMapping("/cancel")
	public String cancelPaymentProc(@RequestParam(name = "id") Integer id, @RequestParam(name = "userId") Integer userId, @RequestParam(name = "cancelReason") String cancelReason) {
		Payment payment = paymentService.readPaymentById(id);

		try {
			// HTTP 클라이언트 생성
			HttpClient client = HttpClient.newHttpClient();

			// 요청 URL
			URI uri = URI.create("https://api.tosspayments.com/v1/payments/" + payment.getPaymentKey() + "/cancel");

			// Basic 인증 헤더
			String auth = apiConfig.getSecretKey() + ":";
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			String authHeader = "Basic " + encodedAuth;

			// 요청 데이터
			String json = String.format("{\"cancelReason\":\"%s\"}", cancelReason);

			// HTTP 요청 생성
			HttpRequest request = HttpRequest.newBuilder().uri(uri).header("Authorization", authHeader).header("Content-Type", "application/json")
					.header("Idempotency-Key", apiConfig.getIdempotencyKey()).POST(HttpRequest.BodyPublishers.ofString(json)).build();

			// HTTP 요청 보내기
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// 응답 출력
			System.out.println("Response Code: " + response.statusCode());
			System.out.println("Response Body: " + response.body());

			// if (response.statusCode() == 200) {
			payment.setCancelAmount(payment.getTotalAmount());
			payment.setCancelReason(cancelReason);
			paymentService.updatePayment(payment);
			// } else {
			// 
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/payment";
	}

}