<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/ebook_list.css">
<div class="book--list">
	<c:forEach var="book" items="${bookList}">
		<div class="book--item" data-book-id="${book.id}">
			<button class="remove--button" onclick="confirmRemove(${book.id})">
				<span>&times;</span>
			</button>
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
			<div class="book--actions">
				<a href="#">상세보기</a> <a href="ebook/view/${book.id}">읽기</a>
			</div>
		</div>
	</c:forEach>
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
<%@ include file="../layout/footer.jsp"%>