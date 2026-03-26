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
      <h1 class="section-title">학습계획 상세 화면 (임시)</h1>
      <p class="section-desc">여기에 페이지 부제목이나 설명을 적습니다.</p>
    </div>
    
    <div class="content-card card-pad">
      <div class="flex justify-center gap-4 pt-2">
        <a href="/teamtwo/plan/edit.do?seq=${dto.seq}" class="btn btn-soft btn-accent">수정</a>
        <a href="/teamtwo/plan/del.do?seq=${dto.seq}" class="btn btn-soft btn-error">삭제</a>
      </div>
    </div>
    
</main>
</body>
</html>