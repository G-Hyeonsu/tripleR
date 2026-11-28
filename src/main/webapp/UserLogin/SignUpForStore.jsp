<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/SignUp.css">
<title>Insert title here</title>
</head>
<body>
<main>
<div class="form-container">
            <form class="signup-form" action="<%= request.getContextPath() %>/user/register" method="post" enctype="multipart/form-data">
                <h1>회원가입</h1>

                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" maxlength="26" required>
                </div>

                <div class="form-group">
                    <label for="email">이메일</label>
                    <div class="input-group email-group">
                        <input type="text" id="email-prefix" name="emailPrefix" placeholder="이메일">
                        <span>@</span>
                        <select id="email-domain" name="emailDomain" required>
                            <option value="">도메인 선택</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="naver.com">naver.com</option>
                            <option value="daum.net">daum.net</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="confirm-password">비밀번호 확인</label>
                    <input type="password" id="confirm-password" name="confirmPassword" required>
                </div>

                <div class="form-group">
                    <label for="nickname">닉네임</label>
                    <input type="text" id="nickname" name="nickname" maxlength="30" required>
                </div>

                <div class="form-group">
                    <label for="address">주소</label>
                    <input type="text" id="address" name="address" required>
                </div>

                <button type="submit">회원가입하기</button>
            </form>
        </div>
</main>
</body>
</html>