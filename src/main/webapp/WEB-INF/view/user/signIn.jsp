<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="/css/signIn.css" type="text/css" />
</head>
<body>
    <main class="container_wrapper">
        <section class="contents_wrap login">
            <div class="login_form_wrap">

                <!-- 로그인 -->
                <div class="form_col_group valid_check">
                    <div class="col_box id">
                        <input type="email" title="아이디 입력" class="form_ip" placeholder="아이디를 입력해 주세요." autocomplete="off">
                        <!-- 아이디 상태값 -->
                        <span class="form_desc tip"></span>
                    </div>
                    <div class="col_box pw">
                        <div class="form_ip_pw">
                            <input type="password" class="form_ip" placeholder="비밀번호를 입력해 주세요." title="비밀번호 입력">
                         
                        </div>
                    </div>
                    <!-- 로그인 페이지의 경고 문구는 모두 여기에 표시됩니다.  -->
                    <span class="valid_desc"></span>
                </div>
                <!-- // 로그인 -->

                <div class="btn_wrap justify">
                    <button class="btn_lg btn_light_gray" id="loginBtn" disabled>
                        <span class="text">로그인</span>
                    </button>
                </div>

                <!-- 아이디 저장 -->
                <div class="save_id_box">
                    <span class="form_chk">
                        <input id="formSaveId" type="checkbox" >
                        <label for="formSaveId">아이디 저장</label>
                    </span>
                    <div class="right_area">
                        <a href="#"><span class="text btn_text_id_link">아이디 찾기</span></a>
                        <span class="gap">|</span>
                        <a href="#"><span class="text btn_text_pw_link">비밀번호 찾기</span></a>
                    </div>
                </div>
                <!-- // 아이디 저장 -->

                <!-- SNS 로그인 -->
                <div class="sns_login_box">
                    <ul class="sns_login_list">
                        <li class="sns_login">
                            <button class="btn_sns_login naver">
                                <span class="hidden">네이버로그인</span>
                            </button>
                        </li>
                        <li class="sns_login">
                            <button class="btn_sns_login kakao">
                                <span class="hidden">카카오로그인</span>
                            </button>
                        </li>
                        <li class="sns_login">
                            <button class="btn_sns_login google">
                                <span class="hidden">구글 로그인</span>
                            </button>
                        </li>
                    </ul>
                </div>
                <!-- // SNS 로그인 -->

                <!-- 회원가입 -->
                <div class="btn_wrap justify">
                    <a href="/user/sign-up" class="btn_lg btn_line_primary" id="join">
                        <span class="text">회원가입</span>
                    </a>
                </div>
                <!-- // 회원가입 -->

            </div>
        </section>
    </main>
</body>
</html>
