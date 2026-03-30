<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 상세 정보 - deverytime 관리자</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<div class="flex max-w-6xl mx-auto w-full">
		<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>

		<main class="flex-1 p-8">
			<div class="flex justify-between items-center mb-6 border-b border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">회원 상세 정보</h1>
					<p class="text-slate-500">회원의 활동 내역을 확인하고 상태를 변경할 수 있습니다.</p>
				</div>
				<a href="/teamtwo/admin/member-list.do" class="btn btn-outline btn-sm">목록으로</a>
			</div>

			<div class="grid grid-cols-2 gap-6 mb-6">
				<div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100">
					<h3 class="font-bold text-lg mb-4 text-slate-700 border-b border-slate-200 pb-2">기본 정보</h3>
					<ul class="space-y-3">
						<li class="flex"><span class="w-24 text-slate-500">회원번호</span><span class="font-semibold">${dto.seq}</span></li>
						<li class="flex"><span class="w-24 text-slate-500">아이디</span><span class="font-semibold text-primary">${dto.id}</span></li>
						<li class="flex"><span class="w-24 text-slate-500">이름</span><span>${dto.name}</span></li>
						<li class="flex"><span class="w-24 text-slate-500">닉네임</span><span>${dto.nickname}</span></li>
						<li class="flex"><span class="w-24 text-slate-500">이메일</span><a href="mailto:${dto.email}" class="text-blue-500 hover:underline">${dto.email}</a></li>
						<li class="flex"><span class="w-24 text-slate-500">인증실패</span><span class="text-error font-bold">${dto.failCount}회</span></li>
					</ul>
				</div>

				<div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100">
					<h3 class="font-bold text-lg mb-4 text-slate-700 border-b border-slate-200 pb-2">활동 정보</h3>
					<ul class="space-y-3">
						<li class="flex"><span class="w-32 text-slate-500">작성 게시글 수</span><span class="font-bold">${dto.totalPosts}개</span></li>
						<li class="flex"><span class="w-32 text-slate-500">작성 댓글 수</span><span class="font-bold">${dto.totalComments}개</span></li>
						<li class="flex"><span class="w-32 text-slate-500">가입 스터디 수</span><span class="font-bold">${dto.totalStudies}개</span></li>
					</ul>
				</div>
			</div>

			<form action="/teamtwo/admin/member-view.do" method="POST" class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center justify-between">
				<input type="hidden" name="seq" value="${dto.seq}">
				<div class="flex items-center gap-4">
					<h3 class="font-bold text-lg text-slate-700">회원 상태 변경</h3>
					<div class="text-sm">
						현재 상태: 
						<c:choose>
							<c:when test="${dto.status == 0}"><span class="badge badge-success badge-sm badge-outline">정상</span></c:when>
							<c:when test="${dto.status == 1}"><span class="badge badge-warning badge-sm badge-outline">정지 (잠김)</span></c:when>
							<c:when test="${dto.status == 2}"><span class="badge badge-error badge-sm badge-outline">탈퇴</span></c:when>
						</c:choose>
					</div>
				</div>
				
				<div class="flex items-center gap-2">
					<span class="text-sm text-slate-500 whitespace-nowrap">상태 선택:</span>
					<select name="status" class="select select-bordered select-sm focus:select-primary">
						<option value="0" ${dto.status == 0 ? 'selected' : ''}>정상</option>
						<option value="1" ${dto.status == 1 ? 'selected' : ''}>정지</option>
						<option value="2" ${dto.status == 2 ? 'selected' : ''}>탈퇴</option>
					</select>
					<button type="submit" class="btn btn-primary btn-sm ml-2">저장</button>
				</div>
			</form>

		</main>
	</div>
</body>
</html>