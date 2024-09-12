<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.library.bookwave.repository.model.Book"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Book Detail</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }
    .book-detail {
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .book-detail h1 {
        margin: 0;
        font-size: 2.5em;
        color: #333;
    }
    .book-detail p {
        margin: 10px 0;
        line-height: 1.6;
    }
    .book-detail strong {
        color: #555;
    }
    .book-detail img {
        max-width: 200px;
        height: auto;
        border-radius: 8px;
        margin: 20px 0;
    }
    .book-detail a {
        display: inline-block;
        padding: 10px 20px;
        color: #fff;
        background-color: #007bff;
        text-decoration: none;
        border-radius: 5px;
        font-weight: bold;
    }
    .book-detail a:hover {
        background-color: #0056b3;
    }
    .book-detail .section-header {
        margin-top: 20px;
        padding-bottom: 10px;
        border-bottom: 2px solid #eee;
        color: #007bff;
    }
</style>
</head>
<body>
    <div class="container">
        <div class="book-detail">
            <h1>${book.title}</h1>
            <div class="section-header">도서 상세보기 페이지</div>
            <p><strong>Author:</strong> ${book.author}</p>
            <p><strong>Publisher:</strong> ${book.publisher}</p>
            <p><strong>Category:</strong> ${book.category}</p>
            <p><strong>Published Date:</strong> ${book.publishDate}</p>
            <p><strong>Description:</strong> ${book.description}</p>
            <p><strong>Total Stock:</strong> ${book.totalStock}</p>
            <p><strong>Current Stock:</strong> ${book.currentStock}</p>
            <p><strong>eBook:</strong> ${book.ebook == 1 ? 'Available' : 'Not Available'}</p>
            <c:if test="${book.ebook == 1}">
                <a href="${book.ebookPath}">Download eBook</a>
            </c:if>
            <p><strong>Likes:</strong> ${book.likes}</p>
            <p><strong>Score:</strong> ${book.score}</p>
            <p><strong>Created At:</strong> <fmt:formatDate value="${book.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
            <c:if test="${book.cover != null && book.cover != ''}">
                <img src="${book.cover}" alt="${book.title}">
            </c:if>
        </div>
    </div>
</body>
</html>
