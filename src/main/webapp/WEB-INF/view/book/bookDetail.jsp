<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Book Detail</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }

    .container {
        max-width: 900px;
        margin: 0 auto;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .book--cover {
        width: 200px;
        height: auto;
        border-radius: 5px;
        margin-right: 20px;
        float: left;
    }

    .book--details {
        overflow: hidden;
    }

    .book--title {
        font-size: 24px;
        margin-top: 0;
    }

    .book--info {
        margin-top: 10px;
    }

    .book--info dt {
        font-weight: bold;
        margin-top: 10px;
    }

    .book--info dd {
        margin: 0 0 10px 0;
    }

    .description {
        margin-top: 20px;
    }

    .back--link {
        display: inline-block;
        margin-top: 20px;
        font-size: 14px;
        color: #007bff;
        text-decoration: none;
    }

    .back--link:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>

    <div class="container">
        <img src="${book.cover}" alt="${book.title} Cover" class="book--cover" />

        <div class="book--details">
            <h1 class="book--title">${book.title}</h1>

            <dl class="book--info">
                <dt>저자: ${book.author}</dt>
         

                <dt>출판일: ${book.publishDate}</dt>

                <dt>카테고리: ${book.category}</dt>

                <dt>출판사: ${book.publisher}</dt>
            </dl>

            <div class="description">
                <h2>설명</h2>
                <p>${book.description}</p>
            </div>
        </div>

        <a class="back--link" onclick="window.history.back();">Back to Previous Page</a>
    </div>

</body>
</html>
