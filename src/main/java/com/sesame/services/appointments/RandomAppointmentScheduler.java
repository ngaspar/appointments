package com.sesame.services.appointments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service class responsible for creating any number of dummy appointments with
 * random datetime and duration, up to 365 days in the future.
 * 
 * @author ngaspar
 * @version 1.0
 */
@Component
public class RandomAppointmentScheduler {

	@Autowired
	AppointmentService service;

	private String[] names = "${dummy-doctor-names}".split(",");

	private Date getFutureDate(int days, int hours, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public List<Appointment> generateNRandomAppointments(int numberAppointments) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		for (int i = 0; i < numberAppointments; i++) {
			int daysInFuture = ThreadLocalRandom.current().nextInt(1, 365);
			int hour = ThreadLocalRandom.current().nextInt(0, 20);
			int minute = ThreadLocalRandom.current().nextInt(0, 6) * 10;
			Date appointmentDate = getFutureDate(daysInFuture, hour, minute);

			Long duration = Math.max((ThreadLocalRandom.current().nextLong(BusinessRules.MINIMUM_DURATION,
					BusinessRules.MAXIMUM_DURATION) / 10) * 10, BusinessRules.MINIMUM_DURATION);

			int namePosition = ThreadLocalRandom.current().nextInt(0, names.length);
			String nameOfDoctor = names[namePosition];

			String status = ThreadLocalRandom.current().nextBoolean() ? BusinessRules.STATUS_AVAILABLE
					: BusinessRules.STATUS_BOOKED;

			Long price = Math
					.max((ThreadLocalRandom.current().nextLong(BusinessRules.MINIMUM_PRICE, BusinessRules.MAXIMUM_PRICE)
							/ 10) * 10, BusinessRules.MINIMUM_PRICE);

			appointments.add(service.createAppointment(appointmentDate, duration, nameOfDoctor, status, price));
		}
		return appointments;
	}
}
