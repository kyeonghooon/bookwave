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
		<div class="progress--container">
			<div class="progress--bar"></div>
		</div>
	</div>
</div>
<div class="toolbar--container">
	<div class="toolbar--circle" id="toolbar">
		<button id="toolbarButton" class="toolbar--btn">≡</button>
		<div id="toolbarContent" class="toolbar--content">
			<div class="toolbar--section">
				<input type="number" id="pageInput" placeholder="Page" />
				<button id="goToPage" class="toolbar--btn">Go</button>
			</div>
			<div class="toolbar--section">
				<input type="text" id="searchInput" placeholder="Search..." />
				<button id="searchBtn" class="toolbar--btn">Search</button>
			</div>
			<div id="searchNav" class="toolbar--section" style="display: none;">
				<button id="prevResult" class="toolbar--btn">&lt;</button>
				<span id="searchCounter" class="toolbar--btn">1/1</span>
				<button id="nextResult" class="toolbar--btn">&gt;</button>
				<button id="endSearch" class="toolbar--btn">End</button>
			</div>
		</div>
	</div>
	<div class="toolbar--btn" id="savePage">저장</div>
</div>

<script type="text/javascript">
	const ebookPath = "${ebookPath}";
	const lastPoint = "${ebook.lastPoint}";
	const bookId = "${ebook.bookId}";
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.5/jszip.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/epubjs@latest/dist/epub.min.js"></script>
<script src="/js/ebook.js"></script>
<%@ include file="../layout/footer.jsp"%>
