package rest.service;

import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.model.Comment;
import rest.model.Rate;
import rest.model.VideoGame;
import rest.model.util.Date;
import rest.model.util.Timestamp;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class VideoGameService {
	private Map<Long, VideoGame> videoGames = DB_web_services.getVideoGames();

	/**
	 * Constructor of the class {@link VideoGameService}
	 * 
	 * @throws SQLException
	 */
	public VideoGameService() throws SQLException
	{
		// We initialize some helpful variables
		try(DB_web_services db = new DB_web_services()){
			
			PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.getAll);
			ResultSet rs = ppsm.executeQuery();

			// We empty our current map
			videoGames.clear();

			// As long as the database returns a row, we fill the map
			while (rs.next())
			{
				
				VideoGame videoGame = new VideoGame(
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
						rs.getLong("ID_videoGame"),
						rs.getString("developer"),
						rs.getString("publisher")
						);

				// We put our values in the map
				videoGames.put(rs.getLong("ID_videoGame"), videoGame);
			}
		}
		SetallAverage();
	}

	
	
	
	/**
	 * Gets all the row from the database's table {@link VideoGame}
	 * 
	 * @return All the row from the database's table {@link VideoGame}
	 */
	public List<VideoGame> getAllVideoGames() {
		List<VideoGame> return_videoGames = new ArrayList<VideoGame>(videoGames.values());

		if (return_videoGames.isEmpty())
			throw new DataNotFoundException("No video games was found !");

		return return_videoGames;
	}
	
	public List<VideoGame> filtre(String filtrage) throws SQLException{
		List<VideoGame> result = new ArrayList<VideoGame>();
		
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.getByName);
			ppsm.setString(1, "%"+filtrage+"%");
			ResultSet rs = ppsm.executeQuery();
			
			while(rs.next()){
				VideoGame videoGame = new VideoGame(
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
						rs.getLong("ID_videoGame"),
						rs.getString("developer"),
						rs.getString("publisher")
						);
				
				result.add(videoGame);
			}
		}
		
		return result;
	}
	

	/**
	 * Returns a specific {@link VideoGame} row from the database according to
	 * its ID
	 * 
	 * @param id
	 *            ID of the {@link VideoGame} we want to return
	 * @return A {@link VideoGame} row from the database according to its ID
	 */
	public VideoGame getVideoGame(long id) {
		VideoGame videoGame = this.videoGames.get(id);

		if (videoGame == null)
			throw new DataNotFoundException("The video game with the id `" + id + "` was not found !");

		return videoGame;
	}
	
	
	/**
	 * Returns the number of row in the database's table {@link VideoGame}
	 * 
	 * @return The number of row in the database's table {@link VideoGame}
	 */
	public int getVideoGameCount() {
		return videoGames.size();
	}
	
	public List<Rate> getRate(long id) throws SQLException{
		List<Rate> result = new ArrayList<Rate>();
		
		VideoGame graded = videoGames.get(id);
		
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement psmt = db.getPreparedStatement(Constants.Rate.getByMult);
			psmt.setLong(1, graded.getId_multimedia());
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				result.add(new Rate(rs.getLong("ID_rate"), rs.getInt("value"), new Timestamp(rs.getString("date_creation")), rs.getLong("ID_user"), rs.getLong("ID_multimedia")));
			}
		}
		
		return result;
	}
	
	public List<Comment> getComment(Long id) throws SQLException{
		List<Comment> result = new ArrayList<Comment>();
		
		VideoGame graded = videoGames.get(id);
		
		try(DB_web_services db = new DB_web_services()){
			PreparedStatement psmt = db.getPreparedStatement(Constants.Comment.getByMult);
			psmt.setLong(1, graded.getId_multimedia());
			
			ResultSet rs = psmt.executeQuery();
			
			while(rs.next()){
				result.add(new Comment(rs.getLong("ID_comment"), rs.getString("value"), new Timestamp(rs.getString("date_creation")), rs.getLong("ID_user"), rs.getLong("ID_multimedia")));
			}
		}
		
		return result;
	}

	/**
	 * PLEASE ADD THE DOCUMENTATION PLEASE PLEASE PLEASE
	 */
	public VideoGame addVideoGame(VideoGame videoGame) throws SQLException
	{

		if (videoGame.getTitle() == null || videoGame.getTitle().length() == 0)
			throw new InvalidParameterException("Le champ 'title' ne peut être vide (null)");
		if (videoGame.getLanguage() == null || videoGame.getLanguage().length() == 0)
			throw new InvalidParameterException("Le champ 'language' ne peut être vide (null)");
		if (videoGame.getCategory() == 0)
			throw new InvalidParameterException("Le champ 'Category' ne peut être vide (0)");
		if (videoGame.getStatus() == 0)
			throw new InvalidParameterException("Le champ 'Status' ne peut être vide (0)");
		if (videoGame.getID_uploader() == 0)
			throw new InvalidParameterException("Le champ 'ID_uploader' ne peut être vide (0)");
		

		
		// We initialize some variables
		try(DB_web_services db = new DB_web_services()){
			
			db.setAutoCommit(false);
			
			PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.post);
			
			// We initialize our statement's values
			ppsm.setString(1, videoGame.getTitle());
			ppsm.setString(2, videoGame.getLanguage());
			ppsm.setString(3, videoGame.getGenre());
			ppsm.setInt(4, 3); //category
			ppsm.setInt(5, videoGame.getStatus());
			ppsm.setLong(6, videoGame.getID_uploader());
			ppsm.setString(7, videoGame.getDescription());
			ppsm.setString(8, videoGame.getDate_release().toString());
	
			int succes = ppsm.executeUpdate();
	
			if (succes == 1) {
				ResultSet generated_id = ppsm.getGeneratedKeys();
				
				if(generated_id.next()){
					
					PreparedStatement ppsm2 = db.getPreparedStatement(Constants.VideoGame.post);
					
					// We initialize our statement's values
					
					ppsm2.setString(1, videoGame.getDeveloper());
					ppsm2.setString(2, videoGame.getPublisher());
					ppsm2.setLong(3, generated_id.getLong(1));
			
					int succes2 = ppsm2.executeUpdate();
			
					if (succes2 == 1) {
						ResultSet generated_id2 = ppsm2.getGeneratedKeys();
			
						if (generated_id2.next())
						{
							
							PreparedStatement ppsm3 = db.getPreparedStatement(Constants.VideoGame.getByID);

							ppsm3.setLong(1, generated_id2.getLong(1));
			
							ResultSet rs = ppsm3.executeQuery();
							
							if(rs.next()){
								
								
								return new VideoGame(
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
										rs.getLong("ID_videoGame"),
										rs.getString("developer"),
										rs.getString("publisher")
										);
							}
						}
					}
				}
			}
	
			
			return null;
			
		}
		
	}
	
	
	public boolean updateVideoGame(long id, VideoGame videoGame)
			throws SQLException{
		
		boolean change = false;
		boolean return_value = true;
		
		try(DB_web_services db = new DB_web_services()) {

			db.setAutoCommit(false);

			if(videoGame.getDescription() != null){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putDescriptionByID);
		    	
		    	ppsm.setString(1, videoGame.getDescription());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setDescription(videoGame.getDescription());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			if(videoGame.getLanguage() != null){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putLangueByID);
		    	
		    	ppsm.setString(1, videoGame.getLanguage());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setLanguage(videoGame.getLanguage());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			if(videoGame.getGenre() != null){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putGenreByID);
		    	
		    	ppsm.setString(1, videoGame.getGenre());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setGenre(videoGame.getGenre());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			if(videoGame.getStatus() != 0){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putStatusByID);
		    	
		    	ppsm.setInt(1, videoGame.getStatus());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setStatus(videoGame.getStatus());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			if(videoGame.getDeveloper() != null){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putDeveloperByID);
		    	
		    	ppsm.setString(1, videoGame.getDeveloper());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setDeveloper(videoGame.getDeveloper());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			if(videoGame.getPublisher() != null){
					
		    	PreparedStatement ppsm = db.getPreparedStatement(Constants.VideoGame.putPublisherByID);
		    	
		    	ppsm.setString(1, videoGame.getPublisher());
		    	ppsm.setLong(2, id);
		    	
		    	int rs = ppsm.executeUpdate();

		    	if(rs == 1){
		    		this.videoGames.get(id).setPublisher(videoGame.getPublisher());
		    	}
		    	
		    	return_value = (rs == 1 && return_value == true) ? true : false;
		    	change = return_value;
			}

			db.commit();
			
		}
		
		
		return (return_value && change);
	}

	
	public boolean removeVideoGame(long id)
			throws SQLException{
	
		try(DB_web_services db = new DB_web_services()){
			
			VideoGame videoGame = this.videoGames.get(id);
			
			if(videoGame == null)
				throw new DataNotFoundException("The video game with the id `" + id + "` doesn't exist !");

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Multimedia.deleteByID);
	
	    	ppsm.setLong(1, videoGame.getId_multimedia());
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		videoGames.remove(id);
	    		
	    		
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
		List<VideoGame> listTodo = new ArrayList<VideoGame>(this.videoGames.values());
		
		for(VideoGame iterator : listTodo){
			iterator.setAverage(this.CalculatingAverage(iterator.getId_multimedia()));
		}
	}
}
