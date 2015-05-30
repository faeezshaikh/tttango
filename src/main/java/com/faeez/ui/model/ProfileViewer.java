package com.faeez.ui.model;

public class ProfileViewer {

	private Long viewers_id;
	private String viewers_pic_url;
	private String view_time;
	private Long viewers_likes;
	private Long viewers_views;
	private Long viewers_meetReqs;

	private String viewers_onlineStatus;
	private int viewers_age;
	private String viewers_occupation;
	private String viewers_city;

	public String getViewers_onlineStatus() {
		return viewers_onlineStatus;
	}

	public void setViewers_onlineStatus(String viewers_onlineStatus) {
		this.viewers_onlineStatus = viewers_onlineStatus;
	}

	public int getViewers_age() {
		return viewers_age;
	}

	public void setViewers_age(int viewers_age) {
		this.viewers_age = viewers_age;
	}

	public String getViewers_occupation() {
		return viewers_occupation;
	}

	public void setViewers_occupation(String viewers_occupation) {
		this.viewers_occupation = viewers_occupation;
	}

	public String getViewers_city() {
		return viewers_city;
	}

	public void setViewers_city(String viewers_city) {
		this.viewers_city = viewers_city;
	}

	public Long getViewers_likes() {
		return viewers_likes;
	}

	public void setViewers_likes(Long viewers_likes) {
		this.viewers_likes = viewers_likes;
	}

	public Long getViewers_views() {
		return viewers_views;
	}

	public void setViewers_views(Long viewers_views) {
		this.viewers_views = viewers_views;
	}

	public Long getViewers_meetReqs() {
		return viewers_meetReqs;
	}

	public void setViewers_meetReqs(Long viewers_meetReqs) {
		this.viewers_meetReqs = viewers_meetReqs;
	}

	public Long getViewers_id() {
		return viewers_id;
	}

	public void setViewers_id(Long viewers_id) {
		this.viewers_id = viewers_id;
	}

	public String getViewers_pic_url() {
		return viewers_pic_url;
	}

	public void setViewers_pic_url(String viewers_pic_url) {
		this.viewers_pic_url = viewers_pic_url;
	}

	public String getView_time() {
		return view_time;
	}

	public void setView_time(String view_time) {
		this.view_time = view_time;
	}

}
