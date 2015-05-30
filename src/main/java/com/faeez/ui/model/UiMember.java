package com.faeez.ui.model;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class UiMember {
	
	
	private Long id;

	private String email;
	private String username;
	private String firstname;
	private String surname;
	private String country;
	private String city;
	private Date birth;  // ??
	private String sex;
	private String height;
	private String bodytype;
	private String eyecolor;
	private String haircolor;
	private String education;
	private String religion;
	private String ethnicity;
	private String language;
	private String hobbies_interests_activities;
	private String marital_status;
	private String children;
	private String drink;
	private String smoke;
	private String occupation;
	private String income;
	private String mymatch;
	private String description;
	private int no_of_pics;
	private String state;
	private String zip;
	private String home;
//	private String password;
	private Long views;
	private Long likes;
	private Long meet_requests;
	private String is_seed;
	private String is_signup_confirmed;
	private String online_status;
	private Date last_login;
	private String nationality;
	private String quran;
	private String relocate;
	private String month_name;
	private int day;
	private int year;
	private String[] languages;
	private int age;
	private String main_img;
	
	

	public int getDay() {
		return day;
	}




	public void setDay(int day) {
		this.day = day;
	}




	public int getYear() {
		return year;
	}




	public void setYear(int year) {
		this.year = year;
	}

	
	public int getAge() {
		return calculateAgeInYears();
	}


	

	public String[] getLanguages() {
		return languages;
	}


	public void setLanguages(String[] languages) {
		this.languages = languages;
	}


	public String getHome() {
		return home;
	}




	public void setHome(String home) {
		this.home = home;
	}




	public int calculateAgeInYears() {
		Date birth2 = getBirth();
		LocalDate birthdate = new LocalDate (birth2);
		LocalDate now = new LocalDate();                    //Today's date
		Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
		return period.getYears();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getBodytype() {
		return bodytype;
	}
	public void setBodytype(String bodytype) {
		this.bodytype = bodytype;
	}
	public String getEyecolor() {
		return eyecolor;
	}
	public void setEyecolor(String eyecolor) {
		this.eyecolor = eyecolor;
	}
	public String getHaircolor() {
		return haircolor;
	}
	public void setHaircolor(String haircolor) {
		this.haircolor = haircolor;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHobbies_interests_activities() {
		return hobbies_interests_activities;
	}
	public void setHobbies_interests_activities(String hobbies_interests_activities) {
		this.hobbies_interests_activities = hobbies_interests_activities;
	}
	public String getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public String getDrink() {
		return drink;
	}
	public void setDrink(String drink) {
		this.drink = drink;
	}
	public String getSmoke() {
		return smoke;
	}
	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getMymatch() {
		return mymatch;
	}
	public void setMymatch(String mymatch) {
		this.mymatch = mymatch;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNo_of_pics() {
		return no_of_pics;
	}
	public void setNo_of_pics(int no_of_pics) {
		this.no_of_pics = no_of_pics;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Long getViews() {
		return views;
	}
	public void setViews(Long views) {
		this.views = views;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Long getMeet_requests() {
		return meet_requests;
	}
	public void setMeet_requests(Long meet_requests) {
		this.meet_requests = meet_requests;
	}
	public String getIs_seed() {
		return is_seed;
	}
	public void setIs_seed(String is_seed) {
		this.is_seed = is_seed;
	}
	public String getIs_signup_confirmed() {
		return is_signup_confirmed;
	}
	public void setIs_signup_confirmed(String is_signup_confirmed) {
		this.is_signup_confirmed = is_signup_confirmed;
	}
	public String getOnline_status() {
		return online_status;
	}
	public void setOnline_status(String online_status) {
		this.online_status = online_status;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getQuran() {
		return quran;
	}
	public void setQuran(String quran) {
		this.quran = quran;
	}
	public String getRelocate() {
		return relocate;
	}
	public void setRelocate(String relocate) {
		this.relocate = relocate;
	}
	public String getMonth_name() {
		return month_name;
	}
	public void setMonth_name(String month_name) {
		this.month_name = month_name;
	}




	public String getMain_img() {
		return main_img;
	}


	public void setMain_img(String main_img) {
		this.main_img = main_img;
	}
	
	
	
		
	
}
