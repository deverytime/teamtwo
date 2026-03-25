<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>deverytime</title>
	<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
	<style>
		
		#scheduleDiv {
			margin: 50px 120px;
		}
		
	</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap">
		
		<div class="mb-8">
			<div>
				<h1 class="section-title">스터디 상세</h1>
				<!-- 참가 여부 및 스터디장 여부 -->
				<%-- <c:if test="${}">
                	<span class="badge badge-success badge-outline">참가중</span>
                </c:if>
                <c:if test="${}">
                	<span class="badge badge-success badge-outline">스터디장</span>
                </c:if> --%>
			</div>
			<p class="section-desc">스터디 상세화면 입니다.</p>
		</div>

		<div class="content-card card-pad">
		
		<!-- 스터디 상세 대시보드 -->	
		<div id="main-layout">
		
		  <div id="main">
	        <div class="content-card card-pad">
	          <div class="flex items-start justify-between gap-4 mb-4">
	            <div>
	              <p class="text-sm text-slate-500"></p>
	              <h3 class="text-xl font-bold mt-1">${dto.name}</h3>
	            </div>
	            <span class="badge badge-outline badge-primary">${dto.status == '0' ? '모집중' : '모집완료'}</span>
	          </div>
	
	          <p class="text-slate-600 leading-7 mb-6">
	            ${dto.description}
	          </p>
	
	          <div class="grid sm:grid-cols-3 gap-4 mb-6">
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">인원수</p>
	              <p class="mt-2">${dto.headCount} / ${dto.capacity}</p>
	            </div>
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">일정</p>
	              <p class="mt-2 text-2xl font-bold">${dto.scheduleCount}개</p>
	            </div>
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">생성일</p>
	              <p class="mt-2 text-2xl font-bold">${dto.createDate}</p>
	            </div>
	          </div>
	
	          <div class="flex flex-wrap justify-between gap-2">
	            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/studyschedule-list.do?seq=${seq}';">일정 보기</button>
	            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-member.do?seq=${seq}';">참여 하기</button>
	          </div>
	        </div>
	      </div>
			
		</div>

	</main>
	
    <!-- 스터디 참가중인 멤버 목록 -->
    <div id="scheduleDiv">
    <section class="demo-block">
      <h2 class="section-title">스터디 멤버 목록</h2>
      <p class="section-desc">스터디에 참가중인 멤버 목록입니다.</p>

      <div class="content-card overflow-hidden">
        <div class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
          <div>
            <h3 class="text-xl font-bold">멤버 목록</h3>
            <p class="text-sm text-slate-500 mt-1"></p>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>이름</th>
                <th>이메일</th>
                <th></th>
                <th></th>
                <th>참여일</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${mlist}" var="memberDto">
              <tr>
                <td>${memberDto.id}</td>
                <td>${memberDto.name}</td>
                <td>${memberDto.email}</td>
                <td>
                	<%-- <c:if test="${}">
                		<button type="button" class="badge badge-warning badge-outline">권한위임</button>
                	</c:if> --%>
                </td>
                <td>
                	<button type="button" class="badge badge-error badge-outline">추방하기</button>
                </td>
                <td></td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>

        <div class="card-pad border-t border-slate-200 flex justify-between items-center">
          <p class="text-sm text-slate-500"></p>
          <div class="join">
            <button class="join-item btn btn-sm">1</button>
            <button class="join-item btn btn-sm btn-active">2</button>
            <button class="join-item btn btn-sm">3</button>
          </div>
        </div>
      </div>
    </section>
    </div>
	
	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
	</script>
</body>
</html>