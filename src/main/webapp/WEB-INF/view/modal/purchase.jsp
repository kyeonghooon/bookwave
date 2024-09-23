<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="dynamicModal" class="modal">
	<div class="purchase--content">
		<h2 id="purchaseTitle">타이틀</h2>
		<p id="purchaseDescription">설명 텍스트</p>
		<span>wave:</span><input type="number" id="wave--input" placeholder="wave입력" value="0"/> 
		<span>mileage:</span><input type="number" id="mileage--input" placeholder="mileage입력" value="0"/> <br />
		<button class="btn btn-sm btn-primary mr-2" id="purchaseConfirmBtn">결정</button>
		<button class="btn btn-sm btn-danger ml-2" onclick="closeModal()">취소</button>
		<div class="mt-1">
			<span>wave 잔액 : ${principal.wave}</span><br>
			<span>mileage 잔액 : ${principal.mileage}</span><br>
		</div>
	</div>
</div>
<script type="text/javascript">
	const waveBalance = ${principal.wave};
	const mileageBalance = ${principal.mileage};
</script>
<link rel="stylesheet" type="text/css" href="/css/modal/purchase.css">
<script src="/js/modal/purchase.js"></script>
