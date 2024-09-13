<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/book-list.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/book-list.js"></script>
</head>
<body>
	<div class="page--wrapper">
		<div class="sidebar">
			 <form class="search--form" action="/book/list" method="get">
        <input type="text" name="search" value="${searchQuery}" placeholder="Search..." />
        <input type="submit" value="Search" />
        <input type="hidden" name="category" value="${selectedCategory}" />
    </form>

			<!-- 카테고리 버튼들 -->
			<c:set var="selectedCategory" value="${param.category}" />
			<a href="/book/list?page=1&category="
				class="${empty selectedCategory ? 'active' : ''}">전체</a>
			<c:forEach var="category" items="${categories}">
				<a href="/book/list?page=1&category=${category}"
					class="${category == selectedCategory ? 'active' : ''}">${category}</a>
			</c:forEach>
		</div>

		<div class="main--content">
			<div class="container">
				<c:forEach var="book" items="${books}">
					<div class="book--item" data-book-id="${book.id}">
						<a href="/book/detail/${book.id}" class="book--link">
							<div class="book--image-wrapper">
								<img src="${book.cover}" alt="${book.title}" class="book--cover" />
								<!-- 관심등록 버튼 -->
								<button type="button"
									class="favorite--button ${book.isFavorited == true ? 'favorited' : ''}"
									data-book-id="${book.id}" data-favorited="${book.isFavorited}">&#9733;</button>
								<!-- 좋아요 버튼 -->
								<button type="button"
									class="like--button ${book.isLiked == true ? 'liked' : ''}"
									data-book-id="${book.id}" data-liked="${book.isLiked}">&#128077;</button>
							</div>
						</a>
						<div class="book--title">${book.title}</div>
					</div>
				</c:forEach>
			</div>

			<div class="pagination">
				<!-- "맨앞으로 가기" 버튼 -->
				<c:choose>
					<c:when test="${currentPage > 1}">
						<a
							href="?page=1&category=${selectedCategory}&search=${searchQuery}">맨앞으로
							가기</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">맨앞으로 가기</a>
					</c:otherwise>
				</c:choose>

				<!-- "이전 블록" 버튼 -->
				<c:choose>
					<c:when test="${startBlock > 1}">
						<a
							href="?page=${startBlock - 1}&category=${selectedCategory}&search=${searchQuery}">이전
							블록</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">이전 블록</a>
					</c:otherwise>
				</c:choose>

				<!-- 페이지 블록들 -->
				<c:forEach var="i" begin="${startBlock}" end="${endBlock}">
					<a
						href="?page=${i}&category=${selectedCategory}&search=${searchQuery}"
						class="${i == currentPage ? 'active' : ''}">${i}</a>
				</c:forEach>

				<!-- "다음 블록" 버튼 -->
				<c:choose>
					<c:when test="${endBlock < totalPages}">
						<a
							href="?page=${endBlock + 1}&category=${selectedCategory}&search=${searchQuery}">다음
							블록</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">다음 블록</a>
					</c:otherwise>
				</c:choose>

				<!-- "맨뒤로 가기" 버튼 -->
				<c:choose>
					<c:when test="${currentPage < totalPages}">
						<a
							href="?page=${totalPages}&category=${selectedCategory}&search=${searchQuery}">맨뒤로
							가기</a>
					</c:when>
					<c:otherwise>
						<a class="disabled">맨뒤로 가기</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<%@ include file="../layout/footer.jsp"%>
