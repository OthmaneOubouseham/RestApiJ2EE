package WebServices.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import static WebServices.util.Constants.*;
import rest.model.User;



public class JSP_Account
{
	private static int cpt_ID = 0;
	private static JspWriter jw;
	private static HttpServletRequest request;
	private static boolean isConnected;
	private static boolean allowedToModify;
	
	
	public static void AddFields(JspWriter JW, HttpServletRequest REQUEST, boolean ISCONNECTED) throws Exception
	{
		jw = JW;
		request = REQUEST;
		isConnected = ISCONNECTED;
		allowedToModify = false;
		if( !isConnected || request.getSession().getAttribute("modifyUser") != null)
		{
			allowedToModify = true;
		}

		
		AddAllInputFields();
		
		jw.println("<hr class=\"theme\" />");
		
		AddAllButtons();
	}
	
	
	
	/** Adds all the Fields correctly, whether the user is logged in or not
	 * 
     * @param JW the JSP Writer used to write the file
     * @param r1 The request scope used to access request / session / etc ... variables
	 * @param ISCONNECTED Whether the user has logged in or not
	 * @throws Exception If something goes wrong
	 */
	private static void AddAllInputFields() throws Exception
	{
		String Username = "Username";
		String Email = "Email";
		String CreationDate = "Creation Date";
		
		if( isConnected )
		{
			User user = (User)request.getSession().getAttribute("user");
			Username = user.getPseudo();
			Email = user.getEmail();
			CreationDate = user.getCreation_date().toString();
		}
		
    	AddField("text", FORM_ACCOUNT_USERNAME, "Username", Username);
    	if( !isConnected )
    	{
        	AddField("password", FORM_ACCOUNT_PASSWORD, "Password", "Password");	
        	AddField("password", FORM_ACCOUNT_PASSWORD_VERIF, "Password Verification", "Password Verification");		
    	}
    	AddField("email", FORM_ACCOUNT_EMAIL, "Email", Email);
    	
    	// Only a logged user can access the Creation Date, but he CANNOT modify it
    	if( isConnected && !allowedToModify)
    	{
        	AddField("text", FORM_ACCOUNT_DATE, "Creation Date", CreationDate);
    	}
	}
	
	
	
	
    /** A method that creates the Fields for a given user
	 * 
     * @param jw the JSP Writer used to write the file
	 * @param isConnected Whether the user has logged in or not
	 * @param content Text displayed within the field
	 * @throws Exception If something goes wrong
	 */
	private static void AddField(String type, String name, String label, String content) throws Exception
	{
        jw.println("<div class=\"form-group row\">");
        
        jw.println("	<div class=\"col-sm-1\"></div>");
        
        // Adding the label
        jw.println("	<label for=\""+ name + "\" class=\"col-sm-2 col-form-label\" style=\"text-align: right;\">"+ label + "</label>");

        // Adding the Field
        jw.println("	<div class=\"col-sm-8\">");
        jw.println("		<input type=\""+ type + "\" class=\"form-control\" name=\""+ name + "\" id=\"" + cpt_ID++ + "\" placeholder=\"" + content + "\"");

        // If the users is logged in and wants to modify its data
        if( allowedToModify && isConnected )
        {
            jw.println("value=\"" + content + "\"");
        }
        else
        // If someone is connected and not willing to change the field, they are disabled
        if( !allowedToModify )
        {
            jw.println("disabled");
        }
        jw.println(" maxlength=\"25\" required>");
        
        jw.println("	</div>");
        jw.println("</div>");
	}
	
	
	
	/** Adds all the buttons correctly, whether the user is logged in or not
	 * 
     * @param JW the JSP Writer used to write the file
     * @param request The request scope used to access request / session / etc ... variables
	 * @param ISCONNECTED Whether the user has logged in or not
	 * @throws Exception If something goes wrong
	 */
	private static void AddAllButtons() throws Exception
	{
		if( isConnected )
		{
			if( request.getAttribute("modifyUser") != null )
			{
				AddButton("Save the changes");
			}
			else
			{
				AddButton("Modify my data");
			}
		}
		else
		{
			AddButton("Sign in");
		}
	}
	
	
	
    /** A method that creates the button for a given user
	 * 
     * @param value Text inside the button
	 * @throws Exception If something goes wrong
	 */
	private static void AddButton(String value) throws Exception
	{
        jw.println("<div class=\"col-sm-11\" style=\"text-align: center\">");
        jw.println("	<div class=\"float-right\">");
        jw.println("		<input type=\"submit\" class=\"btn btn-primary\" value=\"" + value + "\">");
        jw.println("	</div>");
        jw.println("</div>");
	}
}
