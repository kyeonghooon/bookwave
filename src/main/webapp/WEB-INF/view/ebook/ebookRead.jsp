<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#viewer {
	width: 100%;
	height: 600px;
	border: 1px solid #ccc;
}

#controls {
	margin-top: 10px;
	text-align: center;
}

button {
	padding: 10px;
	margin: 0 5px;
}
</style>
</head>
<body>
	<div id="viewer"></div>
	<div id="controls">
		<button id="prev">Previous</button>
		<button id="next">Next</button>
	</div>

	<!-- EPUB.js -->

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.5/jszip.min.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/epubjs@latest/dist/epub.min.js"></script>
	<script>
		// ePub 파일을 로드하는 코드
		var book = ePub("/ebooks/애국가2.epub");

		// viewer에 ePub을 렌더링합니다.
		var rendition = book.renderTo("viewer", {
			flow : "paginated",
			width : "100%",
			height : "100%",
		});

		rendition.display();

		document.getElementById("next").addEventListener("click", function() {
			rendition.next();
		});

		document.getElementById("prev").addEventListener("click", function() {
			rendition.prev();
		});
	</script>
</body>
</html>