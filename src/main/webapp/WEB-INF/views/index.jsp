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
	
	<main class="page-wrap pb-20">
		
		<section class="mb-14 mt-4">
			<div class="relative group w-full">
				<button id="btnAdPrev" class="absolute left-2 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white/80 shadow-md z-10 hidden group-hover:flex hover:bg-white border-none">❮</button>

				<div class="carousel w-full h-20 rounded-xl shadow-sm overflow-hidden" id="adCarousel">
					<a href="https://www.coupang.com" target="_blank" id="slide1" class="carousel-item relative w-full bg-indigo-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">💻</span>
							<div>
								<h3 class="text-lg font-bold text-indigo-900 leading-tight">웹 개발 부트캠프 5기 모집 중!</h3>
								<p class="text-sm text-indigo-700 font-medium mt-0.5">HTML, CSS, JS부터 백엔드까지 한 번에 마스터하세요.</p>
							</div>
						</div>
					</a>
					<a href="https://www.coupang.com" target="_blank" id="slide2" class="carousel-item relative w-full bg-emerald-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">💺</span>
							<div>
								<h3 class="text-lg font-bold text-emerald-900 leading-tight">프리미엄 사무용 의자 기획전</h3>
								<p class="text-sm text-emerald-700 font-medium mt-0.5">장시간 코딩에도 허리가 편안한 의자, 지금 바로 확인하세요.</p>
							</div>
						</div>
					</a>
					<a href="https://www.coupang.com" target="_blank" id="slide3" class="carousel-item relative w-full bg-rose-50 flex items-center justify-between px-8 transition-all duration-500 hover:opacity-90 cursor-pointer">
						<div class="flex items-center gap-4">
							<span class="text-3xl">🖱️</span>
							<div>
								<h3 class="text-lg font-bold text-rose-900 leading-tight">최고의 그립감, 게이밍 마우스 특가</h3>
								<p class="text-sm text-rose-700 font-medium mt-0.5">대칭형 무선 마우스로 당신의 에임과 작업 속도를 올려보세요.</p>
							</div>
						</div>
					</a>
				</div>

				<button id="btnAdNext" class="absolute right-2 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white/80 shadow-md z-10 hidden group-hover:flex hover:bg-white border-none">❯</button>
			</div>
		</section>

		<section class="mb-12">
			<div class="flex justify-between items-end mb-4">
				<h2 class="text-xl font-bold">📚 신규 모집 중인 스터디</h2>
				<a href="/teamtwo/study/study-list.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
			</div>
			
			<div class="relative group">
				<button id="btnPrev" class="absolute -left-4 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❮</button>
				
				<div id="studyContainer" class="flex gap-4 overflow-x-auto scroll-smooth scrollbar-hide py-2 px-1 snap-x snap-mandatory">
					<c:forEach items="${studyList}" var="study">
						<div class="content-card card-pad min-w-[280px] max-w-[280px] min-h-[160px] snap-start hover:-translate-y-1 transition-transform cursor-pointer shadow-sm border border-slate-100 bg-white rounded-xl flex flex-col" onclick="location.href='/teamtwo/study/study-view.do?seq=${study.seq}'">
							<div class="flex justify-between items-start mb-2">
								<span class="chip chip-blue">모집중</span>
								<span class="text-xs text-slate-500 font-semibold">최대 ${study.capacity}명</span>
							</div>
							<h3 class="text-lg font-bold truncate mt-1">${study.name}</h3>
							<p class="text-sm text-slate-500 mt-2 line-clamp-2 flex-1">${study.description}</p>
						</div>
					</c:forEach>
				</div>
				
				<button id="btnNext" class="absolute -right-4 top-1/2 -translate-y-1/2 btn btn-circle btn-sm bg-white shadow-md z-10 hidden group-hover:flex hover:bg-slate-100 border-slate-200">❯</button>
			</div>
		</section>

		<section class="grid grid-cols-1 lg:grid-cols-2 gap-8 mt-8">
			
			<div class="content-card card-pad bg-white shadow-sm border border-slate-100 rounded-xl min-h-[250px]">
				<div class="flex justify-between items-end mb-4 border-b border-slate-100 pb-3">
					<h2 class="text-lg font-bold flex items-center gap-2">🔥 실시간 인기글</h2>
					<a href="/teamtwo/board/trendingboard/List.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-50">
					<c:forEach items="${trendingList}" var="post">
						<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors px-2 rounded-lg" onclick="location.href='/teamtwo/board/view.do?seq=${post.seq}'">
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
					<a href="/teamtwo/board/freeboard/List.do" class="text-sm text-brand-600 hover:underline">더보기 &gt;</a>
				</div>
				<ul class="divide-y divide-slate-50">
					<c:forEach items="${freeboardList}" var="post">
						<li class="py-3 hover:bg-slate-50 cursor-pointer transition-colors px-2 rounded-lg" onclick="location.href='/teamtwo/board/view.do?seq=${post.seq}'">
							<div class="flex justify-between items-center">
								<span class="text-sm truncate flex-1 font-medium text-slate-700">${post.title}</span>
								<span class="text-xs text-slate-400 ml-2">${post.regDate.substring(0, 10)}</span>
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
		const scrollAmount = 300; 

		if (btnNext && container) {
			btnNext.addEventListener('click', () => {
				container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
			});
		}
		if (btnPrev && container) {
			btnPrev.addEventListener('click', () => {
				container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
			});
		}

		// --- 2. 자동 광고 배너 캐러셀 (화살표 조작 기능 추가) ---
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
			slideTimer = setInterval(nextAdSlide, 3500); // 3.5초 자동 스크롤
		}

		function resetAdTimer() {
			clearInterval(slideTimer);
			startAdTimer();
		}

		// 광고 화살표 이벤트 설정
		const btnAdNext = document.getElementById('btnAdNext');
		const btnAdPrev = document.getElementById('btnAdPrev');
		
		if (btnAdNext) {
			btnAdNext.addEventListener('click', () => {
				nextAdSlide();
				resetAdTimer(); // 사용자가 누르면 타이머 리셋
			});
		}
		if (btnAdPrev) {
			btnAdPrev.addEventListener('click', () => {
				prevAdSlide();
				resetAdTimer(); // 사용자가 누르면 타이머 리셋
			});
		}

		// 시작
		startAdTimer();
	</script>

	</main>

	<script>
		// --- 1. 스터디 영역 화살표 스크롤 ---
		const container = document.getElementById('studyContainer');
		const btnPrev = document.getElementById('btnPrev');
		const btnNext = document.getElementById('btnNext');
		const scrollAmount = 300; 

		if (btnNext && container) {
			btnNext.addEventListener('click', () => {
				container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
			});
		}

		if (btnPrev && container) {
			btnPrev.addEventListener('click', () => {
				container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
			});
		}

		const style = document.createElement('style');
		style.innerHTML = '#studyContainer::-webkit-scrollbar { display: none; } #studyContainer { -ms-overflow-style: none; scrollbar-width: none; }';
		document.head.appendChild(style);

		// --- 2. 자동 광고 배너 캐러셀 ---
		let currentSlide = 1;
		const totalSlides = 3;

		function updateIndicator() {
			for(let i=1; i<=totalSlides; i++) {
				const dot = document.getElementById('dot' + i);
				if (dot) {
					if(i === currentSlide) {
						dot.classList.remove('bg-slate-300');
						dot.classList.add('bg-primary');
					} else {
						dot.classList.remove('bg-primary');
						dot.classList.add('bg-slate-300');
					}
				}
			}
		}

		setInterval(() => {
			currentSlide = currentSlide >= totalSlides ? 1 : currentSlide + 1;
			const targetSlide = document.getElementById('slide' + currentSlide);
			
			if (targetSlide) {
				targetSlide.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
			}
			updateIndicator();
		}, 3500);

		updateIndicator();
	</script>
	</main>

	<script>
		// --- 1. 스터디 영역 화살표 스크롤 ---
		const container = document.getElementById('studyContainer');
		const btnPrev = document.getElementById('btnPrev');
		const btnNext = document.getElementById('btnNext');
		const scrollAmount = 300; // 한 번에 이동할 픽셀량

		if (btnNext && container) {
			btnNext.addEventListener('click', () => {
				container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
			});
		}

		if (btnPrev && container) {
			btnPrev.addEventListener('click', () => {
				container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
			});
		}

		// 스크롤바 숨기기 (Tailwind에 없는 경우를 위한 안전장치 CSS)
		const style = document.createElement('style');
		style.innerHTML = '#studyContainer::-webkit-scrollbar { display: none; } #studyContainer { -ms-overflow-style: none; scrollbar-width: none; }';
		document.head.appendChild(style);

		// --- 2. 자동 광고 배너 캐러셀 ---
		let currentSlide = 1;
		const totalSlides = 3;

		function updateIndicator() {
			for(let i=1; i<=totalSlides; i++) {
				const dot = document.getElementById('dot' + i);
				if (dot) {
					if(i === currentSlide) {
						dot.classList.remove('bg-slate-300');
						dot.classList.add('bg-primary');
					} else {
						dot.classList.remove('bg-primary');
						dot.classList.add('bg-slate-300');
					}
				}
			}
		}

		// 3.5초마다 다음 슬라이드로 넘어감
		setInterval(() => {
			currentSlide = currentSlide >= totalSlides ? 1 : currentSlide + 1;
			const targetSlide = document.getElementById('slide' + currentSlide);
			
			// 해당 슬라이드로 부드럽게 이동
			if (targetSlide) {
				targetSlide.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'start' });
			}
			updateIndicator();
		}, 3500);

		// 초기 인디케이터 세팅
		updateIndicator();
	</script>
</body>
</html>