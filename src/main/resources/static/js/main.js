const bookList = Array.from(document.querySelectorAll('#newBook'));
let currentPage = 1;
const itemsPerPage = 5;
const totalPages = Math.ceil(bookList.length / itemsPerPage);

function showPage(page) {
    // 모든 책을 숨김 처리
    bookList.forEach((book, index) => {
        book.style.display = 'none';
    });

    // 현재 페이지에 해당하는 5개의 책을 보여줌
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const booksToShow = bookList.slice(start, end);

    booksToShow.forEach(book => {
        book.style.display = 'block';
    });
}

// 이전 페이지로 이동
document.querySelector('.prev').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
    } else {
        currentPage = totalPages; // 첫 페이지에서 클릭 시 마지막 페이지로 이동
    }
    showPage(currentPage);
});

// 다음 페이지로 이동
document.querySelector('.next').addEventListener('click', () => {
    if (currentPage < totalPages) {
        currentPage++;
    } else {
        currentPage = 1; // 마지막 페이지에서 클릭 시 첫 페이지로 이동
    }
    showPage(currentPage);
});

// 페이지 초기화 시 첫 페이지 표시
showPage(currentPage);