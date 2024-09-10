<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../layout/header.jsp"%>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" href="/css/signUp.css" type="text/css" />

<div class="d-flex justify-content-center mt-3">
	<div class="signup--container">
		<h2>회원가입</h2>
		<form action="/user/sign-up" method="post" class="d-flex flex-column align-items-center">
			<div class="form--group">
				<c:choose>
					<c:when test="${socialId != null }">
						<label for="loginId">아이디</label>
						<input style="width: 81%" type="text" class="loginId" placeholder="아이디 입력" id="loginId" name="loginId" maxlength="12" readonly value="${socialId}" required />
						<input type="hidden" name="socialId" value="${socialId}">
					</c:when>
					<c:otherwise>
						<label for="loginId">아이디</label>
						<input style="width: 81%" type="text" class="loginId" placeholder="아이디 입력" id="loginId" name="loginId" maxlength="12" required />
						<button type="button" class="check--id" id="check--id">중복체크</button>
						<p class="result--uid"></p>
					</c:otherwise>
				</c:choose>

			</div>

			<div class="form--group">
				<c:choose>
					<c:when test="${socialId != null }">
						<label for="password" style="display: none;">비밀번호</label>
						<input type="password" class="form--control" placeholder="비밀번호 입력" id="password" name="password" style="display: none;" readonly value="${password}" required />
						<input type="hidden" name="password" value="${password}">
					</c:when>
					<c:otherwise>
						<label for="password">비밀번호</label>
						<input type="password" class="form--control" placeholder="비밀번호 입력" id="password" name="password" required />
						<p class="result-upw"></p>

						<div class="form--group">
							<label for="pwcheck">비밀번호 확인</label> <input type="password" class="form--control" placeholder="비밀번호 재입력" id="pwcheck" name="pwcheck" required />
							<p class="result--pw"></p>
						</div>
					</c:otherwise>
				</c:choose>

			</div>


			<div class="form--group">
				<label for="name">이름</label> <input type="text" class="form--control" placeholder=" 이름을 입력해주세요" id="name" name="name" required />
			</div>
			<br />
			<div class="form--group2">
				<label for="phone">전화번호 </label>

				<div class="phone">
					<select name="phone1">
						<option>010</option>
						<option>02</option>
						<option>031</option>
						<option>051</option>
					</select>
					- <input type="text" name="phone2" size="5" required maxlength="4" /> - <input type="text" name="phone3" size="5" required maxlength="4" />
				</div>
			</div>


			<!-- 생년월일 입력 필드 추가 -->
			<div class="form--group2">
				<label for="birthDate">생년월일:</label>
				<div class="birthDate">
					<select name="year" required>
						<option>년도</option>
						<%
						for (int i = 1920; i <= 2024; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
						}
						%>
					</select>
					<select name="month" required style="margin: 5px">
						<option>월</option>
						<%
						for (int i = 1; i <= 12; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
						}
						%>
					</select>
					<select name="day" required style="margin: 5px">
						<option>일</option>
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

			<div class="form--group">
				<label for="zip">주소:</label> <input type="text" id="zip" style="width: 79%;" placeholder="우편번호" name="zip" readonly="readonly">
				<button type="button" class="btn btn-default" style="width: 19%" onclick="execPostCode();">
					<i class="fa fa-search"></i> 우편번호 찾기
				</button>
				<br>

			</div>
			<div class="form--group">
				<input class="form-control" style="top: 5px;" placeholder="도로명 주소" name="addr1" id="addr1" type="text" readonly="readonly" />
			</div>
			<div class="form--group">
				<input type="text" id="addr2" placeholder="상세주소" name="addr2">
			</div>

			<div class="d-flex w-100">
				<div class="form--group2">
					<label for="email">이메일:</label> <input type="text" class="form--control" placeholder=" email" id="email" name="email1" required /> <span id="email2"
						class="input--group--text">@</span>
					<select name="email2">
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="nate.com">nate.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="daum.net">daum.net</option>
					</select>
				</div>
				<p id="email--verification--status"></p>
				<button type="button" id="send--email--verification" class="btn btn-primary">인증번호 전송</button>
			</div>
			<div class="p-2 d-flex justify-content-center">
				<input class="check" type="radio" name="gender" value="false" checked /> <span class="d-flex align-items-center ml-1 mr-1"> 남자 </span> <input class="check ml-3" type="radio"
					name="gender" value="true" /> <span class="d-flex align-items-center ml-1 mr-1"> 여자 </span>
			</div>

			<div>
				<button type="submit" id="btn">회원가입</button>
			</div>
		</form>
	</div>
</div>
<script src="/js/signup.js"></script>
<script src="/js/address.js"></script>
<%@ include file="../layout/footer.jsp"%>