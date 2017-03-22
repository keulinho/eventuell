package de.eventuell.services;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;
import de.eventuell.services.interfaces.IBookingService;
import de.eventuell.session.UserSession;

@Named
@ApplicationScoped
public class BookingServiceMock implements IBookingService {

	private LinkedList<Booking> allBookings;
	
	private IBookingService bookingService;
	
	@Inject
	UserSession session;	
	
	public BookingServiceMock(){
		allBookings = new LinkedList<Booking>();
		
	}
	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking getBookingByBookingCode(int bookingCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking conductBooking(int amount, Event currentEvent) throws BookingFailedException {
		// TODO Buchung persistieren
		System.out.println("conductBooking");
		double overallPrice = amount * currentEvent.getPrice();
		System.out.println("overall: "+overallPrice);
		System.out.println("amount: "+amount);
		System.out.println("pricePerTicket: "+currentEvent.getPrice());
		Booking booking = new Booking();
		booking.setBookingCode((int) (Math.random()*100000000));
		System.out.println("BookingCode: " + booking.getBookingCode());
		booking.setUser(session.getUser());
		booking.setPrice(overallPrice);
		booking.setEvent(currentEvent);
		System.out.println(currentEvent);
		return booking;
	}

	public IBookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(IBookingService bookingService) {
		this.bookingService = bookingService;
	}

	public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}
	
}
