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
			<c:if test="${board==1}">
				<h1 class="section-title">자유게시판</h1>
				<p class="section-desc">자유롭게 소통하는 공간입니다.</p>
			</c:if>
		</div>

		<div class="content-card card-pad">
			<section class="demo-block">
		     
		
		      <div class="content-card overflow-hidden">
		        <div class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
		          <div>
		            <h3 class="text-xl font-bold"></h3>
		            <p class="text-sm text-slate-500 mt-1"></p>
		          </div>
		
		          <div class="flex flex-col sm:flex-row gap-2">
		            <select class="select select-bordered bg-white">
		              <option>제목</option>
		              <option>내용</option>
		              <option>작성자</option>
		              <option>제목&내용</option>
		            </select>
		            <input
		              type="text"
		              placeholder="검색어 입력"
		              class="input input-bordered bg-white"
		            />
		            <button class="btn btn-brand">검색</button>
		          </div>
		        </div>
		
		        <div class="overflow-x-auto">
		          <table class="table">
		            <thead>
		              <tr>
		                <th>ID</th>
		                <th>이름</th>
		                <th>이메일</th>
		                <th>상태</th>
		                <th>가입일</th>
		              </tr>
		            </thead>
		            <tbody>
		              <tr>
		                <td>101</td>
		                <td>김민수</td>
		                <td>minsu@example.com</td>
		                <td><span class="badge badge-success badge-outline">활성</span></td>
		                <td>2026-03-10</td>
		              </tr>
		              <tr>
		                <td>102</td>
		                <td>이지은</td>
		                <td>jieun@example.com</td>
		                <td><span class="badge badge-warning badge-outline">휴면</span></td>
		                <td>2026-03-11</td>
		              </tr>
		              <tr>
		                <td>103</td>
		                <td>박준호</td>
		                <td>junho@example.com</td>
		                <td><span class="badge badge-error badge-outline">정지</span></td>
		                <td>2026-03-12</td>
		              </tr>
		            </tbody>
		          </table>
		        </div>
		
		        <div class="card-pad border-t border-slate-200 flex justify-between items-center">
		          <p class="text-sm text-slate-500">총 3명</p>
		          <div class="join">
		            <button class="join-item btn btn-sm">1</button>
		            <button class="join-item btn btn-sm btn-active">2</button>
		            <button class="join-item btn btn-sm">3</button>
		          </div>
		        </div>
		      </div>
   		 </section>
		</div>

	</main>
	
	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
	</script>
</body>
</html>