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
  
      <form method="POST" action="/teamtwo/record/add.do" class="space-y-5 mt-5">
  
        <div class="content-card card-pad mb-5 border-3 border-indigo-300!">
          <h2 class="section-title text-center">학습 기록 작성 페이지</h2>
          <p class="section-desc text-center pb-5 border-b-2 border-indigo-400">
            학습 계획에 대한 학습기록을 작성합니다
          </p>
  
          <div class="space-y-5 mt-5">
  
            <!-- 학습일 -->
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">학습일</label>
              <input type="date"
                    name="studyDate"
                    class="input input-bordered w-2/3"
                    required>
            </div>
  
            <!-- 진행률 -->
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">진행률</label>
  
              <div class="flex items-center gap-3 w-2/3">
                <span class="w-10 text-left text-sm font-semibold text-slate-600">
                  <span id="progressValue">20</span>%
                </span>
  
                <input type="range"
                      id="progress"
                      name="progress"
                      min="0" max="100" step="5" value="20"
                      class="range range-primary flex-1"
                      required />
              </div>
            </div>
  
            <!-- 기록 상태 -->
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">기록 상태</label>
              <select name="recordStatus"
                      class="select select-bordered w-2/3"
                      required>
                <option value="완료" selected>완료</option>
                <option value="진행중">진행중</option>
              </select>
            </div>
  
            <!-- 학습 내용 -->
            <div>
              <label class="form-label">학습 내용</label>
              <textarea name="content"
                class="textarea textarea-bordered w-full h-48"
                placeholder="오늘 학습한 내용, 진행 과정, 어려웠던 점 등을 작성하세요"
                required maxlength="500"></textarea>
            </div>
  
            <!-- 메모 (선택) -->
            <div>
              <label class="form-label">메모 (선택)</label>
              <textarea name="memo"
                class="textarea textarea-bordered w-full h-32"
                placeholder="추가로 남기고 싶은 메모가 있다면 작성하세요 (선택)"
                maxlength="500"></textarea>
            </div>
  
            <!-- 계획번호 -->
            <input type="hidden" name="planSeq" value="${planSeq}">
  
          </div>
        </div>
  
        <div class="flex justify-end gap-4">
          <button type="button" class="btn btn-ghost" onclick="history.back();">취소</button>
          <button type="submit" class="btn btn-brand">작성하기</button>
        </div>
  
      </form>
  
    </main>
  </div>

  <script src="https://code.jquery.com/jquery-4.0.0.js"></script>
  <script>
      const range = document.getElementById('progress');
      const value = document.getElementById('progressValue');

      value.textContent = range.value;

      range.addEventListener('input', function () {
        value.textContent = this.value;
      });
  </script>
</body>
</html>