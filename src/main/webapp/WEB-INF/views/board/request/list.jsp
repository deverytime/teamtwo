<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 요청 및 문의 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>
	
	<div class="<c:choose><c:when test='${authDto.type == 1}'>flex max-w-6xl</c:when><c:otherwise>max-w-5xl</c:otherwise></c:choose> mx-auto w-full py-8">
		
		<c:if test="${authDto.type == 1}">
			<%@ include file="/WEB-INF/views/admin/admin-sidebar.jsp"%>
		</c:if>

		<main class="<c:if test='${authDto.type == 1}'>flex-1 ml-8</c:if> w-full">
			<div class="flex justify-between items-end mb-6 border-b !border-slate-200 pb-4">
				<div>
					<h1 class="text-3xl font-bold mb-2">요청 및 문의</h1>
					<p class="text-slate-500">사이트 이용 관련 문의나 게시판 추가를 요청해 주세요.</p>
				</div>
				<div class="flex items-center gap-4">
					<div class="text-slate-500 font-semibold mb-1">총 <span class="text-primary">${totalCount}</span>건</div>
					<c:if test="${not empty authDto}">
						<a href="/teamtwo/board/request/add.do" class="btn btn-primary btn-sm">문의 작성</a>
					</c:if>
				</div>
			</div>

<div class="bg-white p-4 rounded-xl shadow-sm border border-slate-100 mb-6">

  <form action="/teamtwo/board/request/list.do" method="GET"
      class="flex items-center justify-between gap-3">

    <!-- 왼쪽: 필터 -->
    <div class="flex items-center gap-2">
      <select name="subject" class="select select-bordered focus:select-primary">
        <option value="">구분 전체</option>
        <option value="0" ${subject == '0' ? 'selected' : ''}>일반 문의</option>
        <option value="1" ${subject == '1' ? 'selected' : ''}>게시판 요청</option>
      </select>

      <select name="status" class="select select-bordered focus:select-primary">
        <option value="">상태 전체</option>
        <option value="0" ${status == '0' ? 'selected' : ''}>처리 대기</option>
        <option value="1" ${status == '1' ? 'selected' : ''}>처리 완료</option>
      </select>

      <select name="type" class="select select-bordered focus:select-primary">
        <option value="all" ${type == 'all' ? 'selected' : ''}>전체 검색</option>
        <option value="title" ${type == 'title' ? 'selected' : ''}>제목</option>
        <option value="content" ${type == 'content' ? 'selected' : ''}>내용</option>
      </select>
    </div>

    <!-- 오른쪽: 검색 -->
    <div class="flex items-center gap-2">
      <input type="text"
           name="word"
           value="${word}"
           placeholder="검색어 입력"
           class="input input-bordered w-72 focus:input-primary" />

      <button type="submit" class="btn btn-primary">검색</button>

      <c:if test="${not empty word or not empty subject or not empty status}">
        <a href="/teamtwo/board/request/list.do" class="btn btn-ghost btn-sm">초기화</a>
      </c:if>
    </div>

  </form>
</div>

			<div class="bg-white rounded-xl shadow-sm border border-slate-100 overflow-hidden">
				<table class="table w-full text-center hover">
					<thead class="bg-slate-50 text-slate-600">
						<tr><th>번호</th><th>구분</th><th>제목</th><th>작성자</th><th>작성일</th><th>상태</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty list}"><tr><td colspan="6" class="py-10 text-slate-400">등록된 요청이 없습니다.</td></tr></c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto">
									<tr class="hover:bg-slate-50 transition-colors cursor-pointer" onclick="location.href='/teamtwo/board/request/view.do?seq=${dto.seq}';">
										<td>${dto.seq}</td>
										<td>
											<c:choose>
												<c:when test="${dto.subject == 0}"><span class="badge badge-ghost badge-sm">문의</span></c:when>
												<c:when test="${dto.subject == 1}"><span class="badge badge-primary badge-sm badge-outline">게시판 요청</span></c:when>
											</c:choose>
										</td>
										<td class="text-left font-semibold truncate max-w-[250px]">${dto.title}</td>
										<td>${dto.nickname}</td>
										<td class="text-sm text-slate-500">${dto.regDate}</td>
										<td>
											<c:choose>
												<c:when test="${dto.status == 0}"><span class="badge badge-error badge-sm badge-outline">처리 대기</span></c:when>
												<c:when test="${dto.status == 1}"><span class="badge badge-success badge-sm badge-outline">처리 완료</span></c:when>
											</c:choose>
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
</body>
</html>