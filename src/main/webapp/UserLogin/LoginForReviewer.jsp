<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/Login.css">
</head>
<body>
<header>
</header>
<main>
	<div class="formContainer">
		<form class="loginForm">
			<input type="text" name="reviewerId" placeholder="아이디">
			<input type="password" name="reviewerPw" placeholder="비밀번호">
			<label for="rememberCheck">
				<input type="checkbox" id="rememberCheck">아이디 저장하기
			</label>
			<input type="submit" value="로그인">
			<a href="SignUpForReviewer.jsp">리뷰어 회원가입 하기</a>
			<a href="LoginForStore.jsp">사장님 로그인 하기</a>
			<div class="link-group">
                    <a href="<%= request.getContextPath() %>/webapp/UserLogin/SignUpForReviewer.jsp">회원가입</a>
            </div>
		</form>
	</div>
</main>
</body>
</html>