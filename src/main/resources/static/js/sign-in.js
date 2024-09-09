// 아이디 저장 기능(쿠키)

$(function () {
    // 초기화 (미리 표시하는 역할)
    init();

    function init() {
        const savedId = getCookie("savedLoginId"); // 존재 확인(저장된 ID)

        // 쿠키에 저장된 아이디가 있을 경우, 입력값 설정
        if (savedId !== "" && typeof(savedId) !== "undefined") {
            $("#saveId").prop("checked", true);
            $("#loginId").val(savedId);
        } else {
            $("#saveId").prop("checked", false); // 체크박스 해제
        }
    }

    // 로그인 버튼을 클릭하면 실행
    $("#loginBtn").on("click", function(event) {
        const loginId = $("#loginId").val().trim(); // 입력된 ID값
        const saveIdChecked = $("#saveId").is(":checked"); // 체크박스 상태 확인

        if (saveIdChecked && loginId !== "") {
            setCookie("savedLoginId", loginId, 7);  // 7일 동안 저장
        } else {
            // 쿠키 삭제 (만료일 -1로 설정 (즉시 삭제))
            setCookie("savedLoginId", "", -1);  
        }
    });

    // 쿠키 설정 (이름, 저장할 값, 만료일)
    function setCookie(name, value, days) {
        let expires = ""; // 만료 날짜 설정 변수
        if (days) {
            const date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000)); // 현재 시간에 days만큼 더함
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/"; // 쿠키 설정
    }

    // 쿠키 값 반환
    function getCookie(name) {
        const nameEQ = name + "=";
        const ca = document.cookie.split(';'); // 쿠키들을 배열로 변환 (; -> 구분)
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length); // 앞의 공백 제거
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length); // 일치 하는 쿠키 반환
        }
        return ""; // 일치하는 쿠키가 없으면 빈 문자열 반환
    }
});
