$(document).ready(function() {
	// 마우스 오버 시 드롭다운 메뉴가 보이게 하기
	$(".dropdown").hover(
		function() {
			$(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(200);
		},
		function() {
			$(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(200);
		}
	);
});

document.addEventListener("DOMContentLoaded", function() {
	const subscribeBtn = document.getElementById("subscribeBtn");

	subscribeBtn.addEventListener("click", () => {
		const confirmed = confirm("9900 마일리지를 사용하여 구독 하시겠습니까?");
		if (!confirmed) {
			return;
		}
		fetch("/purchase/subscribe", {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => response.json())
			.then((data) => {
				alert(data.message);
				window.location.reload();
			})
			.catch((error) => {
				console.error("Error:", error);
				messageBox.innerText = "카테고리 생성 중 오류가 발생했습니다.";
			});
	});
});
