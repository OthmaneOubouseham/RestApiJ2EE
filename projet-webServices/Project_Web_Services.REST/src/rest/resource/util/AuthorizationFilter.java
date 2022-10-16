package rest.resource.util;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import rest.util.Constants;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter{

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if(requestContext.getUriInfo().getAbsolutePath().toURL().toString().equals(Constants.documentation))
			return;
		
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
				
		if(authHeader != null && authHeader.size() > 0){
			
			String authTokken = authHeader.get(0);
			authTokken = authTokken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
			
			String decodeString = Base64.decodeAsString(authTokken);
			StringTokenizer tokenizer = new StringTokenizer(decodeString, ":");

			try{
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if(username.equals(Constants.username) && password.equals(Constants.password))
					return;
			}catch(NoSuchElementException e){
				
			}
		}
		
				
//		requestContext.abortWith(Response
//				.status(Status.UNAUTHORIZED)
//				.entity("You are UNAUTHORIZED !")
//				.build());
	}

}
