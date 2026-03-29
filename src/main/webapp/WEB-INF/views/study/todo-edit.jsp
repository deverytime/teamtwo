<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>deverytime - 할 일 수정</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body class="bg-slate-50 text-slate-800">
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    
    <main class="page-wrap">
        <div class="mb-10 text-center">
            <h1 class="section-title text-3xl font-black">할 일 정보 수정</h1>
            <p class="section-desc">작업 내용을 업데이트하거나 완료 상태를 변경하세요.</p>
        </div>

        <div class="max-w-2xl mx-auto">
            <div class="content-card card-pad shadow-lg border-brand-100">
                <form class="space-y-6" method="POST" action="/teamtwo/study/todo-edit.do?seq=${dto.seq}&scheduleSeq=${dto.studyScheduleSeq}">
                    <div>
                        <label class="form-label">할 일 제목</label>
                        <input type="text" name="title" class="input input-bordered w-full bg-white" 
                               value="${dto.title}" required />
                    </div>

                    <div>
                        <label class="form-label">상세 내용</label>
                        <textarea name="content" class="textarea textarea-bordered w-full bg-white min-h-32" 
                                  required>${dto.content}</textarea>
                    </div>

                    <div class="flex items-center gap-4 p-4 bg-brand-50 rounded-xl border border-brand-100">
                        <input type="checkbox" name="status" class="checkbox checkbox-primary" 
                               value="1" ${dto.status == 1 ? 'checked' : ''} />
                        <span class="text-sm font-bold text-brand-700">이 작업을 '완료' 상태로 변경합니다.</span>
                    </div>

                    <div class="flex justify-end gap-3 pt-6 border-t border-slate-100">
                        <button type="button" class="btn btn-ghost px-8" onclick="history.back();">취소</button>
                        <button type="submit" class="btn btn-brand px-12 font-bold">수정 완료</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>