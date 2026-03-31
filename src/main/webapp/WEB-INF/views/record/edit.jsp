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
      <form method="POST" action="/teamtwo/record/edit.do" class="space-y-5 mt-5">      
        <div class="content-card card-pad mb-5 border-3 border-emerald-300!">
          <h2 class="section-title text-center">학습 기록 수정 페이지</h2>
      
          <div class="space-y-5 mt-5">
      
            <!-- 학습일 -->
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">학습일</label>
              <input type="date"
                     name="studyDate"
                     value="${dto.studyDate}"
                     class="input input-bordered w-2/3"
                     required>
            </div>
            
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">학습 시간</label>
              <select name="time"
                      class="select select-bordered w-2/3"
                      required>
                <option value="1" ${dto.time == 1 ? 'selected' : ''}>1시간</option>
                <option value="2" ${dto.time == 2 ? 'selected' : ''}>2시간</option>
                <option value="3" ${dto.time == 3 ? 'selected' : ''}>3시간</option>
                <option value="4" ${dto.time == 4 ? 'selected' : ''}>4시간</option>
                <option value="5" ${dto.time == 5 ? 'selected' : ''}>5시간</option>
                <option value="6" ${dto.time == 6 ? 'selected' : ''}>6시간</option>
                <option value="7" ${dto.time == 7 ? 'selected' : ''}>7시간</option>
                <option value="8" ${dto.time == 8 ? 'selected' : ''}>8시간</option>
                <option value="9" ${dto.time == 9 ? 'selected' : ''}>9시간</option>
                <option value="10" ${dto.time == 10 ? 'selected' : ''}>10시간</option>
                <option value="11" ${dto.time == 11 ? 'selected' : ''}>11시간</option>
                <option value="12" ${dto.time == 12 ? 'selected' : ''}>12시간</option>
              </select>
            </div>
      
            <!-- 진행률 -->
            <div class="flex justify-between items-center">
              <label class="form-label inline! text-center">진행률</label>
      
              <div class="flex items-center gap-3 w-2/3">
                <span class="w-10 text-left text-sm font-semibold text-slate-600">
                  <span id="progressValue">${dto.progress}</span>%
                </span>
      
                <input type="range"
                       id="progress"
                       name="progress"
                       min="${dto.minProgress}" max="100" step="5"
                       value="${dto.progress}"
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
      
                <option value="진행중"
                  <c:if test="${dto.recordStatus == '진행중'}">selected</c:if>>
                  진행중
                </option>
      
                <option value="완료"
                  <c:if test="${dto.recordStatus == '완료'}">selected</c:if>>
                  완료
                </option>
      
              </select>
            </div>
      
            <!-- 학습 내용 -->
            <div>
              <label class="form-label">학습 내용</label>
              <textarea name="content"
                        class="textarea textarea-bordered w-full h-48"
                        required maxlength="500">${dto.content}</textarea>
            </div>
      
            <!-- 메모 -->
            <div>
              <label class="form-label">메모 (선택)</label>
              <textarea name="memo"
                        class="textarea textarea-bordered w-full h-32"
                        maxlength="500">${dto.memo}</textarea>
            </div>
      
            <!-- hidden -->
            <input type="hidden" name="seq" value="${dto.seq}">
            <input type="hidden" name="planSeq" value="${dto.planSeq}">
      
          </div>
        </div>
      
        <div class="flex justify-end gap-4">
          <button type="button" class="btn btn-ghost" onclick="history.back();">취소</button>
          <button type="submit" class="btn btn-accent">수정하기</button>
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