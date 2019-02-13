package com.sesame.services.appointments;

/**
 * POJO wrapper for the {@code updateStatus} JSON request body.
 * 
 * @author ngaspar
 * @version 1.0
 */
public class UpdateStatusRequestWrapper {

	private long id;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UpdateStatusRequestWrapper [id=" + id + ", status=" + status + "]";
	}
}
