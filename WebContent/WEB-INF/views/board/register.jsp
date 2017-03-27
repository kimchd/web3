<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file = "../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form  method ="post">
		<div>
				<label> Title</label>
				<input type="text" name="title" value= "sample 한글">		
		</div>	
		<div>
				<label> Content</label>
				<textarea rows='5' cols='30' name="contnd">sample 내용</textarea>   				
		</div>
		<div>
				<label> Writer</label>
				<input type="text" name="writer" value= "user06">
		</div>
		 <button>Register</button>
	</form>
</body>
</html>