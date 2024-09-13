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

<title>도서 정보 수정 페이지</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>
/* General Container Styling */
.container-fluid {
	padding: 20px;
}

.container {
	max-width: 1000px; /* Increase the max-width of the container */
	margin: 0 auto;
}

/* Form Styling */
.book-detail {
	background: #f8f9fc;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* Adjust the form width */
.book-detail form {
	width: 100%;
}

.book-detail h1 {
	font-size: 24px;
	margin-bottom: 20px;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

.form-group input[type="text"], .form-group input[type="date"],
	.form-group input[type="number"], .form-group textarea, .form-group select
	{
	width: 100%;
	padding: 10px; /* Increase padding for a better appearance */
	border: 1px solid #ced4da;
	border-radius: 4px;
	box-sizing: border-box;
}

.form-group input[type="file"] {
	border: none;
}

.form-group textarea {
	resize: vertical;
}

.form-group select {
	height: 45px; /* Increase height to make the select box taller */
	padding: 8px; /* Add padding for better text visibility */
}

/* Button Styling */
button[type="submit"] {
	background-color: #007bff;
	color: white;
	border: none;
	padding: 12px 24px; /* Adjust padding for a wider button */
	font-size: 16px;
	border-radius: 4px;
	cursor: pointer;
	transition: background-color 0.2s ease;
}

button[type="submit"]:hover {
	background-color: #0056b3;
}

/* Image Preview Styling */
#coverPreview, #newCoverPreview {
	max-width: 200px; /* Increase max-width for wider image preview */
	margin-top: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
}

#coverPreview {
	display: block;
}

