<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="w-64 bg-white border-r border-slate-200 min-h-[calc(100vh-64px)] p-6 flex-shrink-0">
	<h2 class="text-lg font-bold text-slate-800 mb-6 px-2">관리자 메뉴</h2>
	<ul class="menu bg-base-100 w-full p-0 gap-2">
		<li><a href="/teamtwo/admin/member-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('member') ? 'active bg-blue-50 text-primary' : ''}">회원 관리</a></li>
		<li><a href="/teamtwo/admin/board-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('board') ? 'active bg-blue-50 text-primary' : ''}">게시글 관리</a></li>
		<li><a href="/teamtwo/admin/study-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('study') ? 'active bg-blue-50 text-primary' : ''}">스터디 관리</a></li>
		<li><a href="/teamtwo/admin/request-list.do" class="font-semibold text-slate-600 hover:text-primary ${uri.contains('request') ? 'active bg-blue-50 text-primary' : ''}">요청 관리</a></li>
	</ul>
</div>