package rest.model;


import java.util.ArrayList;
import java.util.List;

import rest.model.util.Timestamp;
import rest.model.util.Link;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;




@XmlRootElement
public class User {
	
	private long id_user;
	
	private String pseudo;
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String new_password;
	
	private Timestamp creation_date;
	
	private List<Link> links = new ArrayList<Link>();
	
	

	public User() {	}
	
	public User(long id_user, String pseudo, String email, Timestamp creation_date) {
		this.id_user = id_user;
		this.pseudo = pseudo;
		this.email = email;
		this.creation_date = creation_date;
	}

	/**
	 * @return the id_user
	 */
	public long getId_user() {
		return id_user;
	}

	/**
	 * @param id_user the id_user to set
	 */
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the new_password
	 */
	public String getNew_password() {
		return this.new_password;
	}


	/**
	 * @param password the password to set
	 */
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}


	/**
	 * @return the creation_date
	 */
	public Timestamp getCreation_date() {
		return creation_date;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id_user=" + this.id_user + ", pseudo=" + this.pseudo + ", email=" + this.email + ", creation_date="
				+ this.creation_date + "]";
	}
}
