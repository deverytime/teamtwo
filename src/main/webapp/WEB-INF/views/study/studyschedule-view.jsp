<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>deverytime</title>
	<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
	<style>
		
		#scheduleDiv {
			margin: 50px 120px;
		}
		
		#pagebar {
			display: flex;
			justify-content: center;
		}
		
		tr:hover {
    		background-color: #f5f5f5;
		}
	    
	    .done-text {
	        text-decoration: line-through;
	        color: #94a3b8; /* 슬레이트 400 색상 */
    	}
    	
    	.label {
	        cursor: pointer; /* 클릭 가능하다는 표시 */
	    }
	    
	    .done-row {
	        background-color: #e2e8f0 !important; /* 연한 그레이/슬레이트 톤으로 변경 */
	        transition: background-color 0.3s ease; /* 부드러운 색상 변경 효과 */
	    }
	    
	    .table .label {
	        text-align: left;
	    }
	    
	    .table {
		    /* 이 속성이 있어야 colgroup에서 지정한 %가 강제로 적용됩니다. */
		    table-layout: fixed; 
		    width: 100%;
		}
		
		.table th, .table td {
		    /* 내용이 너무 길어서 칸을 밀어내지 않도록 설정 */
		    overflow: hidden;
		    white-space: nowrap;
		    word-break: break-all;
		}
	    
		
	</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap">
		
		<div class="mb-8">
			<div>
				<h1 class="section-title">스터디 일정 상세</h1>
			</div>
			<p class="section-desc">스터디 일정 상세화면입니다.</p>
		</div>

		<div class="content-card card-pad">
		
		<!-- 스터디 일정 상세 대시보드 -->	
		<div id="main-layout">
		
		  <div id="main">
	        <div class="content-card card-pad">
	          <div class="flex items-start justify-between gap-4 mb-4">
	            <div>
	              <p class="text-sm text-slate-500"></p>
	              <h3 class="text-xl font-bold mt-1">${dto.title}</h3>
	            </div>
	            <span class="badge badge-outline badge-primary">진행중 / 완료</span>
	          </div>
	
	          <p class="text-slate-600 leading-7 mb-6">
	            ${dto.content}
	          </p>
	
	          <div class="grid sm:grid-cols-3 gap-4 mb-6">
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">시작일</p>
	              <p class="mt-2 text-2xl font-bold">${dto.startDate}</p>
	            </div>
	            <div class="stats-box">
	              <p class="text-sm text-slate-500">종료일</p>
	              <p class="mt-2 text-2xl font-bold">${dto.endDate}</p>
	            </div>
	          </div>
						
	        </div>
	      </div>
			
		</div>

	</main>
	
    <!-- 스터디 할일 목록 -->
    <div id="scheduleDiv">
    <section class="demo-block">
      <h2 class="section-title">스터디 할 일 목록</h2>
      <p class="section-desc">스터디에 할 일 목록입니다.</p>

      <div class="content-card overflow-hidden">
        <div class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
          <div>
            <h3 class="text-xl font-bold">할일 목록</h3>
            <p class="text-sm text-slate-500 mt-1"></p>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="table">
          	<colgroup>
	        	<col style="width: 10%;">
	        	<col style="width: 20%;">
	        	<col style="width: 40%;">
	        	<col style="width: 15%;">
	        	<col style="width: 15%;">
	        </colgroup>
            <thead>
              <tr>
                <th></th>
                <th>할 일</th>
                <th>내용</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${todolist}" var="todoDto">
              <tr class="${todoDto.status == 1 ? 'done-row' : ''}">
                <td>
                <input type="checkbox" class="checkbox" 
		               data-seq="${todoDto.seq}"
		               ${todoDto.status == 1 ? 'checked' : ''}>
               </td>
                <td class="label ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.title}</td>
                <td class="label ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.content}</td>
                <td><button type="button" class="btn btn-warning btn-outline">수정하기</button></td>
                <td><button type="button" class="btn btn-error btn-outline">삭제하기</button></td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        
       <!-- 페이지 바 -->
       <div id="pagebar">${pagebar}</div>

       <div class="flex flex-wrap justify-between gap-2 btns">
			<button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/todo-add.do?seq=${dto.seq}';">할 일 등록</button>
       </div>
      </div>
    </section>
    </div>
	
	<script>
		
		document.querySelectorAll('.label').forEach(label => {
			
			label.addEventListener('click', function(){
				
				const checkbox = this.closest('tr').querySelector('input[type="checkbox"]');
				
				checkbox.checked = !checkbox.checked;
				checkbox.dispatchEvent(new Event('change'));
			});
			
		});
		
		document.querySelectorAll('.checkbox').forEach(ck => {
		
			ck.addEventListener('change', function(){
				
				const row = this.closest('tr');
	            const labels = row.querySelectorAll('.label');
	            const seq = this.dataset.seq; // data-seq 값 가져오기
	            const status = this.checked ? 1 : 0; // 체크면 1, 해제면 0
	            //console.log("JS에서 보낼 데이터 확인 -> seq:", seq, "status:", status);

	            // AJAX 요청
	            fetch('/teamtwo/study/todo-status.do', {
	                method: 'POST',
	                headers: {
	                    'Content-Type': 'application/x-www-form-urlencoded',
	                },
	                body: `seq=\${seq}&status=\${status}`
	            })
	            .then(response => response.json())
	            .then(data => {
	                if (data.result === 1) {
	                    // DB 업데이트 성공 시 UI 변경
	                    if (this.checked) {
	                        row.classList.add('done-row');
	                        labels.forEach(el => el.classList.add('done-text'));
	                    } else {
	                        row.classList.remove('done-row');
	                        labels.forEach(el => el.classList.remove('done-text'));
	                    }
	                } else {
	                    alert('상태 변경에 실패했습니다.');
	                    this.checked = !this.checked; // 실패 시 체크박스 원복
	                }
	            })
	            .catch(error => {
	                console.error('Error:', error);
	                alert('서버 통신 오류가 발생했습니다.');
	                this.checked = !this.checked;
	            });
			});
		});
	
	</script>
</body>
</html>