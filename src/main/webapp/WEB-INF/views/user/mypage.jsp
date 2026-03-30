<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
.mypage-wrap { max-width: 600px; margin: 0 auto; }
.info-row { display: flex; align-items: center; margin-bottom: 1rem; }
.info-label { width: 100px; font-weight: bold; color: #475569; shrink-0; }
.info-value { flex: 1; background-color: #f8fafc; padding: 0.75rem 1rem; border-radius: 0.5rem; border: 1px solid #e2e8f0; color: #1e293b; overflow: hidden; }
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap mypage-wrap py-12">
		<div class="mb-8 text-center !border-b border-slate-200 pb-6">
			<h1 class="text-3xl font-bold mb-2">마이페이지</h1>
			<p class="text-slate-500">내 정보를 관리하세요</p>
		</div>

		<div class="content-card bg-white shadow-xl rounded-2xl p-8">

			<div class="flex flex-col md:flex-row gap-8 mb-8">
				
				<div class="flex-1 min-w-0">
					<div class="info-row">
						<div class="info-label">이름</div>
						<div class="info-value">${authDto.name}</div>
					</div>
					
					<div class="info-row">
						<div class="info-label">아이디</div>
						<div class="info-value">${authDto.id}</div>
					</div>
					
					<div class="info-row">
						<div class="info-label">비밀번호</div>
						<div class="info-value flex justify-between items-center gap-2">
							<span>********</span>
							<button type="button" class="btn btn-sm btn-outline btn-primary shrink-0" onclick="location.href='/teamtwo/user/pw-edit.do';">비밀번호 변경</button>
						</div>
					</div>

					<div class="info-row">
						<div class="info-label">닉네임</div>
						<div class="info-value truncate">${authDto.nickname}</div>
					</div>

					<div class="info-row">
						<div class="info-label">이메일 주소</div>
						<div class="info-value flex justify-between items-center gap-2">
							<span class="truncate flex-1">${authDto.email}</span>
							<c:choose>
								<c:when test="${authDto.twoFactor == 1}">
									<button type="button" class="btn btn-sm btn-outline btn-error shrink-0" onclick="disableTwoFactor();">2차 인증 비활성화</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-sm btn-outline btn-primary shrink-0" onclick="location.href='/teamtwo/user/twofactor-setup.do';">2차 인증 활성화</button>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>

				<div class="flex flex-col items-center justify-start shrink-0">
					<div class="avatar mb-4">
						<div class="w-32 rounded-xl ring ring-primary ring-offset-base-100 ring-offset-2">
							<img src="/teamtwo/asset/pic/${authDto.pic}" alt="프로필 사진" onerror="this.src='/teamtwo/asset/pic/pic.png'" />
						</div>
					</div>
					<span class="text-sm text-slate-500">프로필 사진</span>
				</div>
			</div>

			<div class="flex justify-center gap-4 mt-8 pt-6 border-t border-slate-100">
				<button type="button" class="btn btn-primary w-40" onclick="location.href='/teamtwo/user/pw-check.do?target=edit';">회원정보 수정</button>
				<button type="button" class="btn btn-error btn-outline w-40" onclick="location.href='/teamtwo/user/pw-check.do?target=unregister';">탈퇴하기</button>
			</div>

		</div>
	</main>

	<script>
		function disableTwoFactor() {
			if(confirm('2차 인증을 비활성화하시겠습니까?\n계정 보안이 취약해질 수 있습니다.')) {
				location.href = '/teamtwo/user/twofactor-disable.do'; 
			}
		}
	</script>
</body>
</html>