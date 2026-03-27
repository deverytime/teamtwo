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

  <div class="page-wrap max-w-6xl mx-auto px-4 py-8">

    <!-- 상단 제목 -->
    <div class="flex items-center justify-between mb-6">
      <div>
        <h2 class="text-3xl font-bold text-slate-800">학습계획 상세</h2>
        <p class="text-sm text-slate-500 mt-1">학습계획 관리 및 해당학습의 기록 조회가 가능합니다</p>
      </div>
      <button class="px-4 py-2 btn btn-soft-brand" onclick="location.href = '/teamtwo/plan/list.do';">
        목록으로
      </button>
    </div>

    <!-- 계획 카드 -->
    <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-6 mb-6">

      <!-- 상단 2열 -->
      <div class="flex items-center gap-10 w-fit">
      
        <!-- 좌측 -->
        <div>
          <p class="text-sm text-slate-400 mb-1">계획명</p>
          <h3 class="text-2xl font-bold text-slate-800">${dto.title}</h3>
      
          <span class="inline-flex mt-3 rounded-full px-3 py-1 text-sm font-medium
            ${dto.progressStatus == '진행중' ? 'bg-blue-100 text-blue-700' : ''}
            ${dto.progressStatus == '완료' ? 'bg-emerald-100 text-emerald-700' : ''}
            ${dto.progressStatus == '미완료' ? 'bg-red-100 text-red-700' : ''}
          ">
            ${dto.progressStatus}
          </span>
        </div>
      
        <!-- 우측 (진행률) -->
        <div
          class="radial-progress text-indigo-500 font-bold text-lg"
          style="--value:${empty dto.records ? 0 : dto.records[0].progress}; --size:96px; --thickness:10px;">
          ${empty dto.records ? 0 : dto.records[0].progress}%
        </div>
        
<!--         <div class="shrink-0"> -->
<!--           <div -->
<!--             class="radial-progress text-emerald-500 font-bold text-xl" -->
<!--             style="--value:35; --size:96px; --thickness:10px;" -->
<!--           > -->
<!--             35% -->
<!--           </div> -->
<!--         </div> -->
      
      </div>

      <!-- 설명 -->
      <div class="mt-6 border-t border-slate-100 pt-5">
        <p class="text-sm text-slate-400 mb-2">설명</p>
        <p class="text-slate-700 leading-7">
          ${dto.description}
        </p>
      </div>

      <!-- 버튼 -->
      <div class="flex justify-end gap-4 pt-2">
        <a href="/teamtwo/plan/edit.do?seq=${dto.seq}" class="btn btn-soft btn-accent">수정</a>
    
        
        <button type="button" class="btn btn-soft btn-error"
          onclick="openModal(${dto.seq})">
            삭제
        </button>
      </div>

      
    </div>
    <!-- 기본 정보 + 통계 -->
<div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

  <!-- 기본 정보 -->
  <div class="lg:col-span-2 bg-white rounded-2xl shadow-sm border border-slate-200 p-6">
    <h4 class="text-lg font-semibold text-slate-800 mb-4">기본 정보</h4>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">

      <div class="rounded-xl bg-slate-50 p-4">
        <p class="text-sm text-slate-400 mb-1">주제</p>
        <p class="text-base font-medium text-slate-800">${dto.subject}</p>
      </div>

      <div class="rounded-xl bg-slate-50 p-4">
        <p class="text-sm text-slate-400 mb-1">계획 유형</p>
        <p class="text-base font-medium text-slate-800">${dto.type}</p>
      </div>

      <div class="rounded-xl bg-slate-50 p-4">
        <p class="text-sm text-slate-400 mb-1">시작일</p>
        <p class="text-base font-medium text-slate-800">
          <fmt:formatDate value="${dto.startDate}" pattern="M월 d일" />
        </p>
      </div>

      <c:if test="${not empty dto.endDate}">
        <div class="rounded-xl bg-slate-50 p-4">
          <p class="text-sm text-slate-400 mb-1">종료일</p>
          <p class="text-base font-medium text-slate-800">
            <fmt:formatDate value="${dto.endDate}" pattern="M월 d일" />
          </p>
        </div>
      </c:if>

      <div class="rounded-xl bg-slate-50 p-4">
        <p class="text-sm text-slate-400 mb-1">최근 수정일</p>
        <p class="text-base font-medium text-slate-800">
          <fmt:formatDate value="${dto.updateDate}" pattern="M월 d일" />
        </p>
      </div>

    </div>
  </div>

  <!-- 통계 정보 -->
  <div class="bg-white rounded-2xl shadow-sm border border-slate-200 p-6">
    <h4 class="text-lg font-semibold text-slate-800 mb-4">진행 현황</h4>

    <div class="space-y-4">
      <div class="rounded-xl bg-blue-50 p-4">
        <p class="text-sm text-blue-500 mb-1">시작한 지</p>
        <p class="text-2xl font-bold text-blue-700">
          ${dto.daysFromStart}일
        </p>
      </div>

