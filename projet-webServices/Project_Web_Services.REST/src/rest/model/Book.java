
package rest.model;


import rest.model.util.Date;
import rest.model.util.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement
public class Book extends Multimedia{

	private long id_book;
	private String author;
	private String publisher;
	
	public Book() { }
	
	
	public Book(long ID_multimedia, String title,String language, String description, 
			String genre, int category, int status, long ID_upp,
			Timestamp status_date, Timestamp upload_date , Date release, long ID_book, String author, String publisher)
	{
		super(ID_multimedia, title, description, language, genre, category, status, status_date, upload_date, release, ID_upp);
		this.id_book = ID_book;
		this.author = author;
		this.publisher = publisher;	
	}
	
	
	
	public Book(long id_book, String author, String publisher) {
		super();
		this.id_book = id_book;
		this.author = author;
		this.publisher = publisher;
	}


	public Book(long id_book) {
		super();
		this.id_book = id_book;
	}


	/**
	 * @return the id_book
	 */
	public long getId_book() {
		return id_book;
	}
	/**
	 * @param id_book the id_book to set
	 */
	public void setId_book(long id_book) {
		this.id_book = id_book;
	}
	
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
}
