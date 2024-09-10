<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/ebook_list.css">
<div class="d-flex">
	<div class="sidebar">
		<a href="/ebook" class="${selectedCategory == -1 ? 'active' : ''}">전체</a>
		<c:forEach var="category" items="${categoryList}">
			<div class="category--item" 
				data-category-id="${category.id}" 
				data-category-name="${category.name}" 
				data-selectedCategory="${selectedCategory}">
				<a href="/ebook?category=${category.id}" class="${category.id == selectedCategory ? 'active' : ''}"> ${category.name} </a>
			</div>
		</c:forEach>
		<!-- 카테고리 추가 폼 (초기에는 숨김 처리) -->
		<div id="category--form" class="category--form mt-3" style="display: none;">
			<input type="text" id="category--name" class="form--control" placeholder="카테고리 이름" maxlength="8">
			<div class="d-flex justify-content-end">
				<button id="create--category--btn" class="btn btn-sm btn-success mt-2">생성</button>
			</div>
			<div id="message" class="text-danger mt-2"></div>
		</div>
		<div class="d-flex mt-2 flex-wrap" id="btn--container">
			<div class="category--add d-flex justify-content-center mr-1">
				<button id="add--category--btn" class="m-1 btn btn-sm btn-primary">카테고리 추가</button>
			</div>
			<c:if test="${selectedCategory > 0}">
				<div class="category--edit">
					<button id="edit--category--btn" class="m-1 btn btn-sm btn-warning">카테고리 이름 변경</button>
				</div>
			</c:if>
			<div class="category--reorder">
				<button id="reorder--category--btn" class="m-1 btn btn-sm btn-info">카테고리 순서 변경</button>
			</div>
		</div>

	</div>
	<div class="book--list">
		<c:forEach var="book" items="${bookList}">
			<div class="book--item" data-book-id="${book.id}" draggable="true" ondragstart="bookDrag(event)">
				<div class="book--actions">
					<a href="ebook/view/${book.id}">읽기</a> <a href="#">상세보기</a>
				</div>
				<img src="${book.cover}" alt="책 표지">
				<div class="book--details">
					<div class="book--title">${book.title}</div>
					<c:choose>
						<c:when test="${not empty book.lastReadDate}">
							<div class="last--read--date text-muted">
								마지막 읽은 날짜: <br>${book.timestampToString(book.lastReadDate)}
							</div>
						</c:when>
						<c:otherwise>
							<div class="last--read--date text--none">읽지 않음</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="progress--bar--container">
					<div class="progress--bar ${book.progress == 1 ? 'completed' : ''}" style="width: ${book.progress * 100}%;"></div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<script src="/js/ebook_list.js"></script>
<%@ include file="../layout/footer.jsp"%>