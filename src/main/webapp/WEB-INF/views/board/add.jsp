<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
/* 현재 페이지 전용 CSS가 필요하면 여기에 작성 */
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap">

		<div class="mb-8">
			<c:if test="${board==1}">
				<h1 class="section-title">자유게시판</h1>
			</c:if>
			<c:if test="${board==2}">
				<h1 class="section-title">질문 게시판</h1>
			</c:if>
			<c:if test="${board==3}">
				<h1 class="section-title">자료 공유 게시판</h1>
			</c:if>
			<c:if test="${board==4}">
				<h1 class="section-title">학습 공유 게시판</h1>
			</c:if>
			<hr>
			<br>
			<button class="btn btn-soft-brand"
				onclick="location.href='list.do?board=${board}';">돌아가기</button>
		</div>


		<form action="add.do" method="post" enctype="multipart/form-data">
			<div class="content-card card-pad">
				<section class="demo-block">


					<div class="content-card overflow-hidden">
						<div
							class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
							<div>
								<h3 class="text-xl font-bold"></h3>
								<p class="text-sm text-slate-500 mt-1"></p>
							</div>

							<div class="flexshirink-0">
								<select name="subject" class="select select-boarderd white">
									<c:forEach var="entry" items="${categoryMap}">
										<option value="${entry.key}">${entry.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="flex-1">
								<input type="text" name="title"
									class="input input-bordered w-full bg-white"
									placeholder="제목을 입력하세요." required />
							</div>
						</div>
						<div class="card-pad">
							<div>
								<textarea name="content"
									class="textarea textarea-bordered w-full bg-white resize-none"
									placeholder="내용을 입력하세요." rows="15" required></textarea>
							</div>
							<div>
								<div class="card-pad border-b border-slate-200">
									<div id="fileList" class="flex flex-col gap-2">
										<input type="file" name="file1"
											class="file-input file-input-bordered w-full bg-white">
									</div>

									<input type="hidden" name="board" value="${param.board}">
									<!-- 검색 조건 유지 (list에서 온 경우) -->
									<input type="hidden" name="category" value="${param.category}">
									<input type="hidden" name="searchType"
										value="${param.searchType}"> <input type="hidden"
										name="keyword" value="${param.keyword}"> <input
										type="hidden" name="page" value="${param.page}">
									<button type="button" class="btn btn-soft-brand mt-2"
										onclick="addFile()">+ 파일 추가</button>
								</div>
							</div>
						</div>

						<div class="flex justify-end mr-8 mb-5">
							<button type="submit" class="btn btn-brand">글쓰기</button>
						</div>
					</div>
				</section>
			</div>
			<input type="hidden" name="board" value="${board}">
		</form>

	</main>

	<script>
		// 현재 페이지 전용 JavaScript가 필요하면 여기에 작성
		let fileIndex = 2;

		function addFile() {
		  const fileList = document.getElementById('fileList');
		
		  if (fileList.children.length >= 5) {
		    alert('파일은 최대 5개까지 첨부할 수 있습니다.');
		    return;
		  }
		
		  const div = document.createElement('div');
		  div.className = 'flex gap-2 items-center';
		  div.innerHTML = `
		    <input type="file" name="file\${fileIndex}" class="file-input file-input-bordered w-full bg-white">
		    <button type="button" class="btn btn-error btn-sm" onclick="removeFile(this)">✕</button>
		  `;
		
		  fileList.appendChild(div);
		  fileIndex++;
		
		  if (fileList.children.length >= 5) {
		    document.querySelector('[onclick="addFile()"]').style.display = 'none';
		  }
		}
		
		function removeFile(btn) {
			  const fileList = document.getElementById('fileList');
			  
			  // 첫 번째 줄(기본 1개)은 삭제 안 되게 하려면 아래 조건 추가
			  // if (fileList.children.length <= 1) return;
			  
			  btn.parentElement.remove();
			  document.querySelector('[onclick="addFile()"]').style.display = '';
		}
	</script>
</body>
</html>