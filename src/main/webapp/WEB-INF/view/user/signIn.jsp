<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../layout/header.jsp"%>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="/css/signIn.css" type="text/css" />


    <main class="container--wrapper">
        <section class="contents--wrap login">
            <div class="login--form--wrap">
				<form action="/user/sign-in" method = "post">

                <!-- 로그인 -->
                <div class="form_col_group valid_check">
                    <div class="col_box id">
                        <input type="text" title="아이디 입력" class="form_ip" id="loginId" name="loginId" placeholder="아이디를 입력해 주세요." >
                        <!-- 아이디 상태값 -->
                        <span class="form_desc tip"></span>
                    </div>
                    <div class="col_box pw">
                        <div class="form_ip_pw">
                            <input type="password" class="form_ip" id="password" name="password" placeholder="비밀번호를 입력해 주세요." title="비밀번호 입력">
                    <span class="valid_desc"></span>
                         
                        </div>
                    </div>
                    <!-- 로그인 페이지의 경고 문구는 모두 여기에 표시됩니다.  -->
                </div>
                <!-- // 로그인 -->

                <div class="btn--wrap justify">
                    <button class="btn_lg btn_light_gray" type="submit" id="loginBtn" >
                       로그인
                    </button>
                </div>
                </form>

                <!-- 아이디 저장 -->
                <div class="save_id_box">
                    <span class="form_chk">
                        <input id="saveId" type="checkbox" >
                        <label for="saveId">아이디 저장</label>
                    </span>
                    <div class="right_area">
                        <a href="#" onclick="openWindow('id')"><span class="text btn_text_id_link">아이디 찾기</span></a>
                        <span class="gap">|</span>
                        <a href="#" onclick="openWindow('pw')"><span class="text btn_text_pw_link">비밀번호 찾기</span></a>
                    </div>
                </div>
                
                <!-- // 아이디 저장 -->

                <!-- SNS 로그인 -->
                <div class="sns--login--box">
                    <ul class="sns--login--list">
                        <li class="sns--login">
                                <div class="btn--sns--login--naver" >
                            <a href="/user/social?type=naver">
                            <img alt="네이버" src="/img/navericon.png" style="width: 50px; height: auto;">
                       
                            </a>
                        </div>
                        </li>
                        <li class="sns--login">
                               <div class="btn--sns--login--kakao" >
                            <a href="/user/social?type=kakao">
                             <img alt="카카오톡" src="/img/social_kakao.png" style="width: 50px; height: auto;">
                            </a>
                        </div>
                        </li>
                        <li class="sns--login">
                        <div class="btn--sns--login--google" >
                            <a href="/user/social?type=google">
                              <img alt="구글" src="/img/social_google.png" style="width: 50px; height: auto;">
                            </a>
                        </div>
                        </li>
                    </ul>
                </div>
                
    
                <!-- // SNS 로그인 -->

                <!-- 회원가입 -->
                <div class="btn--wrap justify">
                    <a href="/user/sign-up" class="btn_lg btn_line_primary" id="join">
                        <span class="text">회원가입</span>
                    </a>
                </div>
                <!-- // 회원가입 -->

            </div>
        </section>
    </main>
<%@ include file="../layout/footer.jsp"%>
<script src="/js/sign-in.js"></script>
