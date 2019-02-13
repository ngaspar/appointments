package com.sesame.services.appointments;

import java.util.Date;
import java.util.List;

/**
 * The service layer interface, responsible for defining and isolating the
 * business methods of the application.
 * 
 * @author ngaspar
 * @version 1.0
 */
public interface AppointmentService {

	/**
	 * Deletes an existent appointment from persistence/storage, if it exists.
	 * 
	 * @param id the id of the appointment to delete
	 */
	void deleteAppointment(Long id);

	/**
	 * Creates and persists a new appointment.
	 * 
	 * @param appointmentDate     the appointment date
	 * @param appointmentDuration the appointment duration
	 * @param nameOfDoctor        the name of the doctor
	 * @param status              the starting status of the appointment
	 * @param price               the price for this appointment with this doctor
	 * @return the newly created appointment
	 */
	Appointment createAppointment(Date appointmentDate, Long appointmentDuration, String nameOfDoctor, String status,
			Long price);

	/**
	 * Updates the status of an existing appointment.
	 * 
	 * @param id        the id of the appointment to update
	 * @param newStatus the new status to update the appointment with
	 * @return the updated appointment
	 */
	Appointment updateAppointmentStatus(Long id, String newStatus);

	/**
	 * Retrieves an existing appointment given its id.
	 * 
	 * @param id the id of the appointment to retrieve
	 * @return the appointment with the given id
	 */
	Appointment findAppointmentById(Long id);

	/**
	 * Retrieve all appointments whose appointment date is between a start date and
	 * an end date, ordered by price.
	 * 
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @param orderBy   the order by SQL keyword (either ASC to order by ascending
	 *                  price, or DESC to order by descending price)
	 * @return a list containing all appointments found between the two dates,
	 *         ordered by price according to the SQL keyword
	 */
	List<Appointment> findAllAppointmentsBetweenDateRangeSortByPrice(Date startDate, Date endDate, String order);
}
