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
import rest.model.User;



public class REST_User extends REST_Utils
{
	/** Transform a JSON list of {@link User} into a JAVA ArrayList of {@link User}
	 * 
	 * @param JSON_string JSON list of {@link User}
	 * @return a JAVA list of {@link User}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ArrayList<User> REST_Users_GET()
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/users").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( new GenericType<ArrayList<User>>() {} );
	}	
	
	
	/** Transform a JSON  {@link User} into an instance of {@link User}
	 * 
	 * @param ID ID of the {@link User} we search
	 * @return a JAVA list of {@link User}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static User REST_Users_GET_byID(long ID)
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/users/" + Long.toString(ID)).request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( User.class );
	}	
	
	
	
	
	/** Adds a new {@link User} to the Database
	 * 
	 * @param newUser {@link User} to add to the database
	 */
	public static User REST_User_POST(User newUser)
	{
		// Add it to the database
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/users").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		post(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
		
		return resp.readEntity( User.class );
	}	
	
	
	
	
	/** Modifies a given {@link User} in the Database
	 * 
	 * @param newUser {@link User} to modify
	 */
	public static void REST_User_PUT(User newUser)
	{
		System.out.println("DOING REST STUFF");
		// Add it to the database
		service = REST_GetService();
		
		ServiceAuthorization.
				getWebTarget( service.path("rest/v1/users").path( Long.toString(newUser.getId_user()) ).request() ).
				accept(MediaType.APPLICATION_JSON).
	    		put(Entity.entity(newUser, MediaType.APPLICATION_JSON), Response.class);
	}	
}