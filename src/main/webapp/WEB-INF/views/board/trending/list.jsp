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

		<h1 class="section-title">인기 게시판</h1>
		<p class="section-desc">추천을 받고 인기 게시글을 노려보세요!</p>

		<div class="content-card card-pad">
			<section class="demo-block">


				<div class="content-card overflow-hidden">
					<div
						class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
						<div>
							<h3 class="text-xl font-bold"></h3>
							<p class="text-sm text-slate-500 mt-1"></p>
						</div>

					</div>


					<br>
					
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
										onclick="location.href='/teamtwo/board/view.do?seq=${dto.seq}&page=${param.page }'">${dto.title}</td>
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
					<div>${pagebar}</div>

					<!-- 오른쪽 빈 공간 -->
					<div class="w-24"></div>
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