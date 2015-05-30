/*package com.faeez.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

@Entity
@Table(name="MEMBERS")
@NamedQueries({
	@NamedQuery(name=Member.FIND_ALL_MEMBERS, query="Select m from Member m"),
	@NamedQuery(name=Member.FIND_ALL_MEMBERS_WITH_PICS, query="Select m from Member m where no_of_pics > 0 ")
	
})
public class Member_original {
	
	public static final String FIND_ALL_MEMBERS = "findAllMembers";
	public static final String FIND_ALL_MEMBERS_WITH_PICS = "findAllMembersWithPics";
	
	@Id
	@GeneratedValue
	@Column(name="profile_id")
	private Long id;

	private String username;
	private String firstname;
	private String surname;
	private String sex;
	private Date birthday;  // Not used anymore 
	private String email;
	private String country;
	private String occupation;
	private String ethnicity;
	private int day; 
	private int month; 
	private int year; 
	private String height;
	private String bodytype;
	private String haircolor;
	private String eyecolor;
	private String education;
	private String children;
	private String drink; // not used for muslim profiles
	private String smoke;
	private String drugs; // not used for muslim profiles
	private String car; // not used
	private String home;
	private String starsign; // not used
	private String State;
	private String City;
	private String zip;
	private String mymatch;
	private String description;
	private String looking_for; // no longer used. Use mymatch instead.
	private String marital_status;
	private Integer no_of_pics;
	private String password;
	private Long views;
	private Long likes;
	private Long meet_requests;
	private String is_seed;
	private String is_signup_confirmed;
	private String online_status;
	private Date last_login;
	private String nationality;
	private String religion;
	private String income;
	private String quran;
	private String relocate;
	private String language;
	private String month_name;
	

	@Transient
	private String[] languages;
	
	@Transient
	private int age;

	
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "member",cascade=CascadeType.ALL)
	private Set<Email> emails = new HashSet<Email>();
	
	
	
	
	@OneToMany(mappedBy="goal", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Exercise> exercises = new ArrayList<Exercise>();
	
	public List<Exercise> getExercises() {
		return exercises;
	}
	

	

	public String[] getLanguages() {
		return languages;
	}


	public void setLanguages(String[] languages) {
		this.languages = languages;
	}


	public String getOnline_status() {
		return online_status;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getReligion() {
		return religion;
	}


	public void setReligion(String religion) {
		this.religion = religion;
	}


	public String getIncome() {
		return income;
	}


	public void setIncome(String income) {
		this.income = income;
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


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getMonth_name() {
		return month_name;
	}


	public void setMonth_name(String month_name) {
		this.month_name = month_name;
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


	public int getAge() {
		return calculateAgeInYears();
	}




	public int calculateAgeInYears() {

		if(getYear()==0) return 0;
		LocalDate birthdate = new LocalDate (getYear(), getMonth(), getDay());          //Birth date
		LocalDate now = new LocalDate();                    //Today's date
		Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
		System.out.println(period.getDays());
		System.out.println(period.getMonths());
		System.out.println(period.getYears());
		return period.getYears();
		
	
	}
	
	
	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
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


	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}



	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
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

	public String getHaircolor() {
		return haircolor;
	}

	public void setHaircolor(String haircolor) {
		this.haircolor = haircolor;
	}

	public String getEyecolor() {
		return eyecolor;
	}

	public void setEyecolor(String eyecolor) {
		this.eyecolor = eyecolor;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
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

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getStarsign() {
		return starsign;
	}

	public void setStarsign(String starsign) {
		this.starsign = starsign;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getLooking_for() {
		return looking_for;
	}

	public void setLooking_for(String looking_for) {
		this.looking_for = looking_for;
	}

	public String getMarital_status() {
		return marital_status;
	}

	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}


	public Long getId() {
		return id;
	}

	public Integer getNo_of_pics() {
		return no_of_pics;
	}

	public void setNo_of_pics(Integer no_of_pics) {
		this.no_of_pics = no_of_pics;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((City == null) ? 0 : City.hashCode());
		result = prime * result + ((State == null) ? 0 : State.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((bodytype == null) ? 0 : bodytype.hashCode());
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + day;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((drink == null) ? 0 : drink.hashCode());
		result = prime * result + ((drugs == null) ? 0 : drugs.hashCode());
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((ethnicity == null) ? 0 : ethnicity.hashCode());
		result = prime * result + ((eyecolor == null) ? 0 : eyecolor.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((haircolor == null) ? 0 : haircolor.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((home == null) ? 0 : home.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((is_seed == null) ? 0 : is_seed.hashCode());
		result = prime * result + ((is_signup_confirmed == null) ? 0 : is_signup_confirmed.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((looking_for == null) ? 0 : looking_for.hashCode());
		result = prime * result + ((marital_status == null) ? 0 : marital_status.hashCode());
		result = prime * result + ((meet_requests == null) ? 0 : meet_requests.hashCode());
		result = prime * result + month;
		result = prime * result + ((mymatch == null) ? 0 : mymatch.hashCode());
		result = prime * result + ((no_of_pics == null) ? 0 : no_of_pics.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((smoke == null) ? 0 : smoke.hashCode());
		result = prime * result + ((starsign == null) ? 0 : starsign.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((views == null) ? 0 : views.hashCode());
		result = prime * result + year;
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		Member other = (Member) obj;
		if (City == null) {
			if (other.City != null)
				return false;
		} else if (!City.equals(other.City))
			return false;
		if (State == null) {
			if (other.State != null)
				return false;
		} else if (!State.equals(other.State))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (bodytype == null) {
			if (other.bodytype != null)
				return false;
		} else if (!bodytype.equals(other.bodytype))
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (day != other.day)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (drink == null) {
			if (other.drink != null)
				return false;
		} else if (!drink.equals(other.drink))
			return false;
		if (drugs == null) {
			if (other.drugs != null)
				return false;
		} else if (!drugs.equals(other.drugs))
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (ethnicity == null) {
			if (other.ethnicity != null)
				return false;
		} else if (!ethnicity.equals(other.ethnicity))
			return false;
		if (eyecolor == null) {
			if (other.eyecolor != null)
				return false;
		} else if (!eyecolor.equals(other.eyecolor))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (haircolor == null) {
			if (other.haircolor != null)
				return false;
		} else if (!haircolor.equals(other.haircolor))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (home == null) {
			if (other.home != null)
				return false;
		} else if (!home.equals(other.home))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_seed == null) {
			if (other.is_seed != null)
				return false;
		} else if (!is_seed.equals(other.is_seed))
			return false;
		if (is_signup_confirmed == null) {
			if (other.is_signup_confirmed != null)
				return false;
		} else if (!is_signup_confirmed.equals(other.is_signup_confirmed))
			return false;
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
			return false;
		if (looking_for == null) {
			if (other.looking_for != null)
				return false;
		} else if (!looking_for.equals(other.looking_for))
			return false;
		if (marital_status == null) {
			if (other.marital_status != null)
				return false;
		} else if (!marital_status.equals(other.marital_status))
			return false;
		if (meet_requests == null) {
			if (other.meet_requests != null)
				return false;
		} else if (!meet_requests.equals(other.meet_requests))
			return false;
		if (month != other.month)
			return false;
		if (mymatch == null) {
			if (other.mymatch != null)
				return false;
		} else if (!mymatch.equals(other.mymatch))
			return false;
		if (no_of_pics == null) {
			if (other.no_of_pics != null)
				return false;
		} else if (!no_of_pics.equals(other.no_of_pics))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (smoke == null) {
			if (other.smoke != null)
				return false;
		} else if (!smoke.equals(other.smoke))
			return false;
		if (starsign == null) {
			if (other.starsign != null)
				return false;
		} else if (!starsign.equals(other.starsign))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (views == null) {
			if (other.views != null)
				return false;
		} else if (!views.equals(other.views))
			return false;
		if (year != other.year)
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", firstname=" + firstname + ", is_seed=" + is_seed + ", is_signup_confirmed=" + is_signup_confirmed + ", surname=" + surname + ", sex=" + sex + ", birthday=" + birthday + ", password="
				+ password + ", email=" + email + ", no_of_pics=" + no_of_pics + ", day=" + day + ", month=" + month + ", year=" + year + ", country=" + country + ", occupation=" + occupation + ", ethnicity=" + ethnicity + ", height=" + height
				+ ", bodytype=" + bodytype + ", haircolor=" + haircolor + ", eyecolor=" + eyecolor + ", education=" + education + ", children=" + children + ", drink=" + drink + ", smoke=" + smoke + ", drugs=" + drugs + ", car=" + car + ", home="
				+ home + ", starsign=" + starsign + ", State=" + State + ", City=" + City + ", zip=" + zip + ", mymatch=" + mymatch + ", description=" + description + ", looking_for=" + looking_for + ", marital_status=" + marital_status + ", views="
				+ views + ", likes=" + likes + ", meet_requests=" + meet_requests + "]";
	}



}
*/