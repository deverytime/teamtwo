<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="w-64 min-h-[calc(100vh-64px)] p-6 flex-shrink-0">
	
	<div class="sticky top-24 bg-slate-100 rounded-2xl p-4 shadow-sm">
		<ul class="menu w-full p-0 gap-2">
			<li><a href="/teamtwo/admin/member-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('member') ? 'active bg-white shadow-sm text-primary' : ''}">회원 관리</a></li>
			<li><a href="/teamtwo/admin/board-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('board') ? 'active bg-white shadow-sm text-primary' : ''}">게시글 관리</a></li>
			<li><a href="/teamtwo/admin/study-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('study') ? 'active bg-white shadow-sm text-primary' : ''}">스터디 관리</a></li>
			<li><a href="/teamtwo/admin/request-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('request') ? 'active bg-white shadow-sm text-primary' : ''}">요청 관리</a></li>
		</ul>
	</div>

</div>