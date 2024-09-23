<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
    <meta charset="UTF-8">
    <title>My Lend List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">대출 도서 목록</h1>
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>도서 ID</th>
                    <th>제목</th>
                    <th>대출일</th>
                    <th>반납 기한</th>
                    <th>반납 / 연장</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="books" items="${myLendList}">
                    <tr>
                        <td><c:out value="${books.bookId}" /></td>
                        <td><c:out value="${books.title}" /></td>
                        <td><c:out value="${books.lendDate}" /></td>
                        <td><c:out value="${books.returnDate}" /></td>
                        <td>
                            <form action="/my-library/return/${books.id}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger btn-sm">반납</button>
                            </form>
                            <a href="/my-library/renew/${books.id}" class="btn btn-warning btn-sm">연장</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${not empty errorMessage}">
        <script>
            alert("${errorMessage}");
        </script>
    </c:if>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <%@ include file="../layout/footer.jsp"%>
</body>
