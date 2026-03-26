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
				<h1 class="section-title">자유게시판</h1>
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
								onclick="location.href='edit.do?seq=${dto.seq}&board=${dto.boardType}'">수정</button>
							<button class="btn btn-error btn-sm"
								onclick="location.href='delete.do?seq=${dto.seq}&board=${dto.boardType}'">삭제</button>
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
					<%-- 파일 기능 연결 후 해제
        <c:forEach var="file" items="${fileList}">
          <a href="download.do?file=${file}" class="text-blue-500 hover:underline text-sm block">${file}</a>
        </c:forEach>
        --%>
					<p class="text-sm text-slate-400">없음</p>
				</div>

				<!-- ④ 추천 / 신고 -->
				<div class="card-pad flex gap-2 items-center justify-center">
					<button class="btn btn-soft-brand btn-sm"
						onclick="location.href='recommend.do?seq=${dto.seq}&board=${dto.boardType}'">추천
						${dto.recommend}</button>
					<button class="btn btn-error btn-sm"
						onclick="report_modal.showModal()">신고</button>
					<!-- 모달 -->
					<dialog id="report_modal" class="modal">
					<div class="modal-box">
						<h3 class="font-bold text-lg mb-4">신고 사유 선택</h3>
						<form method="get" action="report.do">
							<input type="hidden" name="seq" value="${dto.seq}">
							<input type="hidden" name="board" value="${dto.boardType}"> 
							<select
								name="reasonSeq" class="select select-bordered w-full mb-4">
								<option value="1">스팸/광고</option>
								<option value="2">욕설/혐오</option>
								<option value="3">음란물</option>
								<option value="4">개인정보 노출</option>
								<option value="5">기타</option>
							</select>
							<div class="modal-action">
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
						onclick="navigator.clipboard.writeText(window.location.href); alert('링크가 복사되었습니다.')">공유</button>
				</div>

			</div>
		</div>

		<!-- 댓글 영역 (BOARD-06) -->
		<div class="content-card card-pad mb-4">
			<p class="text-slate-400 text-center">댓글 영역 (BOARD-06)</p>
		</div>

		<!-- ⑥ 이전글 / 다음글 -->
		<div class="flex justify-between">
			<c:if test="${not empty dto.prevSeq}">
				<button class="btn btn-soft-brand"
					onclick="location.href='view.do?seq=${dto.prevSeq}&board=${dto.boardType}'">이전
					글 보기</button>
			</c:if>
			<c:if test="${empty dto.prevSeq }">
				<button class="btn">이전글이 없습니다</button>
			</c:if>
			<button class="btn btn-soft-brand"
				onclick="location.href='list.do?board=${dto.boardType}'">돌아가기</button>
			<c:if test="${not empty dto.nextSeq}">
				<button class="btn btn-soft-brand"
					onclick="location.href='view.do?seq=${dto.nextSeq}&board=${dto.boardType}'">다음
					글 보기</button>
			</c:if>
			<c:if test="${empty dto.nextSeq }">
				<button class="btn">다음글이 없습니다</button>
			</c:if>
		</div>

	</main>
</body>
</html>