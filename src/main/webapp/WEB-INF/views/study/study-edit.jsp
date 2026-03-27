<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>deverytime</title>
	<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
	<style>
		/* 현재 페이지 전용 CSS가 필요하면 여기에 작성 */
	</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp" %>
	
	<main class="page-wrap">
		
		<div class="mb-8">
			<h1 class="section-title">스터디 수정</h1>
			<p class="section-desc">스터디를 수정합니다.</p>
		</div>

	<div class="content-card card-pad">
	
	<!-- 스터디 수정 폼 -->
    <section class="demo-block">

      <div class="content-card card-pad max-w-3xl">

        <form class="space-y-5" method="POST" action="/teamtwo/study/study-edit.do?seq=${dto.seq}">
          <div>
            <label class="form-label">스터디 명</label>
            <input
              type="text"
              class="input input-bordered w-full bg-white"
              name="name"
              required="required"
              value="${dto.name}"
            />
          </div>
          <div>
            <label class="form-label">스터디 설명</label>
            <textarea
              class="textarea textarea-bordered w-full bg-white min-h-28"
              name="description"
              required="required"
            >${dto.description}</textarea>
          </div>
          <div>
          	<label class="form-label">최대 인원</label>
          	<input 
          		type="number" 
          		class="input input-bordered w-full br-white"
          		name="capacity"
          		min="5" max="20"
          		required="required"
          		value="${dto.capacity}"
         	/>
          </div>

          <div class="flex justify-end gap-2 pt-2">
            <button type="button" class="btn btn-ghost" onclick="location.href='/teamtwo/study/study-view.do?seq=${dto.seq}';">취소</button>
            <button type="submit" class="btn btn-brand">등록하기</button>
          </div>
        </form>
      </div>
    </section>
    
	</div>

	</main>
	
	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
	</script>
</body>
</html>