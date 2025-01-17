<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/main.css">
<main class="d-flex flex-column">
	<div class="container-fluid banner">
		<div class="row banner--content">
			<div class="col-6 banner--text">
				<h1 class="display-4">환영합니다!</h1>
				<p class="lead">BookWave에서 다양한 도서를 만나보세요.</p>
			</div>
		</div>
	</div>
	<div class="d-flex flex-column mt-5 ml-auto mr-auto">
		<h2>지난 달의 인기도서</h2>
		<div class="book--list">
			<c:forEach var="book" items="${montlyBookList}">
				<div class="book--item">
					<div class="book--actions">
						<a href="book/detail/${book.id}">상세보기</a>
					</div>
					<img src="${book.cover}" alt="책 표지">
					<div class="book--details">
						<div class="book--title">${book.title}</div>
						<div class="book--likes">좋아요 : ${book.likes}</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<br>
	<div class="d-flex flex-column ml-auto mr-auto">
		<h2>이달의 신규 입고 도서</h2>
		<div class="book--slider">
			<div class="book--list" id="newBookList">
				<c:forEach var="book" items="${newBookList}">
					<div class="book--item" id="newBook">
						<div class="book--actions">
							<a href="book/detail/${book.id}">상세보기</a>
						</div>
						<img src="${book.cover}" alt="책 표지">
						<div class="book--details">
							<div class="book--title">${book.title}</div>
							<div class="book--likes">입고일 : ${book.timestampToStringDate(book.createdAt)}</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="newbook--nav prev">&lt;</div>
			<div class="newbook--nav next">&gt;</div>
		</div>
	</div>
	<div class="d-flex flex-column ml-auto mr-auto">
		<h2>평점 랭킹</h2>
		<div class="book--list">
			<c:forEach var="book" items="${rankedBookList}">
				<div class="book--item">
					<div class="book--actions">
						<a href="book/detail/${book.id}">상세보기</a>
					</div>
					<img src="${book.cover}" alt="책 표지">
					<div class="book--details">
						<div class="book--title">${book.title}</div>
						<div class="book--likes">평점 : ${book.score}</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</main>
<script src="/js/main.js"></script>
<%@ include file="footer.jsp"%>
