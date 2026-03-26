<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<style>

	#main {
		margin: 0px 40px;
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 40px;
	}
	
	.btns {
		margin: 50px;
		justify-content: center;
	}
	
	#btn2 {
		margin-left: 10px;
	}
	
	#searchDiv {
		margin: 40px;
		margin-left: 120px;
	}
	
	#searchBar {
		width: 400px;
	}
	
	#select {
		width: 160px;
	}
	
	#pagebar {
		display: flex;
		justify-content: center;
	}
	
</style>
<body>
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>

    <main class="page-wrap">
		
		<div class="mb-8">
			<h1 class="section-title">내 스터디 목록</h1>
			<p class="section-desc">내가 참여하고 있는 스터디를 확인하세요!</p>
		</div>

		<div class="content-card card-pad">
			
	<!-- 내 스터디 목록 -->
	<section class="demo-block">
	<div id="main-layout">
	  <div id="main">
	  <c:forEach items="${list}" var="dto">
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
              <p class="mt-2 text-xl font-bold">${dto.scheduleCount}개</p>
            </div>
            <div class="stats-box">
              <p class="text-sm text-slate-500">생성일</p>
              <p class="mt-2 text-xl font-bold">${dto.createDate}</p>
            </div>
          </div>
	
          <div class="flex flex-wrap justify-between gap-2">
            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-view.do?seq=${dto.seq}';">상세 보기</button>
            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-reg.do?seq=${dto.seq}';">참여 하기</button>
          </div>
        </div>
      </c:forEach>
      </div>
      
      <!-- 검색 -->
      <div class="flex flex-col sm:flex-row gap-2" id="searchDiv">
            <select class="select select-bordered bg-white" id="select">
              <option>전체 상태</option> 
              <option>모집중</option>
              <option>모집완료</option>
            </select>
            <input
              type="text"
              placeholder="스터디 검색"
              class="input input-bordered bg-white"
              name="word"
              id="searchBar"
            />
            <button class="btn btn-brand">검색</button>
          </div>
       
       <!-- 페이지 바 -->
       <div id="pagebar">${pagebar}</div>
      
      
       <!-- 버튼 -->
       <div class="flex flex-wrap justify-between gap-2 btns">
			<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/study-add.do';">스터디 등록</button>
			<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/mystudy-list.do';">내 스터디 목록</button>
       </div>
      
    </section>

		</div>

	</main>
	
    
	
	
	
	
</body>
</html>