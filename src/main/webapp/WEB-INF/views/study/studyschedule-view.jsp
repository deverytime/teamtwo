<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 일정 상세</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
    <style>
        .done-text { 
            text-decoration: line-through !important; 
            color: #94a3b8 !important; 
        }
        .done-row { 
            background-color: #f8fafc !important; 
            opacity: 0.6; 
        }
        .todo-row-item { transition: all 0.2s ease; }
        /* 제목과 내용 영역에 커서 스타일 추가 */
        .todo-clickable-label { cursor: pointer; display: block; }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <main class="page-wrap">
        <div class="mb-8 flex justify-between items-start">
            <div>
                <h1 class="section-title text-3xl font-black">${dto.title}</h1>
                <p class="section-desc">기간: ${dto.startDate} ~ ${dto.endDate}</p>
            </div>
            <div class="flex gap-2">
                <button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/studyschedule-edit.do?seq=${dto.seq}';">일정 수정</button>
                <button class="btn btn-ghost" onclick="history.back();">뒤로가기</button>
            </div>
        </div>

        <div class="content-card card-pad mb-10 border-brand-100 shadow-sm">
            <h3 class="panel-title text-brand-600">상세 설명</h3>
            <p class="text-slate-700 leading-relaxed whitespace-pre-wrap">${dto.content}</p>
        </div>

        <div class="content-card overflow-hidden">
            <div class="card-pad border-b flex justify-between items-center bg-slate-50/50">
                <h3 class="font-bold text-lg text-slate-800">To-Do List</h3>
                <button class="btn btn-brand btn-sm" onclick="location.href='/teamtwo/study/todo-add.do?seq=${dto.seq}';">할 일 추가</button>
            </div>
            <table class="table w-full">
                <tbody>
                    <c:forEach items="${todolist}" var="todoDto">
                        <tr class="todo-row-item ${todoDto.status == 1 ? 'done-row' : ''}">
                            <td class="w-16 text-center">
                                <input type="checkbox" class="checkbox checkbox-primary todo-status-ajax" 
                                       id="todo-${todoDto.seq}"
                                       data-seq="${todoDto.seq}" ${todoDto.status == 1 ? 'checked' : ''}>
                            </td>
                            <td class="py-5">
                                <label for="todo-${todoDto.seq}" class="todo-clickable-label">
                                    <div class="font-bold text-slate-800 todo-title-text ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.title}</div>
                                    <div class="text-xs text-slate-400 mt-1 todo-content-text ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.content}</div>
                                </label>
                            </td>
                            <td class="text-right pr-6">
                                <div class="join">
                                    <button class="btn btn-ghost btn-xs text-warning join-item" onclick="location.href='/teamtwo/study/todo-edit.do?seq=${todoDto.seq}&scheduleSeq=${dto.seq}';">수정</button>
                                    <button class="btn btn-ghost btn-xs text-error join-item" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='/teamtwo/study/todo-del.do?seq=${todoDto.seq}&scheduleSeq=${dto.seq}';">삭제</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="flex justify-center p-6 border-t bg-slate-50/30">
                <div class="pagination-wrap">${pagebar}</div>
            </div>
        </div>
    </main>

    <script>
    document.querySelectorAll('.todo-status-ajax').forEach(ck => {
        ck.addEventListener('change', function() {
            const row = this.closest('tr');
            const titleText = row.querySelector('.todo-title-text');
            const contentText = row.querySelector('.todo-content-text');
            const seq = this.dataset.seq;
            const status = this.checked ? 1 : 0;

            fetch('/teamtwo/study/todo-status.do', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `seq=\${seq}&status=\${status}`
            })
            .then(res => res.json())
            .then(data => {
                if (data.result === 1) {
                    if (this.checked) {
                        row.classList.add('done-row');
                        titleText.classList.add('done-text');
                        contentText.classList.add('done-text');
                    } else {
                        row.classList.remove('done-row');
                        titleText.classList.remove('done-text');
                        contentText.classList.remove('done-text');
                    }
                } else {
                    alert('상태 변경에 실패했습니다.');
                    this.checked = !this.checked;
                }
            })
            .catch(err => {
                console.error(err);
                this.checked = !this.checked;
            });
        });
    });
    </script>
</body>
</html>