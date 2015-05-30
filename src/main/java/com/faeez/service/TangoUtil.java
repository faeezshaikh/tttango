package com.faeez.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.BeanUtils;

import com.faeez.model.Member;
import com.faeez.ui.model.UiMember;

public class TangoUtil {
	
	public static String  base_img_url = "https://s3-us-west-2.amazonaws.com/mbuds/";
	static Map<Integer,String> monthMap ;
	static Map<String,Integer> monthMapInt ;
	static {
		monthMapInt = new HashMap<String,Integer>();		
		monthMapInt.put("January", 1);
		monthMapInt.put("February", 2);
		monthMapInt.put("March", 3);
		monthMapInt.put("April", 4);
		monthMapInt.put("May", 5);
		monthMapInt.put("June", 6);
		monthMapInt.put("July", 7);
		monthMapInt.put("August", 8);
		monthMapInt.put("September", 9);
		monthMapInt.put("October", 10);
		monthMapInt.put("November", 11);
		monthMapInt.put("December", 12);
		
		
		monthMap = new HashMap<Integer,String>();		
		monthMap.put(1,"January");
		monthMap.put(2,"February");
		monthMap.put(3,"March");
		monthMap.put(4,"April");
		monthMap.put(5,"May");
		monthMap.put(6,"June");
		monthMap.put(7,"July");
		monthMap.put(8,"August");
		monthMap.put(9,"September");
		monthMap.put(10,"October");
		monthMap.put(11,"November");
		monthMap.put(12,"December");
	}
	public static Date getNow() {
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy,HH:mm:ss");
		Date date = new Date();
		formatter.format(date);
		return date;
	}

	public static int getMonthInt(String month) {
		return monthMapInt.get(month);
	}
	
	public static String getMonth(int month) {
		return monthMap.get(month);
	}
	
	public static UiMember transformToUiMember(Member m) {
		UiMember uim = new UiMember();
		if(m == null) return null;
		BeanUtils.copyProperties(m, uim);
		
		Date birth = m.getBirth();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(birth);
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    int month = cal.get(Calendar.MONTH);
	    if(month==0) month = 1;
	    uim.setMonth_name(monthMap.get(month));
		uim.setDay(day);
		uim.setYear(year);
		
		
		String language = m.getLanguage();
		uim.setLanguages(language.split(","));
		return uim;
	}
	
	public static int calculateAgeInYears(Date birth2) {
		LocalDate birthdate = new LocalDate (birth2);
		LocalDate now = new LocalDate();                    //Today's date
		Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
		return period.getYears();
	}
}
