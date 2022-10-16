package rest.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rest.exception.DataNotFoundException;
import rest.model.Comment;
import rest.model.util.Timestamp;
import rest.service.util.Constants;
import rest.util.DB_web_services;

public class CommentService {
	
	private Map<Long, Comment> comments = DB_web_services.getComments();
	
	
	public CommentService()
			throws SQLException
	{    	
		try(DB_web_services db = new DB_web_services()){
	    	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Comment.getAll);
	    	
	    	ResultSet rs = ppsm.executeQuery();
	    	this.comments.clear();
	    	
	    	while(rs.next()){
	    		this.comments.put(rs.getLong("ID_comment"), new Comment(rs.getLong("ID_comment"), rs.getString("value"), new Timestamp(rs.getString("date_creation")), rs.getLong("ID_user"), rs.getLong("ID_multimedia")));
	    	}
		}
	}

	public List<Comment> getAllComments()
	{
		List<Comment> return_comments = new ArrayList<Comment>(this.comments.values());
		
		if(return_comments.isEmpty())
			throw new DataNotFoundException("No comments was found !");
		
		
    	return return_comments;	
	}
	

	public Comment getComment(long id){
		Comment comment = comments.get(id);

		if(comment == null)
			throw new DataNotFoundException("The comment with the id `" + id + "` was not found !");
		
		
		return comment;
	}
	
	
	public int getCommentCount(){
		return this.comments.size();
	}
	
	
	public Comment addComment(String value, long id_user, long id_multimedia) 
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){
    	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Comment.post);
	    	
	    	ppsm.setString(1, value);
	    	ppsm.setLong(2, id_user);
	    	ppsm.setLong(3, id_multimedia);
	    	
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		ResultSet genecommentd_id = ppsm.getGeneratedKeys();
	    		
	    		if(genecommentd_id.next()){
	    			ppsm = db.getPreparedStatement(Constants.Comment.getByID);
	    			
	    			ppsm.setLong(1, genecommentd_id.getLong(1));
	    			
	    			ResultSet rs_comment = ppsm.executeQuery();
	    	    	
	    	    	if(rs_comment.next()){
	    	    		Comment comment = new Comment(rs_comment.getLong("ID_comment"), rs_comment.getString("value"), new Timestamp(rs_comment.getString("date_creation")), rs_comment.getLong("ID_user"), rs_comment.getLong("ID_multimedia"));
	    	    		this.comments.put(rs_comment.getLong("ID_comment"), comment);
	    	    		
	    	    		
	    	    		return comment;
	    	    	}
	    		}
	    	}
	
	    	
	    	return null;
		}
		
	}
	
	
	public boolean updateComment(long id, String value) 
			throws SQLException{

		try(DB_web_services db = new DB_web_services()){
    	
	
	    	PreparedStatement ppsm = db.getPreparedStatement(Constants.Comment.putByID);
	        	
	    	ppsm.setString(1, value);
	    	ppsm.setLong(2, id);
	
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		Comment comment = getComment(id);
	    		
	    		comment.setValue(value);
	    	}
	    	
	    	
	    	return (rs == 1) ? true : false;
		}
		
	}
	
	
	public boolean removeComment(long id) 
			throws SQLException{
		
		try(DB_web_services db = new DB_web_services()){

			PreparedStatement ppsm = db.getPreparedStatement(Constants.Comment.deleteByID);
	
	    	ppsm.setLong(1, id);
	    	
	    	int rs = ppsm.executeUpdate();
	
	    	if(rs == 1){
	    		this.comments.remove(id);
	    	}
	    	
	    	
	    	return (rs == 1) ? true : false;
		}
		
	}
}
