package rest.model;


import rest.model.util.Date;
import rest.model.util.Link;
import rest.model.util.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;




@XmlRootElement
public class Multimedia{

	private long id_multimedia;
	
	private String title;
	private String description;
	private String language;
	private String genre;
	private int category;
	
	private int status;
	private Timestamp date_status;
	
	private Timestamp date_upload;
	private Date date_release;

	private long Average;
	
	@JsonIgnore
	private long ID_uploader;
	

	private List<Link> links = new ArrayList<Link>();
	
	
	
	public Multimedia() { }
	

	public Multimedia(long id_multimedia, String title, String description, String language, String genre, int category,
			int status, Timestamp status_date, Timestamp upload_date, Date release_date, long ID_uploader)
	{
		this.id_multimedia = id_multimedia;
		this.title = title;
		this.description = description;
		this.language = language;
		this.genre = genre;
		this.category = category;
		this.status = status;
		this.date_status = status_date;
		this.date_upload = upload_date;
		this.date_release = release_date;
		this.ID_uploader = ID_uploader;
	}
	

	public Multimedia(String title, String description, String language, String genre, int category,
			int status, long ID_uploader, Date release){
		
		this.title = title;
		this.description = description;
		this.language = language;
		this.genre = genre;
		this.category = category;
		this.status = status;
		this.ID_uploader = ID_uploader;
		this.date_release = release;
	}



	/**
	 * @return the id_multimedia
	 */
	public long getId_multimedia() {
		return id_multimedia;
	}

	/**
	 * @param id_multimedia the id_multimedia to set
	 */
	public void setId_multimedia(long id_multimedia) {
		this.id_multimedia = id_multimedia;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the status_date
	 */
	public Timestamp getDate_status() {
		return date_status;
	}

	/**
	 * @param date_status the status_date to set
	 */
	public void setDate_status(Timestamp date_status) {
		this.date_status = date_status;
	}

	/**
	 * @return the upload_date
	 */
	public Timestamp getDate_upload() {
		return date_upload;
	}

	/**
	 * @param date_upload the upload_date to set
	 */
	public void setDate_upload(Timestamp date_upload) {
		this.date_upload = date_upload;
	}

	/**
	 * @return the release_date
	 */
	public Date getDate_release() {
		return date_release;
	}

	/**
	 * @param date_release the release_date to set
	 */
	public void setDate_release(Date date_release) {
		this.date_release = date_release;
	}

	/**
	 * @return the uploader's ID
	 */
	@JsonIgnore
	public long getID_uploader() {
		return ID_uploader;
	}

	/**
	 * @param ID_uploader the uploader's ID to set
	 */
	@JsonProperty
	public void setID_uploader(long ID_uploader) {
		this.ID_uploader = ID_uploader;
	}
	
	
	

	/**
	 * @return the links
	 */
	public List<Link> getLinks()
	{
		return links;
	}


	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Link> links)
	{
		this.links = links;
	}


	public long getAverage() {
		return Average;
	}


	public void setAverage(long average) {
		Average = average;
	}


	/**
	 * @param link the link to add
	 */
	public void addLink(String rel, String href)
	{
		Link link = new Link(rel, href);
		this.links.add(link);
	}
	
}
