package WebServices.Servlets;

import static rest.util.REST_Book.*;
import static rest.util.REST_Film.*;
import static rest.util.REST_VideoGame.*;
import static WebServices.util.Constants.PATH_PAGE_MULTIMEDIA;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rest.model.Multimedia;


/**
 * Servlet implementation class Servlet_Multimedia
 */
public class Servlet_Multimedia extends HttpServlet
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
    	// Getting the URI and checking where we are
    	String URI = request.getRequestURI();
    	
    	// Get the ID of the {@link Multimedia} we want to display
    	long ID = Long.valueOf( request.getParameter("ID") );
    	
    	
    	Boolean isBook = URI.contains("book");
    	Boolean isFilm = URI.contains("film");
    	Boolean isVideoGame = URI.contains("videoGame");
    	
    	
    	Multimedia multimedia = new Multimedia();
    	if( isBook )
    	{
    		multimedia = REST_Books_GET_byID(ID);
    	}
    	if( isFilm )
    	{
    		multimedia = REST_Films_GET_byID(ID);
    	}
    	if( isVideoGame )
    	{
    		multimedia = REST_VideoGames_GET_byID(ID);
    	}
    	
    	// The {@link Multimedia} was  found (!= null)
    	if(multimedia != null)
    	{
    	    // We put the {@Multimedia} into the request scope
            request.setAttribute("multimedia", multimedia);
            request.getRequestDispatcher(PATH_PAGE_MULTIMEDIA).forward(request, response);
    		return;
    	}
    	// The {@link Multimedia} was not found (= null)
    	else
    	{
            response.sendRedirect("multimedias");
            return;
    	}
    }
    
    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	
	
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "I'm the multimedia servlet : I display most data about a given Multimedia.";
    }// </editor-fold>
}
