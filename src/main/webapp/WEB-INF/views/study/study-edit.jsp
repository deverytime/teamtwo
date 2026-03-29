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
        <h1 class="section-title text-3xl">스터디 일정 수정</h1>
        <p class="section-desc">팀원들과 공유할 학습 계획을 설정하세요.</p>
    </div>

    <div class="max-w-3xl mx-auto">
        <div class="content-card card-pad shadow-lg">
            <form class="space-y-6" method="POST" action="/teamtwo/study/study-edit.do?seq=${dto.seq}">
                <div>
                    <label class="form-label">일정 제목</label>
                    <input type="text" name="name" class="input input-bordered w-full bg-white" value="${dto.name}" required />
                </div>
                <div>
                    <label class="form-label">일정 상세 설명</label>
                    <textarea name="description" class="textarea textarea-bordered w-full bg-white min-h-32" required>${dto.description}</textarea>
                </div>
                <div class="grid md:grid-cols-2 gap-6">
                  <div>
		          	<label class="form-label">최대 인원</label>
		          	<input 
		          		type="number" 
		          		class="input input-bordered w-full br-white"
		          		name="capacity"
		          		min="${totalCountM}"
		          		max="20"
		          		required="required"
		          		value="${dto.capacity}"
		         	/>
		          </div>
                </div>
                <div class="flex items-center gap-4 p-4 bg-brand-50 rounded-xl border border-brand-100">
                        <input type="checkbox" name="status" class="checkbox checkbox-primary" 
                               value="1" ${dto.status == 1 ? 'checked' : ''} />
                        <span class="text-sm font-bold text-brand-700">체크시 모집완료 상태로 변경합니다.</span>
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

</script>
</body>
</html>