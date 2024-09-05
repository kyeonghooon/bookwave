<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/book-list.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 좋아요 버튼 클릭 이벤트 처리
    document.querySelectorAll('.like--button').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            var bookId = this.dataset.bookId;
            var isLiked = this.dataset.liked === 'true';
            var url = "/book/like/"+ bookId;

         
            console.log('Like button clicked. Is liked:', isLiked);
            console.log('Sending request to:', url);

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            .then(response => response.text())
            .then(responseText => {
                if (responseText === 'liked') {
                    this.classList.add('liked');
                    this.dataset.liked = 'true';
                } else if (responseText === 'unliked') {
                    this.classList.remove('liked');
                    this.dataset.liked = 'false';
                }
            })
            .catch(error => {
                console.error('좋아요 처리 중 오류가 발생했습니다.', error);
            });
        });
    });

    // 관심등록 버튼 클릭 이벤트 처리
    document.querySelectorAll('.favorite--button').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            var bookId = this.dataset.bookId;
            var isFavorited = this.dataset.favorited === 'true';
            // var isFavorited = ${isFavorited};
            var url = "/book/favorite/"+ bookId;

            console.log('Favorite button clicked. Is favorited:', isFavorited);
            console.log('Sending request to:', url);

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            .then(response => response.text())
            .then(responseText => {
                if (responseText === 'favorited') {
                    this.classList.add('favorited');
                    this.dataset.favorited = 'true';
                } else if (responseText === 'unfavorited') {
                    this.classList.remove('favorited');
                    this.dataset.favorited = 'false';
                }
            })
            .catch(error => {
                console.error('관심등록 처리 중 오류가 발생했습니다.', error);
            });
        });
    });
});
</script>
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
