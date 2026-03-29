<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>deverytime - 스터디 등록</title>
    <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    
    <main class="page-wrap">
        <div class="mb-10 text-center">
            <h1 class="section-title text-3xl font-black">새 스터디 만들기</h1>
            <p class="section-desc">함께 공부할 목표와 인원을 설정해 보세요.</p>
        </div>

        <div class="max-w-2xl mx-auto">
            <div class="content-card card-pad shadow-lg border-brand-100">
                <form class="space-y-6" method="POST" action="/teamtwo/study/study-add.do">
                    <div>
                        <label class="form-label">스터디 이름</label>
                        <input type="text" name="name" class="input input-bordered w-full bg-white" 
                               placeholder="예: Java 백엔드 프로젝트 스터디" required />
                    </div>

                    <div class="grid md:grid-cols-2 gap-5">
                        <div>
                            <label class="form-label">모집 인원 (5~20명)</label>
                            <input type="number" name="capacity" class="input input-bordered w-full bg-white" 
                                   min="5" max="20" placeholder="인원 수 입력" required />
                        </div>
                        <div class="flex items-end pb-1 text-xs text-slate-400 italic">
                            * 최소 5명 이상이어야 등록 가능합니다.
                        </div>
                    </div>

                    <div>
                        <label class="form-label">스터디 상세 설명</label>
                        <textarea name="description" class="textarea textarea-bordered w-full bg-white min-h-40" 
                                  placeholder="스터디의 목표, 진행 방식, 장소 등을 자유롭게 설명해 주세요." required></textarea>
                    </div>

                    <div class="flex justify-end gap-3 pt-6 border-t border-slate-100">
                        <button type="button" class="btn btn-ghost px-8" onclick="location.href='/teamtwo/study/study-list.do';">취소</button>
                        <button type="submit" class="btn btn-brand px-12 font-bold">등록하기</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</body>
</html>