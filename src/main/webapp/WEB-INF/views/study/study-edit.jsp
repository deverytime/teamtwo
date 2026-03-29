<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>deverytime - 스터디 수정</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    
    <main class="page-wrap">
        <div class="mb-10 text-center">
            <h1 class="section-title text-3xl font-black">스터디 정보 수정</h1>
            <p class="section-desc">변경된 내용을 반영하여 스터디 정보를 업데이트하세요.</p>
        </div>

        <div class="max-w-2xl mx-auto">
            <div class="content-card card-pad shadow-lg border-brand-100">
                <form class="space-y-6" method="POST" action="/teamtwo/study/study-edit.do?seq=${dto.seq}">
                    <div>
                        <label class="form-label">스터디 이름</label>
                        <input type="text" name="name" class="input input-bordered w-full bg-white" 
                               value="${dto.name}" required />
                    </div>

                    <div class="grid md:grid-cols-2 gap-5">
                        <div>
                            <label class="form-label">최대 모집 인원</label>
                            <input type="number" name="capacity" class="input input-bordered w-full bg-white" 
                                   min="5" max="20" value="${dto.capacity}" required />
                        </div>
                        <div class="flex items-end pb-1 text-xs text-slate-400 italic font-medium">
                            * 기존 인원보다 적게 수정할 경우 주의하세요.
                        </div>
                    </div>

                    <div>
                        <label class="form-label">스터디 상세 설명</label>
                        <textarea name="description" class="textarea textarea-bordered w-full bg-white min-h-40" 
                                  required>${dto.description}</textarea>
                    </div>

                    <div class="flex justify-end gap-3 pt-6 border-t border-slate-100">
                        <button type="button" class="btn btn-ghost px-8" 
                                onclick="location.href='/teamtwo/study/study-view.do?seq=${dto.seq}';">취소</button>
                        <button type="submit" class="btn btn-brand px-12 font-bold">수정 완료</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>