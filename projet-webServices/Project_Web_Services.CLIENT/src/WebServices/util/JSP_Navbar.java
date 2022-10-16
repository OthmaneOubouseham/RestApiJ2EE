package WebServices.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import WebServices.util.Constants.EnumMultimedia;


public class JSP_Navbar
{
    // DROPDOWNS

    /** A method that dynamically adds Dropdown to the navbar
    *
     * @param jw the JSP Writer used to write the file
	 * @throws Exception If something goes wrong
     */
    public static void AddAllDropdowns(JspWriter jw) throws Exception
    {
        for(EnumMultimedia media : EnumMultimedia.values())
        {
        	AddDropdown(jw, media);
        }
    }


    
    
    /** A method that creates the Dropdown for a given multimedia
    *
     * @param jw the JSP Writer used to write the file
     * @param multimedia The current Multimedia of which we want to create the Dropdown
	 * @throws Exception If something goes wrong
     */
    public static void AddDropdown(JspWriter jw, EnumMultimedia multimedia) throws Exception
    {
        jw.println("<li class=\"nav-item dropdown\" style=\"margin:0 0 0 25px\">");

        jw.println("	<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\" style=\"color:white\">");
        jw.println("	" + multimedia.getName());
        jw.println("	</a>");


        jw.println("	<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">");
        jw.println("		<a class=\"dropdown-item\" href=\"" + multimedia.getURL() + "\">See All</a>");
        jw.println("		<a class=\"dropdown-item\" href=\"" + multimedia.getURL() + "?sort=rate\">Sort by Rates</a>");
        jw.println("		<a class=\"dropdown-item\" href=\"" + multimedia.getURL() + "?sort=comment\">Sort by Comments</a>");

        if(multimedia.getValues().size() != 0)
        	jw.println("	<div class=\"dropdown-divider\"></div>");



        for(String s : multimedia.getValues())
        {
                jw.println("		<a class=\"dropdown-item\" href=\"#\">" + s + "</a>");
        }


        jw.println("	</div>");

        jw.println("</li>");
    }


    
    
    // BUTTONS

    
    

    /** A method that dynamically adds Buttons to the navbar, whether you are logged in are not
    *
     * @param jw the JSP Writer used to write the file
     * @param r1 The request scope used to access request / session / etc ... variables
	 * @throws Exception If something goes wrong
     */
    public static void AddAllButtons(JspWriter jw, HttpServletRequest r1) throws Exception
    {
        // If connected : My Profile & Logout
        if( Constants.IS_CONNECTED(r1) )
        {
	            AddButton(jw, r1, "<b>Add a new article !</b>", "multimedia?ID=0");
	            AddButton(jw, r1, "My Profile", "account");
                AddButton(jw, r1, "Logout", "logout");
        }
        // If not connected : New account & Login
        else
        {
                AddButton(jw, r1, "Sign up", "account");
                AddButton(jw, r1, "Log in", "login");	
        }
    }

    
    

    /** A method that creates the Button for a given mission
    *
     * @param jw the JSP Writer used to write the file
     * @param r1 The request scope used to access request / session / etc ... variables
     * @param name Name of the button to add
     * @param path Path to go to when the button is pressed
	 * @throws Exception If something goes wrong
     */
    public static void AddButton(JspWriter jw, HttpServletRequest r1, String name, String path) throws Exception
    {
        jw.println("<li class=\"nav-item\" style=\"margin:0 25px 0 0\">");
        jw.println("<a class=\"nav-link\" href=\" "+ path + " \" style=\"color:white\">" + name + "</a>");
        jw.println("</li>");
    }
}
