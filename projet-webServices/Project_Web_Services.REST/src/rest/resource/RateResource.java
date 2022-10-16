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

import rest.model.Rate;
import rest.service.MultimediaService;
import rest.service.RateService;


@Path("/rates")
@Produces(MediaType.APPLICATION_JSON)
public class RateResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    Request request;
    @Context
    UriInfo uriInfo;
    
    RateService rateService;
    



	private URI getUriForSelf(Rate rate) {	
		return uriInfo.getBaseUriBuilder()
				.path(RateResource.class)
				.path(String.valueOf(rate.getId_rate()))
				.build();
	}
    
    
	private URI getUriForUser(Rate rate) {	
		return uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(rate.getId_user()))
				.build();
	}
    
    
	private URI getUriForMultimedia(Rate rate)
			throws SQLException {			
		return uriInfo.getBaseUriBuilder()
				.path( MultimediaService.getChildClass(rate.getId_multimedia()) ) 
				.path( String.valueOf(MultimediaService.getChildID(rate.getId_multimedia())) )
				.build();
	}


	private void addLinks(Rate rate)
			throws SQLException {
		rate.addLink("self", getUriForSelf(rate).toString());
		rate.addLink("author", getUriForUser(rate).toString());
		rate.addLink("multimedia", getUriForMultimedia(rate).toString());
	}
    

    
    
    @GET
    public Response getRates(@QueryParam("id_user")long id_user, @QueryParam("id_multimedia")long id_multimedia, @QueryParam("value")String value,
    		@QueryParam("start")int start, @QueryParam("end")int end) 
    		throws SQLException{
   		this.rateService = new RateService();

   		List<Rate> rates = rateService.getAllRates();
   		
		if(value != null){
    		
			List<Rate> rates_cpy = new ArrayList<Rate>(rates);
    		for(Rate rate : rates_cpy){
    			if(String.valueOf(rate.getValue()) != value){
    				rates.remove(rate);
    			}
    		}
			
		}
		if(id_user != 0){

			List<Rate> rates_cpy = new ArrayList<Rate>(rates);
    		for(Rate rate : rates_cpy){
    			if( rate.getId_user() != id_user ){
    				rates.remove(rate);
    			}
    		}
			
		}
		if(id_multimedia != 0){

			List<Rate> rates_cpy = new ArrayList<Rate>(rates);
    		for(Rate rate : rates_cpy){
    			if( rate.getId_multimedia() != id_multimedia ){
    				rates.remove(rate);
    			}
    		}
			
		}
		
		if(start >= 0 && end != 0 && start < end){
			
			if(end > rates.size()){
				end = rates.size();
			}
			
			rates = rates.subList(start, end);
		}

		for(Rate rate : rates){
			addLinks(rate);
		}
		
		
		return Response
				.status(Status.OK)
				.entity(rates)
				.build();
    }


    @Path("{id_rate}")
    @GET
    public Response getRate(@PathParam("id_rate") long id) 
    		throws SQLException{
		this.rateService = new RateService();
		
		Rate rate = rateService.getRate(id);
		
		
		
		addLinks(rate);
		
		
        return Response
				.status(Status.OK)
				.entity(rate)
				.build();
    }


    @GET
    @Path("count")
    public Response getCount(@QueryParam("id_multimedia")long id_multimedia) 
    		throws SQLException{
   		this.rateService = new RateService();

   		List<Rate> rates = rateService.getAllRates();
		

		if(id_multimedia != 0){

			List<Rate> rates_cpy = new ArrayList<Rate>(rates);
    		for(Rate rate : rates_cpy){
    			if( rate.getId_multimedia() != id_multimedia ){
    				rates.remove(rate);
    			}
    		}
			
		}
		
		
        return Response
				.status(Status.OK)
				.entity(rates.size())
				.build();
	   	
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRate(Rate rate) 
    		throws SQLException{
		this.rateService = new RateService();
		
		Rate new_rate = rateService.addRate(rate.getValue(), rate.getId_user(), rate.getId_multimedia());
		addLinks(new_rate);
		URI location = getUriForSelf(new_rate);

		
        return Response
        		.created(location)
				.entity(new_rate)
				.build();
			
    }

    
    @Path("/{id_rate}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putRate(@PathParam("id_rate")long id_rate, Rate rate) 
    		throws SQLException{
		this.rateService = new RateService();
		
        return Response
				.status(Status.OK)
				.entity(rateService.updateRate(id_rate, rate.getValue()))
				.build();
			
    }

    
    @Path("/{id_rate}")
    @DELETE
    public Response deleteRate(@PathParam("id_rate")Long id) 
    		throws SQLException{
		this.rateService = new RateService();
		
        return Response
				.status(Status.OK)
				.entity(rateService.removeRate(id))
				.build();
			
    }

}
