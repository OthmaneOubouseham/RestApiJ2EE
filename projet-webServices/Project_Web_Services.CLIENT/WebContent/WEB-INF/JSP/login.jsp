<html lang="en">
        <head>
            <!-- Changing the title of the page -->
            <title> <%=Constants.NAME_PAGE_LOGIN%> </title>
        </head>


        <body>
                <!-- We always add FIRST the Navbar -->
                <%@include file="navbar.jsp" %>

                <div class="container" style="padding-top: 4vh">

            <div class="row">

                <div class="col-lg-5 col-md-8 col-sm-12" style="margin: auto;">

                    <%-- Displaying the Error Message if there is one --%>
                    <div style="color:red; text-align: center">
                        <label>
                            <small>
	                            <%
	                             if(request.getAttribute("errKey") != null)
	                             {
	                                 out.println( request.getAttribute("errKey") );
	                             }
	                            %>
                            </small>
                        </label>
                    </div>

                    <div class="card">

                        <%-- LOGIN FORM HEADER --%>
                        <div class="card-header">
                            Login
                        </div>

                        <div class="card-body">

                            <%-- LOGIN FORM CONTENT --%>
                            <form action="login" method="post">

                                <%-- LOGIN FIELD --%>
                                <div class="form-group">
                                    <input type="text" class="form-control" name="<%=Constants.FORM_LOGIN_USERNAME%>" placeholder="Username" maxlength="45" required>
                                </div>

                                <%-- PASSWORD FIELD --%>
                                <div class="form-group">
                                    <input type="password" class="form-control" name="<%=Constants.FORM_LOGIN_PASSWORD%>" placeholder="Password" maxlength="225" required>
                                </div>

                              <button type="submit" class="btn btn-primary">Login</button>

                            </form>

                        </div>            
                    </div>

                </div>
            </div>
        </div>


        <!-- We always add LAST the Footer -->
        <%@include file="footer.jsp" %>
        </body>
</html>