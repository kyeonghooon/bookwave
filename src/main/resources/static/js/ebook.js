// ePub 파일을 로드하는 코드
// TODO url을 변경해야함
document.addEventListener("DOMContentLoaded", function () {
  const toggleToolbarBtn = document.querySelector(".toolbar--btn");
  const toolbarContainer = document.querySelector(".toolbar--circle");
  const searchNav = document.getElementById("searchNav");
  const searchCounter = document.getElementById("searchCounter");
  const endSearchBtn = document.getElementById("endSearch");
  const book = ePub(ebookPath);
  let searchResults = [];
  let currentSearchIndex = 0;
  let isSearching = false;
  let loadedSections = {}; // 로드된 섹션을 저장하기 위한 객체

  toggleToolbarBtn.addEventListener("click", function () {
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
      rendition.on("relocated", function (location) {
        const progress = book.locations.percentageFromCfi(location.start.cfi);
        updateProgress(progress);
      });

      // 진행도 바 클릭 시 해당 위치로 이동
      progressContainer.addEventListener("click", function (event) {
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
        .addEventListener("click", function () {
          rendition.next();
        });

      // 이전 페이지로 이동
      document
        .getElementById("prevPage")
        .addEventListener("click", function () {
          rendition.prev();
        });

      // 특정 페이지로 이동
      document
        .getElementById("goToPage")
        .addEventListener("click", function () {
          const page = document.getElementById("pageInput").value;

          // 페이지 번호를 위치로 변환
          const location = book.locations.cfiFromPercentage(page / totalPages);
          console.log(location);
          rendition.display(location); // 특정 페이지로 이동
        });

      // 검색 버튼 클릭 시 동작
      document
        .getElementById("searchBtn")
        .addEventListener("click", function () {
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
          isSearching = true;

          book.spine.each(function (section) {
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
              isSearching = false;
            }
          });
        });

      // End 버튼 클릭 시 동작
      endSearchBtn.addEventListener("click", function () {
        isSearching = false;
        searchResults = [];
        currentSearchIndex = 0;
        searchNav.style.display = "none"; // 검색 내비게이션 숨김
        rendition.annotations.remove(); // 모든 하이라이트 제거
		  document.getElementById("searchInput").value = "";
      });
      // 이전 검색 결과로 이동
      document
        .getElementById("prevResult")
        .addEventListener("click", function () {
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
        .addEventListener("click", function () {
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
        searchCounter.textContent = `${currentSearchIndex + 1}/${
          searchResults.length
        }`;
      }

      function highlightResults() {
        searchResults.forEach((result) => {
          rendition.annotations.highlight(
            result.cfi,
            {},
            (e) => {
              console.log("하이라이트 클릭됨:", result.cfi);
            },
            "highlight",
            {
              "background-color": "yellow",
              "border-radius": "2px",
            }
          );
        });
      }
    });

  // 진행도를 업데이트하는 함수
  function updateProgress(percentage) {
    progressBar.style.width = `${percentage * 100}%`;
  }
});
