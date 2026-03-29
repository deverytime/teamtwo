<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 스터디 일정</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>
    <style>
        .fc-event { cursor: pointer; padding: 2px 4px; border: none !important; }
        .fc-daygrid-day-frame { min-height: 120px !important; }
        #calendar { max-height: 800px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <main class="page-wrap">
        <div class="mb-8 flex justify-between items-end">
            <div>
                <h1 class="section-title">스터디 일정 관리</h1>
                <p class="section-desc">한눈에 들어오는 달력으로 학습 흐름을 파악하세요.</p>
            </div>
            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/studyschedule-add.do?seq=${seq}';">일정 등록</button>
        </div>

        <div class="content-card card-pad mb-10 shadow-sm border-brand-100">
            <div id="calendar"></div>
        </div>

        <div class="content-card overflow-hidden">
            <div class="card-pad border-b bg-slate-50/50">
                <h3 class="font-bold text-lg text-slate-800">전체 일정 목록</h3>
            </div>
            <table class="table w-full">
                <thead class="bg-slate-50">
                    <tr>
                        <th class="pl-6">일정 제목</th>
                        <th class="text-center">시작일</th>
                        <th class="text-center">종료일</th>
                        <th class="text-right pr-6">관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${schlist}" var="dto">
                        <tr class="hover:bg-slate-50/50 cursor-pointer" onclick="location.href='/teamtwo/study/studyschedule-view.do?seq=${dto.seq}';">
                            <td class="pl-6 font-bold text-slate-700">${dto.title}</td>
                            <td class="text-center text-sm text-slate-500">${dto.startDate}</td>
                            <td class="text-center text-sm text-slate-500">${dto.endDate}</td>
                            <td class="text-right pr-6" onclick="event.stopPropagation();">
                                <div class="join">
                                    <button class="btn btn-xs btn-ghost text-brand-600 join-item" onclick="location.href='/teamtwo/study/studyschedule-edit.do?seq=${dto.seq}';">수정</button>
                                    <button class="btn btn-xs btn-ghost text-error join-item" onclick="if(confirm('삭제하시겠습니까?')) location.href='/teamtwo/study/studyschedule-del.do?seq=${dto.seq}&studySeq=${seq}';">삭제</button>
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
        document.addEventListener('DOMContentLoaded', function() {
            const calendarEl = document.getElementById('calendar');
            const colors = ['#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899', '#06b6d4'];
            
            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                dayMaxEvents: true, // 일정이 많으면 +more로 표시
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,dayGridWeek'
                },
                events: [
                    <c:forEach items="${schlist}" var="item" varStatus="status">
                    {
                        id: '${item.seq}',
                        title: '${item.title}',
                        start: '${item.startDate}',
                        end: '${item.endDate}',
                        url: '/teamtwo/study/studyschedule-view.do?seq=${item.seq}',
                        color: colors[${status.index % 7}]
                    }${!status.last ? ',' : ''}
                    </c:forEach>
                ],
                eventClick: function(info) {
                    if (info.event.url) {
                        window.location.href = info.event.url;
                        return false;
                    }
                }
            });
            calendar.render();
        });
    </script>
</body>
</html>