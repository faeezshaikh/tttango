package com.faeez.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LIKE_MES")
public class LikeMe {
	
	
	@Id
	@GeneratedValue
	@Column(name="likeme_id")
	private Long id;

	private Long liked_by;
	private Long liked_who;
	private Date liked_when;
	
	public Date getLiked_when() {
		return liked_when;
	}
	public void setLiked_when(Date liked_when) {
		this.liked_when = liked_when;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLiked_by() {
		return liked_by;
	}
	public void setLiked_by(Long liked_by) {
		this.liked_by = liked_by;
	}
	public Long getLiked_who() {
		return liked_who;
	}
	public void setLiked_who(Long liked_who) {
		this.liked_who = liked_who;
	}
	
	
}
