package com.sesamecare.services.appointments;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sesame.services.AppointmentsApplication;
import com.sesame.services.appointments.Appointment;
import com.sesame.services.appointments.AppointmentRepository;
import com.sesame.services.appointments.AppointmentService;
import com.sesame.services.appointments.BusinessRules;

/**
 * Essential business tests coverage (see Pareto principle).
 * 
 * @author ngaspar
 * @version 1.0
 */
@SpringBootTest(classes = { AppointmentsApplication.class })
public class EssentialTests {

	@Autowired
	AppointmentRepository repository;

	@Autowired
	AppointmentService service;

	private static Date oneDayInFuture;

	@BeforeAll
	private static void init() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		oneDayInFuture = cal.getTime();
	}

	@BeforeEach
	private void clearRepository() {
		repository.deleteAll();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	@DisplayName("Service - delete appointment from DB")
	void serviceDeleteAppointment() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Delete",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Long id = repository.findAll().get(0).getId();
		service.deleteAppointment(id);
		repository.flush();

		Assertions.assertTrue(repository.count() == 0);
	}

	@Test
	@DisplayName("Service - create appointment in DB")
	void serviceCreateAppointment() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Create",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Assertions.assertTrue(repository.count() == 1);
	}

	@Test
	@DisplayName("Service - retrieve appointment from DB")
	void serviceRetrieveAppointment() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Retrieve",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Long id = repository.findAll().get(0).getId();
		Appointment appointment = service.findAppointmentById(id);

		Assertions.assertTrue(appointment.getNameOfDoctor().equals("Test Doctor Retrieve"));
	}

	@Test
	@DisplayName("Service - update appointment status in DB")
	void serviceUpdateAppointment() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Update",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Long id = repository.findAll().get(0).getId();
		Appointment appointment = service.updateAppointmentStatus(id, BusinessRules.STATUS_AVAILABLE);
		repository.flush();

		appointment = service.findAppointmentById(id);

		Assertions.assertTrue(appointment.getStatus().equals(BusinessRules.STATUS_AVAILABLE));
	}

	@Test
	@DisplayName("Service - find appointments between two dates from DB")
	void serviceFindBetweenDates() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor FindBetweenDates",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date fiveDaysInFuture = cal.getTime();
		service.createAppointment(fiveDaysInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor FindBetweenDates",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 10);
		Date tenDaysInFuture = cal.getTime();
		List<Appointment> appointments = service.findAllAppointmentsBetweenDateRangeSortByPrice(
				Calendar.getInstance().getTime(), tenDaysInFuture, BusinessRules.SORT_ORDER_ASCENDING);

		Assertions.assertTrue(appointments.size() == 2);
	}

	@Test
	@DisplayName("Service - delete non-existing appointment from DB")
	void serviceDeleteAppointmentError() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.deleteAppointment(Long.valueOf(1));
		});
	}

	@Test
	@DisplayName("Service - create appointment - invalid date error")
	void serviceCreateAppointmentInvalidDateError() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date oneDayInPast = cal.getTime();

		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.createAppointment(oneDayInPast, BusinessRules.MAXIMUM_DURATION, "Test Doctor Invalid Date",
					BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_DATE));
	}

	@Test
	@DisplayName("Service - create appointment - invalid duration error")
	void serviceCreateAppointmentInvalidDurationError() {
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.createAppointment(oneDayInFuture, BusinessRules.MINIMUM_DURATION - 1,
					"Test Doctor Invalid Duration", BusinessRules.STATUS_BOOKED, Long.valueOf(100));
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_DURATION));
	}

	@Test
	@DisplayName("Service - create appointment - invalid name of doctor error")
	void serviceCreateAppointmentInvalidNameOfDoctorError() {
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "", BusinessRules.STATUS_BOOKED,
					BusinessRules.MAXIMUM_PRICE);
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_DOCTOR_NAME));
	}

	@Test
	@DisplayName("Service - create appointment - invalid status error")
	void serviceCreateAppointmentInvalidStatusError() {
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Invalid Status",
					"Sleeping", BusinessRules.MAXIMUM_PRICE);
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_STATUS));
	}

	@Test
	@DisplayName("Service - create appointment - invalid price error")
	void serviceCreateAppointmentInvalidPriceError() {
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Invalid Price",
					BusinessRules.STATUS_BOOKED, BusinessRules.MINIMUM_PRICE - 1);
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_PRICE));
	}

	@Test
	@DisplayName("Service - update appointment - invalid status error")
	void serviceUpdateAppointmentInvalidStatusError() {
		service.createAppointment(oneDayInFuture, BusinessRules.MAXIMUM_DURATION, "Test Doctor Update",
				BusinessRules.STATUS_BOOKED, BusinessRules.MAXIMUM_PRICE);
		repository.flush();

		Long id = repository.findAll().get(0).getId();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.updateAppointmentStatus(id, "Frolicking");
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_STATUS));
	}

	@Test
	@DisplayName("Service - update appointment - not found error")
	void serviceUpdateAppointmentNotFoundError() {
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.updateAppointmentStatus(Long.valueOf(1), BusinessRules.STATUS_BOOKED);
		});
		Assertions.assertTrue(exception.getMessage().equals(String.format(BusinessRules.ERRORMSGS_NOT_FOUND, 1)));
	}

	@Test
	@DisplayName("Service - retrieve appointment - not found error")
	void serviceRetrieveAppointmentNotFoundError() {
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.findAppointmentById(Long.valueOf(1));
		});
		Assertions.assertTrue(exception.getMessage().equals(String.format(BusinessRules.ERRORMSGS_NOT_FOUND, 1)));
	}

	@Test
	@DisplayName("Service - find appointment between dates - invalid date range error")
	void serviceFindbetweenDatesInvalidDateRangeError() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date fiveDaysInFuture = cal.getTime();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.findAllAppointmentsBetweenDateRangeSortByPrice(fiveDaysInFuture, Calendar.getInstance().getTime(),
					BusinessRules.SORT_ORDER_ASCENDING);
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_DATE_RANGE));
	}

	@Test
	@DisplayName("Service - find appointment between dates - invalid SQL order string error")
	void serviceFindbetweenDatesInvalidOrderStringError() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date fiveDaysInFuture = cal.getTime();
		IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.findAllAppointmentsBetweenDateRangeSortByPrice(Calendar.getInstance().getTime(), fiveDaysInFuture,
					"STEADY");
		});
		Assertions.assertTrue(exception.getMessage().equals(BusinessRules.ERRORMSG_INVALID_ORDER_SQL_SORT));
	}
}
