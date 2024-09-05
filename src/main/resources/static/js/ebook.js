// ePub 파일을 로드하는 코드
// TODO url을 변경해야함
document.addEventListener("DOMContentLoaded", function() {
	const toggleToolbarBtn = document.querySelector(".toolbar--btn");
	const toolbarContainer = document.querySelector(".toolbar--circle");
	const searchNav = document.getElementById("searchNav");
	const searchCounter = document.getElementById("searchCounter");
	const endSearchBtn = document.getElementById("endSearch");
	const saveBtn = document.getElementById("savePage");
	const book = ePub(ebookPath);
	let searchResults = [];
	let currentSearchIndex = 0;
	let loadedSections = {}; // 로드된 섹션을 저장하기 위한 객체
	let highlightIds = []; // 하이라이트 ID를 저장하기 위한 배열
	let currentPageProgress = 0; // 현재 페이지의 진행도를 저장하는 변수

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
				currentPageProgress = book.locations.percentageFromCfi(
					location.start.cfi
				);
				updateProgress(currentPageProgress);
			});

			// 진행도 바 클릭 시 해당 위치로 이동
			progressContainer.addEventListener("click", function(event) {
				const containerWidth = progressContainer.offsetWidth;
				const clickX = event.offsetX;
				const clickPercentage = clickX / containerWidth;

				const targetLocation =
					book.locations.cfiFromPercentage(clickPercentage);
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

			// 저장 버튼 클릭 시 동작
			saveBtn.addEventListener("click", function() {
				saveCurrentProgress(currentPageProgress);
			});

			function saveCurrentProgress(progress) {
				const url = "/ebook/save/" + bookId;
				fetch(url, {
					method: "POST",
					headers: {
						"Content-Type": "application/json",
					},
					body: JSON.stringify(progress),
				})
					.then(async (response) => {
						if (response.ok) {
							const message = await response.text();
							alert(message);
						} else {
							const errorMessage = await response.text();
							throw new Error(errorMessage || "저장 실패");
						}
					})
					.catch((error) => {
						alert(error.message);
					});
			}

			// 검색 버튼 클릭 시 동작
			document
				.getElementById("searchBtn")
				.addEventListener("click", function() {
					// 기존 하이라이트 제거
					highlightIds.forEach((id) => {
						rendition.annotations.remove(id.cfiRange, "highlight");
					});
					highlightIds = []; // 하이라이트 ID 배열 초기화

					// 검색 시작
					const searchQuery = document
						.getElementById("searchInput")
						.value.trim();
					if (searchQuery === "") {
						alert("검색어를 입력하세요.");
						return;
					}

					let searchPromises = [];
					searchResults = [];
					currentSearchIndex = 0;

					book.spine.each(function(section) {
						// 이미 로드된 섹션인지 확인
						if (!loadedSections[section.href]) {
							let searchPromise = section
								.load(book.load.bind(book))
								.then(() => {
									let results = section.find(searchQuery);
									if (results.length > 0) {
										searchResults = searchResults.concat(results);
									}
									// 섹션을 메모리에 유지
									loadedSections[section.href] = section;
								})
								.catch((error) => {
									console.error("섹션 로드 중 오류 발생:", error);
								});

							searchPromises.push(searchPromise);
						} else {
							// 이미 로드된 섹션을 사용
							let results = loadedSections[section.href].find(searchQuery);
							if (results.length > 0) {
								searchResults = searchResults.concat(results);
							}
						}
					});

					Promise.all(searchPromises).then(() => {
						if (searchResults.length > 0) {
							searchNav.style.display = "flex";
							highlightResults();
							moveToSearchResult(currentSearchIndex);
						} else {
							alert("검색 결과가 없습니다.");
						}
					});
				});

			// End 버튼 클릭 시 동작
			endSearchBtn.addEventListener("click", function() {
				searchResults = [];
				currentSearchIndex = 0;
				searchNav.style.display = "none"; // 검색 내비게이션 숨김
				highlightIds.forEach((id) => {
					rendition.annotations.remove(id.cfiRange, "highlight");
				});
				document.getElementById("searchInput").value = "";
			});
			// 이전 검색 결과로 이동
			document
				.getElementById("prevResult")
				.addEventListener("click", function() {
					if (currentSearchIndex > 0) {
						currentSearchIndex--;
						moveToSearchResult(currentSearchIndex);
					} else {
						currentSearchIndex = searchResults.length - 1;
						moveToSearchResult(currentSearchIndex);
					}
				});

			// 다음 검색 결과로 이동
			document
				.getElementById("nextResult")
				.addEventListener("click", function() {
					if (currentSearchIndex < searchResults.length - 1) {
						currentSearchIndex++;
						moveToSearchResult(currentSearchIndex);
					} else {
						currentSearchIndex = 0;
						moveToSearchResult(currentSearchIndex);
					}
				});

			// 특정 검색 결과로 이동하는 함수
			function moveToSearchResult(index) {
				const targetCfi = searchResults[index].cfi;
				rendition.display(targetCfi);

				updateProgress(book.locations.percentageFromCfi(targetCfi));
				updateSearchCounter();
			}

			function updateSearchCounter() {
				searchCounter.textContent = `${currentSearchIndex + 1}/${searchResults.length
					}`;
			}

			// 검색 결과를 하이라이트 처리하는 함수
			function highlightResults() {
				highlightIds = [];
				searchResults.forEach((result) => {
					const highlightId = rendition.annotations.highlight(
						result.cfi,
						{}, // 빈 객체로 옵션 전달
						(e) => {
							console.log("하이라이트 클릭됨:", result.cfi);
						},
						"highlight",
						{
							"background-color": "yellow",
							"border-radius": "2px",
						}
					);

					// 반환된 ID가 있는지 확인하고 highlightIds에 추가
					if (highlightId) {
						highlightIds.push(highlightId);
					} else {
						console.warn("하이라이트 ID를 반환받지 못했습니다.");
					}
				});
			}
		});

	// 진행도를 업데이트하는 함수
	function updateProgress(percentage) {
		progressBar.style.width = `${percentage * 100}%`;
		if (percentage == 1){
			progressBar.classList.add('completed');
		} else {
			progressBar.classList.remove('completed');
		}
	}
});