<!--       <div class="rounded-xl bg-blue-50 p-4"> -->
<!--         <p class="text-sm text-blue-500 mb-1">시작한 지</p> -->
<!--         <p class="text-2xl font-bold text-blue-700">7일</p> -->
<!--       </div> -->
<!--       <div class="rounded-xl bg-amber-50 p-4"> -->
<!--         <p class="text-sm text-amber-500 mb-1">종료까지</p> -->
<!--         <p class="text-2xl font-bold text-amber-700">D-14</p> -->
<!--       </div> -->
      
      <c:if test="${not empty dto.endDate}">
        <div class="rounded-xl bg-amber-50 p-4">
          <p class="text-sm text-amber-500 mb-1">종료까지</p>
          <p class="text-2xl font-bold text-amber-700">
      
            <c:choose>
              <c:when test="${dto.daysToEnd > 0}">
                D-${dto.daysToEnd}
              </c:when>
              <c:when test="${dto.daysToEnd == 0}">
                D-Day
              </c:when>
              <c:otherwise>
                종료됨
              </c:otherwise>
            </c:choose>
          </p>
        </div>
      </c:if>

      <div class="rounded-xl bg-violet-50 p-4">
        <p class="text-sm text-violet-500 mb-1">학습기록 수</p>
        <p class="text-2xl font-bold text-violet-700">${dto.recordCount}개</p>
      </div>

    </div>
  </div>

</div>
<!-- 학습기록 목록 -->
  <div class="bg-white rounded-2xl shadow-sm border border-slate-200 mt-6 overflow-hidden">

    <!-- 헤더 -->
    <div class="px-6 py-4 border-b border-slate-200 flex items-center justify-start">
      <div>
        <h4 class="text-lg font-semibold text-slate-800">학습기록 목록</h4>
        <p class="text-sm text-slate-500">해당 계획에 등록된 학습기록입니다.</p>
      </div>
    </div>

    <!-- 테이블 -->
    <div class="p-6">
    <div class="overflow-x-auto">
      <table class="table">
        <thead class="bg-slate-50 text-slate-500">
          <tr>
            <th class="px-6 py-3 font-medium">날짜</th>
            <th class="px-6 py-3 font-medium">진행률</th>
            <th class="px-6 py-3 font-medium">학습 내용</th>
            <th class="px-6 py-3 font-medium">상태</th>
            <th class="px-6 py-3 font-medium">메모</th>
          </tr>
        </thead>

        <tbody class="divide-y divide-slate-100">
          <c:choose>
            <%-- records 없을 때 --%>
            <c:when test="${empty dto.records}">
              <tr>
                <td colspan="5" class="text-center py-6 text-slate-400">
                  등록된 학습 기록이 없습니다.
                </td>
              </tr>
            </c:when>
            <%-- records 있을 때 --%>
            <c:otherwise>
              <c:forEach items="${dto.records}" var="record">
                <tr onclick="location.href='/teamtwo/record/view.do?seq=${record.seq}'" style="cursor:pointer;"
                    class="hover:bg-slate-100 cursor-pointer"
                    id="record-${record.seq}"
                    >
                  <td>${record.studyDate}</td>
                  <td>${record.progress}%</td>
          
                  <td>
                    <div class="max-w-[400px] truncate" title="${record.content}">
                      ${record.content}
                    </div>
                  </td>
          
                  <td>
                    <c:choose>
                      <c:when test="${record.recordStatus eq '진행중'}">
                        <span class="badge badge-outline badge-info">진행중</span>
                      </c:when>
                      <c:when test="${record.recordStatus eq '완료'}">
                        <span class="badge badge-outline badge-success">완료</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge badge-outline badge-error">미완료</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
          
                  <td>
                    <div class="max-w-[180px] truncate" title="${record.memo}">
                      ${record.memo}
                    </div>
                  </td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>

        </tbody>
      </table>
    </div>
    </div>
    
    
    
    <!-- 기록 추가 버튼 (고정) -->
    <button
      class="fixed bottom-6 right-12 z-50 px-5 py-3 rounded-full bg-sky-500 text-white font-semibold shadow-lg hover:bg-sky-600 active:scale-95 transition cursor-pointer"
      onclick="location.href = '/teamtwo/record/add.do?seq=${dto.seq}';"
      >
      + 기록 추가
    </button>
  </div>
  <c:if test="${cnt < dto.recordCount}">
      <div class="flex justify-center mt-6">
        <a href="/teamtwo/plan/view.do?seq=${dto.seq}&cnt=${cnt + 5}"
          class="px-6 py-2 rounded-xl btn btn-wide border-2 border-indigo-200 hover:bg-indigo-50
            text-slate-600"
          onclick="saveScrollAndLastRecord()">
          더보기
        </a>
      </div>
    </c:if>

  </div>
