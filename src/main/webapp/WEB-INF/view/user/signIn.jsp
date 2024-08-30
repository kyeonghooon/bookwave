<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>

<form action="/user/sign-in" >
  <div class="form-group" >
    <label for="id">아이디:</label>
    <input type="text" class="form-control" placeholder="Enter id" id="id" required>
 
  </div>
  <div class="form-group">
    <label for="pwd">비밀번호:</label>
    <input type="password" class="form-control" placeholder="Enter password" id="pwd" required>

  </div>
  <div class="form-group form-check">
    <label class="form-check-label" >
      <input class="form-check-input" type="checkbox"> 기억하기
    </label>
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>

<form action="/sign-up">
  <div>
  <button type="submit" class="btn btn-primary" >회원가입</button>
  </div>
</form>


</body>
</html>