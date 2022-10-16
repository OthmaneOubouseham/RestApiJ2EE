package rest.resource;


import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
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

import rest.service.UserService;
import rest.model.User;



@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    
    UserService userService;
    



	private URI getUriForSelf(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(user.getId_user()))
				.build();
	}
    
    
	private URI getUriForRates(User user) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(RateResource.class)
				.queryParam("id_user", user.getId_user())
				.build();
	}
    
    
	private URI getUriForComments(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(CommentResource.class)
				.queryParam("id_user", user.getId_user())
				.build();
	}
    
    
	private URI getUriForMultimedias(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(MultimediaResource.class)
				.queryParam("uploader", user.getId_user())
				.build();
	}
    
    
	private URI getUriForBooks(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(BookResource.class)
				.queryParam("uploader", user.getId_user())
				.build();
	}
    
    
	private URI getUriForFilms(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(FilmResource.class)
				.queryParam("uploader", user.getId_user())
				.build();
	}
    
    
	private URI getUriForVideoGames(User user) {
		return this.uriInfo.getBaseUriBuilder()
				.path(VideoGameResource.class)
				.queryParam("uploader", user.getId_user())
				.build();
	}


	private void addLinks(User user) {
		user.addLink("self", getUriForSelf(user).toString());
		user.addLink("rates", getUriForRates(user).toString());
		user.addLink("comments", getUriForComments(user).toString());
		user.addLink("multimedias", getUriForMultimedias(user).toString());
		user.addLink("books", getUriForBooks(user).toString());
		user.addLink("films", getUriForFilms(user).toString());
		user.addLink("videoGames", getUriForVideoGames(user).toString());
	}
    
    
    
    /** Returns all the user rows from the database
     * 
     * @return All the user rows from the database
     * @throws SQLException
     */
    @GET
    public Response getUsers(@QueryParam("start")int start, @QueryParam("end")int end)
    		throws SQLException {
		this.userService = new UserService();
		
		List<User> users = userService.getAllUsers();
		
		if(start >= 0 && end != 0 && start < end){
			
			if(end > users.size()){
				end = users.size();
			}
			
			users = users.subList(start, end);
		}
		
		for(User user : users){
			addLinks(user);
		}
		
		
		return Response
				.status(Status.OK)
				.entity(users)
				.build();
    }

    
    
    /** Returns a given user from the database
     * 
     * @param id ID of the user we are returning
     * @return a specific user row
     * @throws SQLException
     */
    @Path("/{id_user}")
    @GET
    public Response getUser(@PathParam("id_user") long id) 
    		throws SQLException {
		this.userService = new UserService();
		
		User user = userService.getUser(id);

		addLinks(user);
		
        return Response
        		.status(Status.OK)
				.entity(user)
				.build();
    }

    
    
    /** Displays the number of rows inside the table user
     * 
     * @return the number of rows inside the table user
     * @throws SQLException
     */
    @GET
    @Path("/count")
    public Response getCount() 
    		throws SQLException {
		this.userService = new UserService();
		
		
        return Response
        		.status(Status.OK)
        		.entity(userService.getUserCount())
        		.build();
    }

    
    
    /** Displays the number of rows inside the table user
     * 
     * @return the number of rows inside the table user
     * @throws SQLException
     */
    @GET
    @Path("/{id_user}/checkPassword")
    public Response getCheckPassword(@PathParam("id_user")long id, @QueryParam("password")String password) 
    		throws SQLException {
		this.userService = new UserService();
		
		
        return Response
        		.status(Status.OK)
        		.entity(userService.checkUserPassword(id, password))
        		.build();
    }

    
    
    /** Returns a given user from the database
     * 
     * @param id ID of the user we are returning
     * @return a specific user row
     * @throws SQLException
     */
    @Path("/login")
    @GET
    public Response getLogin(@QueryParam("username")String username, @QueryParam("password")String password) 
    		throws SQLException {
		this.userService = new UserService();
		
		User user = userService.login(username, password);
			
		addLinks(user);
		
        return Response
        		.status(Status.OK)
				.entity(user)
				.build();
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(User user) 
    		throws SQLException {
		this.userService = new UserService();
				
		User new_user = userService.addUser(user);
		addLinks(new_user);
		URI location = getUriForSelf(new_user);
		
		
		return Response
				.created(location)
				.entity(new_user)
				.build();
    }


    @Path("/{user_id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("user_id")Long id, User user)
    		throws SQLException {
		this.userService = new UserService();
		
		
		return Response
				.status(Status.OK) 
				.entity(userService.updateUser(id, user.getPassword(), user.getNew_password(), user.getEmail()))
				.build();
    }

    
    @Path("/{id_user}")
    @DELETE
    public Response deleteUser(@PathParam("id_user")Long id)
    		throws SQLException {
		this.userService = new UserService();
		
		
		return Response.
				status(Status.OK) 
				.entity(userService.removeUser(id))
				.build();
    }

}