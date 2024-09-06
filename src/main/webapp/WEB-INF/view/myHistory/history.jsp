<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Book List</title>
<style>
body {
	display: flex;
	flex-direction: column;
	margin: 0;
	font-family: Arial, sans-serif;
	min-height: 100vh;
}

.header {
	background-color: #f8f8f8;
	border-bottom: 1px solid #ddd;
	padding: 10px;
	box-sizing: border-box;
	display: flex;
	justify-content: center;
	align-items: center;
}

.header form {
	display: flex;
	gap: 10px;
	align-items: center;
}

.sidebar {
	width: 200px;
	padding: 20px;
	border-right: 1px solid #ddd;
	box-sizing: border-box;
	flex-shrink: 0;
}

.sidebar a {
	display: block;
	padding: 10px;
	margin-bottom: 5px;
	text-decoration: none;
	color: #333;
	border-radius: 5px;
	transition: background-color 0.3s, color 0.3s;
}

.sidebar a.active {
	background-color: #333;
	color: white;
}

.sidebar a:hover {
	background-color: #f0f0f0;
}

.container {
	flex: 1;
	display: grid;
	grid-template-columns: repeat(6, 1fr);
	gap: 10px;
	padding: 20px;
	width: 100%;
	justify-content: center;
}

.book--item {
	width: 190px;
	height: 330px;
	text-align: center;
	border: 1px solid #ddd;
	padding: 10px;
	border-radius: 5px;
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.book--item img {
	width: 170px;
	height: 250px;
	object-fit: cover;
	border-radius: 5px;
}

.book--title {
	font-size: 14px;
	margin-top: 10px;
	display: inline-block;
	width: 100%;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 5px;
	padding: 10px;
	background-color: #f8f8f8;
	border-top: 1px solid #ddd;
}

.pagination a {
	padding: 8px 15px;
	text-decoration: none;
	border: 1px solid #ddd;
	border-radius: 5px;
	color: #333;
	font-size: 14px;
	transition: background-color 0.3s, color 0.3s;
}

.pagination a.active {
	background-color: #333;
	color: white;
}

.pagination a.disabled {
	color: #ddd;
	border-color: #ddd;
	cursor: not-allowed;
}

.pagination .page-numbers {
	display: flex;
	gap: 5px;
}

.page--wrapper {
	display: flex;
	flex: 1;
}

.main--content {
	flex: 1;
}
</style>
</head>
<body>
	<div class="page--wrapper">
		<div class="sidebar">
			<!-- 카테고리 버튼들 -->
			<c:set var="selectedCategory" value="${param.category}" />
			<a href="/book/list?page=1&category=" class="${empty selectedCategory ? 'active' : ''}">전체 목록</a>
			<a href="/book/list?page=1&category=" class="${empty selectedCategory ? 'active' : ''}">월 별 통계</a>
			<a href="/book/list?page=1&category=" class="${empty selectedCategory ? 'active' : ''}">카테고리 별 통계</a>
			<c:forEach var="category" items="${categories}">
				<a href="/book/list?page=1&category=${category}" class="${category == selectedCategory ? 'active' : ''}">${category}</a>
			</c:forEach>
		</div>

		<div class="main--content">
			<div class="container">
				<c:forEach var="history" items="${myHistoryList}">
					<div class="book--item">
						<img src="${history.cover}" alt="${history.title}" />
						<div class="book--title">${history.title}</div>
						<a href="/history/review/${history.id}">리뷰 작성</a>
					</div>
				</c:forEach>
			</div>

			<%-- 			<div class="pagination">
				<!-- "맨앞으로 가기" 버튼 -->
				<c:choose>
					<c:when test="${currentPage > 1}">
						<a href="?page=1&category=${selectedCategory}&search=${searchQuery}">맨앞으로 가기</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">맨앞으로 가기</a>
					</c:otherwise>
				</c:choose>

				<!-- "이전 블록" 버튼 -->
				<c:choose>
					<c:when test="${startBlock > 1}">
						<a href="?page=${startBlock - 1}&category=${selectedCategory}&search=${searchQuery}">이전 블록</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">이전 블록</a>
					</c:otherwise>
				</c:choose>

				<!-- 페이지 블록들 -->
				<c:forEach var="i" begin="${startBlock}" end="${endBlock}">
					<a href="?page=${i}&category=${selectedCategory}&search=${searchQuery}" class="${i == currentPage ? 'active' : ''}">${i}</a>
				</c:forEach>

				<!-- "다음 블록" 버튼 -->
				<c:choose>
					<c:when test="${endBlock < totalPages}">
						<a href="?page=${endBlock + 1}&category=${selectedCategory}&search=${searchQuery}">다음 블록</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">다음 블록</a>
					</c:otherwise>
				</c:choose>

				<!-- "맨뒤로 가기" 버튼 -->
				<c:choose>
					<c:when test="${currentPage < totalPages}">
						<a href="?page=${totalPages}&category=${selectedCategory}&search=${searchQuery}">맨뒤로 가기</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">맨뒤로 가기</a>
					</c:otherwise>
				</c:choose>
			</div> --%>
		</div>
	</div>
</body>
</html>
