<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../layout/header.jsp"%>
<head>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages' : [ 'corechart' ]});
	google.charts.setOnLoadCallback(drawChart);
	google.charts.setOnLoadCallback(drawChart2);

	function drawChart() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', '카테고리');
		data.addColumn('number', '수');
        data.addRows([
            <c:forEach var="item" items="${categoryData}">
              ['${item.key}', ${item.value}],
            </c:forEach>
        ]);

		var options = {
			'title' : '카테고리 별 차트 (총 데이터 수: ${totalCountCategory})',
			'width' : 600,
			'height' : 250
		};

		var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
		chart.draw(data, options);
	}

	function drawChart2() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', '월');
		data.addColumn('number', '수');
        data.addRows([
            <c:forEach var="item" items="${monthlyData}">
              ['${item.key}', ${item.value}],
            </c:forEach>
        ]);

		var options = {
			'title' : '월 별 차트 (총 데이터 수: ${totalCountMonth})',
			'width' : 600,
			'height' : 250
		};

		var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
		chart.draw(data, options);
	}
</script>
<meta charset="UTF-8">
<title>Book List</title>
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

.button {
    background-color: #333;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 15px; /* Added horizontal padding */
    cursor: pointer;
    text-decoration: none;
    transition: background-color 0.3s;
    margin-right: 10px; /* Add margin between buttons */
    white-space: nowrap; /* Prevent text from wrapping */
}

.button:hover {
    background-color: #555;
}

.search--form {
	display: flex;
	align-items: center;
}

.search--form input[type="text"] {
	padding: 10px;
	margin-right: 5px;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.search--form button {
	background-color: #333;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 10px 15px; /* Adjust padding for the search button */
	cursor: pointer;
	transition: background-color 0.3s;
}

.search--form button:hover {
	background-color: #555;
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
			    <div style="display: flex; align-items: center; margin-bottom: 20px;">
			        <a href="<c:url value='/history/list'><c:param name='type' value='all'/></c:url>" class="button">전체</a>
			        <a href="<c:url value='/history/list'><c:param name='type' value='book'/></c:url>" class="button">책</a>
			        <a href="<c:url value='/history/list'><c:param name='type' value='ebook'/></c:url>" class="button">전자책</a>
			        <form action="<c:url value='/history/list'/>" method="get" class="search--form" style="margin-left: auto;">
			            <input type="hidden" name="type" value="${param.type}">
			            <input type="text" maxlength="20" id="searchInput" name="search" placeholder="Search..." value="${param.search}">
			            <button type="submit" class="button">검색</button>
			        </form>
			    </div>
			</div>

			<div class="container">
				<c:forEach var="history" items="${myHistoryList}">
					<div class="book--item">
						<img src="${history.cover}" alt="${history.title}" />
						<div class="book--title">${history.title}</div>
						<c:choose>
							<c:when test="${fn:contains(reviewedBookIds, history.id)}">
								<a class="review-button disabled">이미 작성하셨습니다</a>
							</c:when>
							<c:otherwise>
								<a href="/review/create/${history.id}">리뷰 작성</a>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
		</script>
	</c:if>
	<%@ include file="../layout/footer.jsp"%>
