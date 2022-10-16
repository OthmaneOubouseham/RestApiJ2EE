package rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.model.Book;
import rest.model.Film;
import rest.model.Multimedia;
import rest.model.VideoGame;
import rest.model.util.Date;
import rest.model.util.Time;
import rest.model.util.Timestamp;
import rest.resource.BookResource;
import rest.resource.FilmResource;
import rest.resource.VideoGameResource;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class MultimediaService {
	
	private Map<Long, Multimedia> multimedias = new HashMap<Long, Multimedia>();
	
	
	public enum Category{
		BOOK,
		FILM,
		VIDEO_GAME
	}
	
	
	
	
	public static Category getChildCategory(long id_multimedia) throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){
    	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.getTypeByID);
	    	    	
	    	ppsm.setLong(1, id_multimedia);
	    	
	    	ResultSet rs = ppsm.executeQuery();
	    	
	    	if(rs.next()){
	    		int type = rs.getInt(1);
	    		
	    		switch(type){
	    		case 1:
	    			return Category.BOOK;
	    		case 2:
	    			return Category.FILM;
	    		case 3:
	    			return Category.VIDEO_GAME;
	    		}
	    	}
	    	
	    	
	    	return null;
		}
		
	}
	
	
	public static Class getChildClass(long id_multimedia)
			throws SQLException{
		Category category = getChildCategory(id_multimedia);
		
		switch(category){
		case BOOK:
			return BookResource.class;
		case FILM:
			return FilmResource.class;
		case VIDEO_GAME:
			return VideoGameResource.class;
		}
		
		return null;
	}
	
	
	public static long getChildID(long id_multimedia)
			throws SQLException{
		Category category = getChildCategory(id_multimedia);
		String query;
		
		switch(category){
		case BOOK:
			query = Constants.Multimedia.getBookChildID;
			break;
		case FILM:
			query = Constants.Multimedia.getFilmChildID;
			break;
		case VIDEO_GAME:
			query = Constants.Multimedia.getVideoGameChildID;
			break;
		default:
			return 0;
		}
		
		try(DB_web_services db = new DB_web_services()){
    	
	    	PreparedStatement ppsm = db.getPreparedStatement(query);
	    	
	    	ppsm.setLong(1, id_multimedia);
	    	
	    	ResultSet rs = ppsm.executeQuery();
	    	
	    	if(rs.next()){
	    		return rs.getInt(1);
	    	}
	    	
	    	
	    	return 0;
		}
		
	}	
	
	
	public MultimediaService() throws SQLException
	{
		// We initialize some helpful variables
		try(DB_web_services db = new DB_web_services()){

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.getAll);	
	    	ResultSet rs = ppsm.executeQuery();

	    	// We empty our current map
	    	this.multimedias.clear();

	    	// As long as the database returns a row, we fill the map
	    	while(rs.next())
	    	{	
	    		int category = rs.getInt("category");
	    		
	    		switch(category){
	    		case 1:
	    			PreparedStatement ppsm2 = db.getPreparedStatement(Constants.Book.getByIDMultimedia);	
	    			
	    			ppsm2.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs2 = ppsm2.executeQuery();
	    	    	
	    	    	if(rs2.next()){
		    	    	
						Book book = new Book(
								rs2.getLong("ID_multimedia"),
								rs2.getString("title"),
								rs2.getString("language"),
								rs2.getString("description"),
								rs2.getString("genre"),
								rs2.getInt("category"),
								rs2.getInt("status"),
								rs2.getLong("ID_uploader"),
								new Timestamp(rs2.getString("date_status")),
								new Timestamp(rs2.getString("date_upload")),
								new Date(rs2.getString("date_release")),
								rs2.getLong("ID_book"),
								rs2.getString("author"),
								rs2.getString("publisher")
								);

			    		// We put our values in the map
						this.multimedias.put(book.getId_multimedia(), book);
	    	    		
	    	    	}
					
					break;
	    		case 2:
	    			PreparedStatement ppsm3 = db.getPreparedStatement(Constants.Film.getByIDMultimedia);	
	    			
	    			ppsm3.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs3 = ppsm3.executeQuery();

	    	    	if(rs3.next()){

						Film film = new Film(
								rs3.getLong("ID_multimedia"),
								rs3.getString("title"),
								rs3.getString("description"),
								rs3.getString("language"),
								rs3.getString("genre"),
								rs3.getInt("category"),
								rs3.getInt("status"),
								rs3.getLong("ID_uploader"),
								new Timestamp(rs3.getString("date_status")),
								new Timestamp(rs3.getString("date_upload")),
								new Date(rs3.getString("date_release")),
								rs3.getLong("ID_film"),
								rs3.getString("director"),
								rs3.getString("productor"),
								rs3.getString("mainCast"),
								new Time(rs3.getString("duration"))
								);

						// We put our values in the map
						this.multimedias.put(film.getId_multimedia(), film);
	    	    		
	    	    	}
					
					break;
	    		case 3:
	    			PreparedStatement ppsm4 = db.getPreparedStatement(Constants.VideoGame.getByIDMultimedia);	
	    			
	    			ppsm4.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs4 = ppsm4.executeQuery();

	    	    	if(rs4.next()){
	    	    		
						VideoGame videoGame = new VideoGame(
								rs4.getLong("ID_multimedia"),
								rs4.getString("title"),
								rs4.getString("description"),
								rs4.getString("language"),
								rs4.getString("genre"),
								rs4.getInt("category"),
								rs4.getInt("status"),
								rs4.getLong("ID_uploader"),
								new Timestamp(rs4.getString("date_status")),
								new Timestamp(rs4.getString("date_upload")),
								new Date(rs4.getString("date_release")),
								rs4.getLong("ID_videoGame"),
								rs4.getString("developer"),
								rs4.getString("publisher")
								);

						// We put our values in the map
						this.multimedias.put(videoGame.getId_multimedia(), videoGame);
	    	    		
	    	    	}
					
					break;
	    		}
	    		
	    	}
		}
		
		SetallAverage();
	}
	
	
	public List<Multimedia> getMultimedias(){
		List<Multimedia> return_multimedias = new ArrayList<Multimedia>(this.multimedias.values());
		
		if(return_multimedias.isEmpty())
			throw new DataNotFoundException("No multimedias was found !");
		
		
    	return return_multimedias;
	}
	
	
	public Multimedia getMultimedia(long id){
		Multimedia multimedia = this.multimedias.get(id);

		if(multimedia == null)
			throw new DataNotFoundException("The multimedia with the id `" + id + "` was not found !");
		
		
		return multimedia;
	}
	
	
	public boolean removeMultimedia(long id)
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){
			
			if(this.multimedias.get(id) == null)
				throw new DataNotFoundException("The video game with the id `" + id + "` doesn't exist !");

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.deleteByID);
	
	    	ppsm.setLong(1, id);
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		multimedias.remove(id);
	    		
	    		
	    		return true;
	    	}	
			
	    	
			return false;
		}
		
	}
	
	
	public List<Multimedia> filtreByTitle (String filtre) throws SQLException{
		
		List<Multimedia> result = new ArrayList<Multimedia>();
		
		try(DB_web_services db = new DB_web_services()){

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.getByTitle);
			ppsm.setString(1,"%" + filtre + "%" );
	    	ResultSet rs = ppsm.executeQuery();

	    	// We empty our current map
	    	//this.multimedias.clear();

	    	// As long as the database returns a row, we fill the map
	    	while(rs.next())
	    	{	
	    		int category = rs.getInt("category");
	    		
	    		switch(category){
	    		case 1:
	    			PreparedStatement ppsm2 = db.getPreparedStatement(Constants.Book.getByIDMultimedia);	
	    			
	    			ppsm2.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs2 = ppsm2.executeQuery();
	    	    	
	    	    	if(rs2.next()){
		    	    	
						Book book = new Book(
								rs2.getLong("ID_multimedia"),
								rs2.getString("title"),
								rs2.getString("language"),
								rs2.getString("description"),
								rs2.getString("genre"),
								rs2.getInt("category"),
								rs2.getInt("status"),
								rs2.getLong("ID_uploader"),
								new Timestamp(rs2.getString("date_status")),
								new Timestamp(rs2.getString("date_upload")),
								new Date(rs2.getString("date_release")),
								rs2.getLong("ID_book"),
								rs2.getString("author"),
								rs2.getString("publisher")
								);

			    		// We put our values in the map
						result.add(book);
						//this.multimedias.put(book.getId_multimedia(), book);
	    	    		
	    	    	}
					
					break;
	    		case 2:
	    			PreparedStatement ppsm3 = db.getPreparedStatement(Constants.Film.getByIDMultimedia);	
	    			
	    			ppsm3.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs3 = ppsm3.executeQuery();

	    	    	if(rs3.next()){

						Film film = new Film(
								rs3.getLong("ID_multimedia"),
								rs3.getString("title"),
								rs3.getString("description"),
								rs3.getString("language"),
								rs3.getString("genre"),
								rs3.getInt("category"),
								rs3.getInt("status"),
								rs3.getLong("ID_uploader"),
								new Timestamp(rs3.getString("date_status")),
								new Timestamp(rs3.getString("date_upload")),
								new Date(rs3.getString("date_release")),
								rs3.getLong("ID_film"),
								rs3.getString("director"),
								rs3.getString("productor"),
								rs3.getString("mainCast"),
								new Time(rs3.getString("duration"))
								);

						// We put our values in the map
						result.add(film);
						//this.multimedias.put(film.getId_multimedia(), film);
	    	    		
	    	    	}
					
					break;
	    		case 3:
	    			PreparedStatement ppsm4 = db.getPreparedStatement(Constants.VideoGame.getByIDMultimedia);	
	    			
	    			ppsm4.setLong(1, rs.getLong("id_multimedia"));
	    			
	    	    	ResultSet rs4 = ppsm4.executeQuery();

	    	    	if(rs4.next()){
	    	    		
						VideoGame videoGame = new VideoGame(
								rs4.getLong("ID_multimedia"),
								rs4.getString("title"),
								rs4.getString("description"),
								rs4.getString("language"),
								rs4.getString("genre"),
								rs4.getInt("category"),
								rs4.getInt("status"),
								rs4.getLong("ID_uploader"),
								new Timestamp(rs4.getString("date_status")),
								new Timestamp(rs4.getString("date_upload")),
								new Date(rs4.getString("date_release")),
								rs4.getLong("ID_videoGame"),
								rs4.getString("developer"),
								rs4.getString("publisher")
								);

						// We put our values in the map
						result.add(videoGame);
						//this.multimedias.put(videoGame.getId_multimedia(), videoGame);
	    	    		
	    	    	}
					
					break;
	    		}

	    	}
		}
		
		
		return result;
	}

	public Long CalculatingAverage (Long id) throws SQLException{
		
		long average = 0;
		long tot = 0;
				
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement ppsm = db.getPreparedStatement(Constants.Rate.getByMult);
			ppsm.setLong(1, id);
			ResultSet rs = ppsm.executeQuery();
			
			while(rs.next()){
				average += rs.getLong("value");
				tot++;
			}
			
			if(tot>0){
				average = average/tot;
			}
			
		}
		
		return average;
	}
	
	public void SetallAverage() throws SQLException{
		List<Multimedia> listTodo = new ArrayList<Multimedia>(this.multimedias.values());
		
		for(Multimedia iterator : listTodo){
			iterator.setAverage(this.CalculatingAverage(iterator.getId_multimedia()));
		}
	}
	
}
