<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<link href="/css/point-history.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="sidebar">
		<h3>나의 계정</h3>
		<a href="/user-info/mypageAuth">개인정보 수정</a> <a
			href="/user-info/pointHistory">포인트 내역 조회</a> <a
			href="/user-info/paymentHistory">결제 내역</a> <a class="delete-btn"
			onclick="window.open('/user-info/deleteAccount', '_blank', 'width=800,height=500,resizable=no')">회원탈퇴</a>
	</div>

	<div class="content">
		<h2 style="text-align: center">포인트 내역 조회</h2>

		<!-- Links to switch between Wave and Mileage history -->
		<div class="section button-group">
			<a
				href="/user-info/pointHistory?historyType=all&page=1&size=${size}&sortBy=date&sortOrder=desc">전체
				내역 조회</a> <a
				href="/user-info/pointHistory?historyType=wave&page=1&size=${size}&sortBy=date&sortOrder=desc">Wave
				내역 조회</a> <a
				href="/user-info/pointHistory?historyType=mileage&page=1&size=${size}&sortBy=date&sortOrder=desc">마일리지
				내역 조회</a>
		</div>
		<div class="section table-container">
			<table>
				<thead>
					<tr>
						<c:choose>
							<c:when test="${historyType == 'all'}">
								<th class="sortable ${sortBy == 'wave' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=all&page=${currentPage}&size=${size}&sortBy=wave&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">Wave
									변경 내역 <span
									class="arrowup ${sortBy == 'wave' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'wave' ? sortOrder : 'default'}"></span>
								</th>
								<th class="sortable ${sortBy == 'mileage' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=all&page=${currentPage}&size=${size}&sortBy=mileage&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">마일리지
									변경 내역 <span
									class="arrowup ${sortBy == 'mileage' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'mileage' ? sortOrder : 'default'}"></span>
								</th>
							</c:when>

							<c:when test="${historyType == 'wave'}">
								<th class="sortable ${sortBy == 'wave' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=wave&page=${currentPage}&size=${size}&sortBy=wave&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">Wave
									변경 내역 <span
									class="arrowup ${sortBy == 'wave' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'wave' ? sortOrder : 'default'}"></span>
								</th>
							</c:when>


							<c:otherwise>
								<th class="sortable ${sortBy == 'mileage' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=mileage&page=${currentPage}&size=${size}&sortBy=mileage&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">마일리지
									변경 내역 <span
									class="arrowup ${sortBy == 'mileage' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'mileage' ? sortOrder : 'default'}"></span>
								</th>
							</c:otherwise>
						</c:choose>

						<th>설명</th>
						<c:choose>
							<c:when test="${historyType == 'all'}">
								<th class="sortable ${sortBy == 'date' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=all&page=${currentPage}&size=${size}&sortBy=date&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">날짜
									<span
									class="arrowup ${sortBy == 'date' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'date' ? sortOrder : 'default'}"></span>
								</th>
							</c:when>
							<c:when test="${historyType == 'wave'}">
								<th class="sortable ${sortBy == 'date' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=wave&page=${currentPage}&size=${size}&sortBy=date&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">날짜
									<span
									class="arrowup ${sortBy == 'date' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'date' ? sortOrder : 'default'}"></span>
								</th>
							</c:when>
							<c:otherwise>
								<th class="sortable ${sortBy == 'date' ? 'active' : ''}"
									onclick="location.href='/user-info/pointHistory?historyType=mileage&page=${currentPage}&size=${size}&sortBy=date&sortOrder=${sortOrder == 'asc' ? 'desc' : 'asc'}'">날짜
									<span
									class="arrowup ${sortBy == 'date' ? sortOrder : 'default'}"></span>
									<span
									class="arrowdown ${sortBy == 'date' ? sortOrder : 'default'}"></span>
								</th>
							</c:otherwise>
						</c:choose>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="balance" items="${balanceHistory}">
						<tr>
							<c:choose>
								<c:when test="${historyType == 'all'}">
									<td><c:choose>
											<c:when test="${balance.waveChange > 0}">
												<span class="positive">+${balance.waveChange}</span>
											</c:when>
											<c:when test="${balance.waveChange < 0}">
												<span class="negative">${balance.waveChange}</span>
											</c:when>
											<c:otherwise>
												<span>-</span>
											</c:otherwise>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${balance.mileageChange > 0}">
												<span class="positive">+${balance.mileageChange}</span>
											</c:when>
											<c:when test="${balance.mileageChange < 0}">
												<span class="negative">${balance.mileageChange}</span>
											</c:when>
											<c:otherwise>
												<span>-</span>
											</c:otherwise>
										</c:choose></td>
								</c:when>


								<c:when test="${historyType == 'wave'}">
									<td><c:choose>
											<c:when test="${balance.waveChange > 0}">
												<span class="positive">+${balance.waveChange}</span>
											</c:when>
											<c:when test="${balance.waveChange < 0}">
												<span class="negative">${balance.waveChange}</span>
											</c:when>
											<c:otherwise>
												<span>-</span>
											</c:otherwise>
										</c:choose></td>
								</c:when>


								<c:otherwise>
									<td><c:choose>
											<c:when test="${balance.mileageChange > 0}">
												<span class="positive">+${balance.mileageChange}</span>
											</c:when>
											<c:when test="${balance.mileageChange < 0}">
												<span class="negative">${balance.mileageChange}</span>
											</c:when>
											<c:otherwise>
												<span>-</span>
											</c:otherwise>
										</c:choose></td>
								</c:otherwise>


							</c:choose>
							<td>${balance.description}</td>
							<td>${balance.timestampToStringDate(balance.createdAt)}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!-- Wave Pagination -->
		<div class="pagination">
			<c:choose>
				<c:when test="${currentPage > 1}">
					<a
						href="/user-info/pointHistory?historyType=${historyType}&page=1&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}">맨앞으로
						가기</a>
				</c:when>
				<c:otherwise>
					<a class="disabled">맨앞으로 가기</a>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${startBlock > 1}">
					<a
						href="/user-info/pointHistory?historyType=${historyType}&page=${startBlock - 1}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}">이전
						블록</a>
				</c:when>
				<c:otherwise>
					<a class="disabled">이전 블록</a>
				</c:otherwise>
			</c:choose>

			<c:forEach var="i" begin="${startBlock}" end="${endBalanceBlock}">
				<a
					href="/user-info/pointHistory?historyType=${historyType}&page=${i}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}"
					class="${i == currentPage ? 'current' : ''}">${i}</a>
			</c:forEach>

			<c:choose>
				<c:when test="${endBalanceBlock < totalBalancePages}">
					<a
						href="/user-info/pointHistory?historyType=${historyType}&page=${endBalanceBlock + 1}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}">다음
						블록</a>
				</c:when>
				<c:otherwise>
					<a class="disabled">다음 블록</a>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${currentPage < totalBalancePages}">
					<a
						href="/user-info/pointHistory?historyType=${historyType}&page=${totalBalancePages}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}">맨뒤로
						가기</a>
				</c:when>
				<c:otherwise>
					<a class="disabled">맨뒤로 가기</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>