<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wave, Mileage 설정</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 400px;
	margin: 50px auto;
	padding: 20px;
	background-color: white;
	border-radius: 8px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
	color: #333;
	margin-bottom: 20px;
}

.form-group {
	margin-bottom: 15px;
}

label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #555;
}

input[type="text"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	transition: border-color 0.3s;
}

input[type="text"]:focus {
	border-color: #4CAF50;
	outline: none;
}

input[type="submit"] {
	width: 100%;
	padding: 10px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s;
}

input[type="submit"]:hover {
	background-color: #45a049;
}
</style>
<script>
	function confirmAndClose(event) {
		if (!confirm("정말 지급하시겠습니까?")) {
			event.preventDefault();
		}
	}
</script>
</head>
<body>
	<div class="container">
		<h1>전체 포인트 지급</h1>
		<form action="/admin/user-point" method="post" onsubmit="confirmAndClose(event);" onsubmit="this.submit(); alert('포인트가 지급되었습니다.'); window.close();">
			<input type="hidden" value="지급" name="description">
			<div class="form-group">
				<label for="wave">Wave:</label>
				<input type="text" id="wave" name="wave" placeholder="지급할 Wave 입력" required>
			</div>
			<div class="form-group">
				<label for="mileage">Mileage:</label>
				<input type="text" id="mileage" name="mileage" placeholder="지급할 Mileage 입력" required>
			</div>
			<input type="submit" value="지급하기">
		</form>
	</div>
</body>
</html>
