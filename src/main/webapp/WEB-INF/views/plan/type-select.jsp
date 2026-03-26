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
  
  <main class="page-wrap">
  
    <div class="mb-8">
      <h1 class="section-title">학습계획 유형 선택</h1>
      <p class="section-desc">계획의 유형을 선택해주세요.</p>
    </div>
      <div class="flex gap-8 max-w-4xl mx-auto">
        <a href="/teamtwo/plan/periodplan-add.do" class="flex-1">
          <div class="bg-white border border-slate-200 rounded-2xl p-10 h-48 flex flex-col justify-center items-center
                      shadow-sm hover:shadow-md hover:-translate-y-1 transition cursor-pointer
                      hover:bg-blue-100">
            <h3 class="text-2xl font-bold">기간기반</h3>
            <p class="text-base text-slate-500 mt-3">시작일 ~ 종료일 기반 학습 계획</p>
          </div>
        </a>

        <a href="/teamtwo/plan/completionplan-add.do" class="flex-1">
          <div class="bg-white border border-slate-200 rounded-2xl p-10 h-48 flex flex-col justify-center items-center
                      shadow-sm hover:shadow-md hover:-translate-y-1 transition cursor-pointer
                      hover:bg-emerald-100">
            <h3 class="text-2xl font-bold">완료기반</h3>
            <p class="text-base text-slate-500 mt-3">목표 달성 기준 학습 계획</p>
          </div>
        </a>
      </div>
  </main>
</body>
</html>