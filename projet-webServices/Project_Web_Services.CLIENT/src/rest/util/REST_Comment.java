package rest.util;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import WebServices.util.ServiceAuthorization;



public class REST_Comment extends REST_Utils
{
	public static int REST_Comments_GET_countByMultimedia(long id_multimedia)
			throws JsonParseException, JsonMappingException, IOException
	{
		if(id_multimedia == 0)
			return 0;
		
		service = REST_GetService();
		
		service = service.path("rest/v1/comments/count").queryParam("id_multimedia", id_multimedia);
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity(Integer.class);
	}
}
