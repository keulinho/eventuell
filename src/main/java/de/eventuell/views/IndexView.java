package de.eventuell.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IEventService;

@Named
@RequestScoped
public class IndexView {
	private List<Event> actualEvents;
	private String searchText;
	@Inject
	private IEventService eventService;
	
	
	public IndexView(){
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
		System.out.println("---------PostConstruct");
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
