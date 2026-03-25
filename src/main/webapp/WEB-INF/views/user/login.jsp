<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인 - deverytime</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
/* 로그인 폼 전용 너비 설정 (회원가입보다 슬림하게) */
.login-wrap {
	max-width: 400px;
	margin: 0 auto;
}
</style>
</head>
<body class="bg-slate-50 text-slate-800">
	<%@ include file="/WEB-INF/views/inc/header.jsp"%>

	<main class="page-wrap login-wrap py-20">

		<div class="mb-8 text-center">
			<h1 class="text-3xl font-bold mb-2">로그인</h1>
			<p class="text-slate-500">계정에 로그인하세요</p>
		</div>

		<div class="content-card card-pad bg-white shadow-xl rounded-2xl p-8">
			<form action="/teamtwo/user/login.do" method="POST">

				<div class="form-control w-full mb-4">
					<input type="text" name="id" id="id" placeholder="아이디 입력"
						value="${cookie.saveId.value}"
						class="input input-bordered w-full focus:input-primary" required
						autofocus />
				</div>

				<div class="form-control w-full mb-2">
					<input type="password" name="pw" id="pw" placeholder="비밀번호 입력"
						class="input input-bordered w-full focus:input-primary" required />
				</div>

				<div class="form-control mb-6">
					<label class="label cursor-pointer justify-start gap-2"> <input
						type="checkbox" name="saveId" value="y"
						class="checkbox checkbox-primary checkbox-sm"
						${not empty cookie.saveId ? 'checked' : ''} /> <span
						class="label-text text-slate-600">아이디 자동 저장</span>
					</label>
				</div>

				<div class="form-control mt-2">
					<button type="submit" class="btn btn-primary w-full text-lg">로그인</button>
				</div>
			</form>

			<div class="flex justify-center gap-4 mt-8 text-sm text-slate-500">
				<a href="/teamtwo/user/find-id.do"
					class="hover:text-primary hover:underline">아이디 찾기</a> <span
					class="text-slate-300">|</span> <a href="/teamtwo/user/findpw.do"
					class="hover:text-primary hover:underline">비밀번호 찾기</a> <span
					class="text-slate-300">|</span> <a href="/teamtwo/user/register.do"
					class="hover:text-primary font-bold hover:underline">가입하기</a>
			</div>
		</div>

	</main>

</body>
</html>