#newCoverPreview {
	display: none;
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
				<div class="sidebar-brand-text mx-3">LOGO 넣는곳</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- Nav Item - Dashboard -->
			<li class="nav-item"><a class="nav-link" href="/admin/main"> <i class="fas fa-fw fa-tachometer-alt"></i> <span>대시보드</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/user"> <i class="fas fa-fw fa-table"></i> <span>유저 관리</span></a></li>
			<li class="nav-item active"><a class="nav-link" href="/admin/book"> <i class="fas fa-fw fa-table"></i> <span>도서 관리</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/payment"> <i class="fas fa-fw fa-table"></i> <span>결제 관리</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/lend"> <i class="fas fa-fw fa-table"></i> <span>대출 현황</span></a></li>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo"> <i class="fas fa-fw fa-cog"></i>
					<span>고객 지원</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<a class="collapse-item" href="/support/faq">FAQ 관리</a> <a class="collapse-item" href="/support/qna">1:1 문의 관리</a>
					</div>
				</div></li>

			<!-- Divider -->
			<hr class="sidebar-divider">

			<!-- Heading -->
			<div class="sidebar-heading">Interface</div>

			<!-- Nav Item - Utilities Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities"> <i
					class="fas fa-fw fa-wrench"></i> <span>Utilities</span>
			</a>
				<div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Custom Utilities:</h6>
						<a class="collapse-item" href="utilities-color.html">Colors</a> <a class="collapse-item" href="utilities-border.html">Borders</a> <a class="collapse-item" href="utilities-animation.html">Animations</a>
						<a class="collapse-item" href="utilities-other.html">Other</a>
					</div>
				</div></li>

			<!-- Divider -->
			<hr class="sidebar-divider">

			<!-- Heading -->
			<div class="sidebar-heading">Addons</div>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages"> <i
					class="fas fa-fw fa-folder"></i> <span>Pages</span>
			</a>
				<div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">Login Screens:</h6>
						<a class="collapse-item" href="login.html">Login</a> <a class="collapse-item" href="register.html">Register</a> <a class="collapse-item" href="forgot-password.html">Forgot Password</a>
						<div class="collapse-divider"></div>
						<h6 class="collapse-header">Other Pages:</h6>
						<a class="collapse-item" href="404.html">404 Page</a> <a class="collapse-item" href="blank.html">Blank Page</a>
					</div>
				</div></li>

			<!-- Nav Item - Charts -->
			<li class="nav-item"><a class="nav-link" href="charts.html"> <i class="fas fa-fw fa-chart-area"></i> <span>Charts</span></a></li>

			<!-- Nav Item - Tables -->
			<li class="nav-item"><a class="nav-link" href="tables.html"> <i class="fas fa-fw fa-table"></i> <span>Tables</span></a></li>

			<!-- Divider -->
			<hr class="sidebar-divider d-none d-md-block">

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

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Topbar Search -->
					<form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
						<div class="input-group">
							<input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
							<div class="input-group-append">
								<button class="btn btn-primary" type="button">
									<i class="fas fa-search fa-sm"></i>
								</button>
							</div>
						</div>
					</form>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<!-- Nav Item - Alerts -->
						<li class="nav-item dropdown no-arrow mx-1"><a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i class="fas fa-bell fa-fw"></i> <!-- Counter - Alerts --> <span class="badge badge-danger badge-counter">3+</span>
						</a> <!-- Dropdown - Alerts -->
							<div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
								<h6 class="dropdown-header">Alerts Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-primary">
											<i class="fas fa-file-alt text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 12, 2019</div>
										<span class="font-weight-bold">A new monthly report is ready to download!</span>
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
							</div></li>

						<!-- Nav Item - Messages -->
						<li class="nav-item dropdown no-arrow mx-1"><a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i> <!-- Counter - Messages --> <span class="badge badge-danger badge-counter">7</span>
						</a> <!-- Dropdown - Messages -->
							<div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="messagesDropdown">
								<h6 class="dropdown-header">Message Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle" src="/img/undraw_profile_1.svg" alt="...">
										<div class="status-indicator bg-success"></div>
									</div>
									<div class="font-weight-bold">
										<div class="text-truncate">Hi there! I am wondering if you can help me with a problem I've been having.</div>
										<div class="small text-gray-500">Emily Fowler Â· 58m</div>
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500" href="#">Read More Messages</a>
							</div></li>
						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span> <img class="img-profile rounded-circle" src="/img/undraw_profile.svg">
						</a> <!-- Dropdown - User Information -->
							<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
								<a class="dropdown-item" href="#"> <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
								</a> <a class="dropdown-item" href="#"> <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> Settings
								</a> <a class="dropdown-item" href="#"> <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i> Activity Log
								</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal"> <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout
								</a>
							</div></li>
					</ul>
				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">
					<div class="container">
						<div class="book-detail">
							<h1>도서 정보 수정하기</h1>
							<form action="/book/update/${book.id}" method="post">
								<input type="hidden" value="${book.id}" name="id">
								<div class="form-group">
									<label for="title">제목:</label> <input type="text" id="title" name="title" value="${book.title}" required>
								</div>
								<div class="form-group">
									<label for="author">작가:</label> <input type="text" id="author" name="author" value="${book.author}" required>
								</div>
								<div class="form-group">
									<label for="publisher">출판사:</label> <input type="text" id="publisher" name="publisher" value="${book.publisher}" required>
								</div>

								<div class="form-group">
									<label for="category">분류:</label> <select id="category" name="category">
										<c:forEach var="category" items="${categoryList}">
											<option value="${category}" ${book.category == category ? 'selected' : ''}>${category}</option>
										</c:forEach>
									</select>
								</div>

								<div class="form-group">
									<label for="publishDate">출판일:</label> <input type="date" id="publishDate" name="publishDate" value="${book.publishDate}" required>
								</div>
								<div class="form-group">
									<label for="description">설명:</label>
									<textarea id="description" name="description" rows="5" required>${book.description}</textarea>
								</div>
								<div class="form-group">
									<label for="totalStock">총 재고:</label> <input type="number" id="totalStock" name="totalStock" value="${book.totalStock}" required>
								</div>
								<div class="form-group">
									<label for="currentStock">재고:</label> <input type="number" id="currentStock" name="currentStock" value="${book.currentStock}" required>
								</div>
								<div class="form-group">
									<label for="ebook">eBook 여부:</label> <select id="ebook" name="ebook">
										<option value="2">둘다</option>
										<option value="1">전자책</option>
										<option value="0">종이책</option>
									</select>
								</div>
								<div class="form-group">
									<label for="ebookPath">eBook 경로:</label> <input type="text" id="ebookPath" name="ebookPath" value="${book.ebookPath}">
								</div>
								<div class="form-group">
									<label for="cover">커버 이미지 경로:</label> <input type="text" id="cover" name="cover" value="${book.cover}">
									<button type="button" id="previewButton" onclick="updateCoverPreview()">미리보기</button>
									<div id="coverPreviewContainer" style="margin-top: 10px;">
										<img id="coverPreview" src="${book.cover}" alt="Cover Preview" style="max-width: 150px; display: ${book.cover ? 'block' : 'none'};">
									</div>
								</div>
								<button type="submit">수정하기</button>
							</form>
						</div>
					</div>
				</div>
				<!-- End of Main Content -->
			</div>
			<!-- /.container-fluid -->

			<!-- Footer -->
			<%@ include file="/WEB-INF/view/layout/footer.jsp"%>

			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">Ã</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>
	<!-- custom JavaScript -->
	<script type="text/javascript">
		// JavaScript 함수 정의
		function confirmDelete(event) {
			// 확인 메시지 표시
			var confirmed = confirm("정말 삭제하시겠습니까?");

			// 사용자가 "취소"를 클릭하면 링크의 기본 동작을 막음
			if (!confirmed) {
				event.preventDefault(); // 링크 클릭 취소
			}
		}

		function updateCoverPreview() {
			const coverInput = document.getElementById('cover');
			const coverPreview = document.getElementById('coverPreview');
			const coverUrl = coverInput.value;

			if (coverUrl) {
				coverInput.value = coverUrl;
				coverPreview.src = coverUrl;
				coverPreview.style.display = 'block';
			} else {
				coverPreview.style.display = 'none';
			}
		}

		document.addEventListener('DOMContentLoaded', updateCoverPreview);
	</script>
	<script src="/vendor/datatables/custom.js"></script>

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