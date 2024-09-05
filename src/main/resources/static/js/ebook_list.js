document.addEventListener("DOMContentLoaded", function () {
  const addCategoryBtn = document.getElementById("add--category--btn");
  const categoryForm = document.getElementById("category--form");
  const createCategoryBtn = document.getElementById("create--category--btn");
  const categoryNameInput = document.getElementById("category--name");
  const messageBox = document.getElementById("message");
  let draggedBookId = null;

  // 카테고리 추가 버튼을 클릭했을 때 폼을 보여줌
  addCategoryBtn.addEventListener("click", function () {
    if (
      categoryForm.style.display === "none" ||
      categoryForm.style.display === ""
    ) {
      categoryForm.style.display = "block";
      categoryForm.classList.add("adding");
      categoryNameInput.value = ""; // 폼 초기화
      messageBox.innerText = ""; // 메시지 초기화
      addCategoryBtn.innerText = "취소";
      addCategoryBtn.classList.remove("btn-primary");
      addCategoryBtn.classList.add("btn-danger");
    } else {
      addCategoryBtn.innerText = "추가";
      categoryForm.classList.remove("adding");
      addCategoryBtn.classList.remove("btn-danger");
      addCategoryBtn.classList.add("btn-primary");
      categoryForm.style.display = "none"; // 폼 숨김
    }
  });

  // 카테고리 생성 버튼을 클릭했을 때
  createCategoryBtn.addEventListener("click", function () {
    const categoryName = categoryNameInput.value.trim();

    // 유효성 검사 (한글, 영어, 숫자, 8글자 이하)
    const isValidName = /^[가-힣a-zA-Z0-9]+$/.test(categoryName);
    if (!isValidName || categoryName.length > 8) {
      messageBox.innerText =
        "이름은 8글자 이하의 한글, 영어, 숫자만 사용 가능합니다.";
      return;
    }
    const url = "/ebook/add-category?categoryName=" + categoryName;
    // 서버로 데이터 전송
    fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          // 성공 시 성공 메시지를 표시하고 페이지 이동
          alert("카테고리가 성공적으로 생성되었습니다.");
          window.location.href = "/ebook"; // /ebook으로 리다이렉트
        } else {
          // 실패 시 실패 메시지를 표시
          messageBox.innerText = data.message;
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        messageBox.innerText = "카테고리 생성 중 오류가 발생했습니다.";
      });
  });

  // 드래그가 시작될 때 도서의 bookId를 저장
  window.drag = function (event) {
    const bookItem = event.target.closest(".book--item");
    draggedBookId = bookItem.getAttribute("data-book-id");
    console.log("draggedBookId", draggedBookId);
    event.dataTransfer.setData("text", draggedBookId);
  };

  // 드래그 허용
  window.allowDrop = function (event) {
    event.preventDefault();
  };

  // 카테고리 위에 드래그 대상이 진입했을 때 (호버 효과)
  window.highlightCategory = function (event) {
    const categoryItem = event.target.closest(".category--item");
    categoryItem.classList.add("highlight"); // highlight 클래스 추가
  };

  // 카테고리에서 드래그 대상이 벗어났을 때 (호버 효과 제거)
  window.unhighlightCategory = function (event) {
    const categoryItem = event.target.closest(".category--item");
    categoryItem.classList.remove("highlight"); // highlight 클래스 제거
  };

  // 도서 항목이 카테고리 위에 드롭되었을 때
  window.drop = function (event) {
    event.preventDefault();
    // 드롭된 카테고리의 ID와 이름 가져오기
    const categoryElement = event.target.closest(".category--item");
    const categoryId = categoryElement.getAttribute("data-category-id");
    const categoryName = categoryElement.getAttribute("data-category-name");
    categoryElement.classList.remove("highlight");
    const selectedCategory = categoryElement.getAttribute(
      "data-selectedCategory"
    );
    if (selectedCategory == categoryId) {
      return;
    }
    // 사용자에게 카테고리로 이동할지 확인
    const userConfirmed = confirm(`"${categoryName}"으로 변경하시겠습니까?`);
    if (!userConfirmed) {
      return;
    }

    // 비동기로 카테고리 변경 요청
    fetch(`/ebook/change-category`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ bookId: draggedBookId, categoryId: categoryId }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          // 성공 시 확인창 표시
          const userConfirmed = confirm("해당 카테고리로 이동하시겠습니까?");
          if (userConfirmed) {
            // 카테고리로 이동
            window.location.href = `/ebook?category=${categoryId}`;
          } else {
            // 새로고침
            window.location.reload();
          }
        } else {
          // 실패 시 오류 메시지
          alert("카테고리 변경에 실패했습니다.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("카테고리 변경 중 오류가 발생했습니다.");
      });
  };
});
