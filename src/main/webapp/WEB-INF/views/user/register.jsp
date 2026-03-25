<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
/* 콘텐츠를 가운데로 예쁘게 모아주기 위한 추가 폭 설정 */
.register-wrap {
	max-width: 600px;
	margin: 0 auto;
}
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap register-wrap py-12">

		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">회원가입</h1>
			<p class="text-slate-500">deverytime과 함께 성장을 시작해보세요!</p>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">
			<form action="/teamtwo/user/register.do" method="POST"
				enctype="multipart/form-data" onsubmit="return validateForm()">

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">이름
							<span class="text-red-500">*</span>
					</span></label> <input type="text" name="name" id="name" placeholder="홍길동"
						class="input input-bordered w-full focus:input-primary" required />
				</div>

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">아이디
							<span class="text-red-500">*</span>
					</span></label>
					<div class="flex gap-2">
						<input type="text" name="id" id="id" placeholder="영문, 숫자 조합"
							class="input input-bordered w-full focus:input-primary" required />
						<button type="button" id="btnCheckId"
							class="btn btn-outline btn-primary shrink-0">중복확인</button>
					</div>
				</div>

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">비밀번호
							<span class="text-red-500">*</span>
					</span></label> <input type="password" name="pw" id="pw"
						placeholder="영문, 숫자, 특수문자 포함 8자리 이상"
						class="input input-bordered w-full focus:input-primary" required />
					<label class="label"> <span
						class="label-text-alt text-slate-500" id="pwGuide">※ 영문,
							숫자, 특수문자 포함 8자리 이상</span>
					</label>
				</div>

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">비밀번호
							확인 <span class="text-red-500">*</span>
					</span></label> <input type="password" id="pwConfirm"
						placeholder="비밀번호를 다시 한 번 입력해주세요."
						class="input input-bordered w-full focus:input-primary" required />
					<label class="label"> <span
						class="label-text-alt text-red-500 hidden" id="pwConfirmError">비밀번호가
							일치하지 않습니다.</span>
					</label>
				</div>

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">닉네임
							<span class="text-red-500">*</span>
					</span></label>
					<div class="flex gap-2">
						<input type="text" name="nickname" id="nickname"
							placeholder="사용하실 닉네임을 입력해주세요."
							class="input input-bordered w-full focus:input-primary" required />
						<button type="button" id="btnCheckNick"
							class="btn btn-outline btn-primary shrink-0">중복확인</button>
					</div>
				</div>

				<div class="form-control w-full mb-4">
					<label class="label"><span class="label-text font-bold">이메일
							<span class="text-red-500">*</span>
					</span></label>
					<div class="flex gap-2">
						<input type="email" name="email" id="email"
							placeholder="example@domain.com"
							class="input input-bordered w-full focus:input-primary" required />
						<button type="button" id="btnCheckEmail"
							class="btn btn-outline btn-primary shrink-0">중복확인</button>
					</div>
				</div>

				<div class="form-control w-full mb-8">
					<label class="label"><span class="label-text font-bold">프로필
							사진</span></label> <input type="file" name="pic" id="pic"
						class="file-input file-input-bordered w-full focus:file-input-primary"
						accept="image/*" /> <label class="label"> <span
						class="label-text-alt text-slate-500">※ 10MB 이하의 이미지 파일만
							업로드 가능합니다.</span>
					</label>
				</div>

				<div class="form-control mt-4">
					<button type="submit" class="btn btn-primary w-full text-lg">가입하기</button>
				</div>
			</form>
		</div>

	</main>

	<script>
    // 중복 확인 통과 여부를 저장하는 깃발(변수)들
    // 이 3개가 모두 true가 되어야만 가입 가능
    let isIdChecked = false;
    let isNickChecked = false;
    let isEmailChecked = false;

    // 1. 아이디 중복 확인 Ajax
    document.getElementById('btnCheckId').addEventListener('click', function() {
        const id = document.getElementById('id').value;
        // trim 으로 공백 지우기
        if(id.trim() === '') {
            alert('아이디를 입력해주세요.');
            document.getElementById('id').focus();
            return;
        }

        // id값을 들고 서버로 이동
        fetch('/teamtwo/user/checkid.do?id=' + id)
     	// 서블릿이 보낸 JSON 텍스트({"result": 1})를 자바스크립트 객체로 변환 (택배 상자 까기)
        .then(res => res.json()) // 서버가 준 JSON 데이터 받기
        .then(data => {
            if(data.result === 1) {
                alert('이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.');
                isIdChecked = false;
            } else {
                alert('사용 가능한 아이디입니다!');
                isIdChecked = true;
            }
        });
    });

    // 2. 닉네임 중복 확인 Ajax
    document.getElementById('btnCheckNick').addEventListener('click', function() {
        const nickname = document.getElementById('nickname').value;
        if(nickname.trim() === '') {
            alert('닉네임을 입력해주세요.');
            document.getElementById('nickname').focus();
            return;
        }

        fetch('/teamtwo/user/checknick.do?nickname=' + nickname)
        .then(res => res.json())
        .then(data => {
            if(data.result === 1) {
                alert('이미 사용 중인 닉네임입니다.');
                isNickChecked = false;
            } else {
                alert('사용 가능한 닉네임입니다!');
                isNickChecked = true;
            }
        });
    });

    // 3. 이메일 중복 확인 Ajax
    document.getElementById('btnCheckEmail').addEventListener('click', function() {
        const email = document.getElementById('email').value;
        if(email.trim() === '') {
            alert('이메일을 입력해주세요.');
            document.getElementById('email').focus();
            return;
        }

        fetch('/teamtwo/user/checkemail.do?email=' + email)
        .then(res => res.json())
        .then(data => {
            if(data.result === 1) {
                alert('이미 가입된 이메일입니다.');
                isEmailChecked = false;
            } else {
                alert('사용 가능한 이메일입니다!');
                isEmailChecked = true;
            }
        });
    });

    // 중복확인 통과해놓고 글자를 수정하는 경우 막기
    document.getElementById('id').addEventListener('input', () => isIdChecked = false);
    document.getElementById('nickname').addEventListener('input', () => isNickChecked = false);
    document.getElementById('email').addEventListener('input', () => isEmailChecked = false);


    // 4. 폼 제출 전 최종 유효성 검사 (가입하기 버튼 누를 때 실행)
    function validateForm() {
        // 깃발 검사
        if(!isIdChecked) {
            alert('아이디 중복확인을 진행해주세요.');
            document.getElementById('id').focus();
            return false;
        }
        if(!isNickChecked) {
            alert('닉네임 중복확인을 진행해주세요.');
            document.getElementById('nickname').focus();
            return false;
        }
        if(!isEmailChecked) {
            alert('이메일 중복확인을 진행해주세요.');
            document.getElementById('email').focus();
            return false;
        }

        // 비밀번호 유효성 검사
        const pw = document.getElementById('pw').value;
        const pwConfirm = document.getElementById('pwConfirm').value;
        const pwRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+~`\-={}\[\]:;"'<>,.?/]).{8,}$/;
        
        if (!pwRegex.test(pw)) {
            alert('비밀번호는 영문, 숫자, 특수문자를 포함하여 8자리 이상이어야 합니다.');
            document.getElementById('pw').focus();
            return false; 
        }
        
        if (pw !== pwConfirm) {
            alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
            document.getElementById('pwConfirm').focus();
            return false;
        }
        
        return true; // 이 모든 관문을 통과해야만 서버로 INSERT 하러 출발
    }

    // 5. 비밀번호 확인 실시간 검사
    document.getElementById('pwConfirm').addEventListener('keyup', function() {
        const pw = document.getElementById('pw').value;
        const errorMsg = document.getElementById('pwConfirmError');
        
        if (this.value !== pw && this.value.length > 0) {
            errorMsg.classList.remove('hidden'); 
            this.classList.add('input-error');   
        } else {
            errorMsg.classList.add('hidden');    
            this.classList.remove('input-error'); 
        }
    });
</script>
</body>
</html>