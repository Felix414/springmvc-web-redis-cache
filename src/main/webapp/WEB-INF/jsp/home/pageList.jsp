<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>page list.</title>
</head>
<body>
	<table>
	<thead><tr><td>id</td><td>标题</td><td>内容</td></tr></thead>
	<tbody>
	<c:forEach items="${articles}" var="item">  
		<tr>
			<td>${item.id }</td>
			<td>${item.title }</td>
			<td>${item.content }</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	<div style="padding:20px;">${pageStr}</div>
	
</body>
</html>