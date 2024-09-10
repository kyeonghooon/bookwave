// 아이디 (유효성, 중복확인)
const resultUid = document.querySelector(".result--uid"); // 에러 메세지(아이디)
const btnId = document.querySelector(".check--id"); // 중복체크 버튼
const inputId = document.querySelector(".loginId"); // 아이디
const btnSubmit = document.getElementById("btn"); // 회원가입 버튼

const reUid = /^[a-z]+[a-z0-9]{5,12}$/;
if (btnId) {
	btnId.addEventListener("click", function() {
		const inputId = loginId.value.trim();
		console.log(inputId);
		if (!reUid.test(inputId)) {
			resultUid.textContent = "아이디는 6-12자의 영문 소문자 및 숫자만 사용 가능합니다.";
			resultUid.style.color = 'red';
			return;
		}
		const url = "/controller-user/check-userId?loginId=" + encodeURIComponent(inputId);
		console.log("url", url);
		fetch(url).then((response) => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		}).then((isUse) => {
			console.log("결과 확인", isUse);
			if (isUse) {
				resultUid.textContent = "사용 가능한 ID입니다.";
				resultUid.style.color = 'green';
			} else {
				resultUid.textContent = "중복된 ID입니다";
				resultUid.style.color = 'red';
				return;
			}
		}).catch((error) => {
			console.error("에러 발생", error);
			resultUid.textContent = "잘못된 요청입니다.";
			resultUid.style.color = 'red';
		});
	});
}



// 비밀번호 체크 (유효성 검사, 비밀번호 확인)
document.addEventListener('DOMContentLoaded', function() {
	const passwordInput = document.getElementById('password');
	const confirmPasswordInput = document.getElementById('pwcheck');
	const passwordCheckMessage = document.querySelector('.result--upw');
	const confirmPasswordCheckMessage = document.querySelector('.result--pw');

	// 비밀번호 정규식 (최소 8자 이상, 특수문자 포함)
	const passwordRegex = /^(?=.*[\W_]).{8,}$/;

	function validatePasswordStrength(password) {
		if (password.length === 0) {
			return '';
		}

		if (!passwordRegex.test(password)) {
			return '비밀번호는 최소 8자 이상이어야 하며, 특수문자를 포함해야 합니다.';
		}

		return '비밀번호가 유효합니다.';
	}

	function checkPasswordsMatch() {
		const password = passwordInput.value;
		const confirmPassword = confirmPasswordInput.value;
		const strengthMessage = validatePasswordStrength(password);

		if (strengthMessage !== '비밀번호가 유효합니다.') {
			passwordCheckMessage.textContent = strengthMessage;
			passwordCheckMessage.style.color = 'red';
			passwordInput.style.borderColor = 'red';
			confirmPasswordInput.style.borderColor = '';
			return;
		} else {
			passwordCheckMessage.textContent = strengthMessage;
			passwordCheckMessage.style.color = 'green';
			passwordInput.style.borderColor = 'green';
		}

		if (confirmPassword === '') {
			confirmPasswordCheckMessage.textContent = '';
			confirmPasswordInput.style.borderColor = '';
			return;
		}

		if (password !== confirmPassword) {
			confirmPasswordCheckMessage.textContent = '비밀번호가 일치하지 않습니다.';
			confirmPasswordCheckMessage.style.color = 'red';
			confirmPasswordInput.style.borderColor = 'red';
		} else {
			confirmPasswordCheckMessage.textContent = '비밀번호가 일치합니다.';
			confirmPasswordCheckMessage.style.color = 'green';
			confirmPasswordInput.style.borderColor = 'green';
		}
	}

	passwordInput.addEventListener('input', checkPasswordsMatch);
	confirmPasswordInput.addEventListener('input', checkPasswordsMatch);
});


