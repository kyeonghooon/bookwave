<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ include file="../modal/purchase.jsp"%>
<link href="/css/book-detail.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="my--container">
	<img src="${book.cover}" alt="${book.title} Cover" class="book--cover" />

	<div class="book--details">
		<h1 class="book--title">${book.title}</h1>

		<dl class="book--info">
			<dt>저자: ${book.author}</dt>
			<dd></dd>
			<dt>출판일: ${book.publishDate}</dt>
			<dd></dd>
			<dt>카테고리: ${book.categoryName}</dt>
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
					<c:choose>
						<c:when test="${userEbook > 0}">
							<form action="/ebook/view/${book.id}" method="get" style="display: inline;">
								<button type="submit" class="read--ebook--button">eBook 보기</button>
						</c:when>
						<c:when test="${principal.subscribe == true}">
							<form action="#" method="get" style="display: inline;">
								<button type="submit" class="sub--ebook--button">eBook 등록</button>
						</c:when>
						<c:otherwise>
							<form action="#" method="get" style="display: inline;">
								<button type="submit" class="ebook--button">eBook 구매</button>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<form action="#" method="get" style="display: none;">
				</c:otherwise>
			</c:choose>
			</form>
		</div>



	</div>

	<a href="javascript:history.back()" class="back--link">돌아가기</a>

	<!-- 관심등록 버튼 -->
	<button type="button" class="favorite--button ${isFavorited ? 'favorited' : ''}" data-book-id="${book.id}" data-favorited="${isFavorited}">&#9733;</button>

	<!-- 좋아요 버튼 -->
	<button type="button" class="like--button ${isLiked ? 'liked' : ''}" data-book-id="${book.id}" data-liked="${isLiked}">&#128077;</button>

</div>
<div class="reviews">
	<h2>리뷰 목록</h2>

	<c:choose>
		<c:when test="${empty review}">
			<div class="no-reviews">리뷰가 없습니다.</div>
		</c:when>
		<c:otherwise>
			<!-- 수정 가능한 리뷰 표시 -->
			<c:forEach var="review" items="${review}">
				<c:if test="${review.userId == principal}">
					<div class="review-item">
						<div class="review-header">
							<div class="review-author">작성자: ${review.name}</div>
							<div class="review-actions">
								<button type="button" class="edit-review-button" onclick="toggleEdit(${review.id})">수정</button>
								<form action="/book/deleteReview/${review.id}" method="post" style="display: inline;">
									<input type="hidden" name="bookId" value="${book.id}">
									<button type="submit" class="delete-review-button" onclick="return confirm('정말로 이 리뷰를 삭제하시겠습니까?');">삭제</button>
								</form>
							</div>
						</div>
						<div class="review-score">
							<c:forEach var="i" begin="1" end="5">
								<c:choose>
									<c:when test="${i <= review.score}">
										<span class="star filled">&#9733;</span>
									</c:when>
									<c:otherwise>
										<span class="star">&#9734;</span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
						<div class="review-content" id="review-content-${review.id}">${review.content}</div>
						<div class="edit-review-form" id="edit-review-form-${review.id}" style="display: none;">
							<form action="/book/updateReview/${review.id}" method="post">
								<input type="hidden" name="bookId" value="${book.id}">
								<div class="score-selection">
									<label>점수:</label>
									<c:forEach var="j" begin="1" end="5">
										<input type="radio" name="score" value="${j}" id="score-${j}-${review.id}" style="display: none;" <c:if test="${j == review.score}">checked</c:if>>
										<label for="score-${j}-${review.id}" class="star" onclick="setScore(${j}, '${review.id}')"> <c:if test="${j <= review.score}">&#9733;</c:if> <c:if test="${j > review.score}">&#9734;</c:if>
										</label>
									</c:forEach>
								</div>
								<textarea name="content" required>${review.content}</textarea>
								<button type="submit">저장</button>
								<button type="button" onclick="toggleEdit(${review.id})">취소</button>
							</form>
						</div>
						<div class="review-date">${review.createdAt}</div>
					</div>
				</c:if>
			</c:forEach>

			<!-- 일반 리뷰 표시 -->
			<c:forEach var="review" items="${review}">
				<c:if test="${review.userId != principal}">
					<div class="review-item">
						<div class="review-header">
							<div class="review-author">작성자: ${review.name}</div>
						</div>
						<div class="review-score">
							<c:forEach var="i" begin="1" end="5">
								<c:choose>
									<c:when test="${i <= review.score}">
										<span class="star filled">&#9733;</span>
									</c:when>
									<c:otherwise>
										<span class="star">&#9734;</span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
						<div class="review-content">${review.content}</div>
						<div class="review-date">${review.createdAt}</div>
					</div>
				</c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>

<script type="text/javascript">
		const bookId = ${book.id};
		const json = ${items};
		const items = new Map(Object.entries(${items}));
	</script>
<script src="/js/book-detail.js"></script>
<%@ include file="../layout/footer.jsp"%>