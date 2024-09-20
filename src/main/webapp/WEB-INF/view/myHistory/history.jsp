<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	// Load the Visualization API and the corechart package.
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});

	// Set a callback to run when the Google Visualization API is loaded.
	google.charts.setOnLoadCallback(drawChart);
	google.charts.setOnLoadCallback(drawChart2);

	// Callback that creates and populates a data table,
	// instantiates the pie chart, passes in the data and
	// draws it.
	function drawChart() {

		// Create the data table.
		var data = new google.visualization.DataTable();
		data.addColumn('string', '카테고리');
		data.addColumn('number', '수');
        data.addRows([
            <c:forEach var="item" items="${categoryData}">
              ['${item.key}', ${item.value}],
            </c:forEach>
          ]);

		// Set chart options
		var options = {
			'title' : '카테고리 별 차트 (총 데이터 수: ${totalCountCategory})',
			'width' : 600,
			'height' : 450
		};

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.PieChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
	function drawChart2() {

		// Create the data table.
		var data = new google.visualization.DataTable();
		data.addColumn('string', '월');
		data.addColumn('number', '수');
        data.addRows([
            <c:forEach var="item" items="${monthlyData}">
              ['${item.key}', ${item.value}],
            </c:forEach>
          ]);

		// Set chart options
		var options = {
			'title' : '월 별 차트 (총 데이터 수: ${totalCountMonth})',
			'width' : 600,
			'height' : 450
		};

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.PieChart(document
				.getElementById('chart_div2'));
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
				<a href="<c:url value='/history/list'><c:param name='type' value='all'/></c:url>">All</a> <a href="<c:url value='/history/list'><c:param name='type' value='book'/></c:url>">Books</a>
				<a href="<c:url value='/history/list'><c:param name='type' value='ebook'/></c:url>">E-books</a>

				<form action="<c:url value='/history/list'/>" method="get" style="display: flex; align-items: center;">
					<input type="hidden" name="type" value="${param.type}"> <input type="text" id="searchInput" name="search" placeholder="Search..." value="${param.search}">
					<button type="submit">Search</button>
				</form>
			</div>

			<div style="display: flex; flex-direction: row;">
				<div id="chart_div"></div>
				<div id="chart_div2"></div>
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
								<a href="/history/review/${history.id}">리뷰 작성</a>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
			</div>

		</div>
	</div>
</body>

</html>
