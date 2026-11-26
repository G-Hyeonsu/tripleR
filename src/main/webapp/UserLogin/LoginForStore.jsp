<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/Login.css">
<title>Login for Store</title>
</head>
<body>
<main>
	<div class="formContainer">
		<form class="loginForm">
			<input type="text" name="reviewerId" placeholder="아이디">
			<input type="password" name="reviewerPw" placeholder="비밀번호">
			<label for="rememberCheck">
				<input type="checkbox" id="rememberCheck">아이디 저장하기
			</label>
			<input type="submit" value="로그인">
			<a href="LoginForReviewer.jsp">리뷰어 로그인으로 돌아가기</a>
			
			<div class="link-group">
                    회원이 아니시라면<a href="<%= request.getContextPath() %>/webapp/UserLogin/SignUpForStore.jsp">회원가입</a>
            </div>
		</form>
	</div>
</main>
</body>
</html>