package com.sesame.services.appointments;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * POJO wrapper for the {@code create} JSON request body.
 * 
 * @author ngaspar
 * @version 1.0
 */
public class CreateRequestWrapper {

	private Date appointmentDate;
	private long appointmentDuration;
	private String nameOfDoctor;
	private String status;
	private long price;

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	@JsonDeserialize(using = AppointmentJsonDateDeserializer.class)
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public long getAppointmentDuration() {
		return appointmentDuration;
	}

	public void setAppointmentDuration(long appointmentDuration) {
		this.appointmentDuration = appointmentDuration;
	}

	public String getNameOfDoctor() {
		return nameOfDoctor;
	}

	public void setNameOfDoctor(String nameOfDoctor) {
		this.nameOfDoctor = nameOfDoctor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CreateRequestWrapper [appointmentDate=" + appointmentDate + ", appointmentDuration="
				+ appointmentDuration + ", nameOfDoctor=" + nameOfDoctor + ", status=" + status + ", price=" + price
				+ "]";
	}
}
