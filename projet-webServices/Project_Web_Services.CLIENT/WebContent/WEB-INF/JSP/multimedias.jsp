<%@page import="WebServices.util.JSP_Multimedias"%>


<html lang="en">
    <head>
        <!-- Changing the title of the page -->
        <title> <%=Constants.NAME_PAGE_MULTIMEDIAS%> </title>
    </head>


    <body>
    	<!-- We always add FIRST the Navbar -->
		<%@include file="navbar.jsp" %>
		
		
       <%-- Displaying the Error Message if there is one --%>
       
       <%
       out.println( "</br></br>" );
       
       // Display an Error Message if the database is empty / not linked to the database
       if(request.getAttribute("errKey") != null)
       {
           out.println( "<h3 style=\"color:red; text-align: center\">" );
           out.println( request.getAttribute("errKey") );
           out.println( "</h3>" );
       }
       // Otherwise, we display it in a table
       else
       {
    	   JSP_Multimedias.AddAllTableRows(out, request); 
       }
       %>
		
		
		<!-- Display the Table -->
		
		
		
		
		<!-- We always add LAST the Footer -->
		<%@include file="footer.jsp" %>            
    </body>
</html>