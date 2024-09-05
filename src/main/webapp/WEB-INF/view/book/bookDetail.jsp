<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/book-detail.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    var bookId = ${book.id};

    // 좋아요 버튼 클릭 이벤트 처리
    document.querySelector('.like--button').addEventListener('click', function(e) {
        e.preventDefault();
        var button = e.currentTarget;
        var isLiked = button.getAttribute('data-liked') === 'true';
        var url = `/book/like/${book.id}`;
        
        console.log('Like button clicked. Is liked:', isLiked);
        console.log('Sending request to:', url);

        // 비동기 요청을 통해 좋아요 상태 변경
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.text())
        .then(responseText => {
            if (responseText === 'liked') {
                button.classList.add('liked');
                button.setAttribute('data-liked', 'true');
            } else if (responseText === 'unliked') {
                button.classList.remove('liked');
                button.setAttribute('data-liked', 'false');
            }
        })
        .catch(error => {
            console.error('좋아요 처리 중 오류가 발생했습니다.', error);
        });
    });
    // 관심등록 버튼 클릭 이벤트 처리
    document.querySelector('.favorite--button').addEventListener('click', function(e) {
        e.preventDefault();
        var button = e.currentTarget;
        var isFavorited = button.getAttribute('data-favorited') === 'true';
        var url = `/book/favorite/${book.id}`;
        
        console.log('Favorite button clicked. Is favorited:', isFavorited);
        console.log('Sending request to:', url);

        // 비동기 요청을 통해 관심등록 상태 변경
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => response.text())
        .then(responseText => {
            if (responseText === 'favorited') {
                button.classList.add('favorited');
                button.setAttribute('data-favorited', 'true');
            } else if (responseText === 'unfavorited') {
                button.classList.remove('favorited');
                button.setAttribute('data-favorited', 'false');
            }
        })
        .catch(error => {
            console.error('관심등록 처리 중 오류가 발생했습니다.', error);
        });
    });
    // 대여하기 버튼 클릭 이벤트 처리
    document.querySelector('.lend--button').addEventListener('click', function(e) {
        var form = e.currentTarget.closest('form');
        var confirmed = confirm('정말 대여하시겠습니까?');
        if (!confirmed) {
            e.preventDefault(); // 사용자가 '아니오'를 선택하면 폼 제출을 막음
        }
    });
    
    // 예약하기 버튼 클릭 이벤트 처리
    document.querySelector('.reserve--button').addEventListener('click', function(e) {
        var form = e.currentTarget.closest('form');
        var confirmed = confirm('정말 예약하시겠습니까?');
        if (!confirmed) {
            e.preventDefault(); // 사용자가 '아니오'를 선택하면 폼 제출을 막음
        }
    });
});
</script>
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
				<form action="/book/lend/${book.id}" method="get"
					style="display: inline;">
					<c:choose>
						<c:when test="${book.currentStock <= 0 || lendBookCount >= 5}">
							<button type="submit"
								class="lend--button ${lend.status == 0 ? 'renting' : 'unavailable'}"
								${lend.status == 0 ? 'disabled' : 'disabled'}>
								${lend.status == 0 ? '대여 중' : '대여 불가'}</button>
						</c:when>
						<c:otherwise>
							<button type="submit"
								class="lend--button ${lend.status == 0 ? 'renting' : ''}"
								${lend.status == 0 ? 'disabled' : ''}>${lend.status == 0 ? '대여 중' : '대여하기'}
							</button>
						</c:otherwise>
					</c:choose>
				</form>

				<form action="/book/reservation/${book.id}" method="get" style="display: inline;">
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


				<form action="#" method="get" style="display: inline;">
					<button type="submit" class="ebook--button">eBook 등록</button>
				</form>
			</div>
		</div>

		<a href="/book/list" class="back--link">돌아가기</a>

		<!-- 관심등록 버튼 -->
		<button type="button"
			class="favorite--button ${isFavorited ? 'favorited' : ''}"
			data-book-id="${book.id}" data-favorited="${isFavorited}">&#9733;</button>

		<!-- 좋아요 버튼 -->
		<button type="button" class="like--button ${isLiked ? 'liked' : ''}"
			data-book-id="${book.id}" data-liked="${isLiked}">&#128077;</button>

	</div>
	<%@ include file="../layout/footer.jsp"%>
