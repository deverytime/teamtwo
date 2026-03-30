<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>2차 인증 설정 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
.setup-wrap {
	max-width: 450px;
	margin: 0 auto;
}
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap setup-wrap py-20">
		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">이메일 2차 인증</h1>
			<p class="text-slate-500">
				<span class="font-bold text-primary">${authDto.email}</span>(으)로<br>
				인증번호를 보냈습니다.
			</p>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">

			<div class="form-control w-full mb-2 relative">
				<input type="text" id="authCode" placeholder="인증번호 6자리 입력"
					class="input input-bordered w-full pr-24 focus:input-primary"
					maxlength="6" />
				<button type="button" id="btnVerify"
					class="btn btn-sm btn-primary absolute top-1/2 -translate-y-1/2 right-2">인증확인</button>
			</div>
			<div id="verifyMsg" class="text-sm font-semibold mb-6 h-5"></div>

			<div class="flex gap-2">
				<button type="button" id="btnResend" class="btn btn-outline flex-1">인증번호
					재발송</button>
				<button type="button" id="btnEnable" class="btn btn-disabled flex-1">계속하기</button>
			</div>

		</div>
	</main>

	<script>
		const URL = '/teamtwo/user/twofactor-setup.do';
		let isVerified = false; // serverCode 변수는 삭제!

		// 1. 이메일 전송 (정답 인증번호는 받아오지 않음)
		function sendMail() {
			document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-slate-500';
			document.getElementById('verifyMsg').innerText = '인증번호를 발송 중입니다...';
			
			fetch(URL, {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: 'action=sendAuth&email=${authDto.email}' 
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === 1) {
					// data.code를 저장하는 부분 삭제
					document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-primary';
					document.getElementById('verifyMsg').innerText = '인증번호가 발송되었습니다.';
				} else {
					document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-error';
					document.getElementById('verifyMsg').innerText = '메일 발송에 실패했습니다.';
				}
			});
		}

		window.onload = sendMail;
		document.getElementById('btnResend').addEventListener('click', sendMail);

		// 2. 인증번호 확인 (서버로 채점 요청)
		document.getElementById('btnVerify').addEventListener('click', function() {
			const inputCode = document.getElementById('authCode').value;
			const msg = document.getElementById('verifyMsg');
			const mode = '${param.mode}'; // 로그인, 탈퇴 등 어떤 목적인지 챙김

			if (inputCode === '') {
				msg.className = 'text-sm font-semibold mb-6 h-5 text-error';
				msg.innerText = '인증번호를 입력해주세요.';
				return;
			}

			// 서버(Java)로 내가 입력한 번호를 보내서 맞는지 확인받기
			fetch(URL, {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: 'action=verify&code=' + inputCode + '&mode=' + mode
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === 1) {
					msg.className = 'text-sm font-semibold mb-6 h-5 text-success';
					msg.innerText = '인증되었습니다.';
					isVerified = true;
					document.getElementById('btnEnable').className = 'btn btn-primary flex-1';
				} else {
					msg.className = 'text-sm font-semibold mb-6 h-5 text-error';
					msg.innerText = '인증번호가 틀렸습니다.';
					isVerified = false;
					document.getElementById('btnEnable').className = 'btn btn-disabled flex-1';
				}
			});
		});

		// 3. 계속하기
		document.getElementById('btnEnable').addEventListener('click', function() {
			if (!isVerified) return;

			const mode = '${param.mode}';

			if (mode === 'login') {
				// 이미 서버에서 검증 + 세션 승급(tempAuthId -> auth)까지 끝나면 이동
				location.href = '/teamtwo/index.do';
			} else if (mode === 'unregister') {
				location.href = '/teamtwo/user/unregister.do';
			} else if (mode === 'pwchange') {
				location.href = '/teamtwo/user/pw-edit.do?verified=true';
			} else {
				fetch(URL, { 
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
					body: 'action=enable'
				})
				.then(res => res.json())
				.then(data => {
					if(data.result === 1) {
						alert('2차 인증이 활성화되었습니다.');
						location.href = '/teamtwo/user/mypage.do';
					} else {
						alert('오류가 발생했습니다.');
					}
				});
			}
		});
	</script>
</body>
</html>