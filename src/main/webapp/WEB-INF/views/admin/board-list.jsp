<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 관리 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>
	<div class="flex max-w-6xl mx-auto w-full">
		<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>

		<main class="flex-1 p-8">
			<div class="flex justify-between items-end mb-6 border-b border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">게시글 관리</h1>
					<p class="text-slate-500">게시글을 조회하고 삭제합니다.</p>
				</div>
				<div class="text-slate-500 font-semibold mb-1">총 <span class="text-primary">${totalCount}</span>개</div>
			</div>

			<div class="bg-white p-4 rounded-xl shadow-sm border border-slate-100 mb-6 flex justify-between items-center">
				<form action="/teamtwo/admin/board-list.do" method="GET" class="flex gap-2 w-full max-w-lg">
					<select name="type" class="select select-bordered focus:select-primary">
						<option value="" ${type == '' ? 'selected' : ''}>전체</option>
						<option value="1" ${type == '1' ? 'selected' : ''}>자유</option>
						<option value="2" ${type == '2' ? 'selected' : ''}>질문</option>
						<option value="3" ${type == '3' ? 'selected' : ''}>자료 공유</option>
						<option value="4" ${type == '3' ? 'selected' : ''}>학습 공유</option>
					</select>
					<input type="text" name="word" value="${word}" placeholder="제목 검색" class="input input-bordered flex-1 focus:input-primary" />
					<button type="submit" class="btn btn-primary">검색</button>
				</form>
				<c:if test="${not empty word}"><a href="/teamtwo/admin/board-list.do" class="btn btn-ghost btn-sm">초기화</a></c:if>
			</div>

			<div class="bg-white rounded-xl shadow-sm border border-slate-100 overflow-hidden">
				<table class="table w-full text-center">
					<thead class="bg-slate-50 text-slate-600">
						<tr><th>번호</th><th>게시판</th><th>제목</th><th>작성자</th><th>작성일</th><th>관리</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty list}"><tr><td colspan="6" class="py-10 text-slate-400">게시글이 없습니다.</td></tr></c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto">
									<tr class="hover:bg-slate-50 transition-colors">
										<td>${dto.seq}</td>
										<td><span class="badge badge-ghost badge-sm">자유</span></td>
										<td class="text-left font-semibold truncate max-w-[200px]">${dto.title}</td>
										<td>${dto.nickname}</td>
										<td class="text-sm text-slate-500">${dto.regDate.substring(0,10)}</td>
										<td>
										<button type="button" class="btn btn-error btn-xs btn-outline" onclick="deleteBoard(${dto.seq})">삭제</button>
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
	<form id="deleteForm" action="/teamtwo/admin/board-list.do" method="POST">
		<input type="hidden" name="seq" id="deleteSeq">
	</form>

	<script>
		function deleteBoard(seq) {
			if(confirm('선택한 게시글을 삭제하시겠습니까?')) {
				// 숨겨둔 폼의 input에 글 번호를 넣고
				document.getElementById('deleteSeq').value = seq;
				// 폼을 POST 방식으로 제출 (ListBoard.java의 doPost가 받음)
				document.getElementById('deleteForm').submit();
			}
		}
	</script>
</body>
</html>