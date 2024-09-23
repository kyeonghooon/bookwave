<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>

	<form action="/review/${bookId}" method="post">
		<label for="score">점수</label> <select id="score" name="score" required>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select> <br> <br> <label for="content">리뷰</label><br>
		<textarea id="content" name="content" rows="4" cols="50" placeholder="리뷰 작성" required></textarea>
		<br> <br> <input type="submit" value="Submit Review">
	</form>

</body>
</html>
