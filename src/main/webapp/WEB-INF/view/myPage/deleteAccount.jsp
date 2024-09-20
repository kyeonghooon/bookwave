<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link rel="stylesheet" href="/css/delete-account.css" type="text/css" />
<head>
    <meta charset="UTF-8">
    <title>회원 탈퇴 확인</title>
</head>
<body>
    <div class="content">
        <h2>회원 탈퇴 확인</h2>
        <p>회원 탈퇴를 진행하시려면 비밀번호를 입력해 주세요. 이 작업은 되돌릴 수 없습니다.</p>

        <div class="container">
            <form id="deleteAccountForm" action="/user-info/deleteAccount" method="post" onsubmit="return handleFormSubmit(event)">
                <div class="form-group">
                    <label for="password">비밀번호</label> 
                    <input type="password" id="password" name="password" required>
                </div>
                
                <button type="submit" class="btn">탈퇴하기</button>
                <a class="btn cancel-btn" onclick='window.close()'>취소</a>
            </form>
        </div>
    </div>

    <script>
        function handleFormSubmit(event) {
            event.preventDefault(); // Prevent the default form submission

            // Get form data
            const formData = new FormData(document.getElementById('deleteAccountForm'));

            // Send form data using Fetch API
            fetch('/user-info/deleteAccount', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(text => {
                if (text.includes('탈퇴가 완료되었습니다')) {
                    alert('탈퇴가 완료되었습니다.');
                    window.close();
                } else {
                    alert('비밀번호와 일치 하지않습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('문제가 발생했습니다. 다시 시도해 주세요.');
            });

            return false; // Prevent the form from submitting the traditional way
        }
    </script>
</body>
</html>
