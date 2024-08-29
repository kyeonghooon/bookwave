<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Lend List</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>Book ID</th>
				<th>RESERVATION_DATE</th>
				<th>WAIT_DATE</th>
				<th>STATUS</th>
				<th>취소</th>
				<th>대출</th>
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
							<button type="submit">취소</button>
						</form>
					</td>
					<c:if test="${books.status eq 1}">
						<td>
							<form action="/my-reserved/lend/${books.id}" method="post">
								<input type="hidden" name="userId" value="${books.userId}" /> <input type="hidden" name="bookId" value="${books.bookId}" />
								<button type="submit">대출</button>
							</form>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
