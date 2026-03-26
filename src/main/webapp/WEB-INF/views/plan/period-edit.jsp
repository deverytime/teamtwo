<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>deverytime</title>
  <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body class="bg-slate-50 text-slate-800">
  <%@ include file="/WEB-INF/views/inc/header.jsp" %>
  <div class="page-wrap">
    <main class="demo-block mb-8">
        <div class="content-card card-pad mb-5 border-3 border-indigo-300!">
          <h2 class="section-title text-center">학습 계획 수정 페이지</h2>
          <p class="section-desc text-center pb-5 border-b-2 border-indigo-400">기간기반 학습계획</p>

          <form method="POST" action="/teamtwo/plan/edit.do" class="space-y-5 mt-5">        
            <input type="hidden" name="seq" value="${dto.seq}">
        
            <div class="flex justify-between items-center">
                <label class="form-label inline! text-center">계획 유형</label>
                <input type="text" class="input input-bordered w-2/3 pointer-events-none" 
                  name="type" value="${dto.type}" readonly>
            </div>
        
            <div class="flex justify-between items-center">
                <label class="form-label inline! text-center">학습 계획명</label>
                <input type="text" class="input input-bordered w-2/3"
                  name="title" value="${dto.title}">
            </div>
        
            <div class="flex justify-between items-center">
                <label class="form-label inline! text-center">주제</label>
                <input type="text" class="input input-bordered w-2/3"
                  name="subject" value="${dto.subject}">
            </div>
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">진행 상태</label>
              
              <select name="progressStatus" class="select select-bordered w-2/3">
                  <option value="진행중" ${dto.progressStatus == '진행중' ? 'selected' : ''}>진행중</option>
                  <option value="완료" ${dto.progressStatus == '완료' ? 'selected' : ''}>완료</option>
                  <option value="미완료" ${dto.progressStatus == '미완료' ? 'selected' : ''}>미완료</option>
              </select>
            </div>
            <div>
                <label class="form-label">학습계획 설명</label>
                <textarea 
                  class="textarea textarea-bordered w-full h-64"
                  name="description">${dto.description}</textarea>
            </div>
            <label class="form-label">학습 기간</label>
            <div class="flex items-center gap-3 flex-wrap">
                <span>시작일</span>
                <!-- 수정 불가 -->
                <input type="date" class="input input-bordered w-auto"
                  name="startDate" value="${dto.startDate}" readonly>
                <span class="ml-2">종료일</span>
                <input type="date" class="input input-bordered w-auto"
                  name="endDate" value="${dto.endDate}">
            </div>
          </div>
          <input type="hidden" name="memberSeq" value="${dto.memberSeq}">
          <div class="flex justify-end gap-4 pt-2">
              <button type="button" class="btn btn-ghost" onclick="history.back();">취소</button>
              <button type="submit" class="btn btn-accent">수정하기</button>
          </div>
        </form>
      </main> 
  </div>
</body>
</html>