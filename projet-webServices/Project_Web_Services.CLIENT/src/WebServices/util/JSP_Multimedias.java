package WebServices.util;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import rest.model.Book;
import rest.model.Film;
import rest.model.Multimedia;
import rest.model.VideoGame;
import rest.util.REST_Comment;
import rest.util.REST_Rate;



public class JSP_Multimedias
{
    /** A method that dynamically adds rows to the table
    *
     * @param jw the JSP Writer used to write the file
     * @param request The request scope used to access request / session / etc ... variables
     * @throws Exception 
     */
    public static void AddAllTableRows(JspWriter jw, HttpServletRequest request) throws Exception
    {
        @SuppressWarnings("unchecked")
		List<Multimedia> multimedias = (ArrayList<Multimedia>)request.getAttribute("multimedias"); 

        
        jw.println("<div class=\"container\" style=\"padding-top: 4vh\">");
        jw.println("	<div class=\"row\">");
        jw.println("		<div class=\"col\">");

        
        jw.println("			<table class=\"table table-striped table-hover\" style=\"text-align:center;\">");
        
        jw.println("				<thead>");
        jw.println("					<tr>");
        jw.println("				    	<th scope=\"col\">TITLE</th>");
        jw.println("				    	<th scope=\"col\">DESCRIPTION</th>");
        jw.println("				    	<th scope=\"col\">CATEGORY</th>");
        jw.println("				    	<th scope=\"col\">GENRE</th>");
        jw.println("				    	<th scope=\"col\">LANGUAGE</th>");
        jw.println("				    	<th scope=\"col\">RATINGS</th>");
        jw.println("				    	<th scope=\"col\">COMMENTS</th>");
        jw.println("				    	<th scope=\"col\">DATE</th>");
        jw.println("					</tr>");
        jw.println("				</thead>");
        

        jw.println("				<tbody>");
        
    	for(Multimedia multimedia : multimedias)
    	{
    		AddTableRow(jw, request, multimedia);
    	}

        jw.println("				</tbody>");
        jw.println("			</table>");

        jw.println("		</div>");
        jw.println("	</div>");
        jw.println("</div>");
        
        jw.println("</br></br></br></br></br>");
        
        
        
        // Adding some javascript :
        // Basically, it makes the whole td tag with data-href clickable
        // And sends the user to the page of the multimedia
        jw.println("<script>");
        jw.println("	document.addEventListener(\"DOMContentLoaded\", () => {");
        
        jw.println("		const rows = document.querySelectorAll(\"tr[data-href]\");");
        
        jw.println("		rows.forEach(row => {");
        jw.println("			row.addEventListener(\"click\", () => {");
        jw.println("				window.location.href = row.dataset.href;");
        jw.println("			} );");
        jw.println("		} );");
        
        jw.println("	} );");
        jw.println("</script>");
    }
    
    
    
    /** A method that dynamically adds rows to the table
    *
     * @param jw the JSP Writer used to write the file
     * @param request The request scope used to access request / session / etc ... variables
     * @param multimedia The {@link Multimedia} to display
     * @throws Exception 
     */
    public static void AddTableRow(JspWriter jw, HttpServletRequest request, Multimedia multimedia) throws Exception
    {
    	// Some useful variables
    	int MAX = 1500;
    	int comments_count = REST_Comment.REST_Comments_GET_countByMultimedia(multimedia.getId_multimedia());
    	int rates_count = REST_Rate.REST_Rates_GET_countByMultimedia(multimedia.getId_multimedia());
    	String description = multimedia.getDescription();
    	if(description.length() > MAX)
    	{
        	description = description.substring(0, MAX) + " ...";
    	}
    	
    	String link;
    	
    	switch( multimedia.getCategory() )
    	{
			case 1:
				Book book = (Book)multimedia;
				link = "book?ID=" + book.getId_book();
				break;
			
			case 2:
				Film film = (Film)multimedia;
				link = "film?ID=" + film.getId_film();
				break;
				
			case 3:
				VideoGame videoGame = (VideoGame)multimedia;
				link = "videoGame?ID=" + videoGame.getId_videoGame();
				break;
				
			default:
				link = "";  
				break;
    	}
    	
    	String color = multimedia.getColor();
    	

    	// The proper JSP code
        jw.println("<tr style=\"cursor:hand;vertical-align:middle;border-left: 4px solid " + color + ";border-right: 2px solid " + color + "\" data-href=\"" + link + "\">");
        
        jw.println("	<td><b>" + multimedia.getTitle() + "</b></td>");
        jw.println("	<td style=\"text-align: justify;\">" + description + "</td>");
        jw.println("	<td style=\"color:" + color + "\"><b>" + multimedia.getCategoryText() + "</b></td>");
        jw.println("	<td>" + multimedia.getGenre() + "</td>");
        jw.println("	<td>" + multimedia.getLanguage() + "</td>");
        jw.println("	<td>" + multimedia.getAverage() + " | (" + rates_count + ")</td>");
        jw.println("	<td>(" + comments_count + ")</td>");
        jw.println("	<td>" + multimedia.getDate_upload() + "</td>");

        jw.println("</tr>");
    }
}