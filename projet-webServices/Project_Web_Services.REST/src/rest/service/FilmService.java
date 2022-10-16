package rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.model.Comment;
import rest.model.Film;
import rest.model.Multimedia;
import rest.model.Rate;
import rest.model.util.Date;
import rest.model.util.Time;
import rest.model.util.Timestamp;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class FilmService {

	
	private Map<Long, Film> films = new HashMap<Long, Film>();
	
	public FilmService() throws SQLException
	{
		// We initialize some helpful variables
		try(DB_web_services db = new DB_web_services()){
			
			PreparedStatement stmt = db.getPreparedStatement(Constants.Film.getAll);
			
			ResultSet rs = stmt.executeQuery();
			

	    	// We empty our current map
			films.clear();

	    	// As long as the database returns a row, we fill the map
			while(rs.next())
			{
				Film film = new Film(
						rs.getLong("ID_multimedia"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getString("language"),
						rs.getString("genre"),
						rs.getInt("category"),
						rs.getInt("status"),
						rs.getLong("ID_uploader"),
						new Timestamp(rs.getString("date_status")),
						new Timestamp(rs.getString("date_upload")),
						new Date(rs.getString("date_release")),
						rs.getLong("ID_film"),
						rs.getString("director"),
						rs.getString("productor"),
						rs.getString("mainCast"),
						new Time(rs.getString("duration"))
						);

	    		// We put our values in the map
				films.put(film.getId_film(), film);
			}	
		}
		SetallAverage();
	}
	
	
	
	
	public List<Film> getFilms(){
		List<Film> films = new ArrayList<Film>(this.films.values());
		
		if(films.isEmpty())
			throw new DataNotFoundException("No films was found !");
		
		
		return films;
	}
	
	
	public Film getFilm(long id){		
		Film film = films.get(id);
		
		if(film == null)
			throw new DataNotFoundException("The film with the id `" + id + "` was not found !");
		
		return film;
	}
	
	public List<Rate> getRates(Long id) throws SQLException{
		List<Rate> result = new ArrayList<Rate>();
		
		Film graded = films.get(id);
		
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement psmt = db.getPreparedStatement(Constants.Rate.getByMult);
			psmt.setLong(1, graded.getId_multimedia());
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				result.add(new Rate(
						rs.getLong("ID_rate"),
						rs.getInt("value"),
						new Timestamp(rs.getString("date_creation")),
						rs.getLong("ID_user"),
						rs.getLong("ID_multimedia")));
			}
		}
		
		
		return result;
	}
	
	public List<Comment> getComments(Long id) throws SQLException{
		List<Comment> result = new ArrayList<Comment>();
		
		Film graded = films.get(id);
		
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement psmt = db.getPreparedStatement(Constants.Comment.getByMult);
			psmt.setLong(1, graded.getId_multimedia());
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				result.add(new Comment(
						rs.getLong("ID_comment"),
						rs.getString("value"),
						new Timestamp(rs.getString("date_creation")),
						rs.getLong("ID_user"),
						rs.getLong("ID_multimedia")));
			}
		}
		
		return result;
	}
	
	public List<Film> searchFilm(String filtre) throws SQLException{
		List<Film> result = new ArrayList<Film>();
		
		try(DB_web_services db = new DB_web_services()){
			
			PreparedStatement psmt = db.getPreparedStatement(Constants.Film.getByName);
			
			psmt.setString(1, "%" + filtre + "%");
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				Film film =new Film(
						rs.getLong("ID_multimedia"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getString("language"),
						rs.getString("genre"),
						rs.getInt("category"),
						rs.getInt("status"),
						rs.getLong("ID_uploader"),
						new Timestamp(rs.getString("date_status")),
						new Timestamp(rs.getString("date_upload")),
						new Date(rs.getString("date_release")),
						rs.getLong("ID_film"),
						rs.getString("director"),
						rs.getString("productor"),
						rs.getString("mainCast"),
						new Time(rs.getString("duration"))
						);
				
				
				result.add(film);
			}
		}		
		
		
		return result;
	}
	
	public Film addFilm(Film film) throws SQLException{
				
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement psmt = db.getPreparedStatement(Constants.Multimedia.post);
			
			psmt.setString(1, film.getTitle());
			psmt.setString(2, film.getLanguage());
			psmt.setString(3, film.getGenre());
			psmt.setInt(4, film.getCategory());
			psmt.setInt(5, film.getStatus());
			psmt.setLong(6, film.getID_uploader());
			psmt.setString(7, film.getDescription());
			psmt.setString(8, film.getDate_release().toString());
			
			int success = psmt.executeUpdate();
			
			if(success == 1)
			{
				ResultSet id = psmt.getGeneratedKeys();
				
				if(id.next()){
					film.setId_multimedia(id.getLong(1));
					
					PreparedStatement psmt2 = db.getPreparedStatement(Constants.Film.post);
					
					psmt2.setString(1, film.getDirector());
					psmt2.setString(2, film.getProductor());
					psmt2.setString(3, film.getMainCast());
					psmt2.setString(4, film.getDuration().toString());
					psmt2.setLong(5, film.getId_multimedia());
					
					int success2 = psmt2.executeUpdate();
					
					if(success2 == 2){
						PreparedStatement psmt3 = db.getPreparedStatement(Constants.Film.getByIDMultimedia);
						psmt3.setLong(1, film.getId_multimedia());
						ResultSet rs = psmt3.executeQuery();
						
						if(rs.next()){
							Film result = new Film(
									rs.getLong("ID_multimedia"),
									rs.getString("title"),
									rs.getString("description"),
									rs.getString("language"),
									rs.getString("genre"),
									rs.getInt("category"),
									rs.getInt("status"),
									rs.getLong("ID_uploader"),
									new Timestamp(rs.getString("date_status")),
									new Timestamp(rs.getString("date_upload")),
									new Date(rs.getString("date_release")),
									rs.getLong("ID_film"),
									rs.getString("director"),
									rs.getString("productor"),
									rs.getString("mainCast"),
									new Time(rs.getString("duration"))
									);
							return result;
						}
					}
					
					return film;
				}
			}
			
			
			return null;
		}
		
	}
	
	
	public Film UpdateFilm(String description, String Langue, String genre, int Status, String Director, String Productor, String Cast, long id) throws SQLException{
		
		
		try(DB_web_services db = new DB_web_services()){
			
			db.setAutoCommit(false);
			
			if(description !=null){
				//System.out.println("entering the if");
				PreparedStatement stmt = db.getPreparedStatement(Constants.Film.putDescriptionByID);
				stmt.setString(1, description);
				stmt.setLong(2, id);
				
				stmt.executeUpdate();
			}
			
			if(Langue != null){
				PreparedStatement stmt2 = db.getPreparedStatement(Constants.Film.putLangueByID);
				stmt2.setString(1, Langue);
				stmt2.setLong(2, id);
				
				stmt2.executeUpdate();
			}
			
			if(genre != null){
				PreparedStatement stmt3 = db.getPreparedStatement(Constants.Film.putGenreByID);
				stmt3.setString(1, genre);
				stmt3.setLong(2, id);
				
				stmt3.executeUpdate();
			}
			
			if(Status >0){
				PreparedStatement stmt4 = db.getPreparedStatement(Constants.Film.putStatusByID);
				stmt4.setInt(1, Status);
				stmt4.setLong(2, id);
				
				stmt4.executeUpdate();
			}
			
			if(Director != null){
				PreparedStatement stmt5 = db.getPreparedStatement(Constants.Film.putDirectorByID);
				stmt5.setString(1, Director);
				stmt5.setLong(2, id);
				
				stmt5.executeUpdate();
			}
			
			if(Productor != null){
				PreparedStatement stmt6 = db.getPreparedStatement(Constants.Film.putProductorByID);
				stmt6.setString(1, Productor);
				stmt6.setLong(2, id);
				
				stmt6.executeUpdate();
			}
			
			if(Cast != null){
				PreparedStatement stmt7 = db.getPreparedStatement(Constants.Film.putMainCastByID);
				stmt7.setString(1, Cast);
				stmt7.setLong(2, id);
				
				stmt7.executeUpdate();
			}
			
			db.commit();
		}
		
		return films.get(id);
	}
	
	
	public boolean removeFilm(long id)
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){

			Film film = this.films.get(id);
			
			if(film == null)
				throw new DataNotFoundException("The film with the id `" + id + "` doesn't exist !");
						
			PreparedStatement psmt = db.getPreparedStatement(Constants.Multimedia.deleteByID);
			psmt.setLong(1, film.getId_multimedia());
			
			
			int succes = psmt.executeUpdate();
			
			if(succes == 1){
				films.remove(id);
				
				
				return true;
			}
			
			
			return false;
		}
		
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
		List<Film> listTodo = new ArrayList<Film>(this.films.values());
		
		for(Film iterator : listTodo){
			iterator.setAverage(this.CalculatingAverage(iterator.getId_multimedia()));
		}
	}
}
