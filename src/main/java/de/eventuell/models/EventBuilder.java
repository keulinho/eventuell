package de.eventuell.models;

import java.time.LocalDateTime;
import java.util.List;

public class EventBuilder {
	
	private String title = "";
	private String description = "";
	private int maxTickets = 0;
	private LocalDateTime startDateTime;
	private String location = "";
	private String zipCode = "";
	private String city = "";
	private User creator;
	private EventStatus status;
	private String streetNumber = "";
	private double price = 0;
	
	public EventBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	public EventBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	public EventBuilder setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
		return this;
	}
	public EventBuilder setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
		return this;
	}
	public EventBuilder setLocation(String location) {
		this.location = location;
		return this;
	}
	public EventBuilder setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}
	public EventBuilder setCity(String city) {
		this.city = city;
		return this;
	}
	public EventBuilder setCreator(User creator) {
		this.creator = creator;
		return this;
	}
	public EventBuilder setStatus(EventStatus status) {
		this.status = status;
		return this;
	}
	public EventBuilder setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
		return this;
	}
	public EventBuilder setPrice(double price) {
		this.price = price;
		return this;
	}
	
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public int getMaxTickets() {
		return maxTickets;
	}
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public String getLocation() {
		return location;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getCity() {
		return city;
	}
	public User getCreator() {
		return creator;
	}
	public EventStatus getStatus() {
		return status;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public double getPrice() {
		return price;
	}
	public Event build() {
		return new Event(this);
	}
	
	
}
