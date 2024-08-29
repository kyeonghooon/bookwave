<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="/css/ebook.css" />
<div class="d-flex justify-content-center mt-3">
	<h2>제목</h2>
</div>
<div class="d-flex justify-content-center">
	<div id="viewer">
		<button id="prevPage" class="nav--button left--arrow">&lt;</button>
		<button id="nextPage" class="nav--button right--arrow">&gt;</button>
	</div>
</div>
<div class="d-flex justify-content-center mt-3">
	<input type="number" id="pageInput" placeholder="page" />
	<button id="goToPage" class="btn btn-primary">이동</button>
</div>

<script type="text/javascript">
	const ebookPath = "${ebookPath}";
	const lastPoint = "${ebook.lastPoint}";
	console.log("ebookPath", ebookPath);
	console.log("lastPoint", lastPoint);
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.5/jszip.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/epubjs@latest/dist/epub.min.js"></script>
<script src="/js/ebook.js"></script>
<%@ include file="../layout/footer.jsp"%>
