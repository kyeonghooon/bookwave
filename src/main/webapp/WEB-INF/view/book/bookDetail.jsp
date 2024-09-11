<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/book-detail.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/book-detail.js"></script>
</head>
<body>
	<div class="container">
		<img src="${book.cover}" alt="${book.title} Cover" class="book--cover" />

		<div class="book--details">
			<h1 class="book--title">${book.title}</h1>

			<dl class="book--info">
				<dt>저자: ${book.author}</dt>
				<dd></dd>
				<dt>출판일: ${book.publishDate}</dt>
				<dd></dd>
				<dt>카테고리: ${book.category}</dt>
				<dd></dd>
				<dt>출판사: ${book.publisher}</dt>
				<dd></dd>
				<dt>현재 재고: ${book.currentStock}</dt>
				<dd></dd>
				<dt>
					좋아요 수: <span class="like-count">${book.likes}</span>
				</dt>
				<dd></dd>
			</dl>

			<div class="description">
				<h2>설명</h2>
				<p>${book.description}</p>
			</div>

			<!-- 대여 및 예약 버튼 처리 -->
			<div class="button--group">
				<c:choose>
					<c:when test="${book.ebook != 1}">
						<form action="/book/lend/${book.id}" method="get" style="display: inline;">
					</c:when>
					<c:otherwise>
						<form action="/book/lend/${book.id}" method="get" style="display: none;">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${book.currentStock <= 0 || lendBookCount >= 5}">
						<button type="submit" class="lend--button ${lend.status == 0 ? 'renting' : 'unavailable'}" ${lend.status == 0 ? 'disabled' : 'disabled'}>${lend.status == 0 ? '대여 중' : '대여 불가'}</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="lend--button ${lend.status == 0 ? 'renting' : ''}" ${lend.status == 0 ? 'disabled' : ''}>${lend.status == 0 ? '대여 중' : '대여하기'}</button>
					</c:otherwise>
				</c:choose>
				</form>
				<c:choose>
					<c:when test="${book.ebook != 1}">
						<form action="/book/reservation/${book.id}" method="get" style="display: inline;">
					</c:when>
					<c:otherwise>
						<form action="/book/reservation/${book.id}" method="get" style="display: none;">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${reservation.status == 0}">
						<button type="submit" class="reserve--button reserving" disabled>예약 중</button>
					</c:when>
					<c:when test="${book.currentStock > 0 || reservationCount >= 5 || lend.status == 0}">
						<button type="submit" class="reserve--button unavailable" disabled>예약 불가</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="reserve--button">예약하기</button>
					</c:otherwise>
				</c:choose>
				</form>

				<c:choose>
					<c:when test="${book.ebook != 0}">
						<form action="/book/ebook/${book.id}" method="get" style="display: inline;">
					</c:when>
					<c:otherwise>
						<form action="/book/ebook/${book.id}" method="get" style="display: none;">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${userEbook > 0}">
						<button type="submit" class="read--ebook--button">eBook 보기</button>
					</c:when>
					<c:when test="${user1.subscribe == false}">
						<button type="submit" class="ebook--button">eBook 구매</button>
					</c:when>
					<c:when test="${user1.subscribe == true}">
						<button type="submit" class="sub--ebook--button">eBook 등록</button>
					</c:when>
				</c:choose>
				</form>
			</div>



		</div>

		<a href="/book/list" class="back--link">돌아가기</a>

		<!-- 관심등록 버튼 -->
		<button type="button" class="favorite--button ${isFavorited ? 'favorited' : ''}" data-book-id="${book.id}" data-favorited="${isFavorited}">&#9733;</button>

		<!-- 좋아요 버튼 -->
		<button type="button" class="like--button ${isLiked ? 'liked' : ''}" data-book-id="${book.id}" data-liked="${isLiked}">&#128077;</button>

	</div>
	<%@ include file="../layout/footer.jsp"%>