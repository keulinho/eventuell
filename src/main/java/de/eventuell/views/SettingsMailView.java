package de.eventuell.views;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SettingsMailView {
	
	private String oldMail;
	private String newMail;
	
	public SettingsMailView() {
		
	}
	
}
