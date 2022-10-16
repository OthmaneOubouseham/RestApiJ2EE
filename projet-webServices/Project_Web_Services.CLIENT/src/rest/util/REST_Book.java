package rest.util;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import WebServices.util.ServiceAuthorization;
import rest.model.Book;



public class REST_Book extends REST_Utils
{
	/** Transform a JSON list of {@link Book} into a JAVA ArrayList of {@link Book}
	 * 
	 * @param JSON_string JSON list of {@link Book}
	 * @return a JAVA list of {@link Book}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ArrayList<Book> REST_Books_GET(String search)
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp;
		
		if(search != null){
			resp = ServiceAuthorization
			.getWebTarget( service.path("rest/v1/books").queryParam("title", search).request())
			.accept(MediaType.APPLICATION_JSON)
    		.get();
		}
		else{
			resp = ServiceAuthorization
			.getWebTarget( service.path("rest/v1/books").request())
			.accept(MediaType.APPLICATION_JSON)
    		.get();
		}

		return resp.readEntity( new GenericType<ArrayList<Book>>() {} );
	}
	
	
	/** Transform a JSON  {@link Book} into an instance of {@link Book}
	 * 
	 * @param ID ID of the {@link Book} we search
	 * @return a JAVA list of {@link Book}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Book REST_Books_GET_byID(long ID)
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/books/" + Long.toString(ID)).request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( Book.class );
	}	
	
	
	
	
	/** Adds a new {@link Book} to the Database
	 * 
	 * @param newUser {@link Book} to add to the database
	 */
	public static Book REST_Book_POST(Book newBook)
	{
		// Add it to the database
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/books").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		post(Entity.entity(newBook, MediaType.APPLICATION_JSON), Response.class);
		
		return resp.readEntity( Book.class );
	}	
	
	
	
	
	/** Modifies a given {@link Book} in the Database
	 * 
	 * @param newUser {@link Book} to modify
	 */
	public static void REST_Book_PUT(Book book)
	{
		// Add it to the database
		service = REST_GetService();
		
		ServiceAuthorization.
				getWebTarget( service.path("rest/v1/books").path( Long.toString( book.getId_book()) ).request() ).
				accept(MediaType.APPLICATION_JSON).
	    		put(Entity.entity(book, MediaType.APPLICATION_JSON), Response.class);
	}	
}