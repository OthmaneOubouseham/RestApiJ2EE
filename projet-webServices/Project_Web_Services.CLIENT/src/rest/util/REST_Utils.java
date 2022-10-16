package rest.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import WebServices.util.ServiceAuthorization;
import rest.model.*;



public class REST_Utils
{	 
	protected static WebTarget service;
	
    // Linking to the REST Backend
    private static URI GET_BASE_URI = UriBuilder.fromUri("http://localhost:8089/Project_Web_Services.REST").build();

	
    /** Sets up the connection to the REST project (Back-end)
     * 
     * @return The connection with the REST project
     */
    public static WebTarget REST_GetService()
	{
	    ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
	    WebTarget service = client.target(GET_BASE_URI);
	    
	    return service;
	}
	
	
	
	
	/** Transform a JSON list of {@link Multimedia} into a JAVA list of {@link Multimedia}
	 * 
	 * @deprecated Won't work, since it will analyse it as a multimedia, but won't recognize field such as book.author, or film.director
	 * @return a JAVA list of {@link Multimedia}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ArrayList<Multimedia> REST_GetListMultimedias()
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/multimedias").request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( new GenericType<ArrayList<Multimedia>>() {} );
	}
}
