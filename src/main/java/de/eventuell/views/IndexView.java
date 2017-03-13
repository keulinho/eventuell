package de.eventuell.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@ViewScoped
public class IndexView {
	private List<Event> actualEvents;
	private String searchText;
	@ManagedProperty(value = "#{mockEventService}")
	private IEventService eventService;
	
	
	public IndexView() throws LoginFailedException {
	}

	public IEventService getEventService() {
		return eventService;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	
	public String getSearchText() {
		return searchText;
	}

	public String search() {
		actualEvents = eventService.searchAllActualEvents(searchText);
		return "index.jsf";
	}
	public List<Event> getActualEvents() {
		return actualEvents;
	}
	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}
	
	@PostConstruct
	public void populateVariables() {
		getAllActualEvents();
	}
	
	public void getAllActualEvents() {
		actualEvents = eventService.getAllActualEvents();
	}
	
	
	public String removeSearch() {
		searchText="";
		getAllActualEvents();
		return "index.jsf?faces-redirect=true";
	}
	
}
