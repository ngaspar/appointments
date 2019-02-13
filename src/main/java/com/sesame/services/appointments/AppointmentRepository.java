package com.sesame.services.appointments;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Domain model repository interface.
 * 
 * @author ngaspar
 * @version 1.0
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

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
	List<Appointment> findAllByAppointmentDateGreaterThanEqualAndAppointmentDateLessThanEqual(
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Sort orderBy);

}