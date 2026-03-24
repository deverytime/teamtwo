<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="bg-white border-b border-slate-200">
	
	<div class="navbar max-w-6xl w-full mx-auto px-4">
		
		<div class="flex-1 items-center">
			<a href="/teamtwo/Index.do" class="text-2xl font-bold text-brand-600">deverytime</a>
			<c:if test="${not empty auth}">
				<span class="ml-3 chip ${authDto.lv == '2' ? 'chip-amber' : 'chip-blue'}">
					${authDto.lv == '2' ? '관리자' : '일반'} | ${auth}
				</span>
			</c:if>
		</div>
		
		<div class="flex-none">
			<ul class="menu menu-horizontal px-1 text-slate-700 font-semibold items-center z-[50]">
				<li><a href="/teamtwo/plan/List.do">학습계획</a></li>
				<li><a href="/teamtwo/study/ListStudy.do">스터디</a></li>
				
				<li class="dropdown dropdown-hover dropdown-end">
					<div tabindex="0" role="button" class="m-1">게시판</div>
					<ul tabindex="0" class="dropdown-content menu bg-white rounded-xl w-48 p-2 shadow-sm border border-slate-200">
						<li><a href="/teamtwo/board/freeboard/List.do">자유 게시판</a></li>
						<li><a href="/teamtwo/board/questionboard/List.do">질문 게시판</a></li>
						<li><a href="/teamtwo/board/experiencesharingboard/List.do">학습 공유 게시판</a></li>
						<li><a href="/teamtwo/board/datasharingboard/List.do">자료 공유 게시판</a></li>
						<li><a href="/teamtwo/board/inquiryboard/List.do">문의 게시판</a></li>
						<li><a href="/teamtwo/board/trendingboard/List.do">인기글 게시판</a></li>
					</ul>
				</li>
				
				<c:choose>
					<c:when test="${empty auth}">
						<li><a href="/teamtwo/user/Login.do">로그인/회원가입</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/teamtwo/user/Mypage.do">마이페이지</a></li>
						<li><a href="/teamtwo/user/Logout.do">로그아웃</a></li>
						<c:if test="${authDto.lv == '2'}">
							<li><a href="/teamtwo/admin/Admin.do" class="text-point-500">관리자 메뉴</a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		
	</div>
</header>