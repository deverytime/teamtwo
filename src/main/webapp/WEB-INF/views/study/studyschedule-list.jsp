<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<style>
	
	#pagebar {
		display: flex;
		justify-content: center;
	}
	
	tr:hover {
    	background-color: #f5f5f5;
	}
	
</style>
<body>
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>

    <main class="page-wrap">
		
		<div class="mb-8">
			<h1 class="section-title">스터디 일정</h1>
			<p class="section-desc">스터디 일정을 확인하세요!</p>
		</div>

	<div class="content-card card-pad">
			
	<!-- 스터디 일정 목록-->
    <section class="demo-block">
      <h2 class="section-title">달력</h2>
      <p class="section-desc"></p>
      
      <!-- 달력 -->
	
	  달력입니다.
		
	  <!-- 스터디 일정 목록 -->	
      <div class="content-card overflow-hidden">
        <div class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
          <div>
            <h3 class="text-xl font-bold">스터디 일정 목록</h3>
            <p class="text-sm text-slate-500 mt-1"></p>
          </div>
		</div>

        <div class="overflow-x-auto">
          <table class="table">
            <thead>
              <tr>
                <th>일정</th>
                <th>내용</th>
                <th>상태</th>
                <th>시작일</th>
                <th>종료일</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${schlist}" var="dto">
              <tr style="cursor:pointer;" onclick="location.href='/teamtwo/study/studyschedule-view.do?seq=${dto.seq}';">
                <td>${dto.title }</td>
                <td>${dto.content }</td>
                <td><span class="badge badge-success badge-outline">진행중</span></td>
                <td>${dto.startDate }</td>
                <td>${dto.endDate }</td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>

        <!-- <div class="card-pad border-t border-slate-200 flex justify-between items-center">
          <p class="text-sm text-slate-500"></p>
          <div class="join">
            <button class="join-item btn btn-sm">1</button>
            <button class="join-item btn btn-sm btn-active">2</button>
            <button class="join-item btn btn-sm">3</button>
          </div>
        </div> -->
      </div>
    </section>
	       
	       <!-- 페이지 바 -->
	       <div id="pagebar">${pagebar}</div>
	      
	      
	       <!-- 버튼 -->
	       <div class="flex flex-wrap justify-between gap-2 btns">
	       		<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/studyschedule-delete.do';">스터디 일정 삭제</button>
				<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/studyschedule-add.do';">스터디 일정 등록</button>
	       </div>
	     
	    </div> 
	    </section>

	</div>

	</main>
	
    
	
	
	
	
</body>
</html>