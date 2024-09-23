<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
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
    <div class="container mt-5">
        <h2 class="mb-4">리뷰 작성</h2>
        <form action="/review/create/${bookId}" method="post" onsubmit="return validateForm();" class="needs-validation" novalidate>
            <div class="form-group">
                <label for="score">점수</label>
                <select id="score" name="score" class="form-control" required>
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
                </select>
                <div class="invalid-feedback">점수를 선택해주세요.</div>
            </div>
            <div class="form-group">
                <label for="content">리뷰</label>
                <textarea id="content" name="content" class="form-control" rows="4" placeholder="리뷰 작성" required></textarea>
                <div class="invalid-feedback">빈 리뷰는 작성할 수 없습니다.</div>
            </div>
            <button type="submit" class="btn btn-primary">Submit Review</button>
        </form>

        <c:if test="${not empty errorMessage}">
            <script>
                alert("${errorMessage}");
            </script>
        </c:if>
    </div>
    <%@ include file="../layout/footer.jsp"%>
</body>
