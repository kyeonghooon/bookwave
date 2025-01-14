<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Book Wave</title>
<!-- 부트스트랩 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" />
<!-- jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- 커스텀 CSS -->
<link rel="stylesheet" href="/css/common.css" />
<link href="/img/favicon.ico" rel="icon">

</head>
<body>
	<header>
		<nav class="navbar navbar-expand-lg navbar-light d-flex justify-content-between">
			<a class="navbar-brand" href="/"> <img class="img--logo" src="/img/logo_bookwave4.png" alt="Book Wave Logo"></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
				<ul class="navbar-nav mr-2">
					<li class="nav-item "><a class="nav-link" href="/">Home ${homeUrl}<span class="sr-only">(current)</span></a></li>
					<li class="nav-item "><a class="nav-link" href="/introduce">소개</a></li>
					<li class="nav-item dropdown "><a class="nav-link dropdown-toggle" href="/book/list" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 도서 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="/book/list">도서 목록</a>
						</div></li>
					<li class="nav-item dropdown "><a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 내 서재 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="/my-library/my-lend">대출 도서 목록</a> 
							<a class="dropdown-item" href="/my-reserved/list">예약 도서 목록</a> 
							<a class="dropdown-item" href="/my-favorite/list">관심 도서 목록</a> 
							<a class="dropdown-item" href="/review/list">내 리뷰</a> 
							<a class="dropdown-item" href="/history/list">읽은 내역 통계</a>
						</div></li>
					<li class="nav-item"><a class="nav-link" href="/ebook">eBook</a></li>
					<li class="nav-item dropdown "><a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 시설 이용 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="/facility/computer">pc 이용</a> 
							<a class="dropdown-item" href="/facility/printer">프린트 이용</a> 
						</div></li>
					<li class="nav-item"><a class="nav-link" href="/payment/charge" onclick="window.open(this.href, '_blank', 'width=800, height=760'); return false">충전하기</a></li>
				</ul>
				<ul class="navbar-nav personal--active ml-1">
					<c:choose>
						<c:when test="${principal != null}">
							<c:if test="${!principal.subscribe}">
								<li class="nav-item"><a class="nav-link" href="#" id="subscribeBtn">구독하기</a></li>
							</c:if>
							<li class="nav-item"><a class="nav-link" href="/user-info/mypageAuth">마이 페이지</a></li>
							<li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="/user/sign-in">로그인</a></li>
							<li class="nav-item"><a class="nav-link" href="/user/sign-up">회원가입</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</nav>
	</header>