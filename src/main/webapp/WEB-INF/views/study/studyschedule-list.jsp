<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 일정 목록</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <main class="page-wrap">
        <div class="mb-8 flex justify-between items-end">
            <div>
                <h1 class="section-title">스터디 일정</h1>
                <p class="section-desc">전체적인 학습 흐름과 세부 일정을 확인하세요.</p>
            </div>
            <button class="btn btn-brand" onclick="location.href='/teamtwo/study/studyschedule-add.do?seq=${seq}';">일정 등록</button>
        </div>

        <div class="content-card card-pad mb-10 bg-brand-50 border-brand-100 flex items-center justify-center min-h-40">
            <div class="text-center">
                <p class="text-brand-600 font-bold mb-1">Calendar View</p>
                <p class="text-slate-400 text-sm">달력 라이브러리 연동 시 이 영역을 활용하세요.</p>
            </div>
        </div>

        <div class="content-card overflow-hidden">
            <div class="card-pad border-b bg-white">
                <h3 class="font-bold text-lg">상세 일정 목록</h3>
            </div>
            <table class="table w-full">
                <thead class="bg-slate-50 text-slate-500">
                    <tr>
                        <th class="pl-6 w-1/4">일정명</th>
                        <th class="w-1/3">설명</th>
                        <th class="text-center">기간</th>
                        <th class="text-right pr-6">관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${schlist}" var="dto">
                        <tr class="hover:bg-slate-50/80 cursor-pointer transition-colors" onclick="location.href='/teamtwo/study/studyschedule-view.do?seq=${dto.seq}';">
                            <td class="pl-6 font-bold text-slate-700">${dto.title}</td>
                            <td class="text-slate-500 text-sm line-clamp-1">${dto.content}</td>
                            <td class="text-center">
                                <span class="text-xs font-medium text-slate-400 block">${dto.startDate}</span>
                                <span class="text-xs font-medium text-slate-400 block">~ ${dto.endDate}</span>
                            </td>
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
            <div class="flex justify-center p-6 border-t">${pagebar}</div>
        </div>
    </main>
</body>
</html>