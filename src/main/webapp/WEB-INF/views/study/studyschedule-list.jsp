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
	
	table th:nth-child(n+3):nth-child(-n+5),
	table td:nth-child(n+3):nth-child(-n+5) {
		text-align: center;
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
          <colgroup>
        	<col style="width: 20%;">
        	<col style="width: 30%;">
        	<col style="width: 10%">
        	<col style="width: 10%;">
        	<col style="width: 10%;">
        	<col style="width: 10%;">
        	<col style="width: 10%;">
          </colgroup>
            <thead>
              <tr>
                <th>일정</th>
                <th>내용</th>
                <th>상태</th>
                <th>시작일</th>
                <th>종료일</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${schlist}" var="dto">
              <tr>
                <td style="cursor:pointer;" onclick="location.href='/teamtwo/study/studyschedule-view.do?seq=${dto.seq}';">${dto.title }</td>
                <td style="cursor:pointer;" onclick="location.href='/teamtwo/study/studyschedule-view.do?seq=${dto.seq}';">${dto.content }</td>
                <td>
                	진행중 / 종료 향후 구현
                	<%-- <c:if test="{isProgress}">
                		<span class="badge badge-outline badge-primary">진행중</span>
                	</c:if>
                	<c:if test="{!isProgress}">
                		<span class="badge badge-outline badge-primary">종료</span>
                	</c:if> --%>
                </td>
                <td>${dto.startDate }</td>
                <td>${dto.endDate }</td>
                <td><button class="btn btn-soft-brand" onclick="event.stopPropagation(); location.href='/teamtwo/study/studyschedule-edit.do?seq=${dto.seq}';">수정</button></td>
                <td><button class="btn btn-soft-brand" onclick="event.stopPropagation(); location.href='/teamtwo/study/studyschedule-del.do?seq=${dto.seq}&studySeq=${seq}';">삭제</button></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>

      </div>
    </section>
	       
	       <!-- 페이지 바 -->
	       <div id="pagebar">${pagebar}</div>
	      
	      
	       <!-- 버튼 -->
	       <div class="flex flex-wrap justify-between gap-2 btns">
				<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/studyschedule-add.do?seq=${seq}';">스터디 일정 등록</button>
	       </div>
	     
	    </div> 
	    </section>

	</div>

	</main>
	
    
	
	
	
	
</body>
</html>