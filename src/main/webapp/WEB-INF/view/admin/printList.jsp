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

<title>BookWave - PrintList</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>
table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 8px;
	border: 1px solid #ddd;
}
/* 기본 행 스타일 */
tbody tr {
	transition: background-color 0.3s ease;
}
/* hover 스타일 */
tbody tr:hover {
	background-color: #99CCFF;
}

.btn-point {
	display: inline-block;
	padding: 10px 20px;
	background-color: #4CAF50; /* Green background */
	color: white; /* White text */
	text-align: center; /* Centered text */
	text-decoration: none; /* Remove underline */
	border-radius: 5px; /* Rounded corners */
	font-size: 16px; /* Font size */
	transition: background-color 0.3s, transform 0.3s;
	/* Transition effects */
}

.btn-point:hover {
	background-color: #45a049; /* Darker green on hover */
	transform: scale(1.05); /* Slightly increase size */
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
			<li class="nav-item"><a class="nav-link" href="/admin/user">
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
			<li class="nav-item active"><a class="nav-link" href="/admin/printer">
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

					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">프린트 요청 목록</h1>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-body">
							<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
								<thead>
									<tr>
										<th>번호</th>
										<th>회원 ID</th>
										<th>파일명</th>
										<th>페이지</th>
										<th>상태</th>
										<th>요청일</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="printer" items="${printList}">
										<tr>
											<th>${printer.id}</th>
											<th>${printer.userId}</th>
											<th>${printer.originFileName}</th>
											<th>${printer.pages}</th>
											<th>${printer.status == 0 ? '미처리' : '완료'}</th>
											<th><fmt:formatDate value="${printer.createdAt}" type="both" /></th>
											<th>
												<!-- status가 0인 경우에만 버튼 생성 --> <c:choose>
													<c:when test="${printer.status == 0}">
														<button type="button" class="btn--print" data-id="${printer.id}">프린트</button>
														<a href="/facility/printer-done/${printer.id}" class="btn--done"> 완료처리</a>
													</c:when>
													<c:otherwise>
											                -
											            </c:otherwise>
												</c:choose>
											</th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- End of Main Content -->

	</div>
	<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top">
		<i class="fas fa-angle-up"></i>
	</a>

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
	<script src="/js/admin_print.js"></script>
</body>

</html>