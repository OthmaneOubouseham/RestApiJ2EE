<html lang="en">
    <head>
        <!-- Changing the title of the page -->
        <title> <%=Constants.NAME_PAGE_LOGOUT%> </title>
    </head>


    <body>
            <!-- We always add FIRST the Navbar -->
            <%@include file="navbar.jsp" %>
            
            
            <!-- Display some stuff -->
            <div style="text-align:center">
                
                <br><br><br><br>
                
                <h2>We hope to see you soon !</h2>
                
                <br><br><br><br>

                <img src="<%=Constants.PATH_IMAGE_LOGOUT%>" width="200" height="200" class="d-inline-block align-top" alt="">
                
                <br><br><br><br>

            </div>



            <!-- We always add LAST the Footer -->
            <%@include file="footer.jsp" %>            
    </body>
</html>