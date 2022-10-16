package WebServices.Servlets;

import static WebServices.util.Constants.*;
import static rest.util.REST_User.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rest.model.User;


/**
 * Servlet implementation class Servlet_Account
 */
@WebServlet(name = "Servlet_Accounts", urlPatterns = {"/Servlet_Accounts"})
public class Servlet_Account extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
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
    	// There is a user logged in  : display its data
       if(IS_CONNECTED(request))
       {
           request.setAttribute("title", "My Account");
       }
   		// There is no user logged in  : create a new one
       else
       {
           request.setAttribute("title", "New Account"); 	   
       }
        
        request.getRequestDispatcher(PATH_PAGE_ACCOUNT).forward(request, response);
        return;
    }
    
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        processRequest(request, response);
	}

	
	
	/** Adds {@link User} or modifies the current {@link User}
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Not a user : trying to SIGN IN
		if( !IS_CONNECTED(request) )
		{
			// We get all the inputs
			String inputUsername = request.getParameter(FORM_ACCOUNT_USERNAME);
			String inputPassword = request.getParameter(FORM_ACCOUNT_PASSWORD);
			String inputPasswordVerif = request.getParameter(FORM_ACCOUNT_PASSWORD_VERIF);
			String inputEmail = request.getParameter(FORM_ACCOUNT_EMAIL);
			

			// if the 2 passwords don't match -> TRY AGAIN
			if( !inputPassword.equals(inputPasswordVerif) )
			{ 
		       request.setAttribute("title", "New Account");
	           request.getRequestDispatcher(PATH_PAGE_ACCOUNT).forward(request, response);
	           return;
			}
			// OTHERWISE : we add the new user to the database
			else
			{ 
				// INSERT CODE - AJOUT BDD
		    	User newUser = new User();
		    	newUser.setPseudo(inputUsername);
		    	newUser.setPassword(inputPassword);
		    	newUser.setEmail(inputEmail);
		    	
		    	// Add it to the database
		    	newUser = REST_User_POST(newUser);


                // Setting the session value
                request.getSession().setAttribute("user", newUser);

                // Returning to the home page
	            response.sendRedirect("multimedias");
	            return;
			}
		}
		// user seeing its data : WANTS TO BE ABLE TO MODIFY THEM
		else if(request.getSession().getAttribute("modifyUser") == null)
		{
			System.out.println("I want to modify ...");
			// Setting the request value to be able to modify data
            request.getSession().setAttribute("modifyUser", true); 
            
            // Change the title
            request.setAttribute("title", "Modifying my Account");
            
            // redirect
            request.getRequestDispatcher(PATH_PAGE_ACCOUNT).forward(request, response);
            return;
		}
		// user modifying its data : WANTS TO APPLY THE CHANGES
		else
		{
			System.out.println("changing ...");
			// We get all the inputs
			String inputUsername = request.getParameter(FORM_ACCOUNT_USERNAME);
			String inputEmail = request.getParameter(FORM_ACCOUNT_EMAIL);
			
			
			// We verify that the inputs are valid
			if(inputUsername.length() == 0 || inputEmail.length() == 0)
			{
				// BAD : redirect to the Account page : do it again !
	            request.getRequestDispatcher(PATH_PAGE_ACCOUNT).forward(request, response);
	            return;
			}
			else
			{
				System.out.println("valid !");
				// GOOD : we remove the Session attribute
				request.getSession().removeAttribute("modifyUser");
				
				
				// We change the {@link User}'s value
				User user = (User)request.getSession().getAttribute("user");
				user.setPseudo(inputUsername);
				user.setEmail(inputEmail);
				
				// We modify the database
				REST_User_PUT(user);

				System.out.println("finish !");
				
                // Returning to the home page
	            response.sendRedirect("multimedias");
	            return;
			}
		}
	}
}
