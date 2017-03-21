package de.eventuell.views;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class EventEditView {
	@Inject
	private IEventService eventService;
	@Inject
	UserSession session;
	private String title;
	private String description;
	private int maxTickets;
	private double price;
	private String startDate;
	private String startTime;
	private String location;
	private String zipCode;
	private String city;
	private String streetNumber;
	private String hash;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	
	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}
	
	public IEventService getEventService() {
		return eventService;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
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

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public String getStreetNumber() {
		return streetNumber;
	}
	
	public void getCurrentEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		Event e = eventService.getEventByID(Integer.parseInt(id));
		this.title = e.getTitle();
		this.description = e.getDescription();
		this.city = e.getCity();
		this.location = e.getLocation();
		this.maxTickets = e.getMaxTickets();
		this.price = e.getPrice();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.startDate = e.getStartDateTime().format(dtf);
		dtf = DateTimeFormatter.ofPattern("HH:mm");
		this.startTime = e.getStartDateTime().format(dtf);
		this.streetNumber = e.getStreetNumber();
	}
	
	public String publishEvent() {
		return null;
	}
	
	
	public String deleteEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		eventService.deleteEventByID(Integer.parseInt(id));
		hash = "tab-not-published";
		return "managerIndex.jsf?faces-redirect=true";
	}
}
