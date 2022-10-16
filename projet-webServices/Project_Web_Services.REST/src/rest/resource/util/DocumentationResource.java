package rest.resource.util;




import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;




@Path("/api-doc")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentationResource
{
    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    
    @Context
    Request request;
    
    @Context
    ServletContext ctx;
    
    
	
	
    @GET
    public Response getDocumentation()
    {
    	
    	Scanner s = new Scanner(ctx.getResourceAsStream("/WEB-INF/doc.json")).useDelimiter("\\A");
    	String result = s.hasNext() ? s.next() : "";
    	
    	s.close();
    	
    	if(result != null)
			return Response.ok(result)
					//.header("Content-Disposition", "attachment; filename=\"doc.json\"" ) //optional
					.build();
    	
    	

		return Response
				.status(Status.NOT_FOUND)
				.build();
    }
}
