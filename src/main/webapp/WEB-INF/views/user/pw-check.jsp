<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>비밀번호 확인 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
	.find-wrap { max-width: 400px; margin: 0 auto; }
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap find-wrap py-20">
		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">계속하려면 비밀번호를 입력하세요</h1>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">
			
			<div class="form-control w-full mb-6">
				<input type="password" id="pw" placeholder="비밀번호 입력" class="input input-bordered w-full focus:input-primary" />
				<input type="hidden" id="target" value="${target}" />
			</div>

			<div class="flex gap-2">
				<button type="button" class="btn btn-outline flex-1" onclick="history.back();">돌아가기</button>
				<button type="button" id="btnVerify" class="btn btn-primary flex-1">확인</button>
			</div>

		</div>
	</main>

	<script>
		// 엔터키 쳤을 때도 바로 확인 버튼 눌리게 하는 센스!
		document.getElementById('pw').addEventListener('keyup', function(e) {
			if(e.key === 'Enter') document.getElementById('btnVerify').click();
		});

		// 확인 버튼 로직
		document.getElementById('btnVerify').addEventListener('click', function() {
			const pw = document.getElementById('pw').value;
			const target = document.getElementById('target').value;
			
			if(pw.trim() === '') { alert('비밀번호를 입력해주세요.'); return; }

			fetch('/teamtwo/user/pw-check.do', {
				method: 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				body: 'pw=' + encodeURIComponent(pw)
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === 1) {
					// 1. 회원 정보 수정으로 가기
					if (target === 'edit') {
						location.href = '/teamtwo/user/info-edit.do';
					} 
					// 2. 회원 탈퇴로 가기
					else if (target === 'unregister') {
						location.href = '/teamtwo/user/unregister.do';
					}
					// 3. 기타 (방어 코드)
					else {
						location.href = '/teamtwo/user/mypage.do';
					}
				} else {
					alert('비밀번호가 일치하지 않습니다.');
					document.getElementById('pw').value = ''; // 틀리면 입력창 싹 비워주기
					document.getElementById('pw').focus();
				}
			});
		});
	</script>
</body>
</html>