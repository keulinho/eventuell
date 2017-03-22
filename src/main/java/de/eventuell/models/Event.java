package de.eventuell.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private int eventID;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private int maxTickets;
	@Column
	private LocalDateTime startDateTime;
	@Column
	private String location;
	@Column
	private String zipCode;
	@Column
	private String city;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User creator;
	@Enumerated(EnumType.STRING)
	@Column
	private EventStatus status;
	@Column
	private String streetNumber;
	@OneToMany(mappedBy="event")
	private List<Booking> bookings;
	@Column
	private double price;

	
	public Event() {}
	
	public Event(EventBuilder eventBuilder) {
		this.price = eventBuilder.getPrice();
		this.title = eventBuilder.getTitle();
		this.description = eventBuilder.getDescription();
		this.maxTickets = eventBuilder.getMaxTickets();
		this.startDateTime = eventBuilder.getStartDateTime();
		this.location = eventBuilder.getLocation();
		this.zipCode = eventBuilder.getZipCode();
		this.city = eventBuilder.getCity();
		this.creator = eventBuilder.getCreator();
		this.status = eventBuilder.getStatus();
		this.streetNumber = eventBuilder.getStreetNumber();
		this.bookings = new LinkedList<>();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public int getEventID() {
		return eventID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public boolean isAgo() {
		if (this.startDateTime.compareTo(LocalDateTime.now()) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public int availableTickets() {
		int bookedTickets = this.bookings.stream().map(e -> e.getAmount()).reduce(0, (x, y) -> x + y);
		return maxTickets - bookedTickets;
	}

	public String availableTicketsAsString() {
		return "" + availableTickets();
	}
}
