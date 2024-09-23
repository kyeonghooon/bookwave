<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<body>
    <div class="container mt-4">
        <h2>리뷰 수정</h2>
        <form action="/review/update/${review.id}" method="POST">
            <div class="form-group">
                <label for="score">Score</label>
                <input type="number" id="score" name="score" class="form-control"  value="${review.score}" required max="10" min="1">
            </div>
            <div class="form-group">
                <label for="content">Content</label>
                <textarea name="content" id="content" rows="10" class="form-control" required>${review.content}</textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <c:if test="${not empty errorMessage}">
            <script>
                alert("${errorMessage}");
            </script>
        </c:if>
    </div>
    <%@ include file="../layout/footer.jsp"%>
</body>
