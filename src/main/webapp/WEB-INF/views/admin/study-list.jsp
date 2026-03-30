<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>스터디 관리 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>
	<div class="flex max-w-6xl mx-auto w-full">
		<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>

		<main class="flex-1 p-8">
			<div class="flex justify-between items-end mb-6 border-b border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">스터디 관리</h1>
					<p class="text-slate-500">개설된 스터디를 조회하고 관리합니다.</p>
				</div>
				<div class="text-slate-500 font-semibold mb-1">총 <span class="text-primary">${totalCount}</span>개</div>
			</div>

			<div class="bg-white p-4 rounded-xl shadow-sm border border-slate-100 mb-6 flex justify-between items-center">
				<form action="/teamtwo/admin/study-list.do" method="GET" class="flex gap-2 w-full max-w-lg">
					<select name="type" class="select select-bordered focus:select-primary">
						<option value="all" ${type == 'all' ? 'selected' : ''}>전체 검색</option>
						<option value="name" ${type == 'name' ? 'selected' : ''}>스터디명</option>
						<option value="description" ${type == 'description' ? 'selected' : ''}>설명</option>
					</select>
					<input type="text" name="word" value="${word}" placeholder="검색어 입력" class="input input-bordered flex-1 focus:input-primary" />
					<button type="submit" class="btn btn-primary">검색</button>
				</form>
				<c:if test="${not empty word}"><a href="/teamtwo/admin/study-list.do" class="btn btn-ghost btn-sm">초기화</a></c:if>
			</div>

			<div class="bg-white rounded-xl shadow-sm border border-slate-100 overflow-hidden">
				<table class="table w-full text-center">
					<thead class="bg-slate-50 text-slate-600">
						<tr><th>번호</th><th>스터디명</th><th>설명</th><th>정원</th><th>상태</th><th>개설일</th><th>관리</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty list}"><tr><td colspan="7" class="py-10 text-slate-400">스터디가 없습니다.</td></tr></c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto">
									<tr class="hover:bg-slate-50 transition-colors">
										<td>${dto.seq}</td>
										<td class="text-left font-semibold truncate max-w-[150px]">${dto.name}</td>
										<td class="text-left text-sm text-slate-500 truncate max-w-[200px]">${dto.description}</td>
										<td class="text-sm text-slate-600 font-semibold">${dto.capacity}명</td>
										<td>
											<c:choose>
												<c:when test="${dto.status == 0}"><span class="badge badge-info badge-sm">모집중</span></c:when>
												<c:when test="${dto.status == 1}"><span class="badge badge-neutral badge-sm">모집완료</span></c:when>
												<c:otherwise><span class="badge badge-ghost badge-sm">알수없음</span></c:otherwise>
											</c:choose>
										</td>
										<td class="text-sm text-slate-500">${dto.createDate.substring(0,10)}</td>
										<td>
										<button type="button" class="btn btn-error btn-xs btn-outline" onclick="deleteStudy(${dto.seq})">삭제</button>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="mt-8 flex justify-center">${pagebar}</div>
		</main>
	</div>
	<form id="deleteStudyForm" action="/teamtwo/admin/study-list.do" method="POST">
		<input type="hidden" name="seq" id="deleteStudySeq">
	</form>

	<script>
		function deleteStudy(seq) {
			if(confirm('해당 스터디를 삭제하시겠습니까?')) {
				document.getElementById('deleteStudySeq').value = seq;
				document.getElementById('deleteStudyForm').submit();
			}
		}
	</script>
</body>
</html>