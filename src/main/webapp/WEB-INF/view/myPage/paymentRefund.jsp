<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환불 요청 페이지</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f4f4f4;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	width: 800px;
	height: 500px;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	overflow-y: auto;
}

h1 {
	text-align: center;
	color: #333;
	margin-bottom: 20px;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

.form-group input, .form-group select {
	width: calc(100% - 22px);
	padding: 10px;
	font-size: 16px;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
}

.form-group input[type="submit"] {
	background-color: #4CAF50;
	color: #fff;
	border: none;
	cursor: pointer;
	font-size: 18px;
	width: 100%;
}

.form-group input[type="submit"]:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<div class="container">
    <h1>환불 요청</h1>
    <form action="/user-info/paymentRefund" method="post" onsubmit="return validateRefund()">
        <input type="hidden" name="id" value="${payment.id}">
        
        <div class="form-group">
            <label for="orderNumber">주문번호</label>
            <input type="text" id="orderNumber" name="orderNumber" value="${payment.orderId}" readonly>
        </div>
        
        <div class="form-group">
            <label for="paymentMethod">결제수단</label>
            <input type="text" id="paymentMethod" name="paymentMethod" value="${payment.method}" readonly>
        </div>
        
        <div class="form-group">
            <label for="totalAmount">총금액</label>
            <input type="text" id="totalAmount" name="totalAmount" value="${payment.totalAmount}" readonly>
        </div>
        
        <div class="form-group">
            <label for="cancelReason">환불 사유</label>
            <select id="cancelReason" name="cancelReason" required>
                <option value="">선택해 주세요</option>
                <option value="중복충전">중복충전</option>
                <option value="오류충전">오류충전</option>
                <option value="단순변심">단순변심</option>
                <option value="서비스 이용중지">서비스 이용중지</option>
                <option value="결제오류">결제오류</option>
            </select>
        </div>
        
        <div class="form-group">
            <button type="submit" style="margin-left: 7px">환불 요청</button>
        </div>
    </form>
</div>

</body>
<script>
    function validateRefund() {
        const walletWave = ${wallet.wave}; // 현재 지갑의 금액
        const totalAmount = ${payment.totalAmount}; // 환불 금액

        // 지갑 금액이 환불 금액보다 작으면 알림 표시
        if (walletWave < totalAmount) {
            alert("환불 요청이 불가능합니다. 잔액이 부족합니다.");
            return false; // 폼 제출 중단
        } else{
        	alert("환불 요청이 완료되었습니다.");	
        return true; // 조건을 만족하면 폼 제출
        }

    }
</script>
</html>
