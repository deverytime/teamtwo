<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>deverytime - 할 일 등록</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body class="bg-slate-50 text-slate-800">
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    
    <main class="page-wrap">
        <div class="mb-10 text-center">
            <h1 class="section-title text-3xl font-black">새로운 할 일 추가</h1>
            <p class="section-desc">일정을 완수하기 위한 세부 과제를 등록하세요.</p>
        </div>

        <div class="max-w-2xl mx-auto">
            <div class="content-card card-pad shadow-lg border-brand-100">
                <form class="space-y-6" method="POST" action="/teamtwo/study/todo-add.do?seq=${seq}">
                    <div>
                        <label class="form-label">할 일 제목</label>
                        <input type="text" name="title" class="input input-bordered w-full bg-white" 
                               placeholder="예: API 명세서 작성하기" required />
                    </div>

                    <div>
                        <label class="form-label">상세 내용</label>
                        <textarea name="content" class="textarea textarea-bordered w-full bg-white min-h-32" 
                                  placeholder="진행할 작업의 세부 내용을 적어주세요." required></textarea>
                    </div>

                    <div class="flex justify-end gap-3 pt-6 border-t border-slate-100">
                        <button type="button" class="btn btn-ghost px-8" onclick="history.back();">취소</button>
                        <button type="submit" class="btn btn-brand px-12 font-bold">등록하기</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>