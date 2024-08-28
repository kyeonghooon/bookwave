// ePub 파일을 로드하는 코드
// TODO url을 변경해야함
document.addEventListener("DOMContentLoaded", function () {
  const book = ePub("/ebooks/2.epub");

  // viewer에 ePub을 렌더링합니다.
  const rendition = book.renderTo("viewer", {
    // flow: "paginated",
    width: "100%",
    height: "100%",
  });

  rendition.display();

  book.ready
    .then(() => {
      // 위치 리스트를 생성 (기본 값 사용)
      return book.locations.generate(1000);
    })
    .then(() => {
      const totalPages = book.locations.total;
      console.log('Total Pages:', totalPages);

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

          rendition.display(location); // 특정 페이지로 이동
        });
    });
});
