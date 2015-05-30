package com.faeez.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.controller.GoogleMail;
import com.faeez.dao.LikeMeDao;
import com.faeez.dao.MeetMeDao;
import com.faeez.dao.MemberDao;
import com.faeez.dao.ProfileViewDao;
import com.faeez.model.Contact;
import com.faeez.model.Member;
import com.faeez.ui.model.Search;
import com.faeez.ui.model.SignupResponse;
import com.faeez.ui.model.UiMember;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	private ProfileViewDao pViewDao;
	private MeetMeDao meetMeDao;
	private LikeMeDao likeMeDao;
	
//	@Autowired
//	private PicsService picService;
	
	
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void setLikeMeDao(LikeMeDao likeMeDao) {
		this.likeMeDao = likeMeDao;
	}


	public void setpViewDao(ProfileViewDao pViewDao) {
		this.pViewDao = pViewDao;
	}

	public void setMeetMeDao(MeetMeDao meetMeDao) {
		this.meetMeDao = meetMeDao;
	}


	@Transactional
	public void addMember(Member Member) {
		memberDao.addMember(Member);
	}

	@Override
	@Transactional
	public void updateMember(UiMember uim) {
		
		Member m = new Member();
		BeanUtils.copyProperties(uim,m);
		java.util.Date utilDate = extractBirthDate(uim);
		String language = extractLanguage(uim);
		
		m.setBirth(utilDate);
		m.setLanguage(language);
		
		memberDao.updateMember(m);
	}
	
	@Override
	@Transactional
	public void updateMemberPartial() {
		List<Long> listMembers = memberDao.listMembersWithPics();
		List<Long> subList1 = listMembers.subList(0, 25);
		for (Long long1 : subList1) {
			Member memberById = memberDao.getMemberById(long1);
			memberById.setLanguage("Willing to relocate within my country");
			memberDao.updateMember(memberById);
		}
		List<Long> subList2 = listMembers.subList(26, 50);
		for (Long long1 : subList2) {
			Member memberById = memberDao.getMemberById(long1);
			memberById.setLanguage("Not sure at this time");
			memberDao.updateMember(memberById);
		}
		List<Long> subList3 = listMembers.subList(51, 75);
		for (Long long1 : subList3) {
			Member memberById = memberDao.getMemberById(long1);
			memberById.setLanguage("Not willing to relocate");
			memberDao.updateMember(memberById);
		}
		List<Long> subList4 = listMembers.subList(76, 110);
		for (Long long1 : subList4) {
			Member memberById = memberDao.getMemberById(long1);
			memberById.setLanguage("Willing to relocate within my country");
			memberDao.updateMember(memberById);
		}
	}

	private java.util.Date extractBirthDate(UiMember uim) {
		int month = TangoUtil.getMonthInt(uim.getMonth_name());
		int year = uim.getYear();
		int day = uim.getDay();
		
		String date = day + "-" + month + "-" + year;
	    java.util.Date utilDate = null;

	    try {
	      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	      utilDate = formatter.parse(date);
	      System.out.println("utilDate:" + utilDate);
	    } catch (ParseException e) {
	      System.out.println(e.toString());
	      e.printStackTrace();
	    }
		return utilDate;
	}

	private String extractLanguage(UiMember uim) {
		String[] languages = uim.getLanguages();
		String language = "";
		StringBuffer lang = new StringBuffer("") ;
		if(languages!=null) {
			for(int i=0;i<languages.length;i++){
				lang.append(languages[i]);
				lang.append(",");
			}
			language = lang.toString();
		}
		return language;
	}

	@Override
	@Transactional
	public List<Member> listMembers() {
		return memberDao.listMembers();
	}

	@Override
	@Transactional
	public UiMember getMemberById(Long id) {
		Member m = memberDao.getMemberById(id);
		UiMember uim = TangoUtil.transformToUiMember(m);
		return uim;
	}

	/*private UiMember transformToUiMember(Member m) {
		UiMember uim = new UiMember();
		BeanUtils.copyProperties(m, uim);
		Date birth = m.getBirth();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(birth);
	    int year = cal.get(Calendar.YEAR);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		uim.setDay(day);
		uim.setYear(year);
		return uim;
	}*/

	@Override
	@Transactional
	public void removeMember(Long id) {
		memberDao.removeMember(id);
	}

	@Override
	public List<Long> listMembersWithPics() {
		return memberDao.listMembersWithPics();
	}
	
	@Override
	public void incrementViewCount(Long profile_id) {
		memberDao.incrementViewCount(profile_id);
	}

	@Override
	public void incrementLikesCount(Long profile_id) {
		memberDao.incrementLikesCount(profile_id);
	}

	@Override
	public void incrementMeetReqsCount(Long profile_id) {
		memberDao.incrementMeetReqsCount(profile_id);
	}

	@Override
	public List<String> findUsernamesLike(String username) {
		return memberDao.findUsernamesLike(username);
	}

	@Override
	public Member findProfileByUsername(String username) {
		return memberDao.findProfileByUsername(username);
	}

	@Override
	public List<Member> findProfilesWhereUsernameLike(String username) {
		return memberDao.findProfilesWhereUsernameLike(username);
	}

	@Override
	public void addProfileView(Long viewed_by, Long viewed_who) {
		if(pViewDao.addProfileView(viewed_by, viewed_who)) {
			// we only want to increment the view count for a member if he was viewed by a new account. Repeat views from the same account dont count.
			Member memberWhoWasViewed = memberDao.getMemberById(viewed_who);
			if(memberWhoWasViewed!=null) {
				Long views = memberWhoWasViewed.getViews();
				views++;
				memberWhoWasViewed.setViews(views);
				memberDao.updateMember(memberWhoWasViewed);
			}
		}
	}


	@Override
	public void addProfileMeet(Long meet_request_by, Long meet_who) {
		if(meetMeDao.addMeetMeRecord(meet_request_by, meet_who)) {
			Member memberWhoWasLiked = memberDao.getMemberById(meet_who);
			if(memberWhoWasLiked!=null) {
				Long meetReqs = memberWhoWasLiked.getMeet_requests();
				meetReqs++;
				memberWhoWasLiked.setMeet_requests(meetReqs);
				memberDao.updateMember(memberWhoWasLiked);
			}		
		}
	}


	@Override
	public void addLike(Long like_by, Long like_who) {
		if(likeMeDao.addLikeMeRecord(like_by, like_who)) {
			Member memberWhoWasLiked = memberDao.getMemberById(like_who);
			if(memberWhoWasLiked!=null) {
				Long likes = memberWhoWasLiked.getLikes();
				likes++;
				memberWhoWasLiked.setLikes(likes);
				memberDao.updateMember(memberWhoWasLiked);
			}		
		}
	}


	@Override
	public void removeLike(Long unlike_by, Long unlike_who) {
		if(likeMeDao.removeLikeRecord(unlike_by, unlike_who)) {
			Member memberWhoWasLiked = memberDao.getMemberById(unlike_who);
			if(memberWhoWasLiked!=null) {
				Long likes = memberWhoWasLiked.getLikes();
				if(likes!=null && likes.longValue()==0) return;
				likes--;
				// TODO
				// Not sure what the bug is, but the like count is +1 at this point so have to decrement it twice
				//likes--;
				memberWhoWasLiked.setLikes(likes);
				memberDao.updateMember(memberWhoWasLiked);
			}		
		}
	}


	@Override
	public boolean getLikeStatus(Long logged_in_user, Long profile_in_question) {
		return likeMeDao.getLikeStatus(logged_in_user, profile_in_question);
	}

	@Override
	public int getNoOfPics(Long profile_id) {
		return memberDao.getNoOfPics(profile_id);
	}

	@Override
	public void setNoOfPics(Long profile_id, int no) {
		memberDao.setNoOfPics(profile_id, no);
		
	}

	@Override
	public SignupResponse signUp(String username, String email, String password, String gender) throws AddressException, MessagingException {
		String message = "";
		boolean emailFlag = false;
		boolean usernameFlag = false;
		SignupResponse resp = new SignupResponse();
		if(memberDao.checkIfEmailExists(email)) {
			emailFlag = true;
		}
		if(memberDao.checkIfUsernameExists(username)) {
			usernameFlag = true;
		}
		if(emailFlag && !usernameFlag) {
			message = "Email already exists";
			resp.setReason(message);
			resp.setStatus(false);
			return resp;
		}
		if(usernameFlag && !emailFlag) {
			message = "Username already exists";
			resp.setReason(message);
			resp.setStatus(false);
			return resp;
		}
		if(usernameFlag && emailFlag) {
			message = "Username and Email already exist";
			resp.setReason(message);
			resp.setStatus(false);
			return resp;
		}
		if(!usernameFlag && !emailFlag) {
			memberDao.signUp(username, email, password, gender);
			message = "Successfully registered.";
			resp.setReason(message);
			resp.setStatus(true);
			
			String link = "http://localhost:8080/tttango/confirm?username=" + username;
			String results = "Thank you for registering. \nYour username: <b>" + username +"</b> and passord: <b>" + password + "</b>. <p></p> Please <a href=\" "+ link+ "\" >click here <a> to confirm your login.";
			GoogleMail.Send("sarahfaeez", "Password123", email, "Welcome to Two to Tango", results);
			
			return resp;
		}
		
	return null;
	}

	@Override
	public boolean confirm(String username) {
		return memberDao.confirm(username);
	}

	@Override
	public boolean forgot(String email) throws AddressException, MessagingException {
		Member member = memberDao.forgotLogin(email);
		if(member!=null) {
			// go ahead and send email
			String username = member.getUsername();
			String password = member.getPassword();
			String link = "http://localhost:8080/tttango/static/index.html";
			String message = "You requested login information for your account at <a href=\" "+ link+ "\" >www.tttango.com<a>. Your username is : <b>" + username + "</b> and password is: <b>" + password + "</b>";
			GoogleMail.Send("sarahfaeez", "Password123", email, "Two to Tango: Your login information", message);
			
			return true;
		} else {
			// member with that email not found
			return false;
		}
	}

	@Override
	public void setOnlineStatus(Long profile_id,String status){
		memberDao.setOnlineStatus(profile_id,status);
	}


	/**
	 *  Temporary method to update "no_of_pics" column in MEMBERS table based on how many files for a 
	 *  given profile are present in the folder
	 */
	@Transactional
	public void updatePicNumber() {
		List<Member> allMuslims = memberDao.listMembers();
		PicsService picService = new PicsService();
		List<String> fileNames = picService.getFileNames();
		for (Member member : allMuslims) {
			int noOfPics = 0;
		  for (String fileName : fileNames) {
			if(fileName.contains(member.getId().toString())) {
				noOfPics++;
			}
		}
		  member.setNo_of_pics(new Integer(noOfPics));
		  System.out.print("\nMember with profile id = " + member.getId() + " has so many pics = " + member.getNo_of_pics());
		  memberDao.updateMember(member);
			
		}
		
	}

	@Override
	public boolean addContact(Long profile_id, Long contact_id, String contact_username) {
			if(profile_id==null || contact_id==null ) return false;
			return memberDao.addContact(profile_id,contact_id, contact_username);
		
	}

	@Override
	public List<Contact> getMyContacts(Long profile_id) {
		if(profile_id==null) return null;
		return memberDao.getMyContacts(profile_id);
	}
	
	@Override
	public boolean removeContact(Long profile_id, Long contact_id) {
		if(profile_id==null || contact_id==null ) return false;
		return memberDao.removeContact(profile_id,contact_id);
	}
	

	@Override
	public List<UiMember> searchProfiles(Search search) {
		List<UiMember> list = new ArrayList<UiMember>();
		List<Member> members = memberDao.searchProfiles(search);
		for (Member member : members) {
			list.add(TangoUtil.transformToUiMember(member));
		}
		return list;
	}

	@Override
	public void makeImageMain(Long profile_id, String fileName) {
		memberDao.makeImageMain(profile_id,fileName);
		
	}

	@Override
	public void report(Long profile_id, Long reporter_id, String reason) {
		memberDao.report(profile_id,reporter_id,reason);
	}




}
