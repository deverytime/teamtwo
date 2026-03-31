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
          <h2 class="section-title text-center">학습 계획 등록 페이지</h2>
          <p class="section-desc text-center pb-5 border-b-2 border-emerald-400">완료기반 학습계획</p>

          <form method="POST" action="/teamtwo/plan/completionplan-add.do" class="space-y-5 mt-5">
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">계획 유형</label>
                  <input type="text" class="input input-bordered w-2/3 pointer-events-none" 
                    id="type" name="type" value="완료기반" readonly>
              </div>
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">학습 계획명</label>
                  <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
                    id="title" name="title"
                    placeholder="학습 계획의 제목을 입력해주세요" required maxlength="160">
              </div>
              <div class="flex justify-between items-center">
                  <label class="form-label inline! text-center">주제</label>
                  <input type="text" class="input input-bordered w-2/3 placeholder:text-gray-500 placeholder:italic"
                    id="subject" name="subject"
                    placeholder="학습 계획의 주제를 입력해주세요" required maxlength="160">
              </div>

              <div>
                  <label class="form-label">학습계획 설명</label>
                  <textarea 
                    class="textarea textarea-bordered w-full h-64"
                    id="description" name="description"
                    placeholder="학습할 내용, 목표, 진행 방법 등을 자유롭게 작성하세요&#13;&#10;(예: 자바 기본 문법 복습 및 프로젝트 적용 연습)"
                    required maxlength="1000"></textarea>
              </div>
              <div>
                  <!-- 라벨 + 버튼 한 줄 -->
                  <div class="flex justify-start gap-3 items-center">
                      <label class="form-label">세부 목표 (선택)</label>
              
                      <button type="button" class="btn btn-outline btn-secondary btn-sm"
                              onclick="addGoal()">+ 목표 추가</button>
                  </div>
              
                  <!-- 목표 리스트 -->
                  <div id="goal-container" class="space-y-2 mt-2">
                      <div class="flex gap-2 goal-item">
                          <input type="text" name="goalNames"
                                 class="input input-bordered w-full"
                                 placeholder="목표를 입력하세요 (예: 자바 문법 1회독)">
                          <button type="button" class="btn btn-error btn-sm"
                                  onclick="removeGoal(this)">✕</button>
                      </div>
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
  
<script>
function addGoal() {
    const container = document.getElementById("goal-container");

    const div = document.createElement("div");
    div.className = "flex gap-2 goal-item";

    div.innerHTML = `
        <input type="text" name="goalNames"
               class="input input-bordered w-full"
               placeholder="목표를 입력하세요">
        <button type="button" class="btn btn-error btn-sm"
                onclick="removeGoal(this)">✕</button>
    `;

    container.appendChild(div);
}

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
</script>
</body>
</html>