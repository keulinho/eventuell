package de.eventuell.views;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.exceptions.EventCreationFailedException;
import de.eventuell.models.Event;
import de.eventuell.models.EventBuilder;
import de.eventuell.models.EventStatus;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@Named
@RequestScoped
public class ManagerIndexView {
	private List<Event> actualEvents;
	private List<Event> createdEvents;
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
	//wird für die navigation im Reiter des Managerbereiches benötigt
	//clientseitige (javascript) auswertung welches Tab active ist
	private String hash;
	
	
	
	public List<Event> getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(List<Event> createdEvents) {
		this.createdEvents = createdEvents;
	}

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
		//Initialisiert alle Klassenvariablen, damit die Templates auf diese zugreifen können
		getAllActualEventsByManager();
		getAllNotPublishedEventsByManager();
	}
	
	private void getAllNotPublishedEventsByManager() {
		createdEvents=eventService.getAllNotPublishedEventsByManager(session.getUser());
		
	}

	public void getAllActualEventsByManager() {
		actualEvents=eventService.getAllActualEventsByActiveManager(session.getUser());
	}
	
	public String createEvent() {
		Event e;
		try {
			e = createEventFromVariables();
			eventService.addEvent(e);
			populateVariables();
			hash = "tab-not-published";
			return "managerIndex.jsf";
		} catch (EventCreationFailedException e1) {
			e1.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event konnte nicht angelegt werden. Bitte Füllen Sie alle Felder aus!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			hash = "tab-new-event";
			return "managerIndex.jsf";
		}
	}

	private Event createEventFromVariables() throws EventCreationFailedException {
		if (!(title.isEmpty()||city.isEmpty()||startDate.isEmpty()||startTime.isEmpty()||description.isEmpty()||location.isEmpty()||maxTickets<1||streetNumber.isEmpty()||zipCode.isEmpty()||price<1.0))
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(startDate+startTime, formatter);
			EventBuilder eb = new EventBuilder();
			Event e = eb.setTitle(title)
			.setCity(city)
			.setDescription(description)
			.setLocation(location)
			.setMaxTickets(maxTickets)
			.setStartDateTime(dateTime)
			.setStatus(EventStatus.CREATED)
			.setStreetNumber(streetNumber)
			.setZipCode(zipCode)
			.setPrice(price)
			.setCreator(session.getUser())
			.build();
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
			hash = "tab-actual-events";
			populateVariables();
			return "managerIndex.jsf?faces-redirect=true";
		} catch (EventCreationFailedException e1) {
			e1.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event konnte nicht angelegt werden. Bitte Füllen Sie alle Felder aus!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			hash = "tab-new-event";
			return "managerIndex.jsf";
		}
		
	}
	
	public String publishCreatedEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		Event e = eventService.getEventByID(Integer.parseInt(id));
		e.setStatus(EventStatus.PUBLISHED);
		eventService.changeEvent(e);
		hash = "tab-actual-events";
		return "managerIndex.jsf?faces-redirect=true";
	}
	
	public String deleteEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		eventService.deleteEventByID(Integer.parseInt(id));
		populateVariables();
		hash = "tab-not-published";
		return "managerIndex.jsf";
	}
}
