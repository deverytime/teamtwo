<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
.edit-wrap { max-width: 500px; margin: 0 auto; }
.input-msg { font-size: 0.875rem; margin-top: 0.25rem; display: none; }
.msg-error { color: #ef4444; display: block; }
.msg-success { color: #10b981; display: block; }
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap edit-wrap py-12">
		<div class="mb-8 text-center border-b pb-6">
			<h1 class="text-3xl font-bold mb-2">회원 정보 수정</h1>
			<p class="text-slate-500">변경할 정보를 입력하세요</p>
		</div>

		<div class="content-card bg-white shadow-xl rounded-2xl p-8">

			<div class="form-control w-full mb-8 flex flex-col items-center">
				<div class="avatar mb-4">
					<div class="w-32 rounded-xl ring ring-primary ring-offset-base-100 ring-offset-2">
						<img id="picPreview" src="/teamtwo/asset/pic/${authDto.pic}" onerror="this.src='/teamtwo/asset/pic/pic.png'" />
					</div>
				</div>
				<input type="file" id="pic" name="pic" accept="image/*" class="file-input file-input-bordered file-input-sm w-full max-w-xs" />
			</div>

			<div class="form-control w-full mb-6">
				<label class="label"><span class="label-text font-bold">닉네임 변경</span></label>
				<div class="flex gap-2">
					<input type="text" id="nickname" value="${authDto.nickname}" class="input input-bordered w-full focus:input-primary" />
					<button type="button" id="btnCheckNick" class="btn btn-outline shrink-0">중복확인</button>
				</div>
				<div id="nickMsg" class="input-msg"></div>
			</div>

			<div class="form-control w-full mb-8">
				<label class="label"><span class="label-text font-bold">이메일 변경</span></label>
				<div class="flex gap-2">
					<input type="email" id="email" value="${authDto.email}" class="input input-bordered w-full focus:input-primary" />
					<button type="button" id="btnCheckEmail" class="btn btn-outline shrink-0">중복확인</button>
				</div>
				<div id="emailMsg" class="input-msg"></div>
			</div>

			<div class="flex gap-2 mt-4 pt-6 border-t border-slate-100">
				<button type="button" class="btn btn-outline flex-1" onclick="location.href='/teamtwo/user/mypage.do';">취소</button>
				<button type="button" id="btnEdit" class="btn btn-primary flex-1">확인</button>
			</div>

		</div>
	</main>

	<script>
		const currentNick = '${authDto.nickname}';
		const currentEmail = '${authDto.email}';

		let isNickValid = true; 
		let isEmailValid = true;

		// 1. 입력 감지
		document.getElementById('nickname').addEventListener('input', function() {
			if(this.value === currentNick) { isNickValid = true; document.getElementById('nickMsg').className = 'input-msg'; } 
			else { isNickValid = false; document.getElementById('nickMsg').className = 'input-msg msg-error'; document.getElementById('nickMsg').innerText = '중복확인을 해주세요.'; }
		});

		document.getElementById('email').addEventListener('input', function() {
			if(this.value === currentEmail) { isEmailValid = true; document.getElementById('emailMsg').className = 'input-msg'; } 
			else { isEmailValid = false; document.getElementById('emailMsg').className = 'input-msg msg-error'; document.getElementById('emailMsg').innerText = '중복확인을 해주세요.'; }
		});

		// 2. 닉네임 중복 검사
		document.getElementById('btnCheckNick').addEventListener('click', function() {
			const nickname = document.getElementById('nickname').value;
			if(nickname.trim() === '') { alert('닉네임을 입력하세요.'); return; }
			if(nickname === currentNick) { alert('현재 닉네임과 동일합니다.'); return; }

			fetch('/teamtwo/user/info-edit.do', {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: 'action=checkNick&nickname=' + encodeURIComponent(nickname)
			})
			.then(res => res.json())
			.then(data => {
				const msg = document.getElementById('nickMsg');
				if(data.result > 0) { msg.className = 'input-msg msg-error'; msg.innerText = '이미 사용중인 닉네임입니다.'; isNickValid = false; } 
				else { msg.className = 'input-msg msg-success'; msg.innerText = '사용 가능한 닉네임입니다.'; isNickValid = true; }
			});
		});

		// 3. 이메일 중복 검사
		document.getElementById('btnCheckEmail').addEventListener('click', function() {
			const email = document.getElementById('email').value;
			if(email.trim() === '') { alert('이메일을 입력하세요.'); return; }
			if(email === currentEmail) { alert('현재 이메일과 동일합니다.'); return; }

			fetch('/teamtwo/user/info-edit.do', {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: 'action=checkEmail&email=' + encodeURIComponent(email)
			})
			.then(res => res.json())
			.then(data => {
				const msg = document.getElementById('emailMsg');
				if(data.result > 0) { msg.className = 'input-msg msg-error'; msg.innerText = '이미 사용중인 이메일입니다.'; isEmailValid = false; } 
				else { msg.className = 'input-msg msg-success'; msg.innerText = '사용 가능한 이메일입니다.'; isEmailValid = true; }
			});
		});

		// 4. 프로필 사진 미리보기
		document.getElementById('pic').addEventListener('change', function(e) {
			if(e.target.files && e.target.files[0]) {
				const reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById('picPreview').src = e.target.result;
				}
				reader.readAsDataURL(e.target.files[0]);
			}
		});

		// 5. 최종 폼 데이터 전송
		document.getElementById('btnEdit').addEventListener('click', function() {
			if(!isNickValid) { alert('닉네임 중복확인을 완료해주세요.'); return; }
			if(!isEmailValid) { alert('이메일 중복확인을 완료해주세요.'); return; }

			const formData = new FormData();
			formData.append('nickname', document.getElementById('nickname').value);
			formData.append('email', document.getElementById('email').value);
			
			const picFile = document.getElementById('pic').files[0];
			if(picFile) { formData.append('pic', picFile); }

			fetch('/teamtwo/user/info-edit.do', {
				method: 'POST',
				body: formData 
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === 1) {
					alert('정상적으로 수정되었습니다.');
					location.href = '/teamtwo/user/mypage.do'; 
				} else {
					alert('수정에 실패했습니다. 다시 시도해주세요.');
				}
			});
		});
	</script>
</body>
</html>