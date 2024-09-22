document.addEventListener("DOMContentLoaded", function() {
	const printButtons = document.querySelectorAll(".btn--print");

	printButtons.forEach(button => {
		button.addEventListener("click", function(event) {
			event.preventDefault();
			const printerId = this.getAttribute("data-id");

			// 서버로부터 파일을 다운로드 받아 iframe에 표시
			const url = `/facility/printer-file/${printerId}`;
			fetch(url)
				.then(response => response.blob())
				.then(blob => {
					// 파일을 URL로 변환
					const fileURL = URL.createObjectURL(blob);

					// iframe을 생성하여 파일을 브라우저에 표시
					const iframe = document.createElement('iframe');
					iframe.style.display = 'none';  // 화면에 보이지 않게 처리
					iframe.src = fileURL;
					document.body.appendChild(iframe);

					iframe.onload = function() {
						// iframe에서 프린트 기능 호출
						iframe.contentWindow.print();
					};
				})
				.catch(error => {
					console.error("파일을 다운로드하는 중 오류가 발생했습니다:", error);
				});
		});
	});
	
});