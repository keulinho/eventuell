package de.eventuell.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import javax.annotation.PostConstruct;
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
	private Event event;
	private String startDate;
	private String startTime;
	private boolean permissionOK = true;

	
	public boolean isPermissionOK() {
		return permissionOK;
	}


	

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
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


	public String getStartDate() {
		return startDate;
	}

	public String getStartTime() {
		return startTime;
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@PostConstruct
	public void getCurrentEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		//Wenn das parseInt fehlschlägt, weil kein Query-Parameter in der URL angezeigt wird, wird die Error Nachricht angezeigt
		try {
			Event e = eventService.getEventByID(Integer.parseInt(id));
			if (session.getUser().getManager()&&session.getUser().getUserID()==e.getCreator().getUserID())
			{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				this.startDate = e.getStartDateTime().format(dtf);
				dtf = DateTimeFormatter.ofPattern("HH:mm");
				this.startTime = e.getStartDateTime().format(dtf);
				event = e;
			} else {
				permissionOK = false;
			}
		} catch (NumberFormatException e) {
			permissionOK = false;
		}
		
		
	}
	
	public String editEvent() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(startDate+startTime, formatter);
		event.setStartDateTime(dateTime);
		eventService.changeEvent(event);
		return "managerIndex.jsf?faces-redirect=true";
	}
	
	public String publishEvent() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(startDate+startTime, formatter);
		event.setStartDateTime(dateTime);
		event.setStatus(EventStatus.PUBLISHED);
		eventService.changeEvent(event);
		return "managerIndex.jsf?faces-redirect=true";
	}

	public String deleteEvent() {
		Map<String, String> urlParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = urlParameter.get("id");
		eventService.deleteEventByID(Integer.parseInt(id));
		return "managerIndex.jsf?faces-redirect=true";
	}
}
