package rest.util;

import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import WebServices.util.ServiceAuthorization;
import rest.model.Multimedia;



public class REST_Multimedia extends REST_Utils
{
	/** Transform a JSON list of {@link Multimedia} into a JAVA ArrayList of {@link Multimedia}
	 * 
	 * @param JSON_string JSON list of {@link Multimedia}
	 * @return a JAVA list of {@link Multimedia}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ArrayList<Multimedia> REST_Multimedias_GET(String search)
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		service = service.path("rest/v1/multimedias");
		
		if(search != null){
			service = service.queryParam("title", search);
		}
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( new GenericType<ArrayList<Multimedia>>() {} );
	}	
	
	
	
	
	/** Transform a JSON  {@link Multimedia} into an instance of {@link Multimedia}
	 * 
	 * @param ID ID of the {@link Multimedia} we search
	 * @return a JAVA list of {@link Multimedia}
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Multimedia REST_Multimedias_GET_byID(long ID)
			throws JsonParseException, JsonMappingException, IOException
	{
		service = REST_GetService();
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.path("rest/v1/multimedias/" + Long.toString(ID)).request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity( Multimedia.class );
	}	
}
