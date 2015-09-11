<%@page import="java.io.IOException"%>
<%@page import="java.net.HttpRetryException"%>
<%@page import="javax.websocket.Session"%>
<%@page import="models.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
</head>
<body>
<%!
	// check whether a login exists or not
	public boolean checkLogin(HttpSession session, HttpServletResponse response){
		if( session.getAttribute("userName") == null || session.getAttribute("pswd") == null ){
			try{
				response.sendRedirect("login.html");
				return false;
			}
			catch(IOException e){
				return false;
			}
		}
		return true;
		
	}
%>
<%	
	// check login exits
	this.checkLogin(session, response);
%>
	<h1> Welcome Admin!</h1>
	<h3> Available Operations</h3>
    <ul>
        <li>Query Customer</li> 
        <li>Create Customer</li>
    </ul>
    <form action="doLogout" method="post">
    	<a title="Click to logout!"><input id="sbmt" type="submit" value="Logout"/></a>
    </form>
    
    <h1> Query Customer</h1>
    <form action="query_customer" method="get" id="frmQuery" onsubmit="return checkQuery('queryCustId');">
    	<label> Customer ID</label>
    	<input name="CustId" id="queryCustId" type="text"/>
    	<br><input id="sbmtQuery" name="sbmtQuery" type="submit" value="Query"/>
    </form>
    
    <br><h1> Create Customer</h1>
    <a title="Click button to enable input"><input type="button" value="Create" onclick="btnClick('submitForm')"/></a>
    <form action="createCustomer" method="post" id="submitForm" onsubmit="return checkValues(this);">
    	<table>
    		<tr>
    			<td>Customer ID </td>
    			<td><input disabled type="text" id="CustId" name="CustId" value="" /></td>
    		</tr>
     		<tr>
    			<td>Customer Name  </td>
    			<td><input disabled type="text" id="CustName" name="CustName" value="" /></td>
    		</tr>
    		 <tr>
    			<td>Default Account ID </td>
    			<td><input disabled type="text" id=DefAcctId name=DefAcctId value="" /></td>
    		</tr>   		   	
     		<tr>
    			<td>Effective Time  </td>
    			<td><input disabled type="text" id="EffTime" name="EffTime" value="" /></td>
    		</tr>
     		<tr>
    			<td>Expire Time </td>
    			<td> <input disabled type="text" id="ExpTime" name="ExpTime" value="" /></td>
    		</tr>
    	</table>
    	<!-- 
    		 Customer ID : <input disabled type="text" id="CustId" name="CustId" value="" />
    	<br> Customer Name : <input disabled type="text" id="CustName" name="CustName" value="" />
    	<br> Default Account ID : <input disabled type="text" id=DefAcctId name=DefAcctId value="" />
    	<br> Effective Time : <input disabled type="text" id="EffTime" name="EffTime" value="" />
    	<br> Expire Time : <input disabled type="text" id="ExpTime" name="ExpTime" value="" />
    	 -->
    	<br><input type="submit" value="Submit" />
    </form>
    
    
    <script type="text/javascript">
	function btnClick(id){
		//document.getElementById(id).disabled = false;
		//var allElements = document.getElementById(id).elements;
		var all = document.getElementsByTagName("input");
		/*var allTags = document.forms["submitForm"].getElementsByTagName("input");
		for (tag in allTags){
			tag.disabled = false;
		}
		*/
		for (i=0; i<all.length; i++){
			all[i].disabled = false;
			// clear all text boxes
			if (all[i].type == "text")
				all[i].value = "";
			
		}
		/*
		for( int i=0; i < allElements.length; i++){
			//elem.disabled = false;
			//console.log(allElements[i].value);
		}
		*/
		//document.getElementById("submitForm").attributes.item(0)
	}
	function checkValues(obj){
		if( document.getElementById("CustId").value.length > 9){
			window.alert("Customer ID must be smaller than 10!");
			return false;
		}
		if( document.getElementById("CustName").value.length == 0){
			window.alert("Name empty!");
			return false;
		}
		if( document.getElementById("DefAcctId").value.length > 9){
			window.alert("Default Account ID must be smaller than 10!");
			return false;
		}
		if( !checkDate(document.getElementById("EffTime").value)){
			window.alert("EffTime invalid");
			return false;
		}
		if( !checkDate(document.getElementById("ExpTime").value)){
			window.alert("ExpTime invalid!!");
			return false;
		}
		else{
			 return true;
		}
	}
	
	function checkDate(dat){
		if(dat.length != 10){
			return false;
		}
		var seps = dat.split("-");
		/* for(i in seps){
			window.alert("NO!");
			//document.writeln(seps[i]);
		} */
		if( seps[0].length != 4 || seps[1].length != 2 || seps[2].length != 2){
			return false;
		}
		try {
			var year = parseInt(seps[0]);
			var mon = parseInt(seps[1]);
			var day = parseInt(seps[2]);
			if( year == 0 || 2999 < year) return false;
			if( mon < 0 || 12 < mon) return false;
			if( day < 0 || 31 < day) return false;
			
		} catch (e) {
			return false;
		}
		return true;

		
		
	}
	
	function checkQuery(id){
		if (document.getElementById(id).value == "") return false;
		else return true;
	}
</script>

</body>
</html>