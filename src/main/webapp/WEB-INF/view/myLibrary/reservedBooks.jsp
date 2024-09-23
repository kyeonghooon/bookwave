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
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">예약 목록</h1>
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>제목</th>
                    <th>예약일</th>
                    <th>기간</th>
                    <th>취소</th>
                    <th>대출</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="books" items="${myReservedList}">
                    <tr>
                        <td><c:out value="${books.title}" /></td>
                        <td><c:out value="${books.reservationDate}" /></td>
                        <td><c:out value="${books.waitDate}" /></td>
                        <td>
                            <form action="/my-reserved/cancel/${books.id}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger btn-sm">취소</button>
                            </form>
                        </td>
                        <c:choose>
                            <c:when test="${books.status eq 1}">
                                <td>
                                    <form action="/my-reserved/lend/${books.id}" method="post">
                                        <input type="hidden" name="userId" value="${books.userId}" />
                                        <input type="hidden" name="bookId" value="${books.bookId}" />
                                        <button type="submit" class="btn btn-success btn-sm">대출</button>
                                    </form>
                                </td>
                            </c:when>
                            <c:when test="${books.status eq 0}">
                                <td>대기순번: <c:out value="${countBeforeMap[books.bookId]}" /></td>
                            </c:when>
                        </c:choose>
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
