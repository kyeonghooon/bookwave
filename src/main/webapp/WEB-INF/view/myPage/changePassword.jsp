<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>비밀번호 변경하기</title>
<script src="/js/change-password.js"></script>
<link href="/css/change-password.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container">
    <h2>비밀번호 변경하기</h2>
    
    <p>현재 사용하고 계신 비밀번호를 먼저 입력하신 후, 아래에 변경할 새 비밀번호를 입력해주세요</p>
    <p>비밀번호는 최소 8자 이상이어야 하며, 특수문자를 포함해야 합니다.</p>

    <form id="passwordChangeForm" method="post" onsubmit="return false">
        <label for="currentPassword" class="currentPassword" >기존 비밀번호:</label>
        <input type="password" id="currentPassword" name="currentPassword" required>
        <p class="result-current-password"></p>
        
        <label for="newPassword">새 비밀번호:</label>
        <input type="password" id="newPassword" name="newPassword" required>
        <p class="result-upw"></p>

        <label for="confirmPassword">새 비밀번호 확인:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>
        <p class="result-pw"></p>

        <button type="submit" class="btn">비밀번호 변경하기</button>
    </form>
</div>


</body>
</html>
