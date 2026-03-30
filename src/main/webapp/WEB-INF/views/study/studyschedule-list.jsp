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
        /* 1. 일정 띠를 얇게 설정 (한 칸에 더 많이 보이게) */
		.fc-daygrid-event {
		    margin-top: 1px !important;     /* 일정 간의 간격 축소 */
		    margin-bottom: 1px !important;
		    padding: 0 4px 0 8px !important;      /* 상하 패딩 제거, 좌우만 남김 */
		    height: 20px !important;        /* 막대기 높이 조절 (기본은 약 24~26px) */
		    font-size: 0.75rem !important;  /* 글자 크기를 살짝 줄여서 띠에 맞춤 */
		    line-height: 20px !important;   /* 글자가 수직 중앙에 오도록 높이와 맞춤 */
		    border-radius: 3px !important;  /* 모서리를 살짝 둥글게 (선택사항) */
		    transition: all 0.2s ease;      /* 부드러운 변화 효과 */
		}
		
		/* 2. 호버 시 색상을 진하게 + 살짝 강조 */
		.fc-event:hover {
		    filter: brightness(0.8) saturate(1.3); /* 밝기는 낮추고 채도는 높여서 진하게 만듦 */
		    transform: scaleX(1.02);               /* 가로로 아주 살짝 커지는 효과 */
		    z-index: 10 !important;                /* 다른 일정보다 위에 보이게 설정 */
		    box-shadow: 0 2px 4px rgba(0,0,0,0.1);  /* 살짝 그림자 추가 */
		}
		
		/* 3. 내부 텍스트 정렬 */
		.fc-event-title {
		    font-weight: 500 !important;
		    overflow: hidden;
		    text-overflow: ellipsis; /* 글자가 길면 ... 처리 */
		    white-space: nowrap;
		}
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
            <div class="card-pad border-b bg-slate-50/50 flex items-center gap-2">
		        <h3 class="font-bold text-lg text-slate-800 mb-0">전체 일정 목록</h3>
		        <span class="badge badge-sm badge-ghost font-normal">Total ${calendarList.size()}</span>
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
            const colors = [
                'rgba(59, 130, 246, 0.6)', 
                'rgba(16, 185, 129, 0.6)', 
                'rgba(245, 158, 11, 0.6)', 
                'rgba(239, 68, 68, 0.6)', 
                'rgba(139, 92, 246, 0.6)', 
                'rgba(236, 72, 153, 0.6)', 
                'rgba(6, 182, 212, 0.6)'
            ];
            
            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                dayMaxEvents: true, // 일정이 많으면 +more로 표시
                displayEventTime: false,
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,dayGridWeek'
                },
                events: [
                    <c:forEach items="${calendarList}" var="item" varStatus="status">
                    {
                        id: '${item.seq}',
                        title: '${item.title}',
                        start: '${item.startDate}',
                        end: '${item.endDate}',
                        url: '/teamtwo/study/studyschedule-view.do?seq=${item.seq}',
                        color: colors[${status.index % 7}],
                        textColor: '#1e293b'
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