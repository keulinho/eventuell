package de.eventuell.views;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.eventuell.exceptions.EventCreationFailedException;
import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Event;
import de.eventuell.models.EventStatus;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@ManagedBean
@RequestScoped
public class ManagerIndexView {
	private List<Event> actualEvents;
	@ManagedProperty(value = "#{mockEventService}")
	private IEventService eventService;
	@ManagedProperty(value = "#{userSession}")
	UserSession session;
	private String title;
	private String description;
	private int maxTickets;
	private String startDate;
	private String startTime;
	private String location;
	private String zipCode;
	private String city;
	private String streetNumber;
	
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

	public List<Event> getActualEvents() {
		return actualEvents;
	}

	public void setActualEvents(List<Event> actualEvents) {
		this.actualEvents = actualEvents;
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

	@PostConstruct
	public void populateVariables() {
		getAllActualEventsByManager();
	}
	
	public void getAllActualEventsByManager() {
		actualEvents=eventService.getAllActualEventsByActiveManager(session.getUser());
	}
	
	public String createEvent() {
		Event e;
		try {
			e = createEventFromVariables();
			eventService.addEvent(e);
			return "managerIndex.jsf?faces-redirect=true#tab_not-published";
		} catch (EventCreationFailedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event konnte nicht angelegt werden. Bitte Füllen Sie alle Felder aus!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerIndex.jsf?#tab_new-event";
		}
	}

	private Event createEventFromVariables() throws EventCreationFailedException {
		if (!(title.isEmpty()||city.isEmpty()||startDate.isEmpty()||startTime.isEmpty()||description.isEmpty()||location.isEmpty()||maxTickets<1||streetNumber.isEmpty()||zipCode.isEmpty()))
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(startDate+startTime, formatter);
			Event e = new Event();
			e.setTitle(title);
			e.setCity(city);
			e.setDescription(description);
			e.setLocation(location);
			e.setMaxTickets(maxTickets);
			e.setStartDateTime(dateTime);
			e.setStatus(EventStatus.CREATED);
			e.setStreetNumber(streetNumber);
			e.setZipCode(zipCode);
			e.setCreator(session.getUser());
			return e;
		} else {
			throw new EventCreationFailedException();
		}
	}
	
	public String publishEvent() {
		Event e;
		try {
			e = createEventFromVariables();
			e.setStatus(EventStatus.PUBLISHED);
			eventService.addEvent(e);
			return "managerIndex.jsf?faces-redirect=true";
		} catch (EventCreationFailedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event konnte nicht angelegt werden. Bitte Füllen Sie alle Felder aus!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "managerIndex.jsf?#tab_new-event";
		}
		
	}
}
