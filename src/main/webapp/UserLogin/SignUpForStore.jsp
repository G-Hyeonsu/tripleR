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
                    <label for="profile-image">프로필 이미지</label>
                    <input type="file" id="profile-image" name="profileImage" accept="image/*">
                    <img id="image-preview" class="image-preview" src="#" alt="이미지 미리보기">
                </div>

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
                    <label for="bank">계좌</label>
                    <div class="input-group account-group">
                        <select id="bank" name="bank">
                            <option value="">은행 선택</option>
                            <option value="국민은행">국민은행</option>
                            <option value="신한은행">신한은행</option>
                            <option value="카카오뱅크">카카오뱅크</option>
                        </select>
                        <input type="text" id="account-number" name="accountNumber" placeholder="계좌번호">
                    </div>
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