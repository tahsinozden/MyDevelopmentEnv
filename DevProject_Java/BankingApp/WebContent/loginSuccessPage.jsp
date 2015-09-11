<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3> Login Success!</h3>
	<p> Customer ID : <%=session.getAttribute("custId") %></p>
	<p> FullName : <%=session.getAttribute("FullName") %></p>
	<p> DefAcct ID : <%=session.getAttribute("DefAcct") %></p>
	
 <table border="1" style="width:100%">
  <tr>
    <td><b> Customer ID </b></td>
    <td><b> FullName </b></td>
    <td><b> DefAcct ID </b></td>
    <td><b> Balance Amount </b></td>
     <td><b> Currency </b></td>
  </tr>
  <tr>
    <td> <%=session.getAttribute("custId") %></td>
    <td> <%=session.getAttribute("FullName") %></td>
    <td> <%=session.getAttribute("DefAcct") %></td>
    <td> <%=session.getAttribute("Amount") %></td>
    <td> <%=session.getAttribute("Currency") %></td>
  </tr>
</table> 

    <form action="doLogout" method="post">
    	<input id="sbmt" type="submit" value="Logout"/>
    </form>
    
    <form action="makeTransfer" method="post">
    	Dest. Cust. ID :<input type="text" name="reqCustId"/>
    	Transfer Amount :<input type="text" name="reqAmount"/>
    	Dest. Currency: <input type="text" name="reqCurrency"/>
    	<input type="submit" value="Submit"/>
    </form>
</body>
</html>