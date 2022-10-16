package rest.resource;


import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import rest.model.Comment;
import rest.service.CommentService;
import rest.service.MultimediaService;


@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    Request request;
    @Context
    UriInfo uriInfo;
    
    CommentService commentService;
    



	private URI getUriForSelf(Comment comment) {	
		return uriInfo.getBaseUriBuilder()
				.path(CommentResource.class)
				.path(String.valueOf(comment.getId_comment()))
				.build();
	}
    
    
	private URI getUriForUser(Comment comment) {	
		return uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(comment.getId_user()))
				.build();
	}
    
    
	private URI getUriForMultimedia(Comment comment)
			throws SQLException {			
		return uriInfo.getBaseUriBuilder()
				.path( MultimediaService.getChildClass(comment.getId_multimedia()) ) 
				.path( String.valueOf(MultimediaService.getChildID(comment.getId_multimedia())) )
				.build();
	}


	private void addLinks(Comment comment)
			throws SQLException {
		comment.addLink("self", getUriForSelf(comment).toString());
		comment.addLink("author", getUriForUser(comment).toString());
		comment.addLink("multimedia", getUriForMultimedia(comment).toString());
	}
    

    
    
    @GET
    public Response getComments(@QueryParam("id_user")long id_user, @QueryParam("id_multimedia")long id_multimedia, 
    		@QueryParam("start")int start, @QueryParam("end")int end) 
    		throws SQLException{
   		this.commentService = new CommentService();
		
		List<Comment> comments = commentService.getAllComments();
		

		if(id_user != 0){

			List<Comment> comments_cpy = new ArrayList<Comment>(comments);
    		for(Comment comment : comments_cpy){
    			if( comment.getId_user() != id_user ){
    				comments.remove(comment);
    			}
    		}
			
		}
		if(id_multimedia != 0){

			List<Comment> comments_cpy = new ArrayList<Comment>(comments);
    		for(Comment comment : comments_cpy){
    			if( comment.getId_multimedia() != id_multimedia ){
    				comments.remove(comment);
    			}
    		}
			
		}
		

		if(start >= 0 && end != 0 && start < end){
			
			if(end > comments.size()){
				end = comments.size();
			}
			
			comments = comments.subList(start, end);
		}

		for(Comment comment : comments){
			addLinks(comment);
		}
		
		
		return Response
				.status(Status.OK)
				.entity(comments)
				.build();
    }


    @Path("{id_comment}")
    @GET
    public Response getComment(@PathParam("id_comment") long id) 
    		throws SQLException{
		this.commentService = new CommentService();
		
		Comment comment = commentService.getComment(id);
		
		addLinks(comment);
		
		
        return Response
				.status(Status.OK)
				.entity(comment)
				.build();
    }


    @GET
    @Path("count")
    public Response getCount(@QueryParam("id_multimedia")long id_multimedia) 
    		throws SQLException{
		this.commentService = new CommentService();
		
		List<Comment> comments = commentService.getAllComments();
		

		if(id_multimedia != 0){

			List<Comment> comments_cpy = new ArrayList<Comment>(comments);
    		for(Comment comment : comments_cpy){
    			if( comment.getId_multimedia() != id_multimedia ){
    				comments.remove(comment);
    			}
    		}
			
		}
		
		
        return Response
				.status(Status.OK)
				.entity(comments.size())
				.build();
	   	
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postComment(Comment comment) 
    		throws SQLException{
		this.commentService = new CommentService();
		
		Comment new_comment = commentService.addComment(comment.getValue(), comment.getId_user(), comment.getId_multimedia());
		addLinks(new_comment);
		URI location = getUriForSelf(new_comment);

		
        return Response
        		.created(location)
				.entity(new_comment)
				.build();
			
    }

    
    @Path("/{id_comment}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putComment(@PathParam("id_user")long id_user, @PathParam("id_comment")long id_comment, Comment comment) 
    		throws SQLException{
		this.commentService = new CommentService();
		
        return Response
				.status(Status.OK)
				.entity(commentService.updateComment(id_comment, comment.getValue()))
				.build();
			
    }

    
    @Path("/{id_comment}")
    @DELETE
    public Response deleteComment(@PathParam("id_user")long id_user, @PathParam("id_comment")Long id) 
    		throws SQLException{
		this.commentService = new CommentService();
		
        return Response
				.status(Status.OK)
				.entity(commentService.removeComment(id))
				.build();
			
    }

}
