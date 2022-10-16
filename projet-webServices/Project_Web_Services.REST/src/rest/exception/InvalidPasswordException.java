package rest.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import rest.model.ErrorMessage;
import rest.util.Constants;




@Provider
public class InvalidPasswordException extends RuntimeException implements ExceptionMapper<InvalidPasswordException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8904992324400309961L;


	
	public InvalidPasswordException(){
		super("The password is incorrect for this user");
	}

	@Override
	public Response toResponse(InvalidPasswordException e) {		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessage(e.getMessage(), 500, Constants.documentation))
				.build();
	}
}
