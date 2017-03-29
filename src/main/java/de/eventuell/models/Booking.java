package de.eventuell.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private int bookingCode;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	@ManyToOne
	@JoinColumn(name="EVENT_ID")
	private Event event;
	@Column
	private int amount;
	
	public int getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(int bookingCode) {
		this.bookingCode = bookingCode;
	}
	
	public String getBookingCodeAsString(){
		return String.valueOf(bookingCode);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double calculateOverallPrice(){
		return amount * event.getPrice();
	}
	
	public String calculateOverallPriceAsString(){
		return String.valueOf(calculateOverallPrice());
	}
	
	
	
}
