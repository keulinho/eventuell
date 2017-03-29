package de.eventuell.services.interfaces;

import java.util.List;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;

public interface IBookingService {

	List<Booking> getAllBookings();
	Booking getBookingByBookingCode(int bookingCode);
	Booking conductBooking(int amount, double pricePerTicket, Event currentEvent) throws BookingFailedException;
}
