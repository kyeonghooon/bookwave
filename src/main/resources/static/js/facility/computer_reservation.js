document.addEventListener('DOMContentLoaded', function() {
	const dateInput = document.getElementById('reservationDate');
	const timeTableBody = document.getElementById('timeTableBody');

	// 기본 날짜 설정
	const today = new Date().toISOString().split('T')[0];
	dateInput.value = today;

	// 날짜가 변경될 때마다 비동기로 새로운 데이터 요청
	dateInput.addEventListener('change', function() {
		const selectedDate = this.value;
		fetchTimeTable(selectedDate, computerId);
	});

	// 페이지 로드 시 오늘 날짜로 시간표 가져오기
	fetchTimeTable(today, computerId);

	function fetchTimeTable(date, computerId) {
		const now = new Date();  // 현재 시간
		const selectedDate = new Date(date);  // 선택한 날짜
		// 기존 시간표 초기화
		timeTableBody.innerHTML = '';

		// 서버로 비동기 요청 보내기
		const url = `/facility/computer-timetable?date=${date}&computerId=${computerId}`;
		fetch(url)
			.then(response => response.json())
			.then(data => {
				// 시간표 생성
				const timeSlots = createTimeSlots();
				const currentTime = now.getHours() * 60 + now.getMinutes();
				const isPastDate = selectedDate < new Date(now.toDateString());
				timeSlots.forEach((slot, index) => {
					const [hour, minute] = slot.split(':');
					const slotTimeInMinutes = parseInt(hour) * 60 + parseInt(minute);  // 시간 슬롯을 분으로 변환

					// 현재 날짜와 선택한 날짜가 같은 경우에만 시간 비교
					const isPast = (selectedDate.toDateString() === now.toDateString() && slotTimeInMinutes < currentTime);

					const isAvailable = data.availableSlots.includes(index) && !isPast &&!isPastDate;
					const row = document.createElement('tr');
					row.innerHTML = `
                        <td>${slot}</td>
                        <td class="${isAvailable ? 'available' : 'unavailable'}">
                            ${isAvailable ? `<button class="btn btn-primary reserve--btn" data-slot="${index}">예약</button>` : '예약 불가'}
                        </td>
                    `;
					timeTableBody.appendChild(row);
				});
				// 모든 예약 버튼에 클릭 이벤트 추가
				const reserveButtons = document.querySelectorAll('.reserve--btn');
				reserveButtons.forEach(button => {
					button.addEventListener('click', function() {
						const slotIndex = this.getAttribute('data-slot');
						openPurchaseModal(computerId, date, slotIndex);
					});
				});
			})
			.catch(error => console.error('시간 테이블 요청 실패', error));
	}

	function createTimeSlots() {
		const slots = [];
		let hour = 9;
		let minute = 0;
		for (let i = 0; i < 18; i++) {
			const time = `${hour}:${minute === 0 ? '00' : minute}`;
			slots.push(time);
			minute += 30;
			if (minute === 60) {
				minute = 0;
				hour++;
			}
		}
		return slots;
	}

	function reserveComputer(waveInput, mileageInput, computerId, date, slotIndex) {
		const itemId = items.get("computer");
		const startTime = combineDateAndTime(date, slotIndex);
		const endTime = combineDateAndTime(date, slotIndex, true);
		const url = `/purchase/${itemId}`;
		fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				wave: waveInput,
				mileage: mileageInput,
				computerId: computerId,
				startTime: startTime,
				endTime: endTime
			})
		})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					alert("예약 성공");
					fetchTimeTable(date, computerId); // 예약 후 시간표 갱신
				} else {
					alert(data.message);
				}
			})
			.catch(error => console.error('구매 중 오류 발생', error));
	}

	function combineDateAndTime(date, slotIndex, isEndTime = false) {
		const timeSlots = createTimeSlots();
		// endTime이 마지막 슬롯일 경우
		if (isEndTime && slotIndex === timeSlots.length - 1) {
			return `${date} 18:00:00`;
		}
		if (isEndTime) {
			slotIndex++;
		}
		const time = timeSlots[slotIndex];
		console.log(time);


		const [hour, minute] = time.split(':');
		const formattedHour = hour.padStart(2, '0');  // 두 자리로 맞춤
		const formattedMinute = minute.padStart(2, '0');  // 두 자리로 맞춤
		return `${date} ${formattedHour}:${formattedMinute}:00`;
	}

	function openPurchaseModal(computerId, date, slotIndex) {
		openDynamicModal("결제하기", "300을 두 값으로 나누어 입력하세요.", 300, function(waveInput, mileageInput) {
			reserveComputer(waveInput, mileageInput, computerId, date, slotIndex)
		});
	}
});
