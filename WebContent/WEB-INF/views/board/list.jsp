<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<style>
.list ul h2 li {
	list-style: upper-roman;
}
</style>



	<Script>
		var str = '${param.msg}';
		if (str == 'success' || str == 'fail' || str == 'delete_Success') {
			alert(str);
			window.history.replaceState(null, null, "list");
		}
		
	</Script>
	<div class='list'>
		<ul>
			<h2>
				<C:forEach items="${list}" var="board">
					<a href="./context.do?pageNum=${pageNum.current}&bn=${board.bn}">
						<li>${board.bn}${" "}${board.title}${" "}${board.regdate}${" "}
							${board.writer}${" "}</li>
					</a>
				</C:forEach>
			</h2>
		</ul>
	</div>
	<div class='newgle'>
		<ul>
			<form  method="post">
				<button>새 글싸기</button>
			</form>
		</ul>
	</div>

	<style>
.pageList ul li {
	list-style: none;
	float: left;
	margin: 10px;
	border: 1px solid blue;
	width: 20px;
	text-align: center;
}

a {
	text-decoration: none;
}

.important {
	background-color: fuchsia;
}

</style>

	<div class='pageList'>
		<ul>
			<C:if test="${pageNum.prev}">
				<li><a href='list.do?page=${pageNum.start-1}&size=${pageNum.size}&display=${pageNum.viewCount}'> << </a></li>
			</C:if>

			<C:forEach begin="${pageNum.start}" end="${pageNum.end}" var="num">
				<li class='${num==pageNum.current?'important':''}'><a href='list.do?page=${num}&size=${pageNum.size}&display=${pageNum.viewCount}'>${num}</a></li>
			</C:forEach>

			<C:if test="${pageNum.next}">
				<li><a href='list.do?page=${pageNum.end+1}&size=${pageNum.size}&display=${pageNum.viewCount}'> >> </a></li>
			</C:if>
		</ul>
	</div>


</body>
</html>