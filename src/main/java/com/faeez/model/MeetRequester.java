package com.faeez.model;

import java.util.Date;

/*this is the projection class, used to populate data for:
1. Viewed me
2. Liked Me & I Liked
3. Wanna meet me & I wanna meet*/

public class MeetRequester {

	private Long profile_id;
	private String main_img;
	private Long likes;
	private Long views;
	private Long meet_requests;
	private String username;
	private String online_status;
	private Date birth;
	private String occupation;
	private String city;
	private Date last_login;
	private String country;
	private Date  meet_req_when;
	
	public MeetRequester(Long profile_id, String main_img, Long likes,
			Long views, Long meet_requests, String username,
			String online_status, Date birth, String occupation, String city,
			Date last_login, String country, Date meet_req_when) {
		super();
		this.profile_id = profile_id;
		this.main_img = main_img;
		this.likes = likes;
		this.views = views;
		this.meet_requests = meet_requests;
		this.username = username;
		this.online_status = online_status;
		this.birth = birth;
		this.occupation = occupation;
		this.city = city;
		this.last_login = last_login;
		this.country = country;
		this.meet_req_when = meet_req_when;
	}
	
	
	
	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public Long getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	public String getMain_img() {
		return main_img;
	}
	public void setMain_img(String main_img) {
		this.main_img = main_img;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Long getViews() {
		return views;
	}
	public void setViews(Long views) {
		this.views = views;
	}
	public Long getMeet_requests() {
		return meet_requests;
	}
	public void setMeet_requests(Long meet_requests) {
		this.meet_requests = meet_requests;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOnline_status() {
		return online_status;
	}
	public void setOnline_status(String online_status) {
		this.online_status = online_status;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getMeet_req_when() {
		return meet_req_when;
	}
	public void setMeet_req_when(Date meet_req_when) {
		this.meet_req_when = meet_req_when;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	
	
}
