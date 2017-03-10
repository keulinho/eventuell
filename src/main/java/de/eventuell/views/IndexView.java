package de.eventuell.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.eventuell.exceptions.LoginFailedException;
import de.eventuell.models.Event;
import de.eventuell.services.MockEventService;
import de.eventuell.services.interfaces.IEventService;

@ManagedBean
@RequestScoped
public class IndexView {
	private List<Event> actualEvents;
	private String searchText;
	private IEventService eventService;
	
	
	public IndexView() throws LoginFailedException {
		eventService=new MockEventService();
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
	public void setEventService(MockEventService eventService) {
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
