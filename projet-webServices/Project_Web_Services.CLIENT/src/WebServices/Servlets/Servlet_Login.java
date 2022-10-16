package WebServices.Servlets;

import static WebServices.util.Constants.*;
import static rest.util.REST_User.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import WebServices.util.ServiceAuthorization;
import rest.model.User;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Servlet_Login
 */
public class Servlet_Login extends HttpServlet
{
   private static final long serialVersionUID = -389100878518890328L;



   
   /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {     
        //If we have a role in the session, we are redirected to the home page
        if(IS_CONNECTED(request))
        {
            response.sendRedirect("multimedias");
        	return;	
        }
        //If we don't have a role in the session, we are in the login page
        else
        {
            // We verify that the user has entered input ( != null )
            if(request.getParameter(FORM_LOGIN_USERNAME) == null
                    || request.getParameter(FORM_LOGIN_PASSWORD) == null)
            {
                request.getRequestDispatcher(PATH_PAGE_LOGIN).forward(request, response);
                return;
            }
            
            
            
            // Data entered by the user
            String inputUser = request.getParameter(FORM_LOGIN_USERNAME);
            String inputPwd = request.getParameter(FORM_LOGIN_PASSWORD);


            
            // -----------
            // REST
            // -----------
            
            

        	// We get the REST service
        	WebTarget service = REST_GetService();

    	
    	    // We get the Users (in a string, JSON format)	    
    	    Response response2 = ServiceAuthorization.getWebTarget(
    	    		service
    	    		.path("rest/v1")
    	    		.path("users")
    	    		.path("login")
    	    		.queryParam("username", inputUser)
    	    		.queryParam("password", inputPwd)
    	    		.request())
    	    		.accept(MediaType.APPLICATION_JSON)
    	    		.get();
	    	
    	    System.out.println(response2.getStatusInfo().toString());
    	    
    	    
    	    if(response2.getStatusInfo().toString().equals(Status.OK.toString()))
    	    {
    	    	User user = response2.readEntity(User.class);

                // Setting the session value
                request.getSession().setAttribute("user", user);

                // Redirecting
                response.sendRedirect("multimedias");
                return;
    	    }


            // Since no match was found : Error message + redirect to the login
            request.setAttribute("errKey", ERR_MESSAGE_INVALID);
            response.sendRedirect("login");
            return;
        }
    }

    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "I'm the login servlet : I log people in.";
    }// </editor-fold>
}
