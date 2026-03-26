<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
.find-wrap {
	max-width: 400px;
	margin: 0 auto;
}
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap find-wrap py-20">
		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">비밀번호 찾기</h1>
			<p class="text-slate-500">아이디를 입력해주세요</p>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">

			<div class="form-control w-full mb-4" id="step1">
				<div class="flex gap-2">
					<input type="text" id="id" placeholder="아이디 입력"
						class="input input-bordered w-full focus:input-primary" />
					<button type="button" id="btnCheckId"
						class="btn btn-primary shrink-0">다음</button>
				</div>
			</div>

			<div class="form-control w-full mb-4 hidden" id="step2">
				<div class="flex gap-2">
					<input type="email" id="email" placeholder="본인확인 이메일 입력"
						class="input input-bordered w-full focus:input-primary" />
					<button type="button" id="btnCheckEmail"
						class="btn btn-primary shrink-0">다음</button>
				</div>
			</div>

			<div class="form-control w-full mb-4 hidden" id="step3">
				<div class="flex gap-2">
					<input type="text" id="authCode" placeholder="인증 번호 입력"
						class="input input-bordered w-full focus:input-primary" />
					<button type="button" id="btnVerify"
						class="btn btn-primary shrink-0">인증하기</button>
				</div>
				<button type="button" id="btnResend"
					class="btn btn-outline btn-sm mt-2 hidden">인증번호 재발송</button>
			</div>

		</div>
	</main>

	<script>
	// 1단계: 아이디 존재 검사
	let isStep1Done = false; // 1단계 완료 상태를 기억하는 변수

	document.getElementById('btnCheckId').addEventListener('click', function() {
		const idInput = document.getElementById('id');
		
		// 1. 만약 이미 완료된 상태에서 '수정' 버튼을 눌렀다면
		if(isStep1Done) {
			idInput.readOnly = false;           // 입력칸 잠금 해제
			this.innerText = '다음';            // 버튼 이름 원상복구
			this.classList.replace('btn-secondary', 'btn-primary'); // 색상 변경
			
			// 아래 열려있던 단계들 전부 다시 숨기기
			document.getElementById('step2').classList.add('hidden');
			document.getElementById('step3').classList.add('hidden');
			
			isStep1Done = false; // 상태 초기화
			return; // 여기서 함수 종료
		}

		// 2. 처음 '다음' 버튼을 눌렀을 때
		const id = idInput.value;
		if(id.trim() === '') { alert('아이디를 입력해주세요.'); return; }

		fetch('/teamtwo/user/pw-find.do', {
			method: 'POST',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			body: 'action=checkId&id=' + id
		})
		.then(res => res.json())
		.then(data => {
			if(data.result > 0) {
				idInput.readOnly = true; // 입력 못하게 막기
				
				// 버튼을 비활성화 하지 않고 '수정' 버튼으로 변경
				this.innerText = '수정';
				this.classList.replace('btn-primary', 'btn-secondary'); // 회색 버튼으로 변경
				
				document.getElementById('step2').classList.remove('hidden');
				isStep1Done = true; // 완료 상태로 변경
			} else {
				alert('존재하지 않는 아이디입니다.');
			}
		});
	});

	// 2단계: 아이디+이메일 검사 및 인증 메일 발송
	document.getElementById('btnCheckEmail').addEventListener('click', function() {
		const id = document.getElementById('id').value;
		const email = document.getElementById('email').value;
		if(email.trim() === '') { alert('이메일을 입력해주세요.'); return; }

		this.innerText = "발송중...";
		this.disabled = true;

		fetch('/teamtwo/user/pw-find.do', {
			method: 'POST',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			body: 'action=sendAuth&id=' + id + '&email=' + email
		})
		.then(res => res.json())
		.then(data => {
			if(data.result === 1) {
				alert('이메일로 본인 인증 번호가 발송되었습니다.');
				document.getElementById('email').readOnly = true;
				this.innerText = "다음";
				document.getElementById('step3').classList.remove('hidden');
			} else {
				alert('사용자 정보가 일치하지 않습니다.');
				this.innerText = "다음";
				this.disabled = false;
			}
		});
	});

	// 3단계: 인증 통과 및 임시 비밀번호 발급
	document.getElementById('btnVerify').addEventListener('click', function() {
		const id = document.getElementById('id').value;
		const email = document.getElementById('email').value;
		const authCode = document.getElementById('authCode').value;
		if(authCode.trim() === '') { alert('인증번호를 입력해주세요.'); return; }

		fetch('/teamtwo/user/pw-find.do', {
			method: 'POST',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			body: 'action=verify&id=' + id + '&email=' + email + '&authCode=' + authCode
		})
		.then(res => res.json())
		.then(data => {
			if(data.result === 1) {
				alert('등록된 이메일로 임시 비밀번호가 발송되었습니다.');
				location.href = '/teamtwo/user/login.do';
			} else {
				alert('인증 번호가 일치하지 않습니다.');
				document.getElementById('btnResend').classList.remove('hidden');
			}
		});
	});
	</script>
</body>
</html>