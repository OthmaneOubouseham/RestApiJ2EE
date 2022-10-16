package rest.resource;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import rest.model.Multimedia;
import rest.model.util.SorterByAverage;
import rest.service.MultimediaService;

@Path("/multimedias")
@Produces(MediaType.APPLICATION_JSON)
public class MultimediaResource
{
    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo

	 @Context
	 UriInfo uriInfo;
	 @Context
	 Request request;
	
	 MultimediaService multimediaService;
	    



	private URI getUriForSelf(Multimedia multimedia) {
		return this.uriInfo.getBaseUriBuilder()
				.path(MultimediaResource.class)
				.path(String.valueOf(multimedia.getId_multimedia()))
				.build();
	}
    
    
	private URI getUriForUploader(Multimedia multimedia) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(multimedia.getID_uploader()))
				.build();
	}
    
    
	private URI getUriForRates(Multimedia multimedia) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(RateResource.class)
				.queryParam("id_multimedia", multimedia.getId_multimedia())
				.build();
	}
    
    
	private URI getUriForComments(Multimedia multimedia) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(CommentResource.class)
				.queryParam("id_multimedia", multimedia.getId_multimedia())
				.build();
	}


	private void addLinks(Multimedia multimedia) {
		multimedia.addLink("self", getUriForSelf(multimedia).toString());
		multimedia.addLink("uploader", getUriForUploader(multimedia).toString());
		multimedia.addLink("rates", getUriForRates(multimedia).toString());
		multimedia.addLink("Comments", getUriForComments(multimedia).toString());
	}
	 

	
	
    /** Returns all the {@Multimedia} rows from the database
     * 
     * @return All the {@Multimedia} rows from the database
     * @throws SQLException
     */
	 @GET
	 public Response getMultimedias(@QueryParam("start")int start, @QueryParam("end")int end,
			 @QueryParam("title")String title, @QueryParam("sort")String trie, @QueryParam("uploader")long id_uploader) 
			 throws SQLException{
		 this.multimediaService = new MultimediaService();
		 
		 
		 List<Multimedia> multimedias;
		 
		 List<Multimedia> result;
		 
		 System.out.println(start);
		 System.out.println(end);
		 
		 if(title != null){
			 multimedias = multimediaService.filtreByTitle(title);
		 }else{
			 multimedias = multimediaService.getMultimedias();
		 }
		 if(id_uploader != 0){

				List<Multimedia> multimedias_cpy = new ArrayList<Multimedia>(multimedias);
	    		for(Multimedia multimedia : multimedias_cpy){
	    			if( multimedia.getID_uploader() != id_uploader ){
	    				multimedias.remove(multimedia);
	    			}
	    		}
		 }
		
		 if(trie != null && trie.equals("average")){
			 System.out.println("entrez de le if");
			 Collections.sort(multimedias, new SorterByAverage());
		 }
		 
		 if(start >=0 && end>0 && end>=start && end < multimedias.size()){
			 result = multimedias.subList(start, end);
		 }else{
			 result = multimedias;
		 }
		 
		 for(Multimedia multimedia : result)
			 addLinks(multimedia);		 
		 
		 return Response
				 .status(Status.OK)
				 .entity(result)
				 .build();
	 }
	 
	 
    /** Returns a given {@Multimedia} from the database
     * 
     * @param id ID of the {@Multimedia} we are returning
     * @return a specific {@Multimedia} row
     * @throws SQLException
     */
	 @Path("/{multimedia_id}")
	 @GET
	 public Response getMultimedia(@PathParam("multimedia_id") long id) 
			 throws SQLException {
		this.multimediaService = new MultimediaService();
		
		Multimedia multimedia = multimediaService.getMultimedia(id);
		
		addLinks(multimedia);
		
		 return Response.status(Status.OK)
	        		.entity(multimedia)
	        		.build();
	 }
	 
	 @Path("/{multimedia_id}/average")
	 @GET
	 public Response getAverage(@PathParam("multimedia_id") long id) 
			 throws SQLException{
			this.multimediaService = new MultimediaService();
			
			long ave = multimediaService.CalculatingAverage(id);
			
			return Response.status(Status.OK).entity(ave).build();
	 }
	 
	 @Path("/{multimedia_id}")
	 @DELETE
	 public Response DeleteMultimedia(@PathParam("multimedia_id") long id)
			 throws SQLException{
		 
		 this.multimediaService = new MultimediaService();
		 
		 return Response
				 .status(Status.OK)
				 .entity(multimediaService.removeMultimedia(id))
				 .build();
	 }
	 
}
