<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/ebook_list.css">
<div class="d-flex">
	<div class="sidebar">
		<a href="/ebook" class="${selectedCategory == -1 ? 'active' : ''}">전체</a>
		<c:forEach var="category" items="${categoryList}">
			<a href="/ebook?category=${category.id}" class="${category.id == selectedCategory ? 'active' : ''}">${category.name}</a>
		</c:forEach>
		<!-- 카테고리 추가 버튼 -->
		<div class="category--add">
			<button id="add--category--btn" class="btn btn-sm btn-primary">+</button>
		</div>

		<!-- 카테고리 추가 폼 (초기에는 숨김 처리) -->
		<div id="category--form" class="category--form" style="display: none;">
			<input type="text" id="category--name" class="form-control" placeholder="카테고리 이름" maxlength="10">
			<button id="create--category--btn" class="btn btn-sm btn-success mt-2">생성</button>
			<div id="message" class="text-danger mt-2"></div>
		</div>
	</div>
	<div class="book--list">
		<c:forEach var="book" items="${bookList}">
			<div class="book--item" data-book-id="${book.id}">
				<div class="remove--button" onclick="confirmRemove(${book.id})">
					<span>X</span>
				</div>
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
			</div>
		</c:forEach>
	</div>
</div>
<script>
function confirmRemove(bookId) {
    if (confirm("정말 이 책을 삭제하시겠습니까?")) {
    	console.log('bookId',bookId);
    	const url = "ebook/remove?bookId=" + bookId;
        window.location.href = url;
    }
}
</script>
<script src="/js/ebook_list.js"></script>
<%@ include file="../layout/footer.jsp"%>