package rest.exception;


import java.security.InvalidParameterException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import rest.model.ErrorMessage;
import rest.util.Constants;




@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {

	@Override
	public Response toResponse(InvalidParameterException e) {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessage(e.getMessage(), 500, Constants.documentation))
				.build();
	}
}
