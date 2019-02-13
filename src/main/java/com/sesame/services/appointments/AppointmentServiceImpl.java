package com.sesame.services.appointments;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service layer for this application - implements the
 * defined methods while enforcing the appointment application business rules.
 * 
 * @author ngaspar
 * @version 1.0
 */
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository repository;

	private EntityNotFoundException appointmentEntityNotFoundException(Long id) {
		return new EntityNotFoundException(String.format(BusinessRules.ERRORMSGS_NOT_FOUND, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sesame.services.appointments.AppointmentService#deleteAppointment(java.
	 * lang.Long)
	 */
	@Override
	public void deleteAppointment(Long id) {
		Appointment appointment = findAppointmentById(id);
		repository.delete(appointment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sesame.services.appointments.AppointmentService#createAppointment(java.
	 * util.Date, java.lang.Long, java.lang.String, java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	public Appointment createAppointment(Date appointmentDate, Long appointmentDuration, String nameOfDoctor,
			String status, Long price) {

		String errorMessage = null;
		if (!BusinessRules.isValidAppointmentDate(appointmentDate)) {
			errorMessage = BusinessRules.ERRORMSG_INVALID_DATE;
		}
		if (!BusinessRules.isValidAppointmentDuration(appointmentDuration)) {
			errorMessage = BusinessRules.ERRORMSG_INVALID_DURATION;
		}
		if (nameOfDoctor == null || nameOfDoctor.trim().isEmpty()) {
			errorMessage = BusinessRules.ERRORMSG_INVALID_DOCTOR_NAME;
		}
		if (!BusinessRules.isValidAppointmentStatus(status)) {
			errorMessage = BusinessRules.ERRORMSG_INVALID_STATUS;
		}
		if (!BusinessRules.isValidAppointmentPrice(price)) {
			errorMessage = BusinessRules.ERRORMSG_INVALID_PRICE;
		}
		if (errorMessage != null) {
			throw new IllegalArgumentException(errorMessage);
		}

		Appointment appointment = new Appointment(appointmentDate, appointmentDuration, nameOfDoctor, status, price);
		repository.save(appointment);
		return appointment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sesame.services.appointments.AppointmentService#updateAppointmentStatus(
	 * java.lang.Long, java.lang.String)
	 */
	public Appointment updateAppointmentStatus(Long id, String newStatus) {
		if (!BusinessRules.isValidAppointmentStatus(newStatus)) {
			throw new IllegalArgumentException(BusinessRules.ERRORMSG_INVALID_STATUS);
		}
		Optional<Appointment> optional = repository.findById(id);
		Appointment appointment = optional.orElseThrow(() -> appointmentEntityNotFoundException(id));
		appointment.setStatus(newStatus);
		repository.save(appointment);
		return appointment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sesame.services.appointments.AppointmentService#findAppointmentById(java.
	 * lang.Long)
	 */
	@Override
	public Appointment findAppointmentById(Long id) {
		Optional<Appointment> optional = repository.findById(id);
		return optional.orElseThrow(() -> appointmentEntityNotFoundException(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sesame.services.appointments.AppointmentService#
	 * findAllAppointmentsBetweenDateRangeSortByPrice(java.util.Date,
	 * java.util.Date, java.lang.String)
	 */
	@Override
	public List<Appointment> findAllAppointmentsBetweenDateRangeSortByPrice(Date startDate, Date endDate,
			String order) {
		if (!BusinessRules.isValidDateRange(startDate, endDate)) {
			throw new IllegalArgumentException(BusinessRules.ERRORMSG_INVALID_DATE_RANGE);
		}
		if (!BusinessRules.isValidSQLOrderString(order)) {
			throw new IllegalArgumentException(BusinessRules.ERRORMSG_INVALID_ORDER_SQL_SORT);
		}
		// return repository.findAllAppointmentsBetweenDateRangeSortByPrice(startDate,
		// endDate, order);

		Sort orderBy = new Sort(Sort.Direction.fromString(order), "price");
		return repository.findAllByAppointmentDateGreaterThanEqualAndAppointmentDateLessThanEqual(startDate, endDate,
				orderBy);
	}

}
