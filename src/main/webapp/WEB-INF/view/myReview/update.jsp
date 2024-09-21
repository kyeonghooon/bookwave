<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/review/update/${review.id}" method="POST">
		<label for="title">Score</label> <input type="number" id="score" name="score" value="${review.score}"> <label for="content">Content:</label>
		<textarea name="content" id="content" rows="10" cols="30">${review.content}</textarea>
		<button type="submit">Submit</button>
	</form>
	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
		</script>
	</c:if>
	<%@ include file="../layout/footer.jsp"%>