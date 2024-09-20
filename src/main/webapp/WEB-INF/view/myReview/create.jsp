<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Submit Review</title>
<script type="text/javascript">
    function validateForm() {
        var score = document.getElementById("score").value;
        var content = document.getElementById("content").value;

        // Validate score
        if (score == "" || score < 1 || score > 10) {
            alert("1 - 10 사이의 점수를 선택해주세요");
            return false;
        }

        if (content.trim() == "") {
            alert("빈 리뷰는 작성할 수 없습니다.");
            return false;
        }

        return true;
    }
</script>
</head>
<body>

	<form action="/review/create/${bookId}" method="post" onsubmit="return validateForm();">
		<label for="score">점수</label> <select id="score" name="score" required>
			<option value="">점수 선택</option>
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
	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
		</script>
	</c:if>
</body>
</html>
