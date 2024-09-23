document.getElementById("submitBtn").addEventListener("click", function() {
	const fileInput = document.getElementById("file");
	const pagesInput = document.getElementById("pages").value;
	const file = fileInput.files[0];
	
	const allowedExtension = /\.pdf$/i;  // PDF 파일만 허용
	if (!allowedExtension.test(file.name)) {
		alert("PDF 파일만 업로드 가능합니다.");
		return;
	}

	const price = pagesInput * 100;
	openDynamicModal("결제하기", price + "을 두 값으로 나누어 입력하세요.", price, function(waveInput, mileageInput) {

		const formData = new FormData();
		formData.append("file", fileInput.files[0]);
		formData.append("pages", pagesInput);
		formData.append("wave", waveInput);
		formData.append("mileage", mileageInput);
		const itemId = items.get("print");
		const url = `/purchase/${itemId}`;
		fetch(url, {
			method: "POST",
			body: formData
		})
			.then(response => response.json())  // 응답 텍스트를 가져옴
			.then(data => {
				alert("요청이 완료되었습니다. 원하실 때 데스크로 찾아와주세요.");
				location.reload();
			})
			.catch(error => {
				console.error("Error:", error);
				alert("구매 중 오류가 발생했습니다.");
			});
	});
});

