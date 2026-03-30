<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>문의/요청 등록</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp"%>
<style>
/* 현재 페이지 전용 CSS가 필요하면 여기에 작성 */
</style>
</head>
<body class="bg-slate-50 text-slate-800">
    <%@ include file="/WEB-INF/views/inc/header.jsp"%>

    <main class="page-wrap">

        <div class="mb-8">
            <h1 class="section-title">문의/요청 게시판</h1>
            <hr>
            <br>
            <button type="button" class="btn btn-soft-brand"
                onclick="location.href='/teamtwo/board/request/list.do';">돌아가기</button>
        </div>

        <form action="/teamtwo/board/request/add.do" method="post">
            <div class="content-card card-pad">
                <section class="demo-block">

                    <div class="content-card overflow-hidden">
                        <div
                            class="card-pad border-b border-slate-200 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">

                            <div class="flex-shrink-0">
                                <select name="subject" class="select select-bordered bg-white" required>
                                    <option value="0">문의</option>
                                    <option value="1">요청</option>
                                </select>
                            </div>

                            <div class="flex-1">
                                <input type="text" name="title"
                                    class="input input-bordered w-full bg-white"
                                    placeholder="제목을 입력하세요." required />
                            </div>
                        </div>

                        <div class="card-pad">
                            <textarea name="content"
                                class="textarea textarea-bordered w-full bg-white resize-none"
                                placeholder="내용을 입력하세요." rows="15" required></textarea>
                        </div>

                        <div class="flex justify-end mr-8 mb-5 gap-2">
                            <button type="button" class="btn btn-outline"
                                onclick="location.href='/teamtwo/board/request/list.do';">취소</button>
                            <button type="submit" class="btn btn-brand">등록하기</button>
                        </div>
                    </div>

                </section>
            </div>
        </form>

    </main>
</body>
</html>