<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
/* 현재 페이지 전용 CSS가 필요하면 여기에 작성 */
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap">

		<div class="mb-8">
			<c:if test="${board==1}">
				<h1 class="section-title">자유게시판</h1>
				<p class="section-desc">자유롭게 소통하는 공간입니다.</p>
			</c:if>
		</div>

		<div class="content-card card-pad">
			<section class="demo-block">


				<div class="content-card overflow-hidden">
					<div
						class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
						<div>
							<h3 class="text-xl font-bold"></h3>
							<p class="text-sm text-slate-500 mt-1"></p>
						</div>

						<div class="flex flex-col sm:flex-row gap-2">
							<select class="select select-bordered bg-white">
								<option>제목</option>
								<option>내용</option>
								<option>작성자</option>
								<option>제목&내용</option>
							</select> <input type="text" placeholder="검색어 입력"
								class="input input-bordered bg-white" />
							<button class="btn btn-brand">검색</button>
						</div>
					</div>

					<div class="flex flex-wrap gap-2 mb-6 p-2 bg-slate-50 rounded-xl">
						<a href="?board=${board}"
							class="px-4 py-2 rounded-full text-sm font-medium 
            ${empty param.category ? 'bg-blue-500 text-white shadow-lg' : 'bg-white text-slate-700 hover:bg-slate-100 border'}">
							전체 </a>
						<c:forEach var="entry" items="${categoryMap}">
							<a href="?board=${board}&category=${entry.key}"
								class="px-4 py-2 rounded-full text-sm font-medium text-center transition-all
                  ${param.category == entry.key ? 'bg-blue-500 text-white shadow-lg' : 'bg-white text-slate-700 hover:bg-slate-100 border'}">
								${entry.value} </a>
						</c:forEach>
					</div>
					<br>
					<hr>
					<br>
					<table class="table">
						<thead>
							<tr>
								<th>글번호</th>
								<th>주제</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>날짜</th>
								<th>조회수</th>
								<th>추천수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dto" items="${list}">
								<tr>
									<td>${dto.seq}</td>
									<td>${dto.category}</td>
									<td class="cursor-pointer hover:underline"
										onclick="location.href='view.do?seq=${dto.seq}&board=${board}'">${dto.title}</td>
									<td>${dto.nickname}</td>
									<td>${dto.regDate}</td>
									<td>${dto.readCount}</td>
									<td>${dto.recommend}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div
					class="card-pad border-t border-slate-200 flex justify-between items-center">
					<!-- 왼쪽 빈 공간 -->
					<div class="w-24"></div>

					<!-- 가운데 페이지바 -->
					<div class="flex gap-1">
						<button class="btn">1</button>
						<button class="btn">2</button>
						<button class="btn">3</button>
					</div>

					<!-- 오른쪽 글쓰기 버튼 -->
					<div class="w-24 flex justify-end">
						<button class="btn btn-brand"
							onclick="location.href='add.do?board=${board}'">글쓰기</button>
					</div>
				</div>
		</div>
		</section>
		</div>

	</main>

	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
	</script>
</body>
</html>