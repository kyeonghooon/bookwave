<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>포인트 지급 완료</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f4f4f4;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background-color: white;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

h1 {
	color: #4CAF50;
}

p {
	color: #555;
}
</style>
<script>
	function closeWindow() {
		alert("포인트 지급이 완료되었습니다.");
		window.close();
	}
	window.onload = closeWindow; // 페이지 로드 후 창 닫기 함수 실행
</script>
</head>
<body>
	<div class="container">
		<h1>포인트 지급 완료</h1>
		<p>모든 유저에게 포인트가 지급되었습니다.</p>
	</div>
</body>
</html>
