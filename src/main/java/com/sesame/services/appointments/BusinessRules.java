package com.sesame.services.appointments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Aggregates business rules from configurable properties and performs
 * "low-level" validations based on those rules.
 * 
 * @author ngaspar
 * @version 1.0
 */
public class BusinessRules {

	/*
	 * APPOINTMENT PROPERTIES
	 */
	public static final String DATETIME_PATTERN = "${datetime-pattern}";
	public static final String STATUS_AVAILABLE = "${status-available}";
	public static final String STATUS_BOOKED = "${status-booked}";
	public static final Long MINIMUM_PRICE = Long.parseLong("${minimum-price-dollars}");
	public static final Long MAXIMUM_PRICE = Long.parseLong("${maximum-price-dollars}");
	public static final Long MINIMUM_DURATION = Long.parseLong("${minimum-duration-minutes}");
	public static final Long MAXIMUM_DURATION = Long.parseLong("${maximum-duration-minutes}");

	/*
	 * API REST ENDPOINT PATHS
	 */
	public static final String DELETE_PATH = "${delete-path}";
	public static final String CREATE_PATH = "${create-path}";
	public static final String UPDATESTATUS_PATH = "${updatestatus-path}";
	public static final String FINDBYID_PATH = "${findbyid-path}";
	public static final String FINDBETWEENDATES_PATH = "${findbetweendates-path}";
	public static final String SCHEDULE_PATH = "${schedule-path}";
	
	
	/**
	 * API RULES 
	 */
	public static final String SORT_ORDER_ASCENDING = "${sort-order-ascending}";
	public static final String SORT_ORDER_DESCENDING = "${sort-order-descending}";

	/*
	 * API ERROR MESSAGES
	 */
	public static final String ERRORMSGS_NOT_FOUND = "${errormsg.not-found}";
	public static final String ERRORMSG_INVALID_DATE = "${errormsg.invalid-date}";
	public static final String ERRORMSG_INVALID_DURATION = "${errormsg.invalid-duration}";
	public static final String ERRORMSG_INVALID_DOCTOR_NAME = "${errormsg.invalid-doctor-name}";
	public static final String ERRORMSG_INVALID_STATUS = "${errormsg.invalid-status}";
	public static final String ERRORMSG_INVALID_PRICE = "${errormsg.invalid-price}";
	public static final String ERRORMSG_INVALID_DATE_RANGE = "${errormsg.invalid-date-range}";
	public static final String ERRORMSG_INVALID_ORDER_SQL_SORT = "${Invalid SQL order string}";

	@SuppressWarnings("serial")
	public static final List<String> API_ENDPOINT_PATHS = new ArrayList<String>() {
		{
			add(DELETE_PATH);
			add(CREATE_PATH);
			add(UPDATESTATUS_PATH);
			add(FINDBYID_PATH);
			add(FINDBETWEENDATES_PATH);
		}
	};

	public static boolean isValidAppointmentStatus(String status) {
		return status.compareToIgnoreCase(STATUS_AVAILABLE) == 0 || status.compareToIgnoreCase(STATUS_BOOKED) == 0;
	}

	public static boolean isValidSQLOrderString(String order) {
		return order.compareToIgnoreCase(SORT_ORDER_ASCENDING) == 0 || order.compareToIgnoreCase(SORT_ORDER_DESCENDING) == 0;
	}

	public static boolean isValidAppointmentPrice(Long price) {
		return price >= MINIMUM_PRICE && price <= MAXIMUM_PRICE;
	}

	public static boolean isValidAppointmentDuration(Long duration) {
		return duration >= MINIMUM_DURATION && duration <= MAXIMUM_DURATION;
	}

	public static boolean isValidAppointmentDate(Date date) {
		return date.after(Calendar.getInstance().getTime());
	}

	public static boolean isValidDateRange(Date startDate, Date endDate) {
		return startDate.before(endDate);
	}

}
