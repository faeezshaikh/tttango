package com.faeez.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEET_MES")
public class MeetMe {
	
	
	@Id
	@GeneratedValue
	@Column(name="meetme_id")
	private Long id;

	private Long meet_by;
	private Long meet_who;
	private Date meet_req_when;
	
	
	public Date getMeet_req_when() {
		return meet_req_when;
	}
	public void setMeet_req_when(Date meet_req_when) {
		this.meet_req_when = meet_req_when;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMeet_by() {
		return meet_by;
	}
	public void setMeet_by(Long meet_by) {
		this.meet_by = meet_by;
	}
	public Long getMeet_who() {
		return meet_who;
	}
	public void setMeet_who(Long meet_who) {
		this.meet_who = meet_who;
	}
	
}
