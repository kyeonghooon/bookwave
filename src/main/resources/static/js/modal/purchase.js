function openDynamicModal(title, description, price, confirmCallback) {
	document.getElementById("purchaseTitle").innerText = title;
	document.getElementById("purchaseDescription").innerText = description;

	const confirmBtn = document.getElementById("purchaseConfirmBtn");
	confirmBtn.onclick = function() {
		const wave = document.getElementById("wave--input").value;
		const mileage = document.getElementById("mileage--input").value;
		const total = parseInt(wave) + parseInt(mileage);

		if (total === price) {
			confirmCallback(wave, mileage);
			closeModal();
		} else {
			alert(`총합이 ${price}이어야 합니다.`);
		}
	};

	document.getElementById("dynamicModal").style.display = "flex";
}

function closeModal() {
	document.getElementById("dynamicModal").style.display = "none";
}