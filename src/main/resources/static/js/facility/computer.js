document.addEventListener('DOMContentLoaded', function () {
	
	const availableComputers = document.querySelectorAll('.computer--available');
	
	availableComputers.forEach(function (button) {
        button.addEventListener('click', function () {
            const computerId = this.getAttribute('data-id');
            openReservation(computerId);
        });
    });
});

function openReservation(computerId) {
    // 예약 페이지를 새 창으로 열기
    window.open(`/facility/computerReservation/${computerId}`, '_blank', 'width=600,height=400');
}