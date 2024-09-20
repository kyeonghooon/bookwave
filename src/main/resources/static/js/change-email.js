document.addEventListener("DOMContentLoaded", function() {
	const emailInput = document.getElementById("newEmail");
	const emailAuthBtn = document.getElementById("emailAuthBtn");

	function sendEmailVerification() {
	const email = emailInput.value;
		if (!email) {
			alert("이메일을 입력해 주세요.");
			return;
		}

		emailAuthBtn.innerText = "인증 이메일 전송중...";
		emailAuthBtn.disabled = true; // 버튼 비활성화

		fetch('/user-info/change-email-verification', {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ "email": email }),
		})
			.then((response) => response.json())
			.then((data) => {
				if (data.success) {
					alert(data.message); // 성공 시 확인창 표시
					startCountdown();
				} else {
					alert(data.message || "이메일 전송에 실패했습니다.");
				}
			})
			.catch((error) => {
				console.error("에러 발생:", error);
				alert("요청 처리 중 오류가 발생했습니다.");
			});
	}

	function showChangeEmailSection() {
		document.getElementById('currentEmail').style.display = 'none';
		document.getElementById('changeEmailSection').style.display = 'block';
		document.getElementById('changeEmailBtn').style.display = 'none';
	}

	function cancelEmailChange() {
		document.getElementById('currentEmail').style.display = 'inline';
		document.getElementById('changeEmailSection').style.display = 'none';
		document.getElementById('changeEmailBtn').style.display = 'inline-block';
	}

	function startCountdown() {
		let countdown = 300; // 5분 (300초)

		const interval = setInterval(function() {
			countdown--;
			emailAuthBtn.innerText = `남은 시간: ${countdown}초`;

			if (countdown <= 0) {
				clearInterval(interval);
				emailAuthBtn.innerText = "재전송";
			}
		}, 1000);

		// 웹소켓을 통해 인증 결과 대기
		const socket = new SockJS('/ws');
		const stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			stompClient.subscribe('/topic/verify', function(message) {
				if (message.body === 'ok') {
					clearInterval(interval); // 카운트다운 중지
					emailAuthBtn.innerText = "완료";
					//alert('이메일 인증 성공!');
					// TODO 인증 완료시 boolean 변수 처리
					updateNewEmail();
				} else {
					emailAuthBtn.innerText = "인증 실패. 다시 시도해주세요.";
				}
			});
		});
	}

	function updateNewEmail() {
		const email = emailInput.value;
		fetch('/user-info/change-email', {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ "email": email }),
		})
			.then((response) => response.json())
			.then((data) => {
				if (data.success) {
					alert(data.message); // 성공 시 확인창 표시
					window.location.reload()
				} else {
					alert(data.message || "이메일 전송에 실패했습니다.");
					window.location.reload()
				}
			})
			.catch((error) => {
				console.error("에러 발생:", error);
				alert("요청 처리 중 오류가 발생했습니다.");
			});
	}
	emailAuthBtn.addEventListener('click',sendEmailVerification);

});
