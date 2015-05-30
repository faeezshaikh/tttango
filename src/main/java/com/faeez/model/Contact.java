package com.faeez.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONTACTS")
public class Contact {
	
	
	@Id
	@GeneratedValue
	private Long id;

	private Long profile_id;
	private Long contact_id;
	private String contact_username;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getContact_username() {
		return contact_username;
	}
	public void setContact_username(String contact_username) {
		this.contact_username = contact_username;
	}
	public Long getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	public Long getContact_id() {
		return contact_id;
	}
	public void setContact_id(Long contact_id) {
		this.contact_id = contact_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contact_id == null) ? 0 : contact_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((profile_id == null) ? 0 : profile_id.hashCode());
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
		Contact other = (Contact) obj;
		if (contact_id == null) {
			if (other.contact_id != null)
				return false;
		} else if (!contact_id.equals(other.contact_id))
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
		return true;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", profile_id=" + profile_id
				+ ", contact_id=" + contact_id + "]";
	}
	
	
}
