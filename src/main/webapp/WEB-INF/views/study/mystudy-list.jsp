<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 내 스터디</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <main class="page-wrap">
        <div class="mb-10 flex flex-col md:flex-row md:items-end md:justify-between gap-4">
            <div>
                <h1 class="section-title text-3xl font-black">내 스터디 목록</h1>
                <p class="section-desc">내가 참여 중인 스터디의 현황을 확인하세요.</p>
            </div>
            <div class="flex gap-2">
                <button class="btn btn-brand" onclick="location.href='/teamtwo/study/study-add.do';">새 스터디 등록</button>
                <button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/study-list.do';">전체 목록 보기</button>
            </div>
        </div>

        <div class="grid gap-6 md:grid-cols-2 mb-12">
            <c:forEach items="${list}" var="dto">
                <div class="content-card card-pad hover:shadow-md transition-all border-brand-100/50">
                    <div class="flex items-start justify-between mb-4">
                        <h3 class="text-xl font-bold text-slate-800">${dto.name}</h3>
                        <span class="badge ${dto.status == '0' ? 'badge-primary' : 'badge-ghost'} badge-outline">
                            ${dto.status == '0' ? '모집중' : '모집완료'}
                        </span>
                    </div>
                    <p class="text-slate-600 mb-6 line-clamp-2 h-12">${dto.description}</p>
                    
                    <div class="grid grid-cols-3 gap-3 mb-6">
                        <div class="stats-box text-center py-3">
                            <p class="text-[10px] font-bold text-slate-400 uppercase">참여인원</p>
                            <p class="font-bold text-slate-700">${dto.headCount}/${dto.capacity}</p>
                        </div>
                        <div class="stats-box text-center py-3">
                            <p class="text-[10px] font-bold text-slate-400 uppercase">일정수</p>
                            <p class="font-bold text-slate-700">${dto.scheduleCount}개</p>
                        </div>
                        <div class="stats-box text-center py-3">
                            <p class="text-[10px] font-bold text-slate-400 uppercase">개설일</p>
                            <p class="font-bold text-slate-700 text-sm">${dto.createDate}</p>
                        </div>
                    </div>

                    <div class="flex gap-2">
                        <button class="btn btn-soft-brand flex-1" onclick="location.href='/teamtwo/study/study-view.do?seq=${dto.seq}';">상세보기</button>
                        <button class="btn btn-ghost text-error hover:bg-red-50 flex-1" onclick="if(confirm('정말 탈퇴하시겠습니까?')) location.href='/teamtwo/study/study-unreg.do?seq=${dto.seq}';">탈퇴하기</button>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="flex justify-center mb-20">
        	<div class="pagination-wrap">
        		${pagebar}
        	</div>
        </div>
    </main>
</body>
</html>