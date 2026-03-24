<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>스터디 추가</h1>

	<form method="POST" action="/teamtwo/study/study-add.do">
	
		<div><input type="text" value="스터디 명" name="name"></div>
		<div><input type="text" value="스터디 설명" name="desc"></div>
		<div><input type="text" value="최대 인원" name="capacity"></div>
		
		<button type="button" onclick="location.href='/teamtwo/study/study-list?';">돌아가기</button>
		<button type="submit">스터디 등록</button>
	</form>

</body>
</html>