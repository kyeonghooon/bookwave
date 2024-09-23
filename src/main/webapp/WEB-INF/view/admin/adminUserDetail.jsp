<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>BookWave - UserDetail</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">
<link href="/css/admin-page.css" rel="stylesheet">
<link href="/img/favicon.ico" rel="icon">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>
/* 테이블 기본 스타일 */
table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
	font-size: 18px;
	text-align: left;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 12px 15px;
}

th {
	background-color: #f4f4f4;
	font-weight: bold;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

/* 페이지 배경 및 전체 레이아웃 */
body {
	font-family: 'Nunito', sans-serif;
	background-color: #f8f9fc;
	color: #333;
}

.container {
	max-width: 900px;
	margin: 40px auto;
	padding: 20px;
	background-color: #ffffff;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

h1 {
	text-align: center;
	margin-bottom: 20px;
	color: #4e73df;
}
</style>
</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a class="sidebar-brand d-flex align-items-center justify-content-center" href="/admin/main">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3">관리자 페이지</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- Nav Item - Dashboard -->
			<li class="nav-item"><a class="nav-link" href="/admin/main">
					<i class="fas fa-fw fa-tachometer-alt"></i> <span>대시보드</span>
				</a></li>
			<li class="nav-item active"><a class="nav-link" href="/admin/user">
					<i class="fas fa-fw fa-table"></i> <span>유저 관리</span>
				</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/book">
					<i class="fas fa-fw fa-table"></i> <span>도서 관리</span>
				</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/payment">
					<i class="fas fa-fw fa-table"></i> <span>결제 관리</span>
				</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/lend">
					<i class="fas fa-fw fa-table"></i> <span>대출 현황</span>
				</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/printer">
					<i class="fas fa-fw fa-wrench"></i> <span>프린트 요청</span>
				</a></li>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
					<i class="fas fa-fw fa-cog"></i> <span>고객 지원</span>
				</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<a class="collapse-item" href="/support/faq">FAQ 관리</a>
						<a class="collapse-item" href="/support/qna">1:1 문의 관리</a>
					</div>
				</div></li>

			<!-- Divider -->
			<hr class="sidebar-divider">

			<!-- Sidebar Toggler (Sidebar) -->
			<div class="text-center d-none d-md-inline">
				<button class="rounded-circle border-0" id="sidebarToggle"></button>
			</div>
		</ul>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
					<a class="navbar-brand" href="/admin/main">
						<img class="img--logo" src="/img/logo_bookwave4.png" alt="Book Wave Logo" style="height: 40px;">
					</a>
					<ul class="navbar-nav ml-auto">
						<li class="nav-item"><a class="nav-link logout-btn" href="/user/logout">
								<i class="fas fa-sign-out-alt"></i> <span>로그아웃</span>
							</a></li>
					</ul>
				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">
					<div class="container">
						<table>
							<tr>
								<th>유저 ID</th>
								<td>${user.userId}</td>
							</tr>
							<tr>
								<th>로그인 ID</th>
								<td>${user.loginId}</td>
							</tr>
							<tr>
								<th>소셜 ID</th>
								<td>${user.socialId}</td>
							</tr>
							<tr>
								<th>이름</th>
								<td>${user.name}</td>
							</tr>
							<tr>
								<th>역할</th>
								<td>${user.role}</td>
							</tr>
							<tr>
								<th>상태</th>
								<c:choose>
									<c:when test="${user.status == 0}">
										<td>정상</td>
									</c:when>
									<c:when test="${user.status == 1}">
										<td>탈퇴예정</td>
									</c:when>
									<c:otherwise>
										<td>탈퇴</td>
									</c:otherwise>

								</c:choose>
							</tr>
							<tr>
								<th>생성일</th>
								<td><fmt:formatDate value="${user.createdAt}" type="both" /></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td>${user.email}</td>
							</tr>
							<tr>
								<th>생일</th>
								<td>${user.birthDate}</td>
							</tr>
							<tr>
								<th>성별</th>
								<td>${user.gender == false ? '남성' : '여성'}</td>
							</tr>
							<tr>
								<th>휴대폰 번호</th>
								<td>${user.phone}</td>
							</tr>
							<tr>
								<th>우편번호</th>
								<td>${user.zip}</td>
							</tr>
							<tr>
								<th>주소</th>
								<td>${user.addr1}<br>${user.addr2}</td>
							</tr>
							<tr>
								<th>웨이브</th>
								<td>${user.wave}</td>
							</tr>
							<tr>
								<th>마일리지</th>
								<td>${user.mileage}</td>
							</tr>
							<tr>
								<th>구독</th>
								<c:choose>
									<c:when test="${not empty user.endDate}">
										<td><fmt:formatDate value="${user.endDate}" type="both" />까지</td>
									</c:when>
									<c:otherwise>
										<td>비활성화</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</div>
				</div>
				<!-- End of Main Content -->
			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top">
		<i class="fas fa-angle-up"></i>
	</a>

	<!-- custom JavaScript -->
	<!-- <script src="/vendor/datatables/custom.js"></script> -->

	<!-- Bootstrap core JavaScript-->
	<script src="/vendor/jquery/jquery.min.js"></script>
	<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="/js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="/vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="/vendor/datatables/dataTables.bootstrap4.min.js"></script>

	<!-- Page level custom scripts -->
	<script src="/js/demo/datatables-demo.js"></script>
</body>

</html>