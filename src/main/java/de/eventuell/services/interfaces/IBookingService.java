package de.eventuell.services.interfaces;

import java.util.List;

import de.eventuell.models.Booking;

public interface IBookingService {

	List<Booking> getAllBookings();
	Booking getBookingByBookingCode(int bookingCode);
	String conductBooking();
}
