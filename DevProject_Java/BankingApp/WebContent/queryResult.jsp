<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
<body>
	<h2> Query Result </h2>
	<table>
		<tr>
			<td><b>Customer ID :</b></td>
			<td><%=session.getAttribute("CustId") %></td>
		</tr>
		<tr>
			<td><b>Customer Name :</b></td>
			<td><%=session.getAttribute("CustName") %></td>
		</tr>
		<tr>
			<td><b>Default Account :</b></td>
			<td><%=session.getAttribute("DEfAcctId") %></td>
		</tr>
		<tr>
			<td><b>Effective Time :</b></td>
			<td><%=session.getAttribute("EffTime").toString() %></td>
		</tr>
		<tr>
			<td><b>Expire Time :</b></td>
			<td> <%=session.getAttribute("ExpTime") %></td>
		</tr>
	</table>
	<!--  
	<br><label> <b> Customer ID : </b> <%=session.getAttribute("CustId") %></label>
	<br><label> <b> Customer Name :</b> <%=session.getAttribute("CustName") %></label>
	<br><label> <b> Default Account :</b> <%=session.getAttribute("DEfAcctId") %></label>
	<br><label> <b> Effective Time :</b> <%=session.getAttribute("EffTime").toString() %></label>
	<br><label> <b> Expire Time :</b> <%=session.getAttribute("ExpTime") %></label>
	-->
</body>
</html>