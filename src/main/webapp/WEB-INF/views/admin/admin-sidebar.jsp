<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="w-64 min-h-[calc(100vh-64px)] p-6 flex-shrink-0 relative">
	
	<div id="floatingMenu" class="bg-slate-100 rounded-2xl p-4 shadow-sm transition-transform duration-500 ease-out will-change-transform">
		<ul class="menu w-full p-0 gap-2">
			<li><a href="/teamtwo/admin/member-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('member') ? 'active bg-white shadow-sm text-primary' : ''}">회원 관리</a></li>
			
			<li><a href="/teamtwo/admin/board-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('admin/board') ? 'active bg-white shadow-sm text-primary' : ''}">게시글 관리</a></li>
			
			<li><a href="/teamtwo/admin/study-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('study') ? 'active bg-white shadow-sm text-primary' : ''}">스터디 관리</a></li>
			<li><a href="/teamtwo/board/request/list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('request') ? 'active bg-white shadow-sm text-primary' : ''}">요청 관리</a></li>
		</ul>
	</div>

</div>

<script>
	window.addEventListener('scroll', function() {
		const menu = document.getElementById('floatingMenu');
		if(menu) {
			// 스크롤이 상단 헤더(64px)를 넘어갈 때부터 계산 시작 (0 이하로는 안 내려가게 방어)
			const moveY = Math.max(0, window.scrollY - 64);
			
			// Y축으로 이동시키면, 태그에 적어둔 duration-500(0.5초) 동안 부드럽게 쫓아옴
			menu.style.transform = 'translateY(' + moveY + 'px)';
		}
	});
</script>