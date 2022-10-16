<%@page import="rest.model.User"%>
<%@page import="WebServices.util.JSP_Account"%>


<html lang="en">
	<head>
	<!-- Changing the title of the page -->
	<title><%=Constants.NAME_PAGE_ACCOUNT%></title>
	</head>
	
	
	<body>
		<!-- We always add FIRST the Navbar -->
		<%@include file="navbar.jsp"%>
	
		<%
			boolean isConnected = Constants.IS_CONNECTED(request);
			User user = new User();
	
			if (isConnected) {
				user = (User) request.getSession().getAttribute("user");
			} else {
				user.setPseudo("your username");
				user.setEmail("your email");
			}
		%>
	
	
		<!-- Display some stuff -->
		<div class="container" style="padding-top: 4vh">
			<div class="row">
				<div class="col">
	
					<h1>
						<%
							out.println(request.getAttribute("title"));
						%>
					</h1>
	
	
					<form action="account" method="POST">
	
						<hr class="theme" />
						<%-- ALL FIELDS --%>
						<%
							JSP_Account.AddFields(out, request, isConnected);
						%>
						
	
					</form>
	
	
				</div>
			</div>
		</div>
	
	
		<!-- We always add LAST the Footer -->
		<%@include file="footer.jsp"%>
	</body>
</html>