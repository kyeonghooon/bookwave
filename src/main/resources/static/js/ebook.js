// ePub 파일을 로드하는 코드
// TODO url을 변경해야함
document.addEventListener("DOMContentLoaded", function() {
	const toggleToolbarBtn = document.querySelector(".toolbar--btn");
	const toolbarContainer = document.querySelector(".toolbar--circle");
	const book = ePub(ebookPath);

	toggleToolbarBtn.addEventListener("click", function() {
		toolbarContainer.classList.toggle("active");
	});

	// viewer에 ePub을 렌더링합니다.
	const rendition = book.renderTo("viewer", {
		// flow: "paginated",
		width: "100%",
		height: "100%",
	});

	const progressBar = document.querySelector(".progress--bar");
	const progressContainer = document.querySelector(".progress--container");

	book.ready
		.then(() => {
			// 위치 리스트를 생성 (기본 값 사용)
			return book.locations.generate(1000);
		})
		.then(() => {
			const totalPages = book.locations.total;

			// 초기 페이지 설정
			const lastLocation = book.locations.cfiFromPercentage(lastPoint);
			rendition.display(lastLocation); // 특정 페이지로 이동
			updateProgress(lastPoint);

			// 페이지가 바뀔 때마다 진행도 업데이트
			rendition.on("relocated", function(location) {
				const progress = book.locations.percentageFromCfi(location.start.cfi);
				updateProgress(progress);
			});

			// 진행도 바 클릭 시 해당 위치로 이동
			progressContainer.addEventListener("click", function(event) {
				const containerWidth = progressContainer.offsetWidth;
				const clickX = event.offsetX;
				const clickPercentage = clickX / containerWidth;

				const targetLocation = book.locations.cfiFromPercentage(clickPercentage);
				rendition.display(targetLocation);
			});

			// 다음 페이지로 이동
			document
				.getElementById("nextPage")
				.addEventListener("click", function() {
					rendition.next();
				});

			// 이전 페이지로 이동
			document
				.getElementById("prevPage")
				.addEventListener("click", function() {
					rendition.prev();
				});

			// 특정 페이지로 이동
			document
				.getElementById("goToPage")
				.addEventListener("click", function() {
					const page = document.getElementById("pageInput").value;

					// 페이지 번호를 위치로 변환
					const location = book.locations.cfiFromPercentage(page / totalPages);
					console.log(location);
					rendition.display(location); // 특정 페이지로 이동
				});

			// 검색 기능
			document
				.getElementById("searchBtn")
				.addEventListener("click", function() {
					const searchQuery = document.getElementById("searchInput").value;
					if (searchQuery.trim() === "") {
						alert("검색어를 입력하세요.");
						return;
					}
					rendition.search(searchQuery).then(function(results) {
						if (results.length > 0) {
							rendition.display(results[0].cfi); // 첫 번째 검색 결과로 이동
						} else {
							alert("검색 결과가 없습니다.");
						}
					}).catch(function(error) {
						console.error("검색 중 오류 발생:", error);
					});
				});

			// 북마크 기능
			document
				.getElementById("bookmarkBtn")
				.addEventListener("click", function() {
					const currentLocation = rendition.currentLocation();
					const bookmark = currentLocation.start.cfi;
					console.log("북마크 저장:", bookmark);
					alert("북마크가 저장되었습니다!");
					// 북마크를 로컬 저장소나 서버에 저장하는 로직을 추가할 수 있습니다.
				});
		});

	// 진행도를 업데이트하는 함수
	function updateProgress(percentage) {
		progressBar.style.width = `${percentage * 100}%`;
	}
});
