<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ include file="../modal/purchase.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/facility/printer.css">

<div class="container">
	<div class="title--section text-center mb-4">
		<h1>프린트 요청</h1>
	</div>
	<form id="printRequestForm">
		<div>
			<label for="file">파일 업로드:</label> <input type="file" id="file" name="file" required>
		</div>

		<div>
			<label for="pages">페이지 수:</label> <input type="number" id="pages" name="pages" min="1" required>
		</div>

		<button type="button" id="submitBtn">프린트 요청</button>
	</form>

</div>
<script>
	const json = ${items};
	const items = new Map(Object.entries(${items}));
</script>
<script src="/js/facility/printer.js"></script>
<%@ include file="../layout/footer.jsp"%>