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
        <h1 class="section-title text-3xl">${empty dto ? '스터디 일정 등록' : '스터디 일정 수정'}</h1>
        <p class="section-desc">팀원들과 공유할 학습 계획을 설정하세요.</p>
    </div>

    <div class="max-w-3xl mx-auto">
        <div class="content-card card-pad shadow-lg">
            <form class="space-y-6" method="POST" action="/teamtwo/study/${empty dto ? 'studyschedule-add.do?seq='.concat(seq) : 'studyschedule-edit.do?seq='.concat(dto.seq)}">
                <div>
                    <label class="form-label">일정 제목</label>
                    <input type="text" name="title" class="input input-bordered w-full bg-white" value="${dto.title}" required />
                </div>
                <div>
                    <label class="form-label">일정 상세 설명</label>
                    <textarea name="content" class="textarea textarea-bordered w-full bg-white min-h-32" required>${dto.content}</textarea>
                </div>
                <div class="grid md:grid-cols-2 gap-6">
                    <div>
                        <label class="form-label">학습 시작일</label>
                        <input type="date" name="startDate" id="start" class="input input-bordered w-full" value="${dto.startDate}" required />
                    </div>
                    <div>
                        <label class="form-label">학습 종료일</label>
                        <input type="date" name="endDate" id="end" class="input input-bordered w-full" value="${dto.endDate}" required />
                    </div>
                </div>
                <div class="flex justify-end gap-3 pt-6 border-t">
                    <button type="button" class="btn btn-ghost px-8" onclick="history.back();">취소</button>
                    <button type="submit" class="btn btn-brand px-12">저장하기</button>
                </div>
            </form>
        </div>
    </div>
</main>
<script>
    const s = document.getElementById('start'), e = document.getElementById('end');
    s.addEventListener('change', () => e.min = s.value);
    e.addEventListener('change', () => s.max = e.value);
</script>
</body>
</html>