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

<title>BookWave - BookDetail</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">
<link href="/css/admin-page.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
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
			<li class="nav-item"><a class="nav-link" href="/admin/main"> <i class="fas fa-fw fa-tachometer-alt"></i> <span>대시보드</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/user"> <i class="fas fa-fw fa-table"></i> <span>유저 관리</span></a></li>
			<li class="nav-item active"><a class="nav-link" href="/admin/book"> <i class="fas fa-fw fa-table"></i> <span>도서 관리</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/payment"> <i class="fas fa-fw fa-table"></i> <span>결제 관리</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/lend"> <i class="fas fa-fw fa-table"></i> <span>대출 현황</span></a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/printer">
				<i class="fas fa-fw fa-table"></i> <span>프린트 요청</span>
				</a></li>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo"> <i class="fas fa-fw fa-cog"></i> <span>고객 지원</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<a class="collapse-item" href="/support/faq">FAQ 관리</a> <a class="collapse-item" href="/support/qna">1:1 문의 관리</a>
					</div>
				</div></li>

			<li class="nav-item"><a class="nav-link" href="/admin/facility"> <i class="fas fa-fw fa-wrench"></i> <span>시설 관리</span>
			</a></li>

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
					<a class="navbar-brand" href="/admin/main"> <img class="img--logo" src="/img/logo_bookwave4.png" alt="Book Wave Logo" style="height: 40px;">
					</a>
					<ul class="navbar-nav ml-auto">
						<li class="nav-item"><a class="nav-link logout-btn" href="/user/logout"> <i class="fas fa-sign-out-alt"></i> <span>로그아웃</span>
						</a></li>
					</ul>
				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">
					<div class="container">
						<div class="book-detail">
							<h3>${book.title}</h3>
							<a href="/book/update/${book.id}" class="edit-link">수정하기</a> <a href="/book/delete/${book.id}" class="delete-link" onclick="confirmDelete(event)">삭제하기</a>
							<div class="section-header"></div>
							<c:if test="${book.cover != null && book.cover != ''}">
								<img src="${book.cover}" alt="${book.title}">
							</c:if>
							<p>
								<strong>작가:</strong> ${book.author}
							</p>
							<p>
								<strong>출판사:</strong> ${book.publisher}
							</p>
							<p>
								<strong>분류:</strong> ${book.categoryName}
							</p>
							<p>
								<strong>출판일:</strong> ${book.publishDate}
							</p>
							<p>
								<strong>설명:</strong> ${book.description}
							</p>
							<p>
								<strong>총 재고:</strong> ${book.totalStock}
							</p>
							<p>
								<strong>재고:</strong> ${book.currentStock}
							</p>
							<p>
								<strong>eBook:</strong> ${book.ebook != 0 ? '가능' : '불가능'}
							</p>
							<c:if test="${book.ebook == 1}">
								<a href="${book.ebookPath}">eBook</a>
							</c:if>
							<p>
								<strong>좋아요:</strong> ${book.likes}
							</p>
							<p>
								<strong>평점:</strong> ${book.score}
							</p>
							<p>
								<strong>등록일:</strong>
								<fmt:formatDate value="${book.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" />
							</p>
						</div>
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