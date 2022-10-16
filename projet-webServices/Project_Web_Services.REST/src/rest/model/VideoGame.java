package rest.model;



import rest.model.util.Date;
import rest.model.util.Timestamp;




public class VideoGame extends Multimedia
{
	private long id_videoGame;
	
	private String developer;
	private String publisher;
	
	
	public VideoGame() { }
	
	
	public VideoGame(String title, String description, String language, String genre, int category,
			int status, long ID_uploader, Date release_date, String developer, String publisher)
	{
		super(title, description, language, genre, category, status, ID_uploader, release_date);
		
		this.developer = developer;
		this.publisher = publisher;	
	}	
	
	public VideoGame(long ID_multimedia, String title, String description, String language, String genre, int category,
			int status, long ID_uploader, Timestamp status_date, Timestamp upload_date, Date release_date, long ID_videoGame, String developer, String publisher)
	{	
		super(ID_multimedia, title, description, language, genre, category, status, status_date,  upload_date, release_date, ID_uploader);
		
		this.id_videoGame = ID_videoGame;
		this.developer = developer;
		this.publisher = publisher;
	}
	
	
	
	/**
	 * @return the id_videoGame
	 */
	public long getId_videoGame() { return id_videoGame; }
	/**
	 * @param id_videoGame the id_videoGame to set
	 */
	public void setId_videoGame(long id_videoGame) { this.id_videoGame = id_videoGame; }
	
	
	/**
	 * @return the developer
	 */
	public String getDeveloper() { return developer; }
	/**
	 * @param developper the developer to set
	 */
	public void setDeveloper(String developer) { this.developer = developer; }
	
	
	/**
	 * @return the publisher
	 */
	public String getPublisher() { return publisher; }
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) { this.publisher = publisher; }
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "VideoGame [id_videoGame = " + this.id_videoGame
				+ ", titre = " + this.getTitle()
				+ ", description = " + this.getDescription()
				+ ", language = " + this.getLanguage()
				+ ", genre = " + this.getGenre()
				+ ", category = " + this.getCategory()
				+ ", status = " + this.getStatus()
				+ ", date_upload = " + this.getDate_upload()
				+ ", date_status = " + this.getDate_status()
				+ ", date_release = " + this.getDate_release()
				+ ", developer = " + this.getDeveloper()
				+ ", publisher = " + this.getPublisher()
				+ "]";
	}
}
