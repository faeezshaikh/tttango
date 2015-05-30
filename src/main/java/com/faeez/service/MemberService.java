package com.faeez.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.faeez.model.Contact;
import com.faeez.model.Member;
import com.faeez.ui.model.Search;
import com.faeez.ui.model.SignupResponse;
import com.faeez.ui.model.UiMember;

public interface MemberService {

/*	Member save(Member goal);

	List<Member> findAllMembersWithPics();

	Member getProfileDetails(String id);

	void sendEmail(String senderId, com.faeez.controller.Email email);*/

	
	public void addMember(Member p);
	public void updateMember(UiMember p);
	public List<Member> listMembers();
	public UiMember getMemberById(Long id);
	public void removeMember(Long id);
	List<Long> listMembersWithPics();
	public void incrementViewCount(Long profile_id);
	public void incrementLikesCount(Long profile_id);
	public void incrementMeetReqsCount(Long profile_id);
	public List<String> findUsernamesLike(String username);
	public Member findProfileByUsername(String username);
	public List<Member> findProfilesWhereUsernameLike(String username);
	
	public void addProfileView(Long viewed_by, Long viewed_who);
	public void addProfileMeet(Long meet_request_by, Long meet_who);
	public void addLike(Long like_by, Long like_who);
	public void removeLike(Long unlike_by, Long unlike_who);
	public boolean getLikeStatus(Long logged_in_user, Long profile_in_question);
	
	public int getNoOfPics(Long profile_id);
	public void setNoOfPics(Long profile_id, int no);
	public SignupResponse signUp(String username,String email,String password, String gender) throws AddressException, MessagingException;
	public boolean confirm(String username);
	public boolean forgot(String email) throws AddressException, MessagingException;
	public void setOnlineStatus(Long profile_id,String string);
	
	public void updatePicNumber();
	void updateMemberPartial();
	public boolean addContact(Long profile_id, Long contact_id, String contact_username);
	public List<Contact> getMyContacts(Long profile_id);
	public List<UiMember> searchProfiles(Search search);
	public boolean removeContact(Long profile_id, Long contact_id);
	public void makeImageMain(Long profile_id, String fileName);
	public void report(Long profile_id, Long reporter_id, String reason);
	
}
