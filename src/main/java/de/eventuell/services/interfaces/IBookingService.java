package de.eventuell.services.interfaces;

import de.eventuell.exceptions.BookingFailedException;
import de.eventuell.models.Booking;
import de.eventuell.models.Event;

public interface IBookingService {

	Booking getBookingByBookingCode(int bookingCode);
	Booking persistBooking(int amount, Event currentEvent) throws BookingFailedException;
}
