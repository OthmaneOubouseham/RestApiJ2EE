package rest.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import rest.model.ErrorMessage;
import rest.util.Constants;




@Provider
public class DataNotFoundException extends RuntimeException implements ExceptionMapper<DataNotFoundException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8904992324400309961L;


	
	public DataNotFoundException(){
		super();
	}
	
	public DataNotFoundException(String message){
		super(message);
	}

	@Override
	public Response toResponse(DataNotFoundException e) {		
		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorMessage(e.getMessage(), 404, Constants.documentation))
				.build();
	}
}
