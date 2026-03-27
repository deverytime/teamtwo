<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>비밀번호 변경 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
	.pw-wrap { max-width: 450px; margin: 0 auto; }
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap pw-wrap py-20">
		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">비밀번호 변경</h1>
			<p class="text-slate-500">새 비밀번호를 입력하세요</p>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">

			<c:choose>
				<c:when test="${param.verified != 'true'}">
					<div class="text-center py-6">
						<p class="text-slate-600 mb-6 font-semibold">안전한 비밀번호 변경을 위해<br>이메일 본인 인증이 필요합니다.</p>
						<button type="button" class="btn btn-primary w-full" onclick="location.href='/teamtwo/user/twofactor-setup.do?mode=pwchange'">이메일로 본인 인증</button>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="form-control w-full mb-4">
						<input type="password" id="newPw" placeholder="새 비밀번호 입력" class="input input-bordered w-full focus:input-primary" />
						<div id="pwMsg" class="text-sm mt-2 min-h-[20px] font-semibold"></div>
					</div>
					
					<div class="form-control w-full mb-8">
						<input type="password" id="newPwConfirm" placeholder="새 비밀번호 확인 입력" class="input input-bordered w-full focus:input-primary" />
						<div id="pwConfirmMsg" class="text-sm mt-2 min-h-[20px] font-semibold"></div>
					</div>

					<div class="flex gap-2">
						<button type="button" class="btn btn-outline flex-1" onclick="location.href='/teamtwo/user/mypage.do'">취소</button>
						<button type="button" id="btnChange" class="btn btn-primary flex-1 btn-disabled">변경하기</button>
					</div>
				</c:otherwise>
			</c:choose>

		</div>
	</main>

	<script>
		// 인증을 마치고 온 상태라면 (비밀번호 폼이 존재한다면) 유효성 검사 실행
		if (document.getElementById('newPw')) {
			const newPw = document.getElementById('newPw');
			const newPwConfirm = document.getElementById('newPwConfirm');
			const pwMsg = document.getElementById('pwMsg');
			const pwConfirmMsg = document.getElementById('pwConfirmMsg');
			const btnChange = document.getElementById('btnChange');

			// 영문, 숫자, 특수문자 포함 8자리 이상 정규식
			const pwRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_~]).{8,}$/;
			let isPwValid = false;
			let isPwMatch = false;

			newPw.addEventListener('keyup', function() {
				if (pwRegex.test(newPw.value)) {
					pwMsg.className = 'text-sm mt-2 min-h-[20px] font-semibold text-success';
					pwMsg.innerText = '사용 가능한 비밀번호입니다.';
					isPwValid = true;
				} else {
					pwMsg.className = 'text-sm mt-2 min-h-[20px] font-semibold text-error';
					pwMsg.innerText = '사용할 수 없는 비밀번호입니다. (영문/숫자/특수문자 포함 8자 이상)';
					isPwValid = false;
				}
				checkAllValid();
			});

			newPwConfirm.addEventListener('keyup', function() {
				if (newPw.value === newPwConfirm.value && newPwConfirm.value !== '') {
					pwConfirmMsg.className = 'text-sm mt-2 min-h-[20px] font-semibold text-success';
					pwConfirmMsg.innerText = '비밀번호가 일치합니다.';
					isPwMatch = true;
				} else {
					pwConfirmMsg.className = 'text-sm mt-2 min-h-[20px] font-semibold text-error';
					pwConfirmMsg.innerText = '비밀번호가 일치하지 않습니다.';
					isPwMatch = false;
				}
				checkAllValid();
			});

			function checkAllValid() {
				if (isPwValid && isPwMatch) {
					btnChange.classList.remove('btn-disabled');
				} else {
					btnChange.classList.add('btn-disabled');
				}
			}

			// 통신: 방금 만든 서블릿으로 요청
			btnChange.addEventListener('click', function() {
				if(btnChange.classList.contains('btn-disabled')) return;

				fetch('/teamtwo/user/pw-edit.do', {
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: 'pw=' + encodeURIComponent(newPw.value)
				})
				.then(res => res.json())
				.then(data => {
					if(data.result === 1) {
						alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.');
						location.href = '/teamtwo/user/login.do';
					} else {
						alert('오류가 발생했습니다.');
					}
				});
			});
		}
	</script>
</body>
</html>