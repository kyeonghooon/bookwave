<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/my-page-auth.css" rel="stylesheet" type="text/css">
</head>
<body>
		<div class="sidebar">
			<h3>나의 계정</h3>
			<a href="/user-info/mypageAuth">개인정보 수정</a> 
			<a href="/user-info/pointHistory">포인트 내역 조회</a> 
			<a href="/user-info/paymentHistory">결제 내역</a> 
			<a class="delete-btn" onclick="window.open('/user-info/deleteAccount', '_blank', 'width=800,height=500,resizable=no')">회원탈퇴</a>
		</div>
		
		<div class="content">
			<h2>비밀번호 재확인</h2>
			<p>- 개인정보 보호를 위해 비밀번호를 다시 확인합니다.</p>


	<div class="container">
		<form action="/user-info/mypageAuth" method="post">
			<div class="form-group">
				<label for="id">아이디</label> <input type="text" id="id" name="id" value="${user.loginId}" readonly disabled="disabled">
			</div>

			<div class="form-group">
				<label for="password">비밀번호</label> <input type="password" id="password" name="password" required>
			</div>

			<button type="submit" class="btn">확인</button>

			<div class="forgot-password">
				<a href="find_password.jsp">비밀번호 찾기</a>
			</div>
		</form>
		</div>
	</div>
</body>
</html>