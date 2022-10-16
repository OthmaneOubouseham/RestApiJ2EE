package rest.service;

import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.exception.InvalidPasswordException;
import rest.model.User;
import rest.model.util.Timestamp;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class UserService {
	
	private Map<Long, User> users = DB_web_services.getUsers();
	
	
	public UserService()
			throws SQLException
	{
    	try(DB_web_services db = new DB_web_services()){
	    	
        	PreparedStatement ppsm = db.getPreparedStatement(Constants.User.getAll);
        	
        	ResultSet rs = ppsm.executeQuery();
        	users.clear();
        	
        	while(rs.next()){
        		users.put(rs.getLong("ID_user"), new User(rs.getLong("ID_user"), rs.getString("pseudo"), rs.getString("email"), new Timestamp(rs.getString("date_creation"))));
        	}
    	}
	}

	public List<User> getAllUsers()
	{
		List<User> return_users = new ArrayList<User>(this.users.values());
		
		if(return_users.isEmpty())
			throw new DataNotFoundException("No users was found !");
		
		
    	return return_users;
	}
	

	public User getUser(long id){
		User user = users.get(id);

		if(user == null)
			throw new DataNotFoundException("The user with the id `" + id + "` was not found !");
		
		
		return user;
	}
	
	
	public int getUserCount(){
		
		
		return users.size();
	}
	
	
	public User login(String username, String password)
			throws SQLException{

		try(DB_web_services db = new DB_web_services()){
			
			PreparedStatement ppsm = db.getPreparedStatement(Constants.User.checkPasswordByPseudo);

			ppsm.setString(1, username);
			ppsm.setString(2, password);
			
			ResultSet rs = ppsm.executeQuery();
			
			if(rs.next()){
				long id_user = rs.getLong(1);
				
				
				return users.get(id_user);
			}
			
			throw new DataNotFoundException("Invalide credentials !");
		}
	}
	
	
	public User addUser(User user)
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){
			
			if(user == null){
				throw new InvalidParameterException("Un user doit être donné en paramètre !");
			}
	    	
			PreparedStatement ppsm = db.getPreparedStatement(Constants.User.post);
	    	
	    	ppsm.setString(1, user.getPseudo());
	    	ppsm.setString(2, user.getPassword());
	    	ppsm.setString(3, user.getEmail());
	    	
	    	int rs = ppsm.executeUpdate();

	    	if(rs == 1){
	    		ResultSet generated_id = ppsm.getGeneratedKeys();
	    		
	    		if(generated_id.next()){
	    			PreparedStatement ppsm2 = db.getPreparedStatement(Constants.User.getByID);
	    			
	    			ppsm2.setLong(1, generated_id.getLong(1));
	    			
	    			ResultSet rs_user = ppsm2.executeQuery();
	    	    	
	    	    	if(rs_user.next()){
	    	    		User new_user = new User(rs_user.getLong("ID_user"), rs_user.getString("pseudo"), rs_user.getString("email"), new Timestamp(rs_user.getString("date_creation")));
	    	    		
	    	    		this.users.put(rs_user.getLong("ID_user"), new_user);
	    	    		
	    	    		
	    	    		return new_user;
	    	    	}
	    		}
	    	}
	    	
	    	
	    	return null;
	    	
		}
		
	}
	
	
	public boolean checkUserPassword(long id, String password)
			throws SQLException{

		try(DB_web_services db = new DB_web_services()){

			PreparedStatement ppsm = db.getPreparedStatement(Constants.User.checkPasswordByID);

			ppsm.setString(1, password);
			ppsm.setLong(2, id);
			
			ResultSet rs_check = ppsm.executeQuery();
			
			
			if(rs_check.next() && rs_check.getInt(1) == 1){
				return true;
			}
			
			
			return false;
		}
	}
	
	
	public boolean updateUser(long id, String password, String new_password, String email) 
			throws SQLException, InvalidPasswordException{
		
		try(DB_web_services db = new DB_web_services()) {

			db.setAutoCommit(false);
			
			boolean change = false;
			boolean return_value = true;
			
			if(password != null){

				if(new_password == null){
					throw new SQLException("Le champ 'new_password' ne peut être vide (null)");
				}

				
				if(checkUserPassword(id, password)){
					
			    	PreparedStatement ppsm2 = db.getPreparedStatement(Constants.User.putPasswordByID);
			    	
			    	ppsm2.setString(1, new_password);
			    	ppsm2.setLong(2, id);
			    	
			    	int succes = ppsm2.executeUpdate();

			    	if(succes == 1){
			    		User user = this.users.get(id);

			    		user.setPassword(new_password);
			    	}
			    	
			    	return_value = (succes == 1 && return_value == true) ? true : false;
			    	change = return_value;
				}
				
				else
					throw new InvalidPasswordException();
			}
			
			if(email != null){		

				PreparedStatement ppsm3 = db.getPreparedStatement(Constants.User.putEmailByID);

				ppsm3.setString(1, email);
				ppsm3.setLong(2, id);
				
				int succes = ppsm3.executeUpdate();

				if(succes == 1){
					User user = this.users.get(id);

					user.setEmail(email);
				}
				

				return_value = (succes == 1 && return_value == true) ? true : false;
				change = return_value;
			}

			db.commit();
			
			
			return (return_value && change);
			
		}
	}
	
	
	public boolean removeUser(long id) 
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){

			if(this.users.get(id) == null){
				throw new DataNotFoundException("The user with the id `" + id + "` doesn't exist !");
			}
			
			PreparedStatement ppsm = db.getPreparedStatement(Constants.User.deleteByID);

			ppsm.setLong(1, id);
			
			int rs = ppsm.executeUpdate();

			if(rs == 1){
				users.remove(id);
				
				
				return true;
			}
			
			return false;
		}
	}
}
