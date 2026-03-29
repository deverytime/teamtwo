<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 스터디 탐색</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>

    <main class="page-wrap">
        <div class="mb-10 flex flex-col md:flex-row md:items-end md:justify-between gap-4">
            <div>
                <h1 class="section-title text-3xl font-black">스터디 목록</h1>
                <p class="section-desc">현재 모집 중인 스터디에 참여하여 함께 성장해보세요.</p>
            </div>
            <div class="flex gap-2">
                <button class="btn btn-brand px-6" onclick="location.href='/teamtwo/study/study-add.do';">스터디 만들기</button>
                <button class="btn btn-soft-brand px-6" onclick="location.href='/teamtwo/study/mystudy-list.do';">내 스터디</button>
            </div>
        </div>

        <div class="content-card card-pad mb-10 bg-slate-50 border-slate-200 shadow-inner">
            <form method="GET" action="/teamtwo/study/study-list.do" class="flex flex-col md:flex-row gap-3">
                <select class="select select-bordered bg-white w-full md:w-44" name="status">
                    <option value="" ${empty map.status ? 'selected' : ''}>전체 상태</option> 
                    <option value="0" ${map.status == '0' ? 'selected' : ''}>모집중</option>
                    <option value="1" ${map.status == '1' ? 'selected' : ''}>모집완료</option>
                </select>
                <div class="join w-full">
                    <input type="text" placeholder="스터디 제목이나 내용으로 검색" 
                           class="input input-bordered bg-white w-full join-item" 
                           name="word" value="${map.word}" />
                    <button class="btn btn-brand join-item px-8">검색</button>
                </div>
            </form>
        </div>

        <div class="grid gap-6 md:grid-cols-2 lg:grid-cols-2">
            <c:forEach items="${list}" var="dto">
                <div class="content-card card-pad hover:border-brand-500 transition-all hover:shadow-md">
                    <div class="flex items-start justify-between gap-4 mb-4">
                        <h3 class="text-xl font-bold text-slate-800 line-clamp-1">${dto.name}</h3>
                        <span class="badge ${dto.status == '0' ? 'badge-primary' : 'badge-ghost'} badge-outline shrink-0">
                            ${dto.status == '0' ? '모집중' : '모집완료'}
                        </span>
                    </div>

                    <p class="text-slate-600 leading-relaxed mb-6 line-clamp-2 h-12">
                        ${dto.description}
                    </p>

                    <div class="grid grid-cols-3 gap-3 mb-6 text-center">
                        <div class="stats-box p-3">
                            <p class="text-[11px] font-bold text-slate-400 uppercase tracking-tighter">참여 현황</p>
                            <p class="mt-1 font-bold text-slate-700">${dto.headCount} / ${dto.capacity}</p>
                        </div>
                        <div class="stats-box p-3">
                            <p class="text-[11px] font-bold text-slate-400 uppercase tracking-tighter">등록 일정</p>
                            <p class="mt-1 font-bold text-slate-700">${dto.scheduleCount}개</p>
                        </div>
                        <div class="stats-box p-3">
                            <p class="text-[11px] font-bold text-slate-400 uppercase tracking-tighter">개설일</p>
                            <p class="mt-1 font-bold text-slate-700 text-sm">${dto.createDate}</p>
                        </div>
                    </div>

                    <div class="flex gap-2">
                        <button class="btn btn-soft-brand flex-1" onclick="location.href='/teamtwo/study/study-view.do?seq=${dto.seq}';">상세 보기</button>
                        <button class="btn btn-brand flex-1" onclick="location.href='/teamtwo/study/study-reg.do?seq=${dto.seq}';">참여 신청</button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <%-- <div class="flex justify-center mt-12 mb-20">
            <div class="join shadow-sm border border-slate-200">
                ${pagebar}
            </div>
        </div> --%>
        
        <div class="flex justify-center mt-12 mb-20">
            <div class="pagination-wrap">
                ${pagebar}
            </div>
        </div>
    </main>
</body>
</html>