// 유효성 검사 (이름, 번호, 생년월일, 주소)
document.addEventListener("DOMContentLoaded", function() {
	const form = document.querySelector("form");

	form.addEventListener("submit", function(event) {
		// Name validation
		const name = document.getElementById("name").value.trim();
		const nameRegex = /^[가-힣a-zA-Z]+$/; // 이름 정규식
		if (!name) {
			alert("이름을 입력해주세요.");
			event.preventDefault();
			return;
		}
		if (!nameRegex.test(name)) {
			alert("이름은 한글 또는 영어로만 입력 가능합니다.");
			event.preventDefault();
			return;
		}

		// Phone number validation
		const phone2 = document.querySelector("input[name='phone2']").value.trim();
		const phone3 = document.querySelector("input[name='phone3']").value.trim();
		if (!phone2 || !/^\d{4}$/.test(phone2)) {
			alert("유효한 전화번호를 입력해주세요.");
			event.preventDefault();
			return;
		}
		if (!phone3 || !/^\d{4}$/.test(phone3)) {
			alert("유효한 전화번호를 입력해주세요.");
			event.preventDefault();
			return;
		}

		// Birthdate validation
		const year = document.querySelector("select[name='year']").value;
		const month = document.querySelector("select[name='month']").value;
		const day = document.querySelector("select[name='day']").value;
		if (year === "년도" || month === "월" || day === "일") {
			alert("생년월일을 올바르게 입력해주세요.");
			event.preventDefault();
			return;
		}

		// Address validation
		const zip = document.getElementById("zip").value.trim();
		const addr1 = document.getElementById("addr1").value.trim();
		if (!zip || !addr1) {
			alert("주소를 입력해주세요.");
			event.preventDefault();
			return;
		}

		// Email validation
		const email1 = document.getElementById("email").value.trim();
		const email2 = document.querySelector("select[name='email2']").value;
		if (!email1 || !validateEmail(email1 + "@" + email2)) {
			alert("유효한 이메일을 입력해주세요.");
			event.preventDefault();
			return;
		}
	});

	// Email validation helper function
	function validateEmail(email) {
		const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return re.test(email);
	}
});

// 이메일 관련 기능
document.addEventListener("DOMContentLoaded", function() {
	const sendEmailBtn = document.getElementById('send--email--verification'); // 이메일 전송 버튼

	sendEmailBtn.addEventListener('click', function() {
		const email = document.getElementById('email').value + "@" + document.querySelector('select[name="email2"]').value;
  		sendEmailBtn.innerText = "전송중";
		// 이메일 인증 요청
		fetch(`/email/sendVerification?email=${encodeURIComponent(email)}`, {
			 method: 'POST'
		})
		.then(response => response.text())
		.then(data => {
			 // 카운트다운 시작
			 startCountdown();
		})
		.catch(error => console.error('Error:', error));
  });

  // 카운트다운 시작 함수
function startCountdown() {
	sendEmailBtn.innerText = "대기중";
	let countdown = 300; // 5분 (300초)
	const statusElement = document.getElementById('email--verification--status');
	statusElement.innerText = "5분 내에 인증을 완료해주세요.";
	
	const interval = setInterval(function() {
		 countdown--;
		 statusElement.innerText = `남은 시간: ${countdown}초`;
		 
		 if (countdown <= 0) {
			  clearInterval(interval);
			  statusElement.innerText = "인증 시간이 만료되었습니다.";
			  sendEmailBtn.innerText = "재전송";
		 }
	}, 1000);

	// 웹소켓을 통해 인증 결과 대기
	const socket = new SockJS('/ws');
	const stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		 stompClient.subscribe('/topic/verify', function (message) {
			  if (message.body === 'ok') {
					clearInterval(interval); // 카운트다운 중지
					statusElement.innerText = "이메일 인증이 완료되었습니다.";
					sendEmailBtn.innerText = "완료";
					document.getElementById("send--email--verification").disabled = true; // 버튼 비활성화
					alert('이메일 인증 성공!');
					// TODO 인증 완료시 boolean 변수 처리
			  } else {
					statusElement.innerText = "인증 실패. 다시 시도해주세요.";
			  }
		 });
	});
}
});










