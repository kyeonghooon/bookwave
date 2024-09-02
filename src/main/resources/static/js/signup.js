const resultUid = document.querySelector(".result-uid"); // 에러 메세지(아이디)
const resultUpw = document.querySelector(".result-upw"); // 에러 메세지(비밀번호)


const btnId = document.querySelector(".check-id"); // 중복체크 버튼
const inputId = document.querySelector(".loginId"); // 아이디
const inputPw= document.querySelector(".password"); // 비밀번호
const inputPwCheck= document.querySelector(".pwcheck"); // 비밀번호 체크

const btnSubmit = document.getElementById("btn"); // 회원가입 버튼


// 정규식 표현
const reUid = /^[a-z]+[a-z0-9]{5,12}$/g; // 조건
const rePass = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,12}$/


btnId.addEventListener("click", function () {
  const inputId = loginId.value.trim(); // 입력 필드의 값 가져오기
  console.log(inputId);
  const url = "/controller-user/check-userId?loginId=" + inputId;
  console.log("url", url);
 
  fetch(url)
    .then((response) => response.json()) // 응답을 JSON 형식으로 반환
    .then((isUse) => {
      console.log("결과 확인 ", isUse);
      if (isUse) {
        resultUid.textContent = "사용 가능한 ID입니다.";
        resultUid.style.color = 'green';
      } else {
        resultUid.textContent = "중복된 ID입니다";
        resultUid.style.color = 'red';
      }
    })
    .catch((error) => {
      console.log("error ", error);
      resultUid.textContent = "잘못된 요청입니다.";
    });
  });
  


// // 이벤트리스너(삭제)
//   document.getElementById('loginId').addEventListener('keydown',function(event2){
//     if(event2.code == 'Enter'){
//       event2.preventDefault();
//       // document.getElementById('check-id').click();
//     }
//   });



    
 



