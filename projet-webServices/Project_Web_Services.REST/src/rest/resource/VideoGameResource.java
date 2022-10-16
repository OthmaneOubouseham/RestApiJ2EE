package rest.resource;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import rest.model.VideoGame;
import rest.model.util.SorterByAverage;
import rest.service.VideoGameService;


@Path("/videoGames")
@Produces(MediaType.APPLICATION_JSON)
public class VideoGameResource
{
    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    
    @Context
    Request request;
    
    VideoGameService videoGameService;
    



	private URI getUriForSelf(VideoGame videoGame) {
		return this.uriInfo.getBaseUriBuilder()
				.path(VideoGameResource.class)
				.path(String.valueOf(videoGame.getId_videoGame()))
				.build();
	}
    
    
	private URI getUriForUploader(VideoGame videoGame) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(videoGame.getID_uploader()))
				.build();
	}
    
    
	private URI getUriForRates(VideoGame videoGame) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(RateResource.class)
				.queryParam("id_multimedia", videoGame.getId_multimedia())
				.build();
	}
    
    
	private URI getUriForComments(VideoGame videoGame) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(CommentResource.class)
				.queryParam("id_multimedia", videoGame.getId_multimedia())
				.build();
	}


    
	
	/** We add links related to the {@VideoGame}
	 * 
	 * @param videoGame Current {@VideoGame} of which we want to give the associated links
	 */
	private void addLinks(VideoGame videoGame) {
		videoGame.addLink("self", getUriForSelf(videoGame).toString());
		videoGame.addLink("uploader", getUriForUploader(videoGame).toString());
		videoGame.addLink("rates", getUriForRates(videoGame).toString());
		videoGame.addLink("comments", getUriForComments(videoGame).toString());
	}
    
    
	
	
    /** Returns all the {@VideoGame} rows from the database
     * 
     * @return All the {@VideoGame} rows from the database
     * @throws SQLException
     */
    @GET
    public Response getVideoGames(@QueryParam("start")int start, @QueryParam("end")int end,
    		@QueryParam("title")String title, @QueryParam("uploader")long id_uploader, @QueryParam("sort")String trie)throws SQLException
    {
		this.videoGameService = new VideoGameService();

		// We add the links to all foreign references
		List<VideoGame> videoGames;
		List<VideoGame> result;
		
		
		if(title != null){
			videoGames = videoGameService.filtre(title);
		}else{
			videoGames = videoGameService.getAllVideoGames();
		}
		 if(id_uploader != 0){

				List<VideoGame> videoGames_cpy = new ArrayList<VideoGame>(videoGames);
	    		for(VideoGame videoGame : videoGames_cpy){
	    			if( videoGame.getID_uploader() != id_uploader ){
	    				videoGames.remove(videoGame);
	    			}
	    		}
		 }
		
		 if(trie != null && trie.equals("average")){
			 System.out.println("entrez de le if");
			 Collections.sort(videoGames, new SorterByAverage());
		 }
		 
		if(start >=0 && end>0 && end>=start && end<=videoGames.size()){
			 result = videoGames.subList(start, end);
		 }else{
			 result = videoGames;
		 }
		
		for(VideoGame videoGame : result)
		{
			addLinks(videoGame);
		}
		
		return Response.status(Status.OK)
				.entity(result)
				.build();
    }

    
    

    /** Returns a given {@VideoGame} from the database
     * 
     * @param id ID of the {@VideoGame} we are returning
     * @return a specific {@VideoGame} row
     * @throws SQLException
     */
    @Path("/{videoGame_id}")
    @GET
    public Response getVideoGame(@PathParam("videoGame_id") long id) 
    		throws SQLException
    {
		this.videoGameService = new VideoGameService();
		
		// We add the links to all foreign references
		VideoGame videoGame = videoGameService.getVideoGame(id);
		addLinks(videoGame);
		
        return Response.status(Status.OK)
				.entity(videoGame)
				.build();
    }

    /** Displays the number of rows inside the table {@VideoGame}
     * 
     * @return the number of rows inside the table {@VideoGame}
     * @throws SQLException
     */
    @GET
    @Path("/count")
    public Response getCount() 
    		throws SQLException
    {
		this.videoGameService = new VideoGameService();
		
		
        return Response.status(Status.OK)
        		.entity(videoGameService.getVideoGameCount())
        		.build();
    }

    
    
 /** Creates a new instance of the table {@VideoGame}
  * 
  * @param film Instance to add to the database
  * @return
  * @throws SQLException
  */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postVideoGame(VideoGame videoGame) 
    		throws SQLException
    {
		this.videoGameService = new VideoGameService();
		VideoGame new_videoGame = videoGameService.addVideoGame(videoGame);
		
		addLinks(new_videoGame);
		 
		return Response
				.status(Status.OK)
				.entity(new_videoGame)
				.build();
    }


	@Path("/{id_videoGame}")
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("id_videoGame")Long id, VideoGame videoGame)
    		throws SQLException {
		this.videoGameService = new VideoGameService();
		
		
		return Response.status(Status.OK) 
				.entity(videoGameService.updateVideoGame(id, videoGame))
				.build();
    }

    
	@Path("/{id_videoGame}")
    @DELETE
    public Response deleteVideoGame(@PathParam("id_videoGame")Long id)
    		throws SQLException {
		this.videoGameService = new VideoGameService();
		
		
		return Response.status(Status.OK) 
				.entity(videoGameService.removeVideoGame(id))
				.build();
    } 
}
