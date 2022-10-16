package rest.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import rest.model.util.Link;
import rest.model.util.Timestamp;




@XmlRootElement
public class Rate {

	private long id_rate;
	
	private int value;
	private Timestamp creation_date;

	@JsonProperty(access = Access.WRITE_ONLY)
	private long id_user;
	@JsonProperty(access = Access.WRITE_ONLY)
	private long id_multimedia;
	
	private List<Link> links = new ArrayList<Link>();
	
	
	
	
	public Rate() { }
	
	
	public Rate(long id_rate, int value, Timestamp creation_date, long id_user, long id_multimedia){
		this.id_rate = id_rate;
		this.value = value;
		this.creation_date = creation_date;
		this.id_user = id_user;
		this.id_multimedia = id_multimedia;
	}
	
	/**
	 * @return the id_rate
	 */
	public long getId_rate() {
		return id_rate;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * @return the creation_date
	 */
	public Timestamp getCreation_date() {
		return creation_date;
	}


	/**
	 * @param creation_date the creation_date to set
	 */
	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}


	/**
	 * @return the user
	 */
	public long getId_user() {
		return this.id_user;
	}
	
	/**
	 * @return the multimedia
	 */
	public long getId_multimedia() {
		return this.id_multimedia;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}


	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}


	/**
	 * @param link the link to add
	 */
	public void addLink(String rel, String href) {
		Link link = new Link(rel, href);
		this.links.add(link);
	}
	
}
