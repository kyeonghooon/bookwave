document.addEventListener('DOMContentLoaded', function() {
    const currentPasswordInput = document.getElementById('currentPassword');
    const passwordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const currentPasswordMessage = document.querySelector('.result-current-password');
    const passwordCheckMessage = document.querySelector('.result-upw');
    const confirmPasswordCheckMessage = document.querySelector('.result-pw');
	const changePasswordBtn = document.querySelector('.btn');
    const passwordRegex = /^(?=.*[\W_]).{8,}$/;
	
	
	// 새 비밀번호 유효성 검사
    function validatePasswordStrength(password) {
        if (password.length === 0) {
            return '';
        }
        if (/\s/.test(password)) { 
            return '비밀번호에는 공백이 포함될 수 없습니다.';
        }
        if (!passwordRegex.test(password)) {
            return '비밀번호는 최소 8자 이상이어야 하며, 특수문자를 포함해야 합니다.';
        }

        return '비밀번호가 유효합니다.';
    }
	
	// 비밀번호 확인 비교
    function checkPasswordsMatch() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        const strengthMessage = validatePasswordStrength(password);
        let isValid = true;

        if (strengthMessage !== '비밀번호가 유효합니다.') {
            passwordCheckMessage.textContent = strengthMessage;
            passwordCheckMessage.style.color = 'red';
            passwordInput.style.borderColor = 'red';
            confirmPasswordInput.style.borderColor = '';
            isValid = false;
        } else {
            passwordCheckMessage.textContent = strengthMessage;
            passwordCheckMessage.style.color = 'green';
            passwordInput.style.borderColor = 'green';
        }

        if (confirmPassword === '') {
            confirmPasswordCheckMessage.textContent = '';
            confirmPasswordInput.style.borderColor = '';
        } else if (password !== confirmPassword) {
            confirmPasswordCheckMessage.textContent = '비밀번호가 일치하지 않습니다.';
            confirmPasswordCheckMessage.style.color = 'red';
            confirmPasswordInput.style.borderColor = 'red';
            isValid = false;
        } else {
            confirmPasswordCheckMessage.textContent = '비밀번호가 일치합니다.';
            confirmPasswordCheckMessage.style.color = 'green';
            confirmPasswordInput.style.borderColor = 'green';
        }

        return isValid;
    }
    
    // 비밀번호 변경 요청
    function changePassword(){
		const currentPassword = currentPasswordInput.value;
		const newPassword = passwordInput.value;
		fetch(`/user-info/changePassword`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ currentPassword: currentPassword, newPassword: newPassword }),
		})
			.then((response) => response.json())
			.then((data) => {
				if (data.success) {
					// 성공 시 확인창 표시
					alert(data.message);
					window.close();
				} else {
					// 실패 시 오류 메시지
					alert(data.message);
					currentPasswordInput.innerText = "";
				}
			})
			.catch((error) => {
				console.error("Error:", error);
				alert("알수 없는 오류 발생");
			});
	}

    passwordInput.addEventListener('input', checkPasswordsMatch);
    confirmPasswordInput.addEventListener('input', checkPasswordsMatch);
    changePasswordBtn.addEventListener('click', changePassword);
    
});
