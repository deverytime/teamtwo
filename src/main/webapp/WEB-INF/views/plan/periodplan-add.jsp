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
          <h2 class="section-title text-center">학습 계획 등록 페이지</h2>
          <p class="section-desc text-center pb-5 border-b-2 border-indigo-400">기간기반 학습계획</p>

          <form method="POST" action="/teamtwo/plan/periodplan-add.do" class="space-y-5 mt-5">
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">계획 유형</label>
                  <input type="text" class="input input-bordered w-2/3 pointer-events-none" 
                    id="type" name="type" value="기간기반" readonly>
              </div>
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">학습 계획명</label>
                  <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
                    id="title" name="title"
                    placeholder="학습 계획의 제목을 입력해주세요">
              </div>
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">주제</label>
                  <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
                    id="subject" name="subject"
                    placeholder="학습 계획의 주제를 입력해주세요">
              </div>

              <div>
                  <label class="form-label">학습계획 설명</label>
                  <textarea 
                    class="textarea textarea-bordered w-full h-64"
                    id="description" name="description"
                    placeholder="학습할 내용, 목표, 진행 방법 등을 자유롭게 작성하세요&#13;&#10;(예: 자바 기본 문법 복습 및 프로젝트 적용 연습)"
                    ></textarea>
              </div>

              <label class="form-label">학습 기간</label>
              <div class="flex items-center gap-3 flex-wrap">
                <span class="text-sm text-slate-600 whitespace-nowrap">시작일</span>
                <input type="date" class="input input-bordered w-auto"
                  id="startDate" name="startDate">

                <span class="text-sm text-slate-600 whitespace-nowrap ml-2">종료일</span>
                <input type="date" class="input input-bordered w-auto"
                  id="endDate" name="endDate">
              </div>
            </div>
            <input type="hidden" id="memberSeq" name="memberSeq" value="${memberSeq}">

            <div class="flex justify-end gap-4 pt-2">
              <button type="button" class="btn btn-ghost"
                onclick="history.back();">취소</button>
              <button type="submit" class="btn btn-brand">등록하기</button>
            </div>
        </form>
      </main> 
  </div>
</body>
</html>