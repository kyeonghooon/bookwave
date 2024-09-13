document.addEventListener('DOMContentLoaded', () => {
	const likeButton = document.querySelectorAll('.like--button');
	const favoriteButton = document.querySelectorAll('.favorite--button');
	// 좋아요 버튼 클릭 이벤트 처리
	likeButton.forEach(button => {
		button.addEventListener('click', e => {
			e.preventDefault();
			const bookId = button.dataset.bookId;
			const isLiked = button.dataset.liked === 'true';
			const url = `/book/like/${bookId}`;

			console.log('Like button clicked. Is liked:', isLiked);
			console.log('Sending request to:', url);

			fetch(url, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				}
			})
				.then(response => response.text())
				.then(responseText => {
					if (responseText === 'liked') {
						button.classList.add('liked');
						button.dataset.liked = 'true';
					} else if (responseText === 'unliked') {
						button.classList.remove('liked');
						button.dataset.liked = 'false';
					}
				})
				.catch(error => {
					console.error('좋아요 처리 중 오류가 발생했습니다.', error);
				});
		});
	});

	// 관심등록 버튼 클릭 이벤트 처리
	favoriteButton.forEach(button => {
		button.addEventListener('click', e => {
			e.preventDefault();
			const bookId = button.dataset.bookId;
			const isFavorited = button.dataset.favorited === 'true';
			const url = `/book/favorite/${bookId}`;

			console.log('Favorite button clicked. Is favorited:', isFavorited);
			console.log('Sending request to:', url);

			fetch(url, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				}
			})
				.then(response => response.text())
				.then(responseText => {
					if (responseText === 'favorited') {
						button.classList.add('favorited');
						button.dataset.favorited = 'true';
					} else if (responseText === 'unfavorited') {
						button.classList.remove('favorited');
						button.dataset.favorited = 'false';
					}
				})
				.catch(error => {
					console.error('관심등록 처리 중 오류가 발생했습니다.', error);
				});
		});
	});
});
