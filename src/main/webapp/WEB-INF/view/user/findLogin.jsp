<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="UTF-8" />
<title>아이디 및 비밀번호 찾기</title>
<link rel="stylesheet" href="/css/findLogin.css" type="text/css" />

<main class="container--wrapper">
  <section class="contents--wrap login">
    <div class="login_form_wrap">
      <!-- Tab Navigation -->
      <div class="tab--list--wrap">
        <ul class="tabs" role="tablist">
          <li class="tab--item active" role="tab" id="findIdTab">
            <span class="tab--text">아이디 찾기</span>
          </li>
          <li class="tab--item" role="tab" id="findPwTab">
            <span class="tab--text">비밀번호 찾기</span>
          </li>
        </ul>
      </div>

      <!-- Form Content -->
      <div class="tab--content active" id="tabContentFindId">
        <form id="findIdForm" action="/user/find-id" method="post">
          <div class="form--group">
            <input
              type="email"
              name="email"
              id="email"
              placeholder="이메일"
              required
            />
          </div>
          <div class="btn--wrap">
            <button type="submit">아이디 찾기</button>
          </div>
        </form>
      </div>
      <div class="result--box"></div>

      <div class="tab--content" id="tabContentFindPw">
        <form id="findPwForm" action="/user/find-password" method="post">
          <div class="form--group">
            <input
              type="text"
              name="loginId"
              id="loginId"
              placeholder="아이디"
              required
            />
          </div>
          <div class="form--group">
            <input
              type="email"
              name="email"
              id="email"
              placeholder="이메일"
              required
            />
          </div>
          <div class="btn--wrap">
            <button type="submit">비밀번호 찾기</button>
          </div>
        </form>
      </div>
    </div>
  </section>
</main>

<script>
  const type = `${type}`;
</script>
<script src="/js/find-login.js"></script>
