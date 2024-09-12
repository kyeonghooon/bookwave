<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="dynamicModal" class="modal">
	<div class="purchase--content">
		<h2 id="purchaseTitle">타이틀</h2>
		<p id="purchaseDescription">설명 텍스트</p>
		<input type="number" id="wave--input" placeholder="wave입력" /> 
		<input type="number" id="mileage--input" placeholder="mileage입력" /> <br />
		<button class="btn btn-sm btn-primary" id="purchaseConfirmBtn">결정</button>
		<button class="btn btn-sm btn-danger" onclick="closeModal()">취소</button>
	</div>
</div>

<link rel="stylesheet" type="text/css" href="/css/modal/purchase.css">
<script src="/js/modal/purchase.js"></script>
