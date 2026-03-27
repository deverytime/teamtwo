<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 메인 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<div class="flex max-w-6xl mx-auto w-full">
		
		<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>

		<main class="flex-1 p-8">
			<div class="mb-8 border-b pb-4">
				<h1 class="text-3xl font-bold mb-2">관리자 메뉴</h1>
				<p class="text-slate-500">사이트의 전체 현황을 한눈에 파악하세요.</p>
			</div>

			<div class="grid grid-cols-3 gap-6 mb-8">
				<div class="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 text-center">
					<div class="text-slate-500 mb-2 font-semibold">총 회원 수</div>
					<div class="text-4xl font-bold text-primary"><span class="counter-up inline-block" data-target="${stats.totalMember}">0</span> <span class="text-lg font-normal text-slate-600">명</span></div>
				</div>
				<div class="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 text-center">
					<div class="text-slate-500 mb-2 font-semibold">총 게시글 수</div>
					<div class="text-4xl font-bold text-primary"><span class="counter-up inline-block" data-target="${stats.totalPost}">0</span> <span class="text-lg font-normal text-slate-600">개</span></div>
				</div>
				<div class="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 text-center">
					<div class="text-slate-500 mb-2 font-semibold">진행 중 스터디</div>
					<div class="text-4xl font-bold text-primary"><span class="counter-up inline-block" data-target="${stats.activeStudy}">0</span> <span class="text-lg font-normal text-slate-600">개</span></div>
				</div>
			</div>

			<div class="grid grid-cols-3 gap-6 mb-8">
				<a href="/teamtwo/admin/member-list.do" class="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 hover:border-primary hover:shadow-md transition-all text-center group">
					<div class="text-5xl mb-4">👤</div>
					<div class="font-bold text-xl mb-2 group-hover:text-primary transition-colors">회원 관리</div>
					<div class="text-sm text-slate-500 break-keep">회원 목록 조회 및<br>상태(정지/탈퇴) 변경</div>
				</a>
				<a href="/teamtwo/admin/board-list.do" class="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 hover:border-primary hover:shadow-md transition-all text-center group">
					<div class="text-5xl mb-4">📝</div>
					<div class="font-bold text-xl mb-2 group-hover:text-primary transition-colors">게시글 관리</div>
					<div class="text-sm text-slate-500 break-keep">게시판별 게시글 조회 및<br>부적절한 글 삭제</div>
				</a>
				<a href="/teamtwo/admin/study-list.do" class="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 hover:border-primary hover:shadow-md transition-all text-center group">
					<div class="text-5xl mb-4">📚</div>
					<div class="font-bold text-xl mb-2 group-hover:text-primary transition-colors">스터디 관리</div>
					<div class="text-sm text-slate-500 break-keep">개설된 스터디 목록 조회 및<br>강제 삭제</div>
				</a>
			</div>

			<a href="/teamtwo/admin/request-list.do" class="block bg-white p-6 rounded-2xl shadow-sm border border-slate-100 hover:border-primary hover:shadow-md transition-all">
				<div class="flex justify-between items-center mb-4">
					<h3 class="font-bold text-lg text-slate-800">게시판 요청 관리</h3>
					<span class="text-sm text-primary font-semibold">목록으로 이동 &rarr;</span>
				</div>
				<div class="flex gap-6 text-slate-600 bg-slate-50 p-5 rounded-xl text-lg items-center justify-center">
					<div>전체 요청: <span class="font-bold text-slate-800">${stats.reqTotal}</span>건</div>
					<div class="text-slate-300">|</div>
					<div>처리 대기: <span class="font-bold text-error">${stats.reqPending}</span>건</div>
					<div class="text-slate-300">|</div>
					<div>완료: <span class="font-bold text-success">${stats.reqCompleted}</span>건</div>
				</div>
			</a>

		</main>
	</div>
<style>
		/* 위에서 아래로 부드럽게 떨어지는 애니메이션 */
		@keyframes dropDown {
			0% { transform: translateY(-20px); opacity: 0; }
			100% { transform: translateY(0); opacity: 1; }
		}
		.counter-up {
			animation: dropDown 0.8s ease-out forwards;
		}
</style>

	<script>
		// 숫자 촤르륵 카운팅 애니메이션 로직
		const counters = document.querySelectorAll('.counter-up');
		const speed = 30; // 숫자가 올라가는 속도 (낮을수록 더 오래 걸림)

		counters.forEach(counter => {
			const updateCount = () => {
				const target = +counter.getAttribute('data-target');
				const count = +counter.innerText;

				// 한번에 올라갈 숫자 단위 계산
				const inc = target / speed;

				if (count < target) {
					// 소수점 올림으로 부드럽게 더해줌
					counter.innerText = Math.ceil(count + inc);
					// 20ms마다 다시 실행
					setTimeout(updateCount, 20);
				} else {
					// 최종 숫자에 도달하면 딱 맞춰줌
					counter.innerText = target;
				}
			};
			updateCount();
		});
	</script>
</body>
</html>