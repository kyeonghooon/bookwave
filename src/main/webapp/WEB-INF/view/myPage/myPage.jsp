<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="/css/my-page.css" rel="stylesheet" type="text/css">
<script src="//cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="/js/mypage.js"></script>
<script src="/js/change-email.js"></script>
</head>
<body>
	<!-- 사이드바 추가 -->
	<div class="sidebar">
		<h3>나의 계정</h3>
		<a href="/user-info/mypage">개인정보 수정</a> <a href="/user-info/pointHistory">포인트 내역 조회</a> <a href="/user-info/paymentHistory">결제 내역</a> 
		<a class="delete-btn" onclick="window.open('/user-info/deleteAccount', '_blank', 'width=800,height=500,resizable=no')">회원탈퇴</a>
	</div>

	<!-- 콘텐츠 영역 -->
	<div class="content">
		<h2>개인정보 수정</h2>

		<table>
			<tr>
				<th>이름</th>
				<td>${user.name}</td>
			</tr>
			<tr>
    <th>이메일 주소</th>
  <td>
    <form id="emailChangeForm" onsubmit="return false;">
        <span id="currentEmail">${user.email}</span>
        <div id="changeEmailSection" style="display: none;">
            <input type="email" name="email" id="newEmail" placeholder="새 이메일 주소" required>
            <button class="btn" id="emailAuthBtn" type="button" onclick="sendEmailVerification();">이메일 인증</button>
            <button class="btn" type="button" onclick="cancelEmailChange();">취소</button>
        </div>
        <button class="btn" id="changeEmailBtn" type="button" onclick="showChangeEmailSection();">변경하기</button>
    </form>
</td>

			<tr>
				<th>주소</th>
				<td>
					<form id="addressChangeForm" action="/user-info/changeAddress" method="post">
						<input class = "zip" data-user-zip="${user.zip}" name="zip" type="text" id="zip" style="width: 20%; border-width: 0;" value="(${user.zip})" readonly>
						<button class="btn" id="findAddressBtn" type="button" onclick="execPostCode();" style="display: none;">우편번호 찾기</button>
						<br> 
						<input class = "addr1" data-user-addr1="${user.addr1}"name="addr1" type="text" id="addr1" style="width: 100%; border-width: 0;" value="${user.addr1}" readonly><br> 
						<input class = "addr2" data-user-addr2="${user.addr2}"name="addr2" type="text" id="addr2" style="width: 100%; border-width: 0;" value="${user.addr2}" readonly><br>

						<button class="btn" id="btnAddress" type="button" onclick="changeAddress();" style="display: inline-block;">변경하기</button>
						<button id="saveAddress" class="btn" type="submit" style="display: none;">저장</button>
						<button id="cancelAddress" class="btn" type="button" style="display: none;" onclick="cancelAddress();">취소</button>
					</form>
				</td>

			</tr>
			<tr>
				<th>휴대전화</th>
				<td>
					<form action="/user-info/changePhone" method="post">
						<input class = "phone" data-user-phone="${user.phone}" name="phone" type="text" id="phone" style="width: 10%; border-width: 0;" value="${user.phone}" readonly oninput="formatPhoneNumber()">
						<button class="btn" id="btnPhone" type="button" onclick="changePhone();" style="display: inline-block;">변경하기</button>
						<button id="savePhone" class="btn" type="submit" style="display: none;">저장</button>
						<button id="cancelPhone" class="btn" type="button" style="display: none;" onclick="cancelChanges();">취소</button>
					</form>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>${user.birthDate}</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<button class="btn" onclick="window.open('/user-info/changePassword', '_blank', 'width=800,height=500,resizable=no')">비밀번호 변경하기</button>
				</td>
			</tr>
		</table>
	</div>
</body>
<script src="/js/address.js"></script>

<script>
	
</script>
</html>
