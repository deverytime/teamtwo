<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>deverytime - 메인</title>
	<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
	<style>
		.hide-scrollbar {
			-ms-overflow-style: none; /* IE, Edge */
			scrollbar-width: none; /* Firefox */
		}
		.hide-scrollbar::-webkit-scrollbar {
			display: none; /* Chrome, Safari, Opera */
		}
	</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap pb-20 !pt-6">
		
		<section class="mb-14">
			<div class="relative group w-full">
				<button id="btnAdPrev" class="absolute -left-5 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❮</button>

				<div class="carousel w-full h-20 rounded-xl shadow-sm overflow-hidden" id="adCarousel">
					<a href="https://www.inflearn.com" target="_blank" id="slide1" class="carousel-item relative w-full bg-indigo-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">💻</span>
							<div>
								<h3 class="text-lg font-bold text-indigo-900 leading-tight">웹 개발 부트캠프 5기 모집 중!</h3>
								<p class="text-sm text-indigo-700 font-medium mt-0.5">HTML, CSS, JS부터 백엔드까지 한 번에 마스터하세요.</p>
							</div>
						</div>
					</a>
					<a href="https://brand.naver.com/sidiz" target="_blank" id="slide2" class="carousel-item relative w-full bg-emerald-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">💺</span>
							<div>
								<h3 class="text-lg font-bold text-emerald-900 leading-tight">프리미엄 사무용 의자 기획전</h3>
								<p class="text-sm text-emerald-700 font-medium mt-0.5">장시간 코딩에도 허리가 편안한 의자, 지금 바로 확인하세요.</p>
							</div>
						</div>
					</a>
					<a href="https://brand.naver.com/razerplace" target="_blank" id="slide3" class="carousel-item relative w-full bg-rose-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">🖱️</span>
							<div>
								<h3 class="text-lg font-bold text-rose-900 leading-tight">최고의 그립감, 게이밍 마우스 특가</h3>
								<p class="text-sm text-rose-700 font-medium mt-0.5">대칭형 무선 마우스로 당신의 에임과 작업 속도를 올려보세요.</p>
							</div>
						</div>
					</a>
				</div>

				<button id="btnAdNext" class="absolute -right-5 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❯</button>
			</div>
		</section>

		<section class="mb-12">
			<div class="flex justify-between items-end mb-4">
				<h2 class="text-xl font-bold">📚 신규 모집 중인 스터디</h2>
				<a href="/teamtwo/study/study-list.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
			</div>
			
			<div class="relative group w-full">
				<button id="btnPrev" class="absolute -left-5 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❮</button>
				
				<div id="studyContainer" class="flex gap-4 overflow-x-auto scroll-smooth py-1 snap-x snap-mandatory hide-scrollbar">
					<c:forEach items="${studyList}" var="study">
						<div class="content-card card-pad flex-none w-[calc(25%-12px)] min-h-[160px] snap-start hover:-translate-y-1 transition-transform cursor-pointer shadow-sm border border-slate-100 bg-white rounded-xl flex flex-col" onclick="location.href='/teamtwo/study/study-view.do?seq=${study.seq}'">
							<div class="flex justify-between items-start mb-2">
								<span class="chip chip-blue">모집중</span>
								<span class="text-xs text-slate-500 font-semibold">최대 ${study.capacity}명</span>
							</div>
							<h3 class="text-lg font-bold truncate mt-1">${study.name}</h3>
							<p class="text-sm text-slate-500 mt-2 line-clamp-2 flex-1">${study.description}</p>
						</div>
					</c:forEach>
				</div>
				
				<button id="btnNext" class="absolute -right-5 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❯</button>
			</div>
		</section>

		<section class="grid grid-cols-1 lg:grid-cols-2 gap-8 mt-8">
			
			<div class="content-card card-pad bg-white shadow-sm border border-slate-100 rounded-xl min-h-[250px]">
				<div class="flex justify-between items-end mb-4 border-b border-slate-100 pb-3">
					<h2 class="text-lg font-bold flex items-center gap-2">🔥 실시간 인기글</h2>
					<a href="/teamtwo/board/trendingboard/list.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-50">
					<c:forEach items="${trendingList}" var="post">
						<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors px-2 rounded-lg" onclick="location.href='/teamtwo/board/view.do?board=${post.boardType}&seq=${post.seq}'">
							<div class="flex justify-between items-center">
								<span class="text-sm truncate flex-1 font-medium text-slate-700">${post.title}</span>
								<span class="text-xs text-point-500 ml-2 font-bold bg-red-50 px-2 py-1 rounded-md">👍 ${post.recommend}</span>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>

			<div class="content-card card-pad bg-white shadow-sm border border-slate-100 rounded-xl min-h-[250px]">
				<div class="flex justify-between items-end mb-4 border-b border-slate-100 pb-3">
					<h2 class="text-lg font-bold flex items-center gap-2">💬 자유게시판 최신글</h2>
					<a href="/teamtwo/board/list.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-50">
					<c:forEach items="${freeboardList}" var="post">
						<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors px-2 rounded-lg" onclick="location.href='/teamtwo/board/view.do?board=${post.boardType}&seq=${post.seq}'">
							<div class="flex justify-between items-center">
								<span class="text-sm truncate flex-1 font-medium text-slate-700">${post.title}</span>
								<span class="text-xs text-slate-400 ml-2">${post.regDate}</span>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</section>

	</main>
	<script>
		// --- 1. 스터디 영역 화살표 스크롤 ---
		const container = document.getElementById('studyContainer');
		const btnPrev = document.getElementById('btnPrev');
		const btnNext = document.getElementById('btnNext');
		
		if (btnNext && container) {
			btnNext.addEventListener('click', () => {
				const scrollAmount = container.clientWidth / 4; 
				container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
			});
		}
		if (btnPrev && container) {
			btnPrev.addEventListener('click', () => {
				const scrollAmount = container.clientWidth / 4;
				container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
			});
		}

		// --- 2. 자동 광고 배너 캐러셀 (화살표 조작) ---
		let currentSlide = 1;
		const totalSlides = 3;
		let slideTimer;

		function goToSlide(slideNum) {
			const targetSlide = document.getElementById('slide' + slideNum);
			if (targetSlide) {
				targetSlide.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
			}
		}

		function nextAdSlide() {
			currentSlide = currentSlide >= totalSlides ? 1 : currentSlide + 1;
			goToSlide(currentSlide);
		}

		function prevAdSlide() {
			currentSlide = currentSlide <= 1 ? totalSlides : currentSlide - 1;
			goToSlide(currentSlide);
		}

		function startAdTimer() {
			slideTimer = setInterval(nextAdSlide, 3500); 
		}

		function resetAdTimer() {
			clearInterval(slideTimer);
			startAdTimer();
		}

		const btnAdNext = document.getElementById('btnAdNext');
		const btnAdPrev = document.getElementById('btnAdPrev');
		
		if (btnAdNext) {
			btnAdNext.addEventListener('click', () => {
				nextAdSlide();
				resetAdTimer(); 
			});
		}
		if (btnAdPrev) {
			btnAdPrev.addEventListener('click', () => {
				prevAdSlide();
				resetAdTimer(); 
			});
		}

		startAdTimer();
	</script>
</body>
</html>