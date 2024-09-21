<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
<meta charset="UTF-8">
<title>My Lend List</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>Book ID</th>
				<th>Reservation Date</th>
				<th>Wait Date</th>
				<th>Status</th>
				<th>Cancel</th>
				<th>Lend</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="books" items="${myReservedList}">
				<tr>
					<td><c:out value="${books.bookId}" /></td>
					<td><c:out value="${books.reservationDate}" /></td>
					<td><c:out value="${books.waitDate}" /></td>
					<td><c:out value="${books.status}" /></td>

					<td>
						<form action="/my-reserved/cancel/${books.id}" method="post">
							<button type="submit">Cancel</button>
						</form>
					</td>

					<c:choose>
						<c:when test="${books.status eq 1}">
							<td>
								<form action="/my-reserved/lend/${books.id}" method="post">
									<input type="hidden" name="userId" value="${books.userId}" /> <input type="hidden" name="bookId" value="${books.bookId}" />
									<button type="submit">Lend</button>
								</form>
							</td>
						</c:when>
						<c:when test="${books.status eq 0}">
							<td><c:out value="${countBeforeMap[books.bookId]}" /></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
		</script>
	</c:if>
	<%@ include file="../layout/footer.jsp"%>
