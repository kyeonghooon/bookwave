<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>User ID</th>
				<th>Book ID</th>
				<th>score</th>
				<th>content</th>
				<th>created_at</th>
				<th>edited_at</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="review" items="${reviewList}">
				<tr>
					<td><c:out value="${review.userId}" /></td>
					<td><c:out value="${review.bookId}" /></td>
					<td><c:out value="${review.score}" /></td>
					<td><textarea><c:out value="${review.content}" /></textarea></td>
					<td><c:out value="${review.createdAt}" /></td>
					<td><c:out value="${review.editedAt}" /></td>
					<td><a href="/review/update/${review.id}">Edit</a>
						<form action="/review/delete/${review.id}" method="post">
							<button type="submit">Delete</button>
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>