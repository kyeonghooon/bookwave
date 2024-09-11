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

.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 5px;
	padding: 10px;
	background-color: #f8f8f8;
	border-top: 1px solid #ddd;
}

.pagination a {
	padding: 8px 15px;
	text-decoration: none;
	border: 1px solid #ddd;
	border-radius: 5px;
	color: #333;
	font-size: 14px;
	transition: background-color 0.3s, color 0.3s;
}

.pagination a.active {
	background-color: #333;
	color: white;
}

.pagination a.disabled {
	color: #ddd;
	border-color: #ddd;
	cursor: not-allowed;
}

.pagination .page-numbers {
	display: flex;
	gap: 5px;
}

.page--wrapper {
	display: flex;
	flex: 1;
}

.main--content {
	flex: 1;
}
</style>
</head>
<body>
	<div class="page--wrapper">

		<div class="main--content">
			<div style="display: flex; flex-direction: row;">
				<a href="/history/list?type=all">asdf</a>
				<a href="/history/list?type=book">asdf</a>
				<a href="/history/list?type=ebook">asdf</a>
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
						<a href="/history/review/${history.id}">리뷰 작성</a>
					</div>
				</c:forEach>
			</div>

		</div>
	</div>
</body>
</html>
