<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>회원가입</title>
<link rel="stylesheet" href="/css/signUp.css" type="text/css" />
</head>
<body>
	<div class="signup-container">
		<h2>회원가입</h2>

		<form action="/user/sign-up" method="post">
			<div class="form-group">
				<label for="loginId">아이디</label> <input style="width: 81%"
					type="text" class="loginId" placeholder="아이디 입력" id="loginId"
					name="loginId" maxlength="12" required />

				<button type="button" class="check-id">중복체크</button>
				<p class="result-uid"></p>
			</div>
			<div class="form-group">
				<label for="password">비밀번호</label> <input type="password"
					class="form-control" placeholder="비밀번호 입력" id="password"
					name="password" required />
			</div>
			
			<div class="form-group">
				<label for="password-check">비밀번호 확인</label> <input type="password"
					class="form-control" placeholder="비밀번호 재입력" id="password-check"
					name="password-check" required />
			</div>

			<div class="form-group">
				<label for="name">이름</label> <input type="text"
					class="form-control" placeholder=" 이름을 입력해주세요" id="name" name="name"
					required />
			</div>
			<br />
			<div class="form-group2">
				<label for="phone">전화번호 </label>

				<div class="phone">
					<select name="phone1">
						<option>010</option>
						<option>02</option>
						<option>031</option>
						<option>051</option>
					</select> - <input type="text" name="phone2" size="5" required maxlength="4" />
					- <input type="text" name="phone3" size="5" required maxlength="4" />
				</div>
			</div>

		
				<!-- 생년월일 입력 필드 추가 -->
				<div class="form-group2">
					<label for="birthDate">생년월일:</label>
					<div class="birthDate">
						<select name="year" required >
							<option>년도</option>
							<%
							for (int i = 1900; i <= 2024; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
							}
							%>
						</select> <select name="month" required style="margin: 5px">
							<option >월</option>
							<%
							for (int i = 1; i <= 12; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
							}
							%>
						</select><select name="day" required style="margin: 5px " >
							<option >일</option>
							<%
							for (int i = 1; i <= 31; i++) {
							%>
							<option value="<%=i%>"><%=i%></option>
							<%
							}
							%>
						</select>
					</div>
				</div>



				<br />
				<div class="form-group">
					<label for="zip">주소(우편번호):</label> <input type="text"
						class="form-control" placeholder=" zip" id="zip" name="zip"
						required />
				</div>

				<br />
				<div class="form-group2">
					<label for="email">이메일:</label> <input type="text"
						class="form-control" placeholder=" email" id="email" name="email1"
						required /> <span id="email2" class="input-group-text">@</span>
					<select name="email2">
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="nate.com">nate.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="daum.net">daum.net</option>
					</select>
				</div>

				<br />
				<br /> <input class="check" type="radio" name="gender"
					value="false" checked />남자 <input class="check"
					type="radio" name="gender" value="true" />여자 <br /> <br />
				<br />

				<div>
					<button type="submit" class="btn">회원가입</button>
				</div>
			</form>
	</div>
</body>
<script src="/js/signup.js"></script>
</html>
