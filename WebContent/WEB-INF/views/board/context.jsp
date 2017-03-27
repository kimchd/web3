<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.zerock.domain.*"%>
<%@include file = "../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
	.buttons form button{
		float : left;
		padding: 5px;
		margin: 10px;
	}
</style>
	<%
		Board vo = (Board) request.getAttribute("BoardVO");
	%>

	<div>${BoardVO.bn}</div>
	<div>${BoardVO.title}</div>
	<div>${BoardVO.contnd}</div>
	<div>${BoardVO.writer}</div>
	<div>${BoardVO.regdate}</div>
<div class = 'buttons'>
	<form method="post">
		<input type="hidden" name="bn" value="${BoardVO.bn}">
		<button>삭제.</button>
	</form>

	<form action="./update.do" method="get">
		<input type="hidden" name="bn" value="${BoardVO.bn}">
		<button>수정.</button>
	</form>
</div>
</body>
</html>