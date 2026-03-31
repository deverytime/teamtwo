<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<%-- 추천 메시지 처리// 화면이 모두 로드된 후 실행되어야 같은 페이지에서 열린거같은 느낌을 낼 수 있음 --%>
	<c:if test="${param.msg == 'login'}">
		<script>
			window.onload = function() {
				alert('로그인한 사용자만 추천할 수 있습니다!');
			}
		</script>
	</c:if>
	<c:if test="${param.msg == 'already'}">
		<script>
			window.onload = function() {
				alert('이미 추천한 글입니다!');
			}
		</script>
	</c:if>
	<c:if test="${param.msg == 'reportalready'}">
		<script>
			window.onload = function() {
				alert('이미 신고한 글입니다!');
			}
		</script>
	</c:if>


	<main class="page-wrap">

		<!-- 게시판 이름 -->
		<div class="mb-4">
			<c:if test="${dto.boardType==1}">
				<h1 class="section-title">자유 게시판</h1>
			</c:if>
			<c:if test="${dto.boardType==2}">
				<h1 class="section-title">질문 게시판</h1>
			</c:if>
			<c:if test="${dto.boardType==3}">
				<h1 class="section-title">자료 공유 게시판</h1>
			</c:if>
			<c:if test="${dto.boardType==4}">
				<h1 class="section-title">학습 공유 게시판</h1>
			</c:if>
		</div>

		<div class="content-card overflow-hidden mb-4">

			<!-- ① 카테고리 + 제목 + 작성자/조회수/날짜 + 수정/삭제 -->
			<div class="card-pad border-b border-slate-200">

				<!-- 카테고리 뱃지 -->
				<span class="text-xs bg-slate-100 text-slate-500 px-2 py-1 rounded">${dto.category}</span>

				<!-- 제목 + 수정/삭제 버튼 (같은 줄) -->
				<div class="flex items-center justify-between mt-2 mb-2">
					<h2 class="text-2xl font-bold">${dto.title}</h2>

					<!-- ② 수정/삭제 버튼 (본인 글 조건 나중에 추가) -->
					<c:if test="${dto.id == auth}">

						<div class="flex gap-2 flex-shrink-0">
							<button class="btn btn-soft-brand btn-sm"
								onclick="location.href='edit.do?seq=${dto.seq}&board=${dto.boardType}&category=${param.category}&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page}'">수정</button>
							<button class="btn btn-error btn-sm"
								onclick="location.href='del.do?seq=${dto.seq}&board=${dto.boardType}&category=${param.category}&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page}'">삭제</button>
						</div>
					</c:if>
				</div>

				<!-- 작성자 / 조회수 / 날짜 -->
				<div class="flex gap-4 text-sm text-slate-500">
					<span>글쓴이: ${dto.nickname}</span> <span>조회수:
						${dto.readCount}</span> <span>날짜: ${dto.regDate}</span>
				</div>
			</div>

			<!-- 본문 내용 -->
			<div class="card-pad min-h-64">
				<p class="whitespace-pre-wrap text-slate-800">${dto.content}</p>
			</div>

			<!-- 하단 3분할: ③첨부파일 / ④추천·신고 / ⑤공유 -->
			<div
				class="border-t border-slate-200 grid grid-cols-3 divide-x divide-slate-200">

				<!-- ③ 첨부파일 -->
				<div class="card-pad">
					<p class="text-sm font-semibold text-slate-600 mb-1">첨부파일</p>

					<c:forEach var="file" items="${fileList}">
						<a href="download.do?file=${file.seq}"
							class="text-blue-500 hover:underline text-sm block">
							${file.originName} </a>
					</c:forEach>


				</div>

				<!-- ④ 추천 / 신고 -->
				<div class="card-pad flex gap-2 items-center justify-center">
					<button class="btn btn-soft-brand btn-sm"
						onclick="location.href='recommend.do?seq=${dto.seq}&board=${dto.boardType}&category=${param.category }&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page }'">추천
						${dto.recommend}</button>
					<button class="btn btn-error btn-sm"
						onclick="report_modal.showModal()">신고</button>
					<!-- 모달 -->
					<dialog id="report_modal" class="modal">
					<div class="modal-box">
						<h3 class="font-bold text-lg mb-4">신고 사유 선택</h3>
						<form method="get" action="report.do">
							<input type="hidden" name="seq" value="${dto.seq}"> <input
								type="hidden" name="board" value="${dto.boardType}"> <select
								name="reasonSeq" class="select select-bordered w-full mb-4">
								<option value="1">스팸/광고</option>
								<option value="2">욕설/혐오</option>
								<option value="3">음란물</option>
								<option value="4">개인정보 노출</option>
								<option value="5">기타</option>
							</select>
							<div class="modal-action">
								<input type="hidden" name="category" value="${param.category }">
								<input type="hidden" name="searchType"
									value="${param.searchType }"> <input type="hidden"
									name="keyword" value="${param.keyword }"> <input
									type="hidden" name="page" value="${param.page }">
								<button type="submit" class="btn btn-error">신고</button>
								<button type="button" class="btn" onclick="report_modal.close()">취소</button>
							</div>
						</form>
					</div>
					</dialog>
				</div>

				<!-- ⑤ 공유 -->
				<div class="card-pad flex items-center justify-center">
					<button class="btn btn-soft-brand btn-sm"
						onclick="shareCleanUrl();">공유</button>
				</div>

			</div>
		</div>

		<!-- 댓글 영역 (입력폼 상단) -->
		<div class="content-card card-pad mb-4">

			<!-- ① 댓글 입력 폼 (상단 고정) -->
			<form method="post" action="/teamtwo/comment/add.do">
				<input type="hidden" name="seq" value="${dto.seq}"> <input
					type="hidden" name="board" value="${dto.boardType}"> <input
					type="hidden" name="category" value="${param.category}"> <input
					type="hidden" name="searchType" value="${param.searchType}">
				<input type="hidden" name="keyword" value="${param.keyword}">
				<input type="hidden" name="page" value="${param.page }">
				<div class="border-b pb-4 mb-6 border-slate-200">
					<div class="flex items-start gap-3 opacity-60">
						<div
							class="w-10 h-10 bg-slate-200 rounded-full flex items-center justify-center">
							<span class="text-xs font-semibold">U</span>
						</div>
						<c:if test="${empty auth }">
							<div class="flex-1">
								<textarea
									class="textarea textarea-bordered w-full h-20 resize-none"
									placeholder="로그인 후 댓글 작성 가능합니다." disabled></textarea>
								<div class="flex justify-end mt-2">
									<button class="btn btn-brand btn-sm px-6" disabled>댓글
										등록</button>
								</div>
							</div>
						</c:if>
						<c:if test="${not empty auth }">
							<div class="flex-1">
								<textarea
									class="textarea textarea-bordered w-full h-20 resize-none"
									name="content" placeholder="댓글을 작성하세요..." required></textarea>
								<div class="flex justify-end mt-2">
									<button class="btn btn-brand btn-sm px-6">댓글 등록</button>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</form>

			<!-- ② 댓글 통계 -->
			<div
				class="flex justify-between items-center mb-4 pb-2 border-b border-slate-100">
				<h3 class="text-lg font-semibold">
					댓글 <span class="text-brand">${dto.comments.size()}</span>
				</h3>
				<c:if test="${empty auth }">
					<span class="text-xs text-slate-500">로그인 후 댓글 작성 가능</span>
				</c:if>
			</div>

			<!-- ③ 댓글 리스트 (하단 스크롤) -->
			<div class="space-y-3 max-h-80 overflow-y-auto">

				<c:forEach var="entry" items="${dto.comments}">

					<div class="comment-item border-l-4 border-brand pl-4 pb-3">
						<div class="flex justify-between items-start mb-1">
							<div class="flex items-center gap-2">
								<span class="font-semibold text-sm">${entry.nickname} </span> <span
									class="text-xs text-slate-500">${entry.regDate}</span>
							</div>
							<!-- 댓글삭제 -->
							<c:if test="${entry.id == auth}">
								<div class="text-xs">
									<a href="#"
										onclick="deleteComment(${entry.seq}, ${dto.seq},${dto.boardType},'${param.category}', '${param.searchType}', '${param.keyword}', '${param.page}')"
										class="text-error hover:underline ml-2">삭제</a>
								</div>
							</c:if>
						</div>
						<p class="text-sm leading-relaxed">${entry.content}</p>
					</div>
				</c:forEach>

			</div>
		</div>



		<!-- ⑥ 이전글 / 다음글 -->
		<div class="flex justify-between">
			<c:if test="${not empty dto.prevSeq}">
				<button class="btn btn-soft-brand"
					onclick="location.href='view.do?seq=${dto.prevSeq}&board=${dto.boardType}&category=${param.category}&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page }'">이전
					글 보기</button>
			</c:if>
			<c:if test="${empty dto.prevSeq }">
				<button class="btn">이전글이 없습니다</button>
			</c:if>
			<c:if test="${not empty param.board}">
			<button class="btn btn-soft-brand"
				onclick="location.href='list.do?board=${dto.boardType}&category=${param.category}&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page }'">돌아가기 ${board}</button>
			</c:if>
			<c:if test="${empty param.board}">
			<button class="btn btn-soft-brand"
				onclick="location.href='/teamtwo/board/trendingboard/list.do'">돌아가기</button>
			</c:if>
			<c:if test="${not empty dto.nextSeq}">
				<button class="btn btn-soft-brand"
					onclick="location.href='view.do?seq=${dto.nextSeq}&board=${dto.boardType}&category=${param.category}&searchType=${param.searchType}&keyword=${param.keyword}&page=${param.page }'">다음
					글 보기</button>
			</c:if>
			<c:if test="${empty dto.nextSeq }">
				<button class="btn">다음글이 없습니다</button>
			</c:if>
		</div>
	</main>
	<script>
	function deleteComment(seq, postSeq, boardType, category, searchType, keyword, page) {
	    if(confirm('댓글을 삭제하시겠습니까?')) {
	        location.href = `/teamtwo/comment/del.do?seq=` + seq + 
	                       `&postSeq=` + postSeq +
	                       `&board=` + boardType + 
	                       `&category=` + category + 
	                       `&searchType=` + searchType + 
	                       `&keyword=` + keyword + 
	                       `&page=` + page;
	    }
	}
	
	function shareCleanUrl() {
	    // 현재 URL에서 msg 파라미터만 제거
	    const url = new URL(window.location.href);
	    url.searchParams.delete('msg');
	    
	    navigator.clipboard.writeText(url.href).then(() => {
	        alert('링크가 복사되었습니다!');
	    }).catch(() => {
	        // 클립보드 실패시 fallback
	        prompt('링크를 복사하세요:', url.href);
	    });
	}

	</script>
</body>

</html>