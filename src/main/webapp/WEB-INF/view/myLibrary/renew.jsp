<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Select Day and Submit</title>
<style>
/* Style the container box */
.day-selector-container {
	border: 2px solid #ccc;
	padding: 10px;
	display: inline-block;
	border-radius: 5px;
}

/* Style the radio buttons to be horizontal */
.day-selector-container label {
	margin-right: 15px;
	cursor: pointer;
}

/* Style the number display */
.number-display {
	margin-top: 10px;
	font-size: 20px;
	font-weight: bold;
}

/* Center the content */
.form-container {
	text-align: center;
	margin-top: 50px;
}
</style>
</head>
<body>
	<div class="form-container">
		<h2>연장할 기간을 선택하세요</h2>
		<form action="/my-library/renew/${bookId}" method="POST">
			<!-- Container for the radio buttons -->
			<div class="day-selector-container">
				<!-- Radio buttons for selecting a day -->
				<label> <input type="radio" name="day" value="1" required> 1일
				</label> <label> <input type="radio" name="day" value="2"> 2일
				</label> <label> <input type="radio" name="day" value="3"> 3일
				</label> <label> <input type="radio" name="day" value="4"> 4일
				</label> <label> <input type="radio" name="day" value="5"> 5일
				</label> <label> <input type="radio" name="day" value="6"> 6일
				</label> <label> <input type="radio" name="day" value="7"> 7일
				</label>
			</div>

			<!-- Hidden input to store the calculated point -->
			<input type="hidden" id="point" name="point" value="0">

			<!-- Display for the calculated number -->
			<div class="number-display">
				사용되는 포인트: <span id="calculated-value">0</span>
			</div>

			<!-- Submit button -->
			<button type="submit">확인</button>
		</form>
	</div>

	<script>
        // JavaScript to update the number display based on the selected day
        const radioButtons = document.querySelectorAll('input[name="day"]');
        const calculatedValueDisplay = document.getElementById('calculated-value');
        const pointInput = document.getElementById('point');

        radioButtons.forEach(radio => {
            radio.addEventListener('change', () => {
                const selectedDay = radio.value;
                const calculatedValue = selectedDay * 100;
                calculatedValueDisplay.textContent = calculatedValue;
                pointInput.value = calculatedValue; // Update the hidden input
            });
        });
    </script>
</body>
</html>
