<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>요청 상세 정보 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>
	
	<div class="<c:choose><c:when test='${authDto.type == 1}'>flex max-w-6xl</c:when><c:otherwise>max-w-4xl</c:otherwise></c:choose> mx-auto w-full py-8">
		
		<c:if test="${authDto.type == 1}">
			<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>
		</c:if>

		<main class="<c:if test='${authDto.type == 1}'>flex-1 ml-8</c:if> w-full">
			<div class="flex justify-between items-center mb-6 border-b !border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">요청 상세 정보</h1>
				</div>
				<a href="/teamtwo/board/request/list.do" class="btn btn-outline btn-sm">목록으로</a>
			</div>

			<div class="bg-white p-8 rounded-xl shadow-sm border border-slate-100 mb-6">
				<div class="flex items-center gap-3 mb-4">
					<c:choose>
						<c:when test="${dto.subject == 0}"><span class="badge badge-ghost">일반 문의</span></c:when>
						<c:when test="${dto.subject == 1}"><span class="badge badge-primary badge-outline">게시판 요청</span></c:when>
					</c:choose>
					<h2 class="text-2xl font-bold">${dto.title}</h2>
				</div>
				
				<div class="flex gap-4 text-sm text-slate-500 mb-8 pb-4 border-b border-slate-100">
					<div>작성자: <span class="font-semibold text-slate-700">${dto.nickname}</span></div>
					<div>|</div>
					<div>작성일: ${dto.regDate}</div>
					<div>|</div>
					<div>조회수: ${dto.readCount}</div>
				</div>

				<div class="min-h-[150px] text-lg text-slate-700 whitespace-pre-wrap">${dto.content}</div>
			</div>

			<c:if test="${authDto.type == 1}">
				<form action="/teamtwo/board/request/view.do" method="POST" class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 mb-6 flex items-center justify-between">
					<input type="hidden" name="mode" value="status">
					<input type="hidden" name="seq" value="${dto.seq}">
					
					<div class="flex items-center gap-4">
						<h3 class="font-bold text-lg text-slate-700">처리 상태 변경</h3>
						<div class="text-sm">
							현재 상태: 
							<c:choose>
								<c:when test="${dto.status == 0}"><span class="badge badge-error badge-sm badge-outline">처리 대기</span></c:when>
								<c:when test="${dto.status == 1}"><span class="badge badge-success badge-sm badge-outline">처리 완료</span></c:when>
							</c:choose>
						</div>
					</div>
					
					<div class="flex items-center gap-2">
						<select name="status" class="select select-bordered select-sm focus:select-primary">
							<option value="0" ${dto.status == 0 ? 'selected' : ''}>처리 대기 (미완료)</option>
							<option value="1" ${dto.status == 1 ? 'selected' : ''}>처리 완료</option>
						</select>
						<button type="submit" class="btn btn-primary btn-sm ml-2">상태 저장</button>
					</div>
				</form>
			</c:if>

			<div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100">
				<h3 class="font-bold text-lg text-slate-700 mb-4 border-b !border-slate-200 pb-2">관리자 답변</h3>
				
				<div class="mb-6 space-y-4">
					<c:choose>
						<c:when test="${empty dto.comments}">
							<div class="text-slate-400 text-sm py-2">아직 등록된 관리자 답변이 없습니다. 조금만 기다려주세요!</div>
						</c:when>
						<c:otherwise>
							<c:forEach items="${dto.comments}" var="comment">
								<div class="bg-blue-50 p-5 rounded-lg border border-blue-100 relative">
									<div class="font-bold text-primary mb-2 flex items-center gap-2">
										<span>🛡️ 관리자</span>
									</div>
									<div class="text-slate-700 whitespace-pre-wrap leading-relaxed">${comment.content}</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>

				<c:if test="${authDto.type == 1}">
					<form action="/teamtwo/board/request/view.do" method="POST" class="flex gap-2">
						<input type="hidden" name="mode" value="comment">
						<input type="hidden" name="seq" value="${dto.seq}">
						<textarea name="content" class="textarea textarea-bordered flex-1 focus:textarea-primary" placeholder="사용자에게 남길 답변을 입력하세요." required></textarea>
						<button type="submit" class="btn btn-primary h-auto">답변 등록</button>
					</form>
				</c:if>
			</div>

		</main>
	</div>
</body>
</html>