<!-- 모달 -->
<dialog id="deleteModal" class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">학습계획 삭제 확인</h3>
    <p class="pt-4 pb-1">
    정말 삭제하시겠습니까?<br>학습계획 삭제시 학습기록도 함께 사라집니다. 
    </p>

    <div class="modal-action">
      <!-- 취소 버튼 (여기에 포커스) -->
      <button class="btn" id="cancelBtn">취소</button>

      <!-- 삭제 -->
      <form method="POST" action="${pageContext.request.contextPath}/plan/del.do">
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
                
        function saveLastRecord() {
      	    const rows = document.querySelectorAll('[id^="record-"]');
      	    if (rows.length > 0) {
      	      const lastRowId = rows[rows.length - 1].id;
      	      sessionStorage.setItem('lastRecordId', lastRowId);
      	    }
      	  }

		function saveScrollAndLastRecord() {
            sessionStorage.setItem('prevScrollY', String(window.scrollY));
        
            const rows = document.querySelectorAll('[id^="record-"]');
            if (rows.length > 0) {
              sessionStorage.setItem('lastRecordId', rows[rows.length - 1].id);
            }
          }
        
          // 브라우저 자동 스크롤 복원 끄기
          if ('scrollRestoration' in history) {
            history.scrollRestoration = 'manual';
          }
        
          window.addEventListener('pageshow', function () {
            const prevScrollY = sessionStorage.getItem('prevScrollY');
            const lastRecordId = sessionStorage.getItem('lastRecordId');
        
            if (!prevScrollY || !lastRecordId) return;
        
            // 1차: 이전 위치로 즉시 이동
            window.scrollTo(0, parseInt(prevScrollY, 10));
        
            // 2차: 렌더링 한 번 끝난 뒤 다시 보정
            requestAnimationFrame(() => {
              window.scrollTo(0, parseInt(prevScrollY, 10));
        
              // 3차: 마지막 항목 위치로 부드럽게
              requestAnimationFrame(() => {
                const target = document.getElementById(lastRecordId);
        
                if (target) {
                  const y = target.getBoundingClientRect().top + window.scrollY - 80;
        
                  window.scrollTo({
                    top: y,
                    behavior: 'smooth'
                  });
                }
        
                sessionStorage.removeItem('prevScrollY');
                sessionStorage.removeItem('lastRecordId');
              });
            });
          });
  </script>
</body>
</html>