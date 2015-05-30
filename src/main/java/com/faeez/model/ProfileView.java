package com.faeez.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE_VIEWS")
public class ProfileView {

	@Id
	@GeneratedValue
	@Column(name = "view_id")
	private Long id;

	private Long viewed_by;
	private Long viewed_who;

	private Date viewed_when;

	public Date getViewed_when() {
		return viewed_when;
	}

	public void setViewed_when(Date viewed_when) {
		this.viewed_when = viewed_when;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getViewed_by() {
		return viewed_by;
	}

	public void setViewed_by(Long viewed_by) {
		this.viewed_by = viewed_by;
	}

	public Long getViewed_who() {
		return viewed_who;
	}

	public void setViewed_who(Long viewed_who) {
		this.viewed_who = viewed_who;
	}


}
