package com.faeez.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REPORTS")
public class Report {
	
	
	@Id
	@GeneratedValue
	private Long id;

	private Long profile_id;
	private Long reporter_id;
	private String reason;
	private Integer count;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	public Long getReporter_id() {
		return reporter_id;
	}
	public void setReporter_id(Long reporter_id) {
		this.reporter_id = reporter_id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((profile_id == null) ? 0 : profile_id.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((reporter_id == null) ? 0 : reporter_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (profile_id == null) {
			if (other.profile_id != null)
				return false;
		} else if (!profile_id.equals(other.profile_id))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (reporter_id == null) {
			if (other.reporter_id != null)
				return false;
		} else if (!reporter_id.equals(other.reporter_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Report [id=" + id + ", profile_id=" + profile_id
				+ ", reporter_id=" + reporter_id + ", reason=" + reason
				+ ", count=" + count + "]";
	}
		
}
