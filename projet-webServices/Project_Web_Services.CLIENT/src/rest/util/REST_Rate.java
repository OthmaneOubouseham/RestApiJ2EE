package rest.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import WebServices.util.ServiceAuthorization;
import rest.model.Rate;

public class REST_Rate extends REST_Utils{


	
	public static ArrayList<Rate> REST_Rates_GetByMultimedia(long id_multimedia)
			throws JsonParseException, JsonMappingException, IOException
	{
		if(id_multimedia == 0)
			return null;
		
		service = REST_GetService();
		
		service = service.path("rest/v1/rates");
		

		if(id_multimedia == 0)
			service = service.queryParam("id_multimedia", id_multimedia);
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity(new GenericType<ArrayList<Rate>>() {});
	}	
	
	
	public static int REST_Rates_GET_countByMultimedia(long id_multimedia)
			throws JsonParseException, JsonMappingException, IOException
	{
		if(id_multimedia == 0)
			return 0;
		
		service = REST_GetService();
		
		service = service.path("rest/v1/rates/count").queryParam("id_multimedia", id_multimedia);
		
		Response resp = ServiceAuthorization.
				getWebTarget( service.request() ).
				accept(MediaType.APPLICATION_JSON).
	    		get();

		return resp.readEntity(Integer.class);
	}
	
}
