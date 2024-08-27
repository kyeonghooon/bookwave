$(document).ready(function () {
  // 마우스 오버 시 드롭다운 메뉴가 보이게 하기
  $(".dropdown").hover(
    function () {
      $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeIn(200);
    },
    function () {
      $(this).find(".dropdown-menu").stop(true, true).delay(200).fadeOut(200);
    }
  );
});
