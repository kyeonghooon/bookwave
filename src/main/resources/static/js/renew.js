document.addEventListener("DOMContentLoaded", function() {

	const radioButtons = document.querySelectorAll('input[name="day"]');
	const calculatedValueDisplay = document.getElementById('calculated-value');
	const pointInput = document.getElementById('point');
	const checkBtn = document.getElementById('checkBtn');

	radioButtons.forEach(radio => {
		radio.addEventListener('change', () => {
			const selectedDay = radio.value;
			const calculatedValue = selectedDay * 100;
			calculatedValueDisplay.textContent = calculatedValue;
			pointInput.value = calculatedValue; // Update the hidden input
		});
	});
	checkBtn.addEventListener('click', () => {
		const selectedDay = document.querySelector('input[name="day"]:checked').value;
		openPurchaseModal(selectedDay);
	});

	function openPurchaseModal(day) {
		const price = day * 100;
		openDynamicModal("결제하기", price + "을 두 값으로 나누어 입력하세요.", price, function(waveInput, mileageInput) {
			const key = "renew" + day;
			const itemId = items.get(key);
			const url = `/purchase/${itemId}`;
			fetch(url, {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ wave: waveInput, mileage: mileageInput, bookId: bookId }),
			})
				.then((response) => response.json())
				.then((data) => {
					if (data.success) {
						alert(data.message);
						history.back();
					} else {
						alert(data.message);
					}
				})
				.catch((error) => {
					console.error("Error:", error);
					alert("구매 중 오류가 발생했습니다.");
				});
		});
	}
});