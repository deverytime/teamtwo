<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 관리 - deverytime 관리자</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<div class="flex max-w-6xl mx-auto w-full">
		
		<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>

		<main class="flex-1 p-8">
			
			<div class="flex justify-between items-end mb-6 border-b border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">회원 관리</h1>
					<p class="text-slate-500">가입된 회원 목록을 조회하고 관리합니다.</p>
				</div>
				<div class="text-slate-500 font-semibold mb-1">
					총 검색 결과: <span class="text-primary">${totalCount}</span>명
				</div>
			</div>

			<div class="bg-white p-4 rounded-xl shadow-sm border border-slate-100 mb-6 flex justify-between items-center">
				<form action="/teamtwo/admin/member-list.do" method="GET" class="flex gap-2 w-full max-w-lg">
					<select name="type" class="select select-bordered focus:select-primary">
						<option value="all" ${type == 'all' ? 'selected' : ''}>전체</option>
						<option value="id" ${type == 'id' ? 'selected' : ''}>아이디</option>
						<option value="name" ${type == 'name' ? 'selected' : ''}>이름</option>
						<option value="nickname" ${type == 'nickname' ? 'selected' : ''}>닉네임</option>
					</select>
					<input type="text" name="word" value="${word}" placeholder="검색어를 입력하세요" class="input input-bordered flex-1 focus:input-primary" />
					<button type="submit" class="btn btn-primary">검색</button>
				</form>
				
				<c:if test="${not empty word}">
					<a href="/teamtwo/admin/member-list.do" class="btn btn-ghost btn-sm text-slate-500">검색 초기화</a>
				</c:if>
			</div>

			<div class="bg-white rounded-xl shadow-sm border border-slate-100 overflow-hidden">
				<table class="table w-full text-center">
					<thead class="bg-slate-50 text-slate-600 text-sm">
						<tr>
							<th>번호</th>
							<th>아이디</th>
							<th>이름</th>
							<th>닉네임</th>
							<th>가입일</th>
							<th>관리</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty list}">
								<tr>
									<td colspan="6" class="py-10 text-slate-400">검색된 회원이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto">
									<tr class="hover:bg-slate-50 transition-colors">
										<td>${dto.seq}</td>
										<td class="font-semibold text-slate-700">${dto.id}</td>
										<td>${dto.name}</td>
										<td>${dto.nickname}</td>
										<td class="text-slate-500 text-sm">${dto.regdate.substring(0, 10)}</td>
										
										<td>
											<a href="/teamtwo/admin/member-view.do?seq=${dto.seq}" class="btn btn-xs btn-outline">상세보기</a>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>

			<div class="mt-8 flex justify-center">
				${pagebar}
			</div>

		</main>
	</div>
</body>
</html>