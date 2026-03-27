<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 탈퇴 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
	.unregister-wrap { max-width: 500px; margin: 0 auto; }
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap unregister-wrap py-20">
		<div class="content-card bg-white shadow-xl rounded-2xl p-10 text-center">
			
			<h1 class="text-3xl font-bold text-slate-800 mb-4 tracking-tight">정말 탈퇴하시겠습니까?</h1>
			<p class="text-slate-500 mb-10">이 작업은 되돌릴 수 없습니다.</p>
			
			<div class="flex gap-3 justify-center">
				<button type="button" class="btn btn-outline w-32" onclick="location.href='/teamtwo/user/mypage.do';">돌아가기</button>
				<button type="button" id="btnUnregister" class="btn btn-error w-32 text-white">탈퇴하기</button>
			</div>

		</div>
	</main>

	<dialog id="leaderModal" class="modal">
		<div class="modal-box">
			<h3 class="font-bold text-lg text-error mb-2">탈퇴 불가 안내</h3>
			<p class="py-4 text-slate-600">
				스터디장으로 설정된 계정입니다.<br>
				<span class="font-semibold text-slate-800">다른 스터디 회원에게 스터디장을 양도하고 탈퇴를 진행해주세요.</span>
			</p>
			<div class="modal-action">
				<form method="dialog">
					<button class="btn btn-primary">확인</button>
				</form>
			</div>
		</div>
		<form method="dialog" class="modal-backdrop">
			<button>닫기</button>
		</form>
	</dialog>

	<script>
		document.getElementById('btnUnregister').addEventListener('click', function() {
			
			// 진짜 탈퇴할 건지 물어보는 기본 알림창
			if(!confirm('모든 정보가 삭제됩니다. 정말 탈퇴하시겠습니까?')) {
				return;
			}

			fetch('/teamtwo/user/unregister.do', { 
				method: 'POST' 
			})
			.then(res => res.json())
			.then(data => {
				if(data.result === -1) {
					// ★ 서버에서 -1 이 오면, 알림창 표시
					document.getElementById('leaderModal').showModal();
				} 
				else if(data.result === 1) {
					alert('정상적으로 처리되었습니다.');
					location.href = '/teamtwo/index.do'; // 탈퇴 후 메인 페이지로 이동
				} 
				else {
					alert('탈퇴 처리 중 오류가 발생했습니다.');
				}
			});
		});
	</script>
</body>
</html>