package rest.model;


import javax.xml.bind.annotation.XmlRootElement;

import rest.model.util.Date;
import rest.model.util.Time;
import rest.model.util.Timestamp;




@XmlRootElement
public class Film extends Multimedia{
	
	private long id_film;
	
	private String director;
	private String productor;
	private String mainCast;
	
	private Time duration;
	
	public Film(){
		
	}

	public Film(String title, String description, String language, String genre, int category,
			int status, long ID_upp, String dir, String prod, String cast, Time dur, Date release){
		super(title, description, language, genre, category, status, ID_upp, release);
		
		this.director = dir;
		this.productor = prod;
		this.mainCast = cast;
		this.duration = dur;
	}
	
	
	public Film(long ID_multimedia, String title, String description, String language, String genre, int category,
			int status, long ID_uploader, Timestamp status_date, Timestamp upload_date, Date release_date,
			long ID_film, String dir, String prod, String cast, Time dur)
	{	
		super(ID_multimedia, title, description, language, genre, category, status, status_date,  upload_date, release_date, ID_uploader);

		this.id_film = ID_film;
		this.director = dir;
		this.productor = prod;
		this.mainCast = cast;
		this.duration = dur;
	}
	
	
	
	
	/**
	 * @return the id_film
	 */
	public long getId_film() {
		return id_film;
	}

	/**
	 * @param id_film the id_film to set
	 */
	public void setId_film(long id_film) {
		this.id_film = id_film;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the productor
	 */
	public String getProductor() {
		return productor;
	}

	/**
	 * @param productor the productor to set
	 */
	public void setProductor(String productor) {
		this.productor = productor;
	}

	/**
	 * @return the mainCast
	 */
	public String getMainCast() {
		return mainCast;
	}

	/**
	 * @param mainCast the mainCast to set
	 */
	public void setMainCast(String mainCast) {
		this.mainCast = mainCast;
	}

	/**
	 * @return the duration
	 */
	public Time getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Time duration) {
		this.duration = duration;
	}

}
