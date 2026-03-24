<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	table {
		border-collapse: collapse;
	}

	table tr td {
		border: 1px solid black;
		text-align: center;
		width: 200px;
	}
	
	#div1 {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 100px;
	}
	
	#div2 {
		display: flex;
		justify-content: center;
	}
	
</style>
<body>
	<h1>스터디 목록</h1>
	
	<hr>
	<!-- 스터디 목록 -->
	<div id="div1">
	<table>
		<tr>
			<td>스터디 명</td>&nbsp;<td>&nbsp;<td>
		</tr>
		<tr>
			<td>인원수</td><td>일정개수</td><td>생성일</td>
		</tr>
	</table>
	</div>
	
	<hr>
	<!-- 페이징 바 -->
	<div>1 2 3 4 5...</div>
	
	<!-- 스터디 검색창 -->
	<input type="text" name="search" value="스터디 검색"> <button type="button" onclick="location.href='/teamtwo/study/liststudy.do?search=${dto.search}';">검색</button>
	
	<!-- 스터디 등록 버튼 / 내 스터디 목록 버튼 -->
	<div id="div2">
		<button type="button" id="addStudyBtn" onclick="location.href='/teamtwo/study/addstudy.do?seq=${dto.seq}';">스터디 등록</button>
		<button type="button" id="myStudyBtn" onclick="location.href='/teamtwo/study/mystudy.do?seq=${dto.seq}';">내 스터디 목록</button>
	</div>
	
	
</body>
</html>