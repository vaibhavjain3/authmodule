<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Logout page</title>
	</head>
	<body>
		<h2>
			<%
			String Name=request.getParameter("name").toString();
			String Pass=request.getParameter("password").toString();
			if((Name.equals("")&&Pass.equals(""))){
				response.sendRedirect("index.jsp");
			}
			else{
			out.println("Welcome"+ " "+Name);
			}
			%>
		</h2>
		<form action="index.jsp">
			<input type="submit" value="logout"/>
		</form>
	</body>
</html>