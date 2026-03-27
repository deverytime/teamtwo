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
		
		#pagebar {
			display: flex;
			justify-content: center;
		}
		
	</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap">
		
		<div class="mb-8">
			<div>
				<h1 class="section-title">스터디 일정 상세</h1>
			</div>
			<p class="section-desc">스터디 일정 상세화면입니다.</p>
		</div>

		<div class="content-card card-pad">
		
		<!-- 스터디 일정 상세 대시보드 -->	
		<div id="main-layout">
		
		  <div id="main">
	        <div class="content-card card-pad">
	          <div class="flex items-start justify-between gap-4 mb-4">
	            <div>
	              <p class="text-sm text-slate-500"></p>
	              <h3 class="text-xl font-bold mt-1">${dto.title}</h3>
	            </div>
	            <span class="badge badge-outline badge-primary"></span>
	          </div>
	
	          <p class="text-slate-600 leading-7 mb-6">
	            ${dto.content}
	          </p>
	
	          <div class="grid sm:grid-cols-3 gap-4 mb-6">
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">인원수</p>
	              <p class="mt-2"></p>
	            </div>
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">일정</p>
	              <p class="mt-2 text-2xl font-bold"></p>
	            </div>
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">생성일</p>
	              <p class="mt-2 text-2xl font-bold"></p>
	            </div>
	          </div>
						
	          <div class="flex flex-wrap justify-between gap-2">
	            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/studyschedule-list.do?seq=${dto.seq}';">일정 보기</button>
		        <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-unreg.do?seq=${dto.seq}';">탈퇴 하기</button>
		        <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-reg.do?seq=${dto.seq}';">참여 하기</button>
	          </div>
	        </div>
	      </div>
			
		</div>

	</main>
	
    <!-- 스터디 할일 목록 -->
    <div id="scheduleDiv">
    <section class="demo-block">
      <h2 class="section-title">스터디 할 일 목록</h2>
      <p class="section-desc">스터디에 할 일 목록입니다.</p>

      <div class="content-card overflow-hidden">
        <div class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
          <div>
            <h3 class="text-xl font-bold">할일 목록</h3>
            <p class="text-sm text-slate-500 mt-1"></p>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="table">
            <thead>
              <tr>
                <th></th>
                <th>할 일</th>
                <th>내용</th>
                <th></th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${todolist}" var="todoDto">
              <tr>
                <td><input type="checkbox"></td>
                <td>${todoDto.title}</td>
                <td>${todoDto.content}</td>
                <td></td>
                <td><button type="button" class="btn btn-warning btn-outline">수정하기</button></td>
                <td><button type="button" class="btn btn-error btn-outline">삭제하기</button></td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        
       <!-- 페이지 바 -->
       <div id="pagebar">${pagebar}</div>

       <div class="flex flex-wrap justify-between gap-2 btns">
			<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/todo-add.do?seq=${dto.seq}';">스터디 등록</button>
       </div>
      </div>
    </section>
    </div>
	
	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
	</script>
</body>
</html>