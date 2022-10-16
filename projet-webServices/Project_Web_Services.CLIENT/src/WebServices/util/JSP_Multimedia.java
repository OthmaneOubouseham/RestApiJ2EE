package WebServices.util;

import static WebServices.util.Constants.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import rest.model.Multimedia;



public class JSP_Multimedia
{
	private static int cpt_ID = 0;
	private static JspWriter jw;
	private static HttpServletRequest request;
	private static boolean isConnected;
	private static boolean allowedToModify;
	private static boolean createNewMultimedia;
	private static Multimedia multimedia;
	
	
	
	public static void AddFields(JspWriter JW, HttpServletRequest REQUEST) throws Exception
	{
		// Some variables
		jw = JW;
		request = REQUEST;
		isConnected = Constants.IS_CONNECTED(request);

		allowedToModify = ( !isConnected || request.getSession().getAttribute("modifyUser") != null);
		
		// If the ID is equal to 0, we create a new Multimedia
		createNewMultimedia = (Long.valueOf( request.getParameter("ID") ) == 0);
		if( createNewMultimedia )
		{
			multimedia = new Multimedia();
		}
		else
		{
			// The {@link Multimedia} we want to display
			multimedia = (Multimedia)request.getAttribute("multimedia");
		}
		
		
		allowedToModify = (createNewMultimedia && !allowedToModify);
		

		AddFieldsCommon();
	}
	
	
	
	private static void AddFieldsCommon() throws Exception
	{			
		// We create some variables
		String title = "Title";
		String description = "Description";
		String language = "Language";
		String genre = "Genre";
		String category = "Multimedia";
		String status = "Available";
		String date_status = "";
		String date_upload = "";
		String date_release = "";
		
		// If we don't create a new {@link Multimedia}
		// we display the value of the {@link Multimedia} contained in the request session
		if( !createNewMultimedia )
		{
			title = multimedia.getTitle();
			description = multimedia.getDescription();
			language = multimedia.getLanguage();
			genre = multimedia.getGenre();
				
			switch(multimedia.getCategory())
			{
			case 1:
				category = "Book";
				break;

			case 2:
				category = "Film";
				break;
				
			case 3:
				category = "Video Game";
				break;

			default:
				category = "Not implemented";
				break;
			}
			
			
			switch(multimedia.getStatus())
			{
				case 1:
					status = "Available";
					break;
	
				case 2:
					status = "To Rent";
					break;
					
				case 3:
					status = "Unavailable";
					break;
	
				default:
					status = "Not implemented";
					break;
			}

			date_status = multimedia.getDate_status().toString();
			date_upload = multimedia.getDate_upload().toString();
			date_release = multimedia.getDate_release().toString();
		}
        

    	AddField("text", FORM_MULTIMEDIA_TITLE, "Title", title);
    	AddField("text", FORM_MULTIMEDIA_DESCRIPTION, "Description", description);
    	AddField("text", FORM_MULTIMEDIA_LANGUAGE, "Language", language);
    	AddField("text", FORM_MULTIMEDIA_GENRE, "Genre", genre);
    	AddField("text", FORM_MULTIMEDIA_CATEGORY, "Category", category);
    	AddField("text", FORM_MULTIMEDIA_STATUS, "Status", status);
    	AddField("text", FORM_MULTIMEDIA_DATE_STATUS, "Date Status", date_status);
    	AddField("text", FORM_MULTIMEDIA_DATE_RELEASE, "Date Release", date_release);
    	
    	if( !allowedToModify )
    	{
        	AddField("text", FORM_MULTIMEDIA_DATE_UPLOAD, "Date Upload", date_upload);
    	}
	}
	
	
	
	
	
	/** A method that creates the Fields for a given user
	 * 
     * @param jw the JSP Writer used to write the file
	 * @param isConnected Whether the user has logged in or not
	 * @param content Text displayed within the field
	 * @throws Exception If something goes wrong
	 */
	public static void AddField(String type, String name, String label, String content) throws Exception
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
        jw.println(" required>");
        
        jw.println("	</div>");
        jw.println("</div>");
	}
}