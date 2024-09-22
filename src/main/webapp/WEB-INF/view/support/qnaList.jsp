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

<title>BookWave - Q&A</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">
<link href="/css/admin-page.css" rel="stylesheet">
<link href="/img/favicon.ico" rel="icon">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

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

/* 상세 내용 숨기기 및 표시 */
.details {
	display: none;
	background-color: #f9f9f9;
}

.details.show {
	display: table-row;
}

.create--answer {
	padding: 5px;
	border-radius: 6px;
	background-color: #FF0033;
	color: white;
}

.update--answer {
	padding: 5px;
	border-radius: 6px;
	background-color: #0099FF;
	color: white;
}

.answer {
	text-decoration: none;
}

.answer:link {
	color: white;
}

.answer:visited {
	color: white;
}

.answer:hover {
	color: #3366FF;
	text-decoration: none;
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
			<li class="nav-item"><a class="nav-link" href="/admin/main"> <i class="fas fa-fw fa-tachometer-alt"></i> <span>대시보드</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/user"> <i class="fas fa-fw fa-table"></i> <span>유저 관리</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/book"> <i class="fas fa-fw fa-table"></i> <span>도서 관리</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/payment"> <i class="fas fa-fw fa-table"></i> <span>결제 관리</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="/admin/lend"> <i class="fas fa-fw fa-table"></i> <span>대출 현황</span>
			</a></li>

			<!-- Nav Item - Pages Collapse Menu -->
			<li class="nav-item active"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo"> <i class="fas fa-fw fa-cog"></i> <span>고객 지원</span>
			</a>
				<div id="collapseTwo" class="collapse show" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<a class="collapse-item" href="/support/faq">FAQ 관리</a> <a class="collapse-item active" href="/support/qna">1:1 문의 관리</a>
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
					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">Q&A 목록</h1>
					<form action="/support/qna-find?keyword=${keyword}" method="GET">
						<input type="text" name="keyword" value="${keyword}">
						<button type="submit">키워드 검색</button>
					</form>
					<div class="card shadow mb-4">
						<div class="card-body">
							<div class="table-responsive">
								<c:choose>
									<c:when test="${qnaList != null}">

										<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
											<thead>
												<tr>
													<th>id</th>
													<th>문의자ID</th>
													<th>문의자</th>
													<th>문의제목</th>
													<th>처리여부</th>
													<th>답변자</th>
													<th>문의시간</th>
													<th>답변시간</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="qna" items="${qnaList}">
													<tr class="toggle-button" onclick="toggleDetails(this)">
														<th>${qna.qid}</th>
														<th>${qna.quserId}</th>
														<th>${qna.qname}</th>
														<th>${qna.qtitle}</th>
														<c:choose>
															<c:when test="${qna.aname != null}">
																<th>답변완료&nbsp;<span class="update--answer"><a class="answer" href="/support/answer-update?id=${qna.qid}">수정</a></span></th>
															</c:when>
															<c:otherwise>
																<th><span class="create--answer"><a class="answer" href="/support/answer-create?id=${qna.qid}">답변하기</a></span></th>
															</c:otherwise>
														</c:choose>
														<th>${qna.aname}</th>
														<th><fmt:formatDate value="${qna.qtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></th>
														<th><fmt:formatDate value="${qna.atime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></th>
													</tr>
													<tr class="details">
														<td colspan="9">
															<!-- 상세보기 내용 -->
															<p>
																<strong>문의 내용:</strong> ${qna.qcontent}
															</p>
															<p>
																<strong>답변 내용:</strong> ${qna.acontent}
															</p>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:otherwise>
										<div class="jumbotron display-4">
											<h5>검색된 Q&A가 없습니다</h5>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
				<!-- /.container-fluid -->
				<!-- Pagination -->
				<div class="d-flex justify-content-center">
					<ul class="pagination">

						<!-- Previous Page Link -->
						<li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>"><a class="page-link" href="?page=${currentPage - 1}&size=${size}">&lt;</a></li>

						<!-- Page Numbers -->
						<c:forEach begin="1" end="${totalPages}" var="page">
							<li class="page-item <c:if test='${currentPage == page}'>active</c:if>"><a class="page-link" href="?page=${page}&size=${size}">${page}</a></li>
						</c:forEach>

						<!-- Next Page Link -->
						<li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>"><a class="page-link" href="?page=${currentPage + 1}&size=${size}">&gt;</a></li>
					</ul>
				</div>

			</div>
			<!-- End of Main Content -->

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
	<!-- Q&A 토글 자바스크립트 -->
	<script>
		function toggleDetails(row) {
			// 현재 클릭된 행의 다음 형제 요소인 상세보기 행을 찾기
			var nextRow = row.nextElementSibling;

			// 다음 행이 .details 클래스를 가지고 있는지 확인
			if (nextRow && nextRow.classList.contains('details')) {
				// .details 클래스의 표시 상태를 토글
				if (nextRow.classList.contains('show')) {
					nextRow.classList.remove('show');
				} else {
					nextRow.classList.add('show');
				}
			}
		}
	</script>
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
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>