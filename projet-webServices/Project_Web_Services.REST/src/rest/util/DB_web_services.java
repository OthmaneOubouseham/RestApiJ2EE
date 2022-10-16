package rest.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import rest.model.Book;
import rest.model.Multimedia;
import rest.model.Rate;
import rest.model.Comment;
import rest.model.User;
import rest.model.VideoGame;




public class DB_web_services implements AutoCloseable{
	
    private final Connection conn;

    
    /** Default constructor of the DBaction Class
     * 
     */
    public DB_web_services() throws SQLException
    {    
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        	System.out.println(ex.getMessage());
        }
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_services?serverTimezone=UTC", "root", "admin");
    }
    
    
    
    /** Try to prepare a Statement from the database
     * 
     * @param query SQL query to execute
     * @return The Statement created from the database
     */
    public PreparedStatement getPreparedStatement(String query)
            throws SQLException
    {
        return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }
    
    
    
    /** Try to define if the query should be auto commit
     * 
     * @param bool the boolean to define if the query should be auto commit
     */
    public void setAutoCommit(boolean bool)
            throws SQLException
    {
        this.conn.setAutoCommit(bool);
    }
    
    
    
    /** Try to commit queries
     * 
     */
    public void commit()
            throws SQLException
    {
        this.conn.commit();
		this.conn.setAutoCommit(true);
    }
    
    
    
    /** Try to rollback queries
     * 
     */
    public void rollback()
            throws SQLException
    {
        this.conn.rollback();
    }
    
    
    
    /** Try to close the connection to the database
     * 
     */
	@Override
	public void close()
			throws SQLException {
    	if(!this.conn.isClosed()){
    		if(!this.conn.getAutoCommit())
    			this.conn.rollback();
    		this.conn.close();    		
    	}
	}
    
    
    private static Map<Long, User> users = new HashMap<Long, User>();
    private static Map<Long, Rate> rates = new HashMap<Long, Rate>();
    private static Map<Long, Comment> comments = new HashMap<Long, Comment>();

    private static Map<Long, Multimedia> multimedias = new HashMap<Long, Multimedia>();
    private static Map<Long, Book> books = new HashMap<Long, Book>();
    private static Map<Long, VideoGame> videoGames = new HashMap<Long, VideoGame>();
    

    
    public static Map<Long, User> getUsers(){
    	return DB_web_services.users;
    }
    
    
    public static Map<Long, Rate> getRates(){
    	return DB_web_services.rates;
    }
    
    
    public static Map<Long, Comment> getComments(){
    	return DB_web_services.comments;
    }
    
    
    public static Map<Long, Multimedia> getMultimedias(){
    	return DB_web_services.multimedias;
    }

    public static Map<Long, Book> getBooks(){
    	return DB_web_services.books;
    }

    public static Map<Long, VideoGame> getVideoGames(){
    	return DB_web_services.videoGames;
    }
    
    
    
    
    
}