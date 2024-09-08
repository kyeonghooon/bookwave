document.addEventListener("DOMContentLoaded", function() {
	const addCategoryBtn = document.getElementById("add--category--btn");
	const editCategoryBtn = document.getElementById("edit--category--btn");
	const categoryForm = document.getElementById("category--form");
	const createCategoryBtn = document.getElementById("create--category--btn");
	const reorderCategoryBtn = document.getElementById("reorder--category--btn");
	const categoryNameInput = document.getElementById("category--name");
	const messageBox = document.getElementById("message");
	let draggedBookId = null;
	let draggedCategoryId = null;
	let isAdding = false;
	let isEditing = false;
	let isReordering = false;

	// 카테고리 추가 버튼을 클릭했을 때 폼을 보여줌
	addCategoryBtn.addEventListener("click", function() {
		if (!isAdding) {
			isAdding = true;
			if (isEditing) {
				resetEditCategoryBtn();
			}
			createCategoryBtn.innerText = "생성";
			categoryForm.style.display = "block";
			categoryForm.classList.add("adding");
			categoryNameInput.value = ""; // 폼 초기화
			messageBox.innerText = ""; // 메시지 초기화
			addCategoryBtn.innerText = "취소";
			addCategoryBtn.classList.remove("btn-primary");
			addCategoryBtn.classList.add("btn-danger");
		} else {
			resetAddCategoryBtn();
			categoryForm.style.display = "none"; // 폼 숨김
		}
	});
	if (editCategoryBtn) {
		editCategoryBtn.addEventListener("click", function() {
			if (!isEditing) {
				isEditing = true;
				if (isAdding) {
					resetAddCategoryBtn();
				}
				createCategoryBtn.innerText = "변경";
				categoryForm.style.display = "block";
				categoryForm.classList.add("adding");
				categoryNameInput.value = ""; // 폼 초기화
				messageBox.innerText = ""; // 메시지 초기화
				editCategoryBtn.innerText = "취소";
				editCategoryBtn.classList.remove("btn-warning");
				editCategoryBtn.classList.add("btn-danger");
			} else {
				resetEditCategoryBtn();
				categoryForm.style.display = "none"; // 폼 숨김
			}
		});
	}

	function resetAddCategoryBtn() {
		isAdding = false;
		addCategoryBtn.innerText = "카테고리 추가";
		categoryForm.classList.remove("adding");
		addCategoryBtn.classList.remove("btn-danger");
		addCategoryBtn.classList.add("btn-primary");
	}

	function resetEditCategoryBtn() {
		isEditing = false;
		editCategoryBtn.innerText = "카테고리 이름 변경";
		categoryForm.classList.remove("adding");
		editCategoryBtn.classList.remove("btn-danger");
		editCategoryBtn.classList.add("btn-warning");
	}

	// 카테고리 생성 버튼을 클릭했을 때 (변경 버튼 공용)
	createCategoryBtn.addEventListener("click", function() {
		const categoryName = categoryNameInput.value.trim();
		const selectedCategory = document
			.querySelector(".category--item")
			.getAttribute("data-selectedCategory");
		// 유효성 검사 (한글, 영어, 숫자, 8글자 이하)
		const isValidName = /^[가-힣a-zA-Z0-9]+$/.test(categoryName);
		if (!isValidName || categoryName.length > 8) {
			messageBox.innerText =
				"이름은 8글자 이하의 한글, 영어, 숫자만 사용 가능합니다.";
			return;
		}
		let url = "";
		if (isAdding) {
			url = "/ebook/add-category?categoryName=" + categoryName;
		}
		if (isEditing) {
			url =
				"/ebook/edit-category?categoryName=" +
				categoryName +
				"&categoryId=" +
				selectedCategory;
		}
		if (url == "") {
			alert("알 수 없는 오류 발생");
		}
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
					alert(data.message);
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

	// 카테고리 항목들을 가져오는 함수
	function getCategoryItems() {
		return document.querySelectorAll(".category--item");
	}

	// 책 드래그 시작 시 카테고리들에 이벤트 추가
	function activateCategoryDragEvents() {
		const categories = getCategoryItems();
		categories.forEach((category) => {
			category.addEventListener("dragenter", highlightCategory);
			category.addEventListener("dragleave", unhighlightCategory);
			category.addEventListener("dragover", allowDrop);
			category.addEventListener("drop", drop);
		});
	}

	// 카테고리들에서 드래그 이벤트 제거
	function deactivateCategoryDragEvents() {
		const categories = getCategoryItems();
		categories.forEach((category) => {
			category.removeEventListener("dragenter", highlightCategory);
			category.removeEventListener("dragleave", unhighlightCategory);
			category.removeEventListener("dragover", allowDrop);
			category.removeEventListener("drop", drop);
		});
	}

	// 드래그가 시작될 때 도서의 bookId를 저장
	window.bookDrag = function(event) {
		activateCategoryDragEvents();
		const bookItem = event.target.closest(".book--item");
		draggedBookId = bookItem.getAttribute("data-book-id");
		event.dataTransfer.setData("text", draggedBookId);
	};

	// 드래그 허용
	function allowDrop(event) {
		event.preventDefault();
	}

	// 카테고리 위에 드래그 대상이 진입했을 때 (호버 효과)
	window.highlightCategory = function(event) {
		const categoryItem = event.target.closest(".category--item");
		categoryItem.classList.add("highlight"); // highlight 클래스 추가
	};

	// 카테고리에서 드래그 대상이 벗어났을 때 (호버 효과 제거)
	window.unhighlightCategory = function(event) {
		const categoryItem = event.target.closest(".category--item");
		categoryItem.classList.remove("highlight"); // highlight 클래스 제거
	};

	// 도서 항목이 카테고리 위에 드롭되었을 때
	function drop(event) {
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
					deactivateCategoryDragEvents();
					alert("카테고리 변경에 실패했습니다.");
				}
			})
			.catch((error) => {
				console.error("Error:", error);
				deactivateCategoryDragEvents();
				alert("카테고리 변경 중 오류가 발생했습니다.");
			});
	}

	// 카테고리 순서 변경 버튼 클릭 시
	reorderCategoryBtn.addEventListener("click", function() {
		const books = getBookItems();
		const categories = getCategoryItems();
		if (!isReordering) {
			isReordering = true;
			books.forEach((book) => {
				book.setAttribute("draggable", false);
				book.querySelector("img").setAttribute("draggable", false);
			});
			if (editCategoryBtn) {
				editCategoryBtn.style.display = "none";
			}
			addCategoryBtn.style.display = "none";
			reorderCategoryBtn.innerText = "완료";

			// 취소 버튼 추가
			const cancelBtn = document.createElement("button");
			cancelBtn.innerText = "취소";
			cancelBtn.classList.add("btn", "btn-danger", "btn-sm", "m-1");
			cancelBtn.id = "cancel--reorder--btn";
			document.getElementById("btn--container").appendChild(cancelBtn);

			cancelBtn.addEventListener("click", cancelReorder);

			// 카테고리를 드래그 가능한 상태로
			categories.forEach((category) => {
				if (
					category.getAttribute("data-category-id") != null &&
					category.getAttribute("data-category-id") > 0
				) {
					category.setAttribute("draggable", true);
					category.addEventListener("dragstart", handleCategoryDragStart);
					category.addEventListener("dragover", handleCategoryDragOver);
					category.addEventListener("dragenter", addRedLine);
					category.addEventListener("dragleave", removeRedLine);
					category.addEventListener("drop", handleCategoryDrop);
				}
			});
		} else {
			// 완료 버튼 클릭시
			isReordering = false;
			const newCategoryOrder = Array.from(categories)
				.filter(
					(category) =>
						category.getAttribute("data-category-id") != null &&
						category.getAttribute("data-category-id") > 0
				)
				.reverse()
				.map((category, index) => {
					return {
						categoryId: category.getAttribute("data-category-id"),
						priority: index + 1, // 우선 순위를 1부터 시작
					};
				});

			fetch("/ebook/reorder-category", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(newCategoryOrder),
			})
				.then((response) => response.json())
				.then((data) => {
					if (data.success) {
						alert("카테고리 순서가 변경되었습니다.");
						window.location.reload(); // 새로고침하여 변경사항 반영
					} else {
						alert("카테고리 순서 변경에 실패했습니다.");
						cancelReorder();
					}
				})
				.catch((error) => {
					console.error("Error:", error);
					alert("카테고리 순서 변경 중 오류가 발생했습니다.");
				});
		}

		// TODO 1. books의 draggable을 비활성화
		// TODO 2. editCategoryBtn, addCategoryBtn는 숨김
		// TODO 3. reorderCategoryBtn의 innerText = "완료"
		// TODO 4. 위 버튼들을 담고 있는 id가 btn--container인 div에 취소 버튼을 추가
		// 취소 버튼 기능 --> 모든걸 reorderCategoryBtn이 눌러지기 전으로 되돌아감
		// TODO 5. 메인 로직
		// --> categories를 드래그 가능한 상태로 만들고 위아래로 위치를 변할 수 있게함
		// top은 "전체" bottom은 "미지정"으로 고정이라 바뀔 수 없음 드래그도 드랍도 불가능
		// 완료 버튼이 눌러지면 해당 상태를 미지정 바로위의 카테고리에 priority 값으로 1을 주고
		// 그 위로 2, 3, 4 이런식으로 할당해서 data-category-id에 담긴 값을 key 값으로 하는 map구조 형태로
		// 비동기로 post 요청
	});

	// 책 항목들을 가져오는 함수
	function getBookItems() {
		return document.querySelectorAll(".book--item");
	}

	// 순서 변경 취소시
	function cancelReorder() {
		isReordering = false;
		const books = getBookItems();
		const categories = getCategoryItems();
		books.forEach((book) => {
			book.setAttribute("draggable", true);
			book.querySelector("img").setAttribute("draggable", true);
		});
		if (editCategoryBtn) {
			editCategoryBtn.style.display = "block";
		}
		addCategoryBtn.style.display = "block";
		reorderCategoryBtn.innerText = "카테고리 순서 변경";
		document.getElementById("cancel--reorder--btn").remove();
		categories.forEach((category) => {
			if (
				category.getAttribute("data-category-id") != null &&
				category.getAttribute("data-category-id") > 0
			) {
				category.setAttribute("draggable", false);
				category.removeEventListener("dragstart", handleCategoryDragStart);
				category.removeEventListener("dragover", handleCategoryDragOver);
				category.removeEventListener("dragenter", addRedLine);
				category.removeEventListener("dragleave", removeRedLine);
				category.removeEventListener("drop", handleCategoryDrop);
			}
		});
	}

	// 카테고리 드래그 시작
	function handleCategoryDragStart(event) {
		const draggedCategory = event.target.closest(".category--item");
		draggedCategoryId = draggedCategory.getAttribute("data-category-id");
	}

	// 카테고리 드래그 중 (드래그 가능)
	function handleCategoryDragOver(event) {
		event.preventDefault();
	}

	function addRedLine(event) {
		const categoryItem = event.target.closest(".category--item");
		categoryItem.classList.add("redLine");
	}

	function removeRedLine(event) {
		const categoryItem = event.target.closest(".category--item");
		categoryItem.classList.remove("redLine");
	}

	// 카테고리 드롭
	function handleCategoryDrop(event) {
		event.preventDefault();
		const droppedCategory = event.target.closest(".category--item");
		const categories = getCategoryItems();
		categories.forEach((category) => {
			category.classList.remove("redLine");
		});
		console.log("Dragged Category ID:", draggedCategoryId);
		console.log(
			"Dropped Category ID:",
			droppedCategory
				? droppedCategory.getAttribute("data-category-id")
				: "null"
		);

		// 드래그된 카테고리와 드롭된 카테고리의 위치 교환
		const draggedCategory = document.querySelector(
			`[data-category-id="${draggedCategoryId}"]`
		);
		const container = document.querySelector(".sidebar");
		console.log("container", container);
		if (droppedCategory && draggedCategory !== droppedCategory) {
			container.insertBefore(draggedCategory, droppedCategory);
		}
	}
});
