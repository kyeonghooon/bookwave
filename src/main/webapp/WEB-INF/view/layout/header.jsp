<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>My Web Application</title>
<!-- 부트스트랩 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" />
<!-- jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- 커스텀 CSS -->
<link rel="stylesheet" href="/css/common.css" />

</head>
<body>
	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">Navbar</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavDropdown">
				<ul class="navbar-nav">
					<li class="nav-item active"><a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a></li>
					<li class="nav-item"><a class="nav-link" href="#">소개</a></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 도서
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="#">도서 목록</a>
						</div></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 내
							서재 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" href="#">대출 도서 목록</a> <a class="dropdown-item" href="#">예약 도서 목록</a> <a class="dropdown-item" href="#">관심 도서 목록</a> <a class="dropdown-item" href="#">내 리뷰</a> <a
								class="dropdown-item" href="#">읽은 내역 통계</a>
						</div></li>
					<li class="nav-item"><a class="nav-link" href="#">eBook</a></li>
					<li class="nav-item"><a class="nav-link" href="/payment/charge" onclick="window.open(this.href, '_blank', 'width=800, height=760'); return false">충전하기</a></li>
					<li class="nav-item"><a class="nav-link" href="#">로그인</a></li>
					<li class="nav-item"><a class="nav-link" href="#">회원가입</a></li>
					<li class="nav-item"><a class="nav-link" href="#">로그아웃</a></li>
				</ul>
			</div>
		</nav>
	</header>