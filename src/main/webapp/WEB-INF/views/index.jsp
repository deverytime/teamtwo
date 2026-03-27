<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>deverytime - 메인</title>
	<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap">
		
		<section class="mb-14">
			<div class="flex justify-between items-end mb-4">
				<h2 class="text-xl font-bold">📚 신규 모집 중인 스터디</h2>
				<a href="/teamtwo/study/study-list.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
			</div>
			
			<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
				<div class="content-card card-pad hover:-translate-y-1 transition-transform cursor-pointer" onclick="location.href='#'">
					<div class="flex justify-between items-start mb-2">
						<span class="chip chip-blue">모집중</span>
						<span class="text-xs text-slate-500 font-semibold">3/5 명</span>
					</div>
					<h3 class="text-lg font-bold truncate mt-1">자바 기초 스터디원 모집!</h3>
					<p class="text-sm text-slate-500 mt-2 line-clamp-2">비전공자 초보 환영합니다. 주 2회 디스코드에서 화면 공유하며 공부해요.</p>
				</div>
				</div>
		</section>

		<section class="grid grid-cols-1 lg:grid-cols-2 gap-8">
			
			<div class="content-card card-pad">
				<div class="flex justify-between items-end mb-4">
					<h2 class="text-lg font-bold flex items-center gap-2">🔥 실시간 인기글</h2>
					<a href="/teamtwo/board/trendingboard/List.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-100">
					<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors" onclick="location.href='#'">
						<div class="flex justify-between items-center">
							<span class="text-sm truncate flex-1 font-medium">정보처리기사 실기 합격 후기 및 꿀팁 공유</span>
							<span class="text-xs text-point-500 ml-2 font-bold">[15]</span>
						</div>
					</li>
					<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors" onclick="location.href='#'">
						<div class="flex justify-between items-center">
							<span class="text-sm truncate flex-1 font-medium">면접 볼 때 이 질문 꼭 나오네요;;</span>
							<span class="text-xs text-point-500 ml-2 font-bold">[12]</span>
						</div>
					</li>
				</ul>
			</div>

			<div class="content-card card-pad">
				<div class="flex justify-between items-end mb-4">
					<h2 class="text-lg font-bold flex items-center gap-2">💬 자유게시판 최신글</h2>
					<a href="/teamtwo/board/freeboard/List.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-100">
					<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors" onclick="location.href='#'">
						<div class="flex justify-between items-center">
							<span class="text-sm truncate flex-1 font-medium">다들 취업 준비 어떻게 하고 계신가요? ㅠㅠ</span>
							<span class="text-xs text-slate-400 ml-2">10분 전</span>
						</div>
					</li>
					<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors" onclick="location.href='#'">
						<div class="flex justify-between items-center">
							<span class="text-sm truncate flex-1 font-medium">오늘 점심 메뉴 추천받습니다!</span>
							<span class="text-xs text-slate-400 ml-2">1시간 전</span>
						</div>
					</li>
				</ul>
			</div>

		</section>

	</main>
</body>
</html>