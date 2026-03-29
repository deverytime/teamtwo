<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deverytime - 스터디 상세</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <main class="page-wrap">
        <div class="mb-8 flex justify-between items-end">
            <div>
                <h1 class="section-title">스터디 정보</h1>
                <p class="section-desc">스터디 상세 내용과 멤버를 관리합니다.</p>
            </div>
            <span class="badge badge-lg ${dto.status == '0' ? 'badge-primary' : 'badge-ghost'} badge-outline h-10 px-6">
                ${dto.status == '0' ? '모집중' : '모집완료'}
            </span>
        </div>

        <div class="content-card card-pad mb-10 border-brand-100">
            <h3 class="text-2xl font-bold mb-4">${dto.name}</h3>
            <p class="text-slate-600 leading-relaxed mb-8 text-lg">${dto.description}</p>
            
            <div class="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-8">
                <div class="stats-box flex flex-col items-center justify-center py-6">
                    <p class="text-slate-500 text-sm mb-1">참여 인원</p>
                    <p class="text-3xl font-black text-brand-600">${dto.headCount} / ${dto.capacity}</p>
                </div>
                <div class="stats-box flex flex-col items-center justify-center py-6">
                    <p class="text-slate-500 text-sm mb-1">등록된 일정</p>
                    <p class="text-3xl font-black text-slate-700">${dto.scheduleCount}개</p>
                </div>
                <div class="stats-box flex flex-col items-center justify-center py-6">
                    <p class="text-slate-500 text-sm mb-1">스터디 생성일</p>
                    <p class="text-xl font-bold text-slate-700 mt-2">${dto.createDate}</p>
                </div>
            </div>

            <div class="flex flex-wrap gap-2 pt-6 border-t">
                <button class="btn btn-brand px-8" onclick="location.href='/teamtwo/study/studyschedule-list.do?seq=${dto.seq}';">일정 보기</button>
                <c:if test="${isManager}">
                    <button class="btn btn-soft-brand" onclick="location.href='/teamtwo/study/study-edit.do?seq=${dto.seq}';">정보 수정</button>
                    <button class="btn btn-ghost text-error" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='/teamtwo/study/study-del.do?seq=${dto.seq}';">삭제 하기</button>
                </c:if>
                <c:if test="${isMember && !isManager}">
                    <button class="btn btn-ghost text-error" onclick="location.href='/teamtwo/study/study-unreg.do?seq=${dto.seq}';">탈퇴 하기</button>
                </c:if>
                <c:if test="${!isMember}">
                    <button class="btn btn-brand btn-outline" onclick="location.href='/teamtwo/study/study-reg.do?seq=${dto.seq}';">참여 하기</button>
                </c:if>
            </div>
        </div>

        <section class="demo-block">
            <div class="flex items-center gap-2 mb-4">
                <h2 class="section-title mb-0">스터디 멤버</h2>
                <span class="badge badge-sm badge-ghost font-normal">Total ${totalCount}</span>
            </div>
            <div class="content-card overflow-hidden">
                <table class="table w-full">
                    <thead class="bg-slate-50 text-slate-500">
                        <tr>
                            <th class="w-24">ID</th>
                            <th>이름 / 이메일</th>
                            <th class="text-center">권한 관리</th>
                            <th class="text-right pr-6">참여일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${mlist}" var="memberDto">
                            <tr class="hover:bg-slate-50/50">
                                <td class="text-slate-400 font-mono text-sm">${memberDto.id}</td>
                                <td>
                                    <div class="font-bold">${memberDto.name}</div>
                                    <div class="text-xs text-slate-400">${memberDto.email}</div>
                                </td>
                                <td class="text-center">
                                	<c:if test="${memberDto.seq == managerSeq}">
                                   		 <span class="badge badge-lg badge-primary badge-outline h-6 px-2">
							                 Manager
							             </span>
                                   	</c:if>
                                    <c:if test="${isManager}">
                                    	<c:if test="${memberDto.seq != managerSeq}">
		                                    <div class="join">
		                                        <button class="btn btn-xs btn-outline btn-warning join-item" 
		                                        	onclick="location.href='/teamtwo/study/studyManager-delegate.do?seq=${dto.seq}&mseq=${memberDto.seq}';">위임</button>
		                                        <button class="btn btn-xs btn-outline btn-error join-item" 
		                                        	onclick="location.href='/teamtwo/study/study-deport.do?seq=${dto.seq}&mseq=${memberDto.seq}';">추방</button>
		                                    </div>
	                                    </c:if>
                                    </c:if>
                                </td>
                                <td class="text-right text-slate-500 text-sm pr-6">${memberDto.regdate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="flex justify-center p-6 border-t bg-slate-50/30">${pagebar}</div>
            </div>
        </section>
    </main>
</body>
</html>