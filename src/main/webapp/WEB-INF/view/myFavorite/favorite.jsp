<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../layout/header.jsp"%>
<head>
    <meta charset="UTF-8">
    <title>관심도서 목록</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
            flex-direction: column;
            margin: 0;
            font-family: Arial, sans-serif;
            min-height: 100vh;
            padding: 20px;
        }

        .header {
            background-color: #f8f8f8;
            border-bottom: 1px solid #ddd;
            padding: 10px;
            box-sizing: border-box;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .sidebar {
            width: 200px;
            padding: 20px;
            border-right: 1px solid #ddd;
            box-sizing: border-box;
            flex-shrink: 0;
        }

        .book--item {
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 5px;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            text-align: center;
            transition: box-shadow 0.3s;
        }

        .book--item img {
            width: 100%;
            height: auto;
            object-fit: cover;
            border-radius: 5px;
        }

        .book--title {
            font-size: 14px;
            margin-top: 10px;
            display: inline-block;
            width: 100%;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(190px, 1fr));
            gap: 10px;
            padding: 20px;
        }

        .disabled-button {
            pointer-events: none;
            color: gray;
            text-decoration: none;
        }
    </style>
</head>
<body>

    <div class="page--wrapper">
        <div class="main--content">
            <div class="container">
            <h1 class="mb-4">관심 목록</h1>
            </div>
            <div class="container">
                <c:forEach var="book" items="${favoriteList}">
                    <div class="book--item">
                        <img src="${book.cover}" alt="${book.title}" />
                        <div class="book--title">${book.title}</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
<%@ include file="../layout/footer.jsp"%>
