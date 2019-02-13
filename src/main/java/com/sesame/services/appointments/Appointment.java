package com.sesame.services.appointments;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Persistence POJO for the Appointment table.
 * 
 * @author ngaspar
 * @version 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Appointment {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	Date appointmentDate;

	@Column(nullable = false)
	Long appointmentDuration;

	@NotBlank
	private String nameOfDoctor;

	@NotBlank
	private String status;

	@Column(nullable = false)
	private Long price;

	public Appointment() {

	}

	public Appointment(Date appointmentDate, Long appointmentDuration, String nameOfDoctor, String status, Long price) {
		super();
		this.appointmentDate = appointmentDate;
		this.appointmentDuration = appointmentDuration;
		this.nameOfDoctor = nameOfDoctor;
		this.status = status;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Long getAppointmentDuration() {
		return appointmentDuration;
	}

	public void setAppointmentDuration(Long appointmentDuration) {
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", createdAt=" + createdAt + ", appointmentDate=" + appointmentDate
				+ ", appointmentDuration=" + appointmentDuration + ", nameOfDoctor=" + nameOfDoctor + ", status="
				+ status + ", price=" + price + "]";
	}
}
