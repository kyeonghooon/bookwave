document.addEventListener("DOMContentLoaded", function() {
    const addCategoryBtn = document.getElementById('add--category--btn');
    const categoryForm = document.getElementById('category--form');
    const createCategoryBtn = document.getElementById('create--category--btn');
    const categoryNameInput = document.getElementById('category--name');
    const messageBox = document.getElementById('message');

    // 카테고리 추가 버튼을 클릭했을 때 폼을 보여줌
    addCategoryBtn.addEventListener('click', function() {
        if (categoryForm.style.display === 'none' || categoryForm.style.display === '') {
            categoryForm.style.display = 'block';
            categoryForm.classList.add('adding');
            categoryNameInput.value = ''; // 폼 초기화
            messageBox.innerText = ''; // 메시지 초기화
            addCategoryBtn.innerText = '취소';
            addCategoryBtn.classList.remove('btn-primary');
            addCategoryBtn.classList.add('btn-danger');
        } else {
            addCategoryBtn.innerText = '추가';
            categoryForm.classList.remove('adding');
            addCategoryBtn.classList.remove('btn-danger');
            addCategoryBtn.classList.add('btn-primary');
            categoryForm.style.display = 'none'; // 폼 숨김
        }
    });
    
    // 카테고리 생성 버튼을 클릭했을 때
    createCategoryBtn.addEventListener('click', function() {
        const categoryName = categoryNameInput.value.trim();

        // 유효성 검사 (한글, 영어, 숫자, 8글자 이하)
        const isValidName = /^[가-힣a-zA-Z0-9]+$/.test(categoryName);
        if (!isValidName || categoryName.length > 8) {
            messageBox.innerText = '이름은 8글자 이하의 한글, 영어, 숫자만 사용 가능합니다.';
            return;
        }
		const url = "/ebook/add-category?categoryName=" +  categoryName;
        // 서버로 데이터 전송
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // 성공 시 성공 메시지를 표시하고 페이지 이동
                alert('카테고리가 성공적으로 생성되었습니다.');
                window.location.href = '/ebook'; // /ebook으로 리다이렉트
            } else {
                // 실패 시 실패 메시지를 표시
                messageBox.innerText = data.message;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            messageBox.innerText = '카테고리 생성 중 오류가 발생했습니다.';
        });
    });
});
