package com.sesame.services.appointments;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller of this application.
 * 
 * @author ngaspar
 * @version 1.0
 */
@RestController
@RequestMapping
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private RandomAppointmentScheduler randomAppointmentSchedulerService;

	@DeleteMapping(BusinessRules.DELETE_PATH)
	public void deleteAppointment(@PathVariable(name = "id", required = true) Long id) {
		appointmentService.deleteAppointment(id);
	}

	@PostMapping(BusinessRules.CREATE_PATH)
	public ResponseEntity<Appointment> createAppointment(@RequestBody CreateRequestWrapper createRequestWrapper)
			throws ParseException {
		Appointment appointment = appointmentService.createAppointment(createRequestWrapper.getAppointmentDate(),
				createRequestWrapper.getAppointmentDuration(), createRequestWrapper.getNameOfDoctor(),
				createRequestWrapper.getStatus(), createRequestWrapper.getPrice());

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(BusinessRules.FINDBYID_PATH)
				.buildAndExpand(appointment.getId()).toUri();

		return ResponseEntity.created(uri).body(appointment);
	}

	@PutMapping(BusinessRules.UPDATESTATUS_PATH)
	public ResponseEntity<Appointment> updateAppointmentStatus(
			@RequestBody UpdateStatusRequestWrapper updateStatusRequestWrapper) {
		Appointment appointment = appointmentService.updateAppointmentStatus(updateStatusRequestWrapper.getId(),
				updateStatusRequestWrapper.getStatus());

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(BusinessRules.FINDBYID_PATH)
				.buildAndExpand(appointment.getId()).toUri();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", uri.toASCIIString());

		return ResponseEntity.ok().headers(responseHeaders).body(appointment);
	}

	@GetMapping(BusinessRules.FINDBYID_PATH)
	public Appointment findAppointmentById(@PathVariable(name = "id", required = true) Long id) {
		return appointmentService.findAppointmentById(id);
	}

	@GetMapping(BusinessRules.FINDBETWEENDATES_PATH)
	public List<Appointment> findAllAppointmentsBetweenDateRangeSortByPrice(
			@PathVariable(name = "startDate", required = true) @DateTimeFormat(pattern = BusinessRules.DATETIME_PATTERN) Date startDate,
			@PathVariable(name = "endDate", required = true) @DateTimeFormat(pattern = BusinessRules.DATETIME_PATTERN) Date endDate,
			@PathVariable(name = "sortOrder", required = true) String sortOrder) throws ParseException {
		return appointmentService.findAllAppointmentsBetweenDateRangeSortByPrice(startDate, endDate, sortOrder);
	}

	@GetMapping(BusinessRules.SCHEDULE_PATH)
	public List<Appointment> scheduleRandomAppointments(
			@PathVariable(name = "howManyAppts", required = true) Long howManyAppts) {
		return randomAppointmentSchedulerService.generateNRandomAppointments(howManyAppts.intValue());
	}
}
