	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
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
                                    <form action="/my-reserved/lend/${books.bookId}" method="post">
                                        <input type="hidden" name="id" value="${books.id}" />
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
    <%@ include file="../layout/footer.jsp"%>
</body>
