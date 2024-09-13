<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wave 충전</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

.container {
	max-width: 500px;
	margin: 0 auto;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 5px;
}

h1 {
	text-align: center;
}

.form-group {
	margin-bottom: 15px;
}

label {
	display: block;
	margin-bottom: 5px;
}

select, input[type="submit"] {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #4CAF50;
	color: white;
	border: none;
	padding: 10px 15px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	border-radius: 5px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Wave 충전</h1>
		<form action="/payment/checkout" method="get">
			<input type="hidden" value="충전" name="orderName">
			<div class="form-group">
				<label for="amount">충전할 금액을 선택하세요:</label> <select id="amount" name="amount" required>
					<option value="">선택하세요</option>
					<option value="5000">5,000원 (+ 5,000 wave )</option>
					<option value="10000">10,000원 (+ 10,000 wave )</option>
					<option value="20000">20,000원 (+ 20,000 wave )</option>
					<option value="30000">30,000원 (+ 20,000 wave )</option>
					<option value="50000">50,000원 (+ 50,000 wave )</option>
				</select>
			</div>
			<input type="submit" value="충전하기">
		</form>
	</div>
</body>
</html>
