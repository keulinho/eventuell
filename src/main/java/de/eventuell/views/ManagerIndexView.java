package de.eventuell.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IEventService;
import de.eventuell.session.UserSession;

@ManagedBean
@RequestScoped
public class ManagerIndexView {
	private List<Event> actualEvents;
	private IEventService eventService;
	@ManagedProperty(value = "#{userSession}")
	UserSession session;
	
	
	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	public ManagerIndexView() throws LoginFailedException {
		eventService=new MockEventService();
	}

	public List<Event> getActualEvents() {
		return actualEvents;
	}

	public void setActualEvents(List<Event> actualEvents) {
		this.actualEvents = actualEvents;
	}
	
	@PostConstruct
	public void populateVariables() {
		getAllActualEventsByManager();
	}
	
	public void getAllActualEventsByManager() {
		actualEvents=eventService.getAllActualEventsByActiveManager(session.getUser());
	}
}
