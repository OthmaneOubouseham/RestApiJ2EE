package rest.resource;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import rest.model.Book;
import rest.model.util.SorterByAverage;
import rest.service.BookService;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    
    @Context
    Request request;
    
    BookService bookService;
    



	private URI getUriForSelf(Book book) {
		return this.uriInfo.getBaseUriBuilder()
				.path(BookResource.class)
				.path(String.valueOf(book.getId_book()))
				.build();
	}
    
    
	private URI getUriForUploader(Book book) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(UserResource.class)
				.path(String.valueOf(book.getID_uploader()))
				.build();
	}
    
    
	private URI getUriForRates(Book book) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(RateResource.class)
				.queryParam("id_multimedia", book.getId_multimedia())
				.build();
	}
    
    
	private URI getUriForComments(Book book) {		
		return this.uriInfo.getBaseUriBuilder()
				.path(CommentResource.class)
				.queryParam("id_multimedia", book.getId_multimedia())
				.build();
	}


	private void addLinks(Book book) {
		book.addLink("self", getUriForSelf(book).toString());
		book.addLink("uploader", getUriForUploader(book).toString());
		book.addLink("rates", getUriForRates(book).toString());
		book.addLink("comments", getUriForComments(book).toString());
	}
	
	
	
    
    @GET
    public Response getBooks(@QueryParam("start")int start, @QueryParam("end")int end,
    		@QueryParam("title")String title, @QueryParam("uploader")long id_uploader, @QueryParam("sort")String trie)throws SQLException {
		this.bookService = new BookService();
		List<Book> books;
		List<Book> result;
		
		if(title != null){
			books = bookService.searchBook(title);
		}else{
			books = bookService.getAllBooks();

		}
		 if(id_uploader != 0){

				List<Book> books_cpy = new ArrayList<Book>(books);
	    		for(Book book : books_cpy){
	    			if( book.getID_uploader() != id_uploader ){
	    				books.remove(book);
	    			}
	    		}
		 }
		
		 if(trie != null && trie.equals("average")){
			 Collections.sort(books, new SorterByAverage());
		 }
		 
		 if(start >=0 && end>0 && end>=start && end < books.size()){
			 result = books.subList(start, end);
		 }else{
			 result = books;
		 }
			
		for(Book book : books){
			addLinks(book);
		}
		
		
        return Response.status(Status.OK)
				.entity(result)
				.build();

    }


    @Path("/{id_book}")
    @GET
    public Response getBook(@PathParam("id_book") long id) 
    		throws SQLException {
		this.bookService = new BookService();
		
		Book book = bookService.getBook(id);
		
		addLinks(book);
		
		
        return Response.status(Status.OK)
				.entity(book)
				.build();
    }


    @GET
    @Path("/count")
    public Response getCount() 
    		throws SQLException {
		this.bookService = new BookService();
		
		
        return Response.status(Status.OK)
        		.entity(bookService.getBookCount())
        		.build();
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBook(@FormParam("id") Long id, @FormParam("author") String author, @FormParam("publisher") String publisher) throws IOException, SQLException {
		this.bookService = new BookService();


        Book book = new Book(id, author, publisher);
        book.setID_uploader(1l);
		Book new_book = bookService.addBook(book);
		
		addLinks(new_book);
		 
		return Response.status(Status.OK).entity(new_book).build();

    }


    @Path("update/{id}/{author}/{publisher}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("id")Long id,@PathParam("author") String author, @PathParam("publisher") String publisher)
    		throws SQLException {
		this.bookService = new BookService();
        Book book = new Book(id, author, publisher);
		return Response.status(Status.OK) 
				.entity(bookService.updateBook(id, book))
				.build();
    }
    
    @Path("/{id_book}")
    @DELETE
    public Response deleteBook(@PathParam("id_book")Long id)
    		throws SQLException {
		this.bookService = new BookService();
		
		
		return Response.status(Status.OK) 
				.entity(bookService.removeBook(id))
				.build();
    }
}
