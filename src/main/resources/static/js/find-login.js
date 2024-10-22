// 수정 필요
// Tab
document.getElementById("findIdTab").addEventListener("click", function () {
  switchTab("tabContentFindId", "findIdTab");
});

document.getElementById("findPwTab").addEventListener("click", function () {
  switchTab("tabContentFindPw", "findPwTab");
});

function switchTab(contentId, tabId) {
  document.querySelectorAll(".tab--content").forEach(function (content) {
    content.classList.remove("active");
  });

  document.querySelectorAll(".tab--item").forEach(function (tab) {
    tab.classList.remove("active");
  });

  document.getElementById(contentId).classList.add("active");
  document.getElementById(tabId).classList.add("active");
}

if (type == "id") {
  switchTab("tabContentFindId", "findIdTab");
} else {
  switchTab("tabContentFindPw", "findPwTab");
}

// email로 ID 찾기
// 이벤트 처리
const findLoginIdBtn = document.getElementById("findLoginIdBtn");
//const findPasswordBtn = document.getElementById("findPasswordBtn");
// 버튼 클릭 이벤트 핸들러 async -> 비동기처리
findLoginIdBtn.addEventListener("click", async function () {
  const emailInput = document.getElementById("findLoginIdEmail");
  const email = emailInput.value;
  findLoginIdBtn.innerText = "이메일 전송중";
  findLoginIdBtn.style = "background-color: #ffc107";
console.log(email);
  fetch('/controller-user/find-id', {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ "email": email }),
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      console.log("응답 데이터 : ", data); // 응답을 콘솔에 출력

      if (data.success) {
        // 성공 시 확인창 표시
        alert(data.message);
        window.close();
      } else {
        findLoginIdBtn.innerText = "아이디 찾기";
        findLoginIdBtn.style = "background-color: #007BFF";
        alert(data.message || "사용자 정보를 찾을 수 없습니다.");
      }
    })
    .catch((error) => {
      console.error("에러 발생:", error);
      alert("요청 처리 중 오류가 발생했습니다.");
      findLoginIdBtn.innerText = "아이디 찾기";
      findLoginIdBtn.style = "background-color: #007BFF";
    });
});

// 비밀번호 찾기
// 이벤트 처리
const findPasswordBtn = document.getElementById("findPasswordBtn");
// 버튼 클릭 이벤트 핸들러 async -> 비동기처리
findPasswordBtn.addEventListener("click", async function () {
  const pwIdInput = document.getElementById("findPasswordId");
  const pwEmailInput = document.getElementById("findPasswordEmail");
  const loginId = pwIdInput.value;
  const email = pwEmailInput.value;

  findPasswordBtn.innerText = "이메일 전송중";
  findPasswordBtn.style = "background-color: #ffc107";

console.log(loginId);
console.log(email);

  fetch('/controller-user/find-pw', {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ "loginId":loginId ,"email": email }),
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      console.log("응답 데이터 : ", data); // 응답을 콘솔에 출력

      if (data.success) {
        // 성공 시 확인창 표시
        alert(data.message);
        window.close();
      } else {
        findPasswordBtn.innerText = "비밀번호 찾기";
        findPasswordBtn.style = "background-color: #007BFF";
        alert(data.message || "사용자 정보를 찾을 수 없습니다.");
      }
    })
    .catch((error) => {
      console.error("에러 발생:", error);
      alert("요청 처리 중 오류가 발생했습니다.");
      findPasswordBtn.innerText = "비밀번호 찾기";
      findPasswordBtn.style = "background-color: #007BFF";
    });
});