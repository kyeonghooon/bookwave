document.addEventListener('DOMContentLoaded', () => {
	const likeCount = document.querySelector('.like-count');
	const likeButton = document.querySelector('.like--button');
	const favoriteButton = document.querySelector('.favorite--button');
	const lendButton = document.querySelector('.lend--button');
	const reserveButton = document.querySelector('.reserve--button');
	const readEbookButton = document.querySelector('.read--ebook--button');
	const ebookButton = document.querySelector('.ebook--button');
	const subEbookButton = document.querySelector('.sub--ebook--button');

	const handleLikeButtonClick = e => {
		e.preventDefault();
		let bookId = likeButton.dataset.bookId;
		const button = e.currentTarget;
		const isLiked = button.getAttribute('data-liked') === 'true';
		let url = `/book/like/${bookId}`;

		console.log('서치원', bookId);
		console.log('Like button clicked. Is liked:', isLiked);
		console.log('Sending request to:', url);

		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
		})
			.then(response => response.text())
			.then(responseText => {
				if (responseText === 'liked') {
					button.classList.add('liked');
					button.setAttribute('data-liked', 'true');
					const currentLikeCount = parseInt(likeCount.textContent, 10);
					likeCount.textContent = currentLikeCount + 1;
				} else if (responseText === 'unliked') {
					button.classList.remove('liked');
					button.setAttribute('data-liked', 'false');
					const currentLikeCount = parseInt(likeCount.textContent, 10);
					likeCount.textContent = currentLikeCount - 1;
				}
			})
			.catch(error => {
				console.error('좋아요 처리 중 오류가 발생했습니다.', error);
			});
	};

	const handleFavoriteButtonClick = e => {
		e.preventDefault();
		let bookId = favoriteButton.dataset.bookId;
		const button = e.currentTarget;
		const isFavorited = button.getAttribute('data-favorited') === 'true';
		const url = `/book/favorite/${bookId}`;

		console.log('Favorite button clicked. Is favorited:', isFavorited);
		console.log('Sending request to:', url);

		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
		})
			.then(response => response.text())
			.then(responseText => {
				if (responseText === 'favorited') {
					button.classList.add('favorited');
					button.setAttribute('data-favorited', 'true');
				} else if (responseText === 'unfavorited') {
					button.classList.remove('favorited');
					button.setAttribute('data-favorited', 'false');
				}
			})
			.catch(error => {
				console.error('관심등록 처리 중 오류가 발생했습니다.', error);
			});
	};

	const handleConfirmation = message => e => {
		const confirmed = confirm(message);
		if (!confirmed) {
			e.preventDefault(); // 사용자가 '아니오'를 선택하면 폼 제출을 막음
		}
	};

	function registEbook() {
		const confirmed = confirm("등록 하시겠습니까");
		if (!confirmed) {
			return;
		}
		const url = `/ebook/regist/${bookId}`;
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
					window.location.href = '/ebook?category=0'; // /ebook으로 리다이렉트
				} else {
					// 실패 시 실패 메시지를 표시
					alert(data.message);
				}
			})
			.catch((error) => {
				console.error("Error:", error);
				messageBox.innerText = "ebook 등록 중 오류가 발생했습니다.";
			});
	}

	function purchaseEbook() {
		const confirmed = confirm("구매 하시겠습니까?");
		if (!confirmed) {
			return;
		}
		openDynamicModal("결제하기", "500을 두 값으로 나누어 입력하세요.", 500, function(waveInput, mileageInput) {
			const itemId = items.get("ebook");
			const params = new URLSearchParams({
				wave: waveInput,
				mileage: mileageInput
			});
			const url = `/purchase/${itemId}?${params.toString()}`;
			fetch(url, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => response.json())
			.then((data) => {
				if (data.success) {
					// 성공 --> 카테고리 한도를 늘림
					messageBox.innerText = "";
					alert(data.message);
				} else {
					// 실패
					messageBox.innerText = data.message;
				}
			})
			.catch((error) => {
				console.error("Error:", error);
				messageBox.innerText = "구매 중 오류가 발생했습니다.";
			});
		});
	}

	// 이벤트 핸들러 등록
	if (likeButton) {
		likeButton.addEventListener('click', handleLikeButtonClick);
	}

	if (favoriteButton) {
		favoriteButton.addEventListener('click', handleFavoriteButtonClick);
	}

	if (lendButton) {
		lendButton.addEventListener('click', handleConfirmation('정말 대여하시겠습니까?'));
	}

	if (reserveButton) {
		reserveButton.addEventListener('click', handleConfirmation('정말 예약하시겠습니까?'));
	}

	if (readEbookButton) {
		subEbookButton.addEventListener('click', handleConfirmation('페이지를 이동하시겠습니까?'));
	}

	if (subEbookButton) {
		subEbookButton.addEventListener('click', registEbook);
	}

	if (ebookButton) {
		ebookButton.addEventListener('click', registEbook);
	}
});