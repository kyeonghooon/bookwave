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
				<th>Status</th>
				<th>Lend Date</th>
				<th>Return Date</th>
				<th>반납</th>
				<th>연장</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="books" items="${myLendList}">
				<tr>
					<td><c:out value="${books.bookId}" /></td>
					<td><c:out value="${books.status}" /></td>
					<td><c:out value="${books.lendDate}" /></td>
					<td><c:out value="${books.returnDate}" /></td>
					<td><form action="/my-library/return/${books.id}" method="post">
							<button type="submit">반납</button>
						</form></td>
					<td><a href="/my-library/renew/${books.id}">연장</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
