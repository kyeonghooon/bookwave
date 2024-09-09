<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../layout/header.jsp"%>
<meta charset="UTF-8">
<title>아이디 및 비밀번호 찾기</title>
<link rel="stylesheet" href="/css/findLogin.css" type="text/css" />



<main class="container_wrapper">
    <section class="contents_wrap login">
        <div class="login_form_wrap">
            <!-- Tab Navigation -->
            <div class="tab_list_wrap">
                <ul class="tabs" role="tablist">
                    <li class="tab_item active" role="tab" id="findIdTab">
                        <span class="tab_text">아이디 찾기</span>
                    </li>
                    <li class="tab_item" role="tab" id="findPwTab">
                        <span class="tab_text">비밀번호 찾기</span>
                    </li>
                </ul>
            </div>

            <!-- Form Content -->
            <div class="tab_content active" id="tabContentFindId">
                <form id="findIdForm" action="/user/find-id" method="post">
                    <div class="form-group">
                        <input type="text" name="name" id="name" placeholder="이름" required>
                    </div>
                    <div class="form-group">
                        <input type="email" name="email" id="email" placeholder="이메일" required>
                    </div>
                    <div class="btn_wrap">
                        <button type="submit">아이디 찾기</button>
                    </div>
                </form>
            </div>
            <div class="result-box">
</div>

            <div class="tab_content" id="tabContentFindPw">
                <form id="findPwForm" action="/user/find-password" method="post">
                    <div class="form-group">
                        <input type="text" name="loginId" id="loginId" placeholder="아이디" required>
                    </div>
                    <div class="form-group">
                        <input type="email" name="email" id="email" placeholder="이메일" required>
                    </div>
                    <div class="btn_wrap">
                        <button type="submit">비밀번호 찾기</button>
                    </div>
                </form>
                
            </div>
        </div>
    </section>
</main>

<script>
    // Tab switching functionality
    document.getElementById('findIdTab').addEventListener('click', function() {
        switchTab('tabContentFindId', 'findIdTab');
    });

    document.getElementById('findPwTab').addEventListener('click', function() {
        switchTab('tabContentFindPw', 'findPwTab');
    });

    function switchTab(contentId, tabId) {
        // Hide all tab contents
        document.querySelectorAll('.tab_content').forEach(function(content) {
            content.classList.remove('active');
        });
        // Remove active class from all tabs
        document.querySelectorAll('.tab_item').forEach(function(tab) {
            tab.classList.remove('active');
        });
        // Show selected tab content and add active class to the clicked tab
        document.getElementById(contentId).classList.add('active');
        document.getElementById(tabId).classList.add('active');
    }
</script>

<%@ include file="../layout/footer.jsp"%>
