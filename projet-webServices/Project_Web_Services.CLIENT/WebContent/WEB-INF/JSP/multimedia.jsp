<%@page import="WebServices.util.JSP_Multimedia"%>

<html lang="en">
    <head>
        <!-- Changing the title of the page -->
        <title> <%=Constants.NAME_PAGE_MULTIMEDIA%> </title>
    </head>


    <body>
            <!-- We always add FIRST the Navbar -->
            <%@include file="navbar.jsp" %>
            
            
            <!-- Display some stuff -->
            <div style="text-align:center">
                
                <br><br><br><br>
                
                <%-- Displaying the Error Message if there is one --%>
       
				<%
					JSP_Multimedia.AddFields(out, request); 
				%>

            </div>



            <!-- We always add LAST the Footer -->
            <%@include file="footer.jsp" %>            
    </body>
</html>