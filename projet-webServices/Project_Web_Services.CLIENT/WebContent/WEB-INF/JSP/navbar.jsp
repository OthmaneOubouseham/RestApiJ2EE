<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<%@page import="WebServices.util.Constants"%>
<%@page import="WebServices.util.JSP_Navbar"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	    <!-- Required meta tags -->
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

	    <!-- Browser's icon -->
        <link rel="icon" type="image/png" href="<%=Constants.PATH_IMAGE_LOGO%>"/>
		

	    <!-- Do something -->
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
	
	
	<body>
	
	    <!-- Define the Navbar -->
	    <nav class="navbar sticky-top navbar-expand-lg navbar-light justify-content-between" style="background-color:cornflowerblue;color:white">
	
	            <!-- Name of the Website -->
	              <a class="navbar-brand my-0 mr-md-auto" href="/Project_Web_Services.CLIENT/multimedias">
	                <img src="<%=Constants.PATH_IMAGE_LOGO%>" width="30" height="30" class="d-inline-block align-top" alt="">
	                    <b style="color:white"> <%=Constants.NAME_WEBSITE%> </b>
	              </a>
	
	
	            <!-- The toggler used when the web browser's horizontal size is small -->
	            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	                    <span class="navbar-toggler-icon"></span>
	            </button>
	
	
	            <!-- The Navbar's content -->			
	            <div class="collapse navbar-collapse" id="navbarSupportedContent">
	
	                    <!-- Dropdowns -->
	                    <ul class="navbar-nav mr-auto">
	                            <% JSP_Navbar.AddAllDropdowns(out); %>
	                    </ul>	
	
	
	                    <!-- Buttons -->
	                    <ul class="navbar-nav ml-md-auto">
	                            <% JSP_Navbar.AddAllButtons(out, request); %>
	                    </ul>
	
	
	
	                    <!-- Search Bar -->
	                    <form class="form-inline my-2 my-lg-0" action="multimedias" method="get">
	                            <input class="form-control mr-sm-2" name="search" type="search" placeholder="Search" aria-label="Search">
	                            <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
	                    </form>
	
	            </div>
	
	    </nav>
	</body>
</html>