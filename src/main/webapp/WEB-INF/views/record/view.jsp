<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

  <!-- 뒤로가기 -->
  <div class="max-w-2xl mx-auto mb-1">
    <button class="btn btn-soft-brand mb-4"
            onclick="history.back()">
      ← 돌아가기
    </button>
  </div>
  
  <!-- 카드 -->
  <div class="content-card max-w-2xl mx-auto overflow-hidden">
  
    <!-- 헤더 -->
    <div class="bg-indigo-100 px-6 py-5 rounded-t-2xl">
      <h2 class="section-title">${dto.planTitle}</h2>
      <p class="text-sm text-slate-500 mt-1">
        ${dto.planSubject}
      </p>
    </div>
  
    <!-- 바디 -->
    <div class="card-pad px-6 py-5">
  
      <!-- 상단 (날짜 및 학습시간 + 상태) -->
      <div class="flex items-center justify-between mb-5">
        <div class="text-lg font-semibold">
          <fmt:formatDate value="${dto.studyDate}" pattern="M월 d일"/> <span class="text-sm text-slate-500">${dto.time}시간</span>
        </div>
  
        <div>
          <c:choose>
            <c:when test="${dto.recordStatus eq '진행중'}">
              <span class="badge badge-info badge-outline">진행중</span>
            </c:when>
            <c:when test="${dto.recordStatus eq '완료'}">
              <span class="badge badge-success badge-outline">완료</span>
            </c:when>
            <c:otherwise>
              <span class="badge badge-error badge-outline">미완료</span>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      
      <!-- 내용 -->
      <div class="mb-5">
        <div class="form-label">학습 내용</div>
        <div class="bg-slate-50 border border-slate-200 rounded-xl p-4 min-h-[120px]">
          ${dto.content}
        </div>
      </div>
      
      <!-- 진행률 -->
      <div class="mb-6">
        <div class="form-label">학습계획 진행률</div>
        <div class="flex items-center gap-4">
          
          <!-- 퍼센트 -->
          <span class="font-semibold text-brand-600 w-12">
            ${dto.progress}%
          </span>
  
          <!-- progress bar (daisyUI) -->
          <progress class="progress progress-primary w-full"
                    value="${dto.progress}" max="100"></progress>
        </div>
      </div>
  
      <!-- 메모 -->
      <c:if test="${not empty dto.memo}">
        <div class="mb-6">
          <div class="form-label">메모</div>
          <div class="bg-slate-50 border border-slate-200 rounded-xl p-3">
            ${dto.memo}
          </div>
        </div>
      </c:if>
  
      <!-- 하단 버튼 -->
      <!-- 버튼 -->
      <div class="flex justify-end gap-2 mt-6">
        <a href="/teamtwo/record/edit.do?seq=${dto.seq}" class="btn btn-soft btn-accent">수정</a>
    
        
        <button type="button" class="btn btn-soft btn-error"
          onclick="openModal(${dto.seq})">
            삭제
        </button>
      </div>
  
    </div>
  </div>
  
  	
  	
    
    

</main>

<!-- 모달 -->
<dialog id="deleteModal" class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">학습기록 삭제 확인</h3>
    <p class="pt-4 pb-1">
    정말 삭제하시겠습니까?<br>
    </p>

    <div class="modal-action">
      <!-- 취소 버튼 (여기에 포커스) -->
      <button class="btn" id="cancelBtn">취소</button>

      <!-- 삭제 -->
      <form method="POST" action="${pageContext.request.contextPath}/record/del.do">
        <input type="hidden" name="seq" id="deleteSeq">
        <button class="btn btn-error">삭제</button>
      </form>
    </div>
  </div>
</dialog>

  <script>
        document.addEventListener('DOMContentLoaded', function () {
        
          function openModal(seq) {
            const modal = document.getElementById('deleteModal');
            document.getElementById('deleteSeq').value = seq;
        
            modal.showModal();
        
            setTimeout(() => {
              document.getElementById('cancelBtn').focus();
            }, 0);
          }
        
          window.openModal = openModal; // 버튼에서 쓰려고 전역 등록
        
          document.getElementById('cancelBtn').onclick = function () {
            document.getElementById('deleteModal').close();
          };
        
          const modal = document.getElementById('deleteModal');
        
          modal.addEventListener('click', function (e) {
            if (e.target === modal) {
              modal.close();
            }
          });
        
        });
        
  </script>
</body>
</html>