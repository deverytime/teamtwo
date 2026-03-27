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
      <h1 class="section-title">학습기록 상세 (임시임시)</h1>
      <p class="section-desc">여기에 페이지 부제목이나 설명을 적습니다.</p>
    </div>
    
    <div class="content-card card-pad">
      <!-- 버튼 -->
      <div class="flex justify-end gap-4 pt-2">
        <a href="/teamtwo/record/edit.do?seq=${dto.seq}" class="btn btn-soft btn-accent">수정</a>
    
        
        <button type="button" class="btn btn-soft btn-error"
          onclick="openModal(${dto.seq})">
            삭제
        </button>
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