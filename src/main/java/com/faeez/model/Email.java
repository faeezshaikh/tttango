package com.faeez.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name="EMAILS")
@NamedQueries({
	//@NamedQuery(name=Email.FIND_ALL_MEMBERS, query="Select m from Member m"),
	//@NamedQuery(name=Email.FIND_ALL_MEMBERS_WITH_PICS, query="Select m from Member m where no_of_pics > 0 ")
	
})
public class Email {
	
	public static final String FIND_ALL_MEMBERS = "findAllMembers";
	public static final String FIND_ALL_MEMBERS_WITH_PICS = "findAllMembersWithPics";
	
	@Id
	@GeneratedValue
	@Column(name="email_id")
	private Long id;

	private Date email_time;
	private String message;
	private String is_opened;
	private String is_conversation_new;
	
	// To get a person's inbox, select * where other_person = 'me';
	private Long receiver_id;

	private Long sender_id;
	
	private Date new_conversation_time;
	private Long new_email_for;
	private String receiver_main_img;
	private String sender_main_img;
	
	
	
	
	
/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	private Member member;
	*/
	


	public Date getNew_conversation_time() {
		return new_conversation_time;
	}

	public String getReceiver_main_img() {
		return receiver_main_img;
	}

	public void setReceiver_main_img(String receiver_main_img) {
		this.receiver_main_img = receiver_main_img;
	}

	public String getSender_main_img() {
		return sender_main_img;
	}

	public void setSender_main_img(String sender_main_img) {
		this.sender_main_img = sender_main_img;
	}

	public void setNew_conversation_time(Date new_conversation_time) {
		this.new_conversation_time = new_conversation_time;
	}

	public Long getNew_email_for() {
		return new_email_for;
	}

	public void setNew_email_for(Long new_email_for) {
		this.new_email_for = new_email_for;
	}

	public Long getId() {
		return id;
	}

	public Long getSender_id() {
		return sender_id;
	}

	
	public String getIs_conversation_new() {
		return is_conversation_new;
	}

	public void setIs_conversation_new(String is_conversation_new) {
		this.is_conversation_new = is_conversation_new;
	}

	public void setSender_id(Long sender_id) {
		this.sender_id = sender_id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEmail_time() {
		return email_time;
	}

	public void setEmail_time(Date email_time) {
		this.email_time = email_time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIs_opened() {
		return is_opened;
	}

	public void setIs_opened(String is_opened) {
		this.is_opened = is_opened;
	}

	public Long getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(Long receiver_id) {
		this.receiver_id = receiver_id;
	}

	

}
