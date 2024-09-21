<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
<meta charset="UTF-8">
<title>Review List</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>User ID</th>
				<th>Book ID</th>
				<th>Score</th>
				<th>Content</th>
				<th>Created At</th>
				<th>Edited At</th>
				<th>Actions</th>
				<th>마일리지 받기</th>
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
					<td><c:choose>
							<c:when test="${review.status == 1}">
								<span>이미 마일리지를 받으셨습니다.</span>
							</c:when>
							<c:when test="${review.daysSinceCreated >= 7}">
								<form action="/review/claim/${review.id}" method="post">
									<button type="submit">마일리지 받기</button>
								</form>
							</c:when>
							<c:otherwise>
								<span>${review.daysUntilClaim}일 후 받으실 수 있습니다.</span>
							</c:otherwise>
						</c:choose></td>
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