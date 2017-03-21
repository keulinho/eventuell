package de.eventuell.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.models.Event;
import de.eventuell.models.EventStatus;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class EventEditView {
	@Inject
	private IEventService eventService;
	@Inject
	UserSession session;
	private int eventID;
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
	private boolean permissionOK = true;

	
	public boolean isPermissionOK() {
		return permissionOK;
	}


	public int getEventID() {
		return eventID;
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


	public void setPrice(double price) {
		this.price = price;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public double getPrice() {
		return price;
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

	public String getStartTime() {
		return startTime;
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
		//Wenn das parseInt fehlschlägt, weil kein Query-Parameter in der URL angezeigt wird, wird die Error Nachricht angezeigt
		try {
			Event e = eventService.getEventByID(Integer.parseInt(id));
			if (session.getUser().getManager()&&session.getUser().getUserID()==e.getCreator().getUserID())
			{
				this.eventID = e.getEventID();
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
				this.zipCode = e.getZipCode();
			} else {
				permissionOK = false;
			}
		} catch (NumberFormatException e) {
			permissionOK = false;
		}
		
		
	}
	
	public String editEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		Event e = eventService.getEventByID(Integer.parseInt(id));
		e = editEvent(e);
		eventService.changeEvent(e);
		return "managerIndex.jsf?faces-redirect=true";
	}
	
	public String publishEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		Event e = eventService.getEventByID(Integer.parseInt(id));
		e = editEvent(e);
		e.setStatus(EventStatus.PUBLISHED);
		eventService.changeEvent(e);
		return "managerIndex.jsf?faces-redirect=true";
	}
	
	
	private Event editEvent(Event e) {
		e.setCity(city);
		e.setDescription(description);
		e.setLocation(location);
		e.setMaxTickets(maxTickets);
		e.setPrice(price);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(startDate+startTime, formatter);
		e.setStartDateTime(dateTime);
		e.setStreetNumber(streetNumber);
		e.setTitle(title);
		e.setZipCode(zipCode);
		return e;
	}


	public String deleteEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		eventService.deleteEventByID(Integer.parseInt(id));
		return "managerIndex.jsf?faces-redirect=true";
	}
}
