<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>컴퓨터 예약</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" />
<!-- jQuery -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- 부트스트랩 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="/img/favicon.ico" rel="icon">
</head>
<body>
<%@ include file="../modal/purchase.jsp"%>
	<div class="container">
		<div class="date-picker">
			<label for="reservation-date">날짜 선택:</label> <input type="date" id="reservationDate">
		</div>

		<div class="time-table mt-4">
			<table class="table">
				<thead>
					<tr>
						<th>시간</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody id="timeTableBody">
					<!-- 타임 테이블은 JavaScript로 동적으로 채워짐 -->
				</tbody>
			</table>
		</div>
	</div>
	<script>
		const computerId = ${computerId};
		const json = ${items};
		const items = new Map(Object.entries(${items}));
	</script>
	<script src="/js/facility/computer_reservation.js"></script>
</body>
</html>