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
		let serverCode = ''; 
		let isVerified = false;

		// 1. 이메일 전송
		function sendMail() {
			document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-slate-500';
			document.getElementById('verifyMsg').innerText = '인증번호를 발송 중입니다...';
			
			fetch(URL, {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				// action=sendAuth 꼬리표 붙이기
				body: 'action=sendAuth&email=${authDto.email}' 
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === 1) {
					serverCode = data.code; 
					document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-primary';
					document.getElementById('verifyMsg').innerText = '인증번호가 발송되었습니다.';
				} else {
					document.getElementById('verifyMsg').className = 'text-sm font-semibold mb-6 h-5 text-error';
					document.getElementById('verifyMsg').innerText = '메일 발송에 실패했습니다.';
				}
			});
		}

		// 창 열리면 즉시 발송
		window.onload = sendMail;

		// 재발송 버튼
		document.getElementById('btnResend').addEventListener('click', sendMail);

		// 2. 인증번호 확인
		document.getElementById('btnVerify').addEventListener('click', function() {
			const inputCode = document.getElementById('authCode').value;
			const msg = document.getElementById('verifyMsg');

			if (inputCode === '') {
				msg.className = 'text-sm font-semibold mb-6 h-5 text-error';
				msg.innerText = '인증번호를 입력해주세요.';
				return;
			}

			if (inputCode === serverCode) {
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

		// 3. 계속하기 (꼬리표에 따라 길 나누기)
		document.getElementById('btnEnable').addEventListener('click', function() {
			if (!isVerified) return;

			// 주소창에 달린 ?mode= 값을 가져옴 (없으면 빈 문자열)
			const mode = '${param.mode}';

			if (mode === 'login') {
				// ===============================================
				// [1] 로그인 목적으로 왔을 때
				// ===============================================
				// 이미 Login.java에서 세션 처리는 끝났으므로, 
				// 2차 인증 통과만 확인되면 바로 메인 페이지로 보냄
				location.href = '/teamtwo/index.do';
				
			} else if (mode === 'unregister') {
				// [2] 탈퇴 목적으로 왔을 때 (인증 성공 시 탈퇴 화면으로)
				location.href = '/teamtwo/user/unregister.do';
			} else if (mode === 'pwchange') {
				// [3] 비밀번호 변경을 위해 왔을 때
				location.href = '/teamtwo/user/pw-edit.do?verified=true';
			} else {
				// ===============================================
				// [4] 마이페이지에서 활성화 목적으로 왔을 때
				// ===============================================
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