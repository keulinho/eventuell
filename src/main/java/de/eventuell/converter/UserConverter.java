package de.eventuell.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import de.eventuell.models.User;
import de.eventuell.services.interfaces.IUserService;

@ManagedBean
public class UserConverter implements Converter{

	@Inject
	private IUserService userService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		User user = userService.getUserByMail(value);
		return (user != null) ? user : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (value != null && value instanceof User) ? ((User) value).getEmail() : null;
	}

}
