package de.eventuell.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -111473571501385550L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private int userID;
	@Column
	private String email;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String password;
	@Column
	private boolean manager;
	@OneToMany(mappedBy="user")
	private List<Booking> bookings;
	@OneToMany(mappedBy="creator")
	private List<Event> events;
	
	public boolean evaluateCredentials(String email, String password) {
		return (this.email.equals(email) && this.password.equals(password)) ? true : false;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 	
	public boolean getManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
}
