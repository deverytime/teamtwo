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
      <div class="content-card card-pad mb-5 border-3 border-emerald-300!">
        <h2 class="section-title text-center">학습 계획 수정 페이지</h2>
        <p class="section-desc text-center pb-5 border-b-2 border-emerald-400">완료기반 학습계획</p>

        <form method="POST" action="/teamtwo/plan/edit.do" class="space-y-5 mt-5">
          
          <input type="hidden" id="seq" name="seq" value="${dto.seq}">
          <input type="hidden" id="memberSeq" name="memberSeq" value="${dto.memberSeq}">
          <input type="hidden" name="startDate" value="${dto.startDate}">

          <div class="flex justify-between items-center">
            <label class="form-label inline! text-center">계획 유형</label>
            <input type="text" class="input input-bordered w-2/3 pointer-events-none"
              id="type" name="type" value="${dto.type}" readonly>
          </div>

          <div class="flex justify-between items-center">
            <label class="form-label inline! text-center">학습 계획명</label>
            <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
              id="title" name="title" value="${dto.title}" required maxlength="160">
          </div>

          <div class="flex justify-between items-center">
            <label class="form-label inline! text-center">주제</label>
            <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
              id="subject" name="subject" value="${dto.subject}" required maxlength="160">
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
              id="description" name="description" required maxlength="1000">${dto.description}</textarea>
          </div>
          
          <div>
            <!-- 라벨 + 버튼 -->
            <div class="flex justify-start gap-3 items-center">
                <label class="form-label">세부 목표 (선택)</label>
        
                <button type="button" class="btn btn-outline btn-secondary btn-sm"
                        onclick="addGoal()">+ 목표 추가</button>
            </div>
        
            <!-- 목표 리스트 -->
            <div id="goal-container" class="space-y-2 mt-2">
                <!-- goals 있으면 출력 -->
                <c:if test="${not empty dto.goals}">
                    <c:forEach var="goal" items="${dto.goals}">
                        <div class="flex gap-2 goal-item">
                            <input type="hidden" name="goalSeqs" value="${goal.seq}">
                            <input type="text" name="goalNames"
                                   class="input input-bordered w-full"
                                   value="${goal.name}">
                            <button type="button" class="btn btn-error btn-sm"
                                    onclick="removeGoal(this)">✕</button>
                        </div>
                    </c:forEach>
                </c:if>
                <!-- goals 없으면 기본 1개 -->
                <c:if test="${empty dto.goals}">
                    <div class="flex gap-2 goal-item">
                        <input type="hidden" name="goalSeqs" value="">
                        <input type="text" name="goalNames"
                               class="input input-bordered w-full"
                               placeholder="목표를 입력하세요 (예: 자바 문법 1회독)">
                        <button type="button" class="btn btn-error btn-sm"
                                onclick="removeGoal(this)">✕</button>
                    </div>
                </c:if>        
            </div>
        </div>

        <div class="flex justify-end gap-4 pt-2">
          <button type="button" class="btn btn-ghost" onclick="history.back();">취소</button>
          <button type="submit" class="btn btn-accent">수정하기</button>
        </div>
      </form>
      
<script>
    function removeGoal(button) {
        const container = document.getElementById("goal-container");
        const items = container.getElementsByClassName("goal-item");
    
        // 최소 1개는 남기기
        if (items.length <= 1) {
            button.previousElementSibling.value = "";
            return;
        }
    
        button.parentElement.remove();
    }

    function addGoal() {
        const container = document.getElementById("goal-container");
    
        const div = document.createElement("div");
        div.className = "flex gap-2 goal-item";
    
        div.innerHTML = `
            <input type="hidden" name="goalSeqs" value="">
            <input type="text" name="goalNames"
                   class="input input-bordered w-full"
                   placeholder="목표를 입력하세요 (예: 자바 문법 1회독)">
            <button type="button" class="btn btn-error btn-sm"
                    onclick="removeGoal(this)">✕</button>
        `;
    
        container.appendChild(div);
    }
</script>
      
    </main>
  </div>
</body>
</html>