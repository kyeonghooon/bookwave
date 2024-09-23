<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
    <meta charset="UTF-8">
    <title>Review List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        textarea {
            width: 100%;
            resize: none;
            vertical-align: middle;
            border: none; /* Remove border */
            background: none; /* Remove background color */
            padding: 0; /* Remove padding */
            margin: 0; /* Remove margin */
            font-family: inherit; /* Use the same font as the rest of the table */
            font-size: inherit; /* Use the same font size as the rest of the table */
            color: inherit; /* Use the same text color as the rest of the table */
        }
        textarea:focus {
            outline: none; /* Remove focus outline */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">리뷰 관리</h1>
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>책 ID</th>
                    <th>점수</th>
                    <th>내용</th>
                    <th>작성일</th>
                    <th>최종 수정일</th>
                    <th>수정/삭제</th>
                    <th>마일리지 받기</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="review" items="${reviewList}">
                    <tr>
                        <td><c:out value="${review.bookId}" /></td>
                        <td><c:out value="${review.score}" /></td>
                        <td><textarea readonly rows="3"><c:out value="${review.content}" /></textarea></td>
                        <td><c:out value="${review.createdAt}" /></td>
                        <td><c:out value="${review.editedAt}" /></td>
                        <td>
                            <a href="/review/update/${review.id}" class="btn btn-primary btn-sm">수정</a>
                            <form action="/review/delete/${review.id}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                            </form>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${review.status == 1}">
                                    <span class="text-muted">이미 마일리지를 받으셨습니다.</span>
                                </c:when>
                                <c:when test="${review.daysSinceCreated >= 7}">
                                    <form action="/review/claim/${review.id}" method="post">
                                        <button type="submit" class="btn btn-success btn-sm">마일리지 받기</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-warning">${review.daysUntilClaim}일 후 받으실 수 있습니다.</span>
                                </c:otherwise>
                            </c:choose>
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
