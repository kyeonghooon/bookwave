<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body {
	display: flex;
	flex-direction: column;
	margin: 0;
	font-family: Arial, sans-serif;
	min-height: 100vh;
}

.header {
	background-color: #f8f8f8;
	border-bottom: 1px solid #ddd;
	padding: 10px;
	box-sizing: border-box;
	display: flex;
	justify-content: center;
	align-items: center;
}

.header form {
	display: flex;
	gap: 10px;
	align-items: center;
}

.sidebar {
	width: 200px;
	padding: 20px;
	border-right: 1px solid #ddd;
	box-sizing: border-box;
	flex-shrink: 0;
}

.sidebar a {
	display: block;
	padding: 10px;
	margin-bottom: 5px;
	text-decoration: none;
	color: #333;
	border-radius: 5px;
	transition: background-color 0.3s, color 0.3s;
}

.sidebar a.active {
	background-color: #333;
	color: white;
}

.sidebar a:hover {
	background-color: #f0f0f0;
}

.container {
	flex: 1;
	display: grid;
	grid-template-columns: repeat(6, 1fr);
	gap: 10px;
	padding: 20px;
	width: 100%;
	justify-content: center;
}

.book--item {
	width: 190px;
	height: 330px;
	text-align: center;
	border: 1px solid #ddd;
	padding: 10px;
	border-radius: 5px;
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.book--item img {
	width: 170px;
	height: 250px;
	object-fit: cover;
	border-radius: 5px;
}

.book--title {
	font-size: 14px;
	margin-top: 10px;
	display: inline-block;
	width: 100%;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.page--wrapper {
	display: flex;
	flex: 1;
}

.main--content {
	flex: 1;
}

.disabled-button {
	pointer-events: none;
	color: gray;
	text-decoration: none;
}
</style>
</head>
<body>

	<div class="page--wrapper">

		<div class="main--content">


			<div style="display: flex; flex-direction: row;">
				<div id="chart_div"></div>
				<div id="chart_div2"></div>
			</div>

			<div class="container">
				<c:forEach var="book" items="${favoriteList}">
					<div class="book--item">
						<img src="${book.cover}" alt="${book.title}" />
						<div class="book--title">${book.title}</div>
					</div>
				</c:forEach>
			</div>

		</div>
	</div>

</body>
<%@ include file="../layout/footer.jsp"%>