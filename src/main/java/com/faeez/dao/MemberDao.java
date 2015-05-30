package com.faeez.dao;

import java.util.List;

import com.faeez.model.Contact;
import com.faeez.model.Member;
import com.faeez.model.Report;
import com.faeez.ui.model.Search;

public interface MemberDao {

	public void addMember(Member p);
	public void updateMember(Member p);
	public List<Member> listMembers();
	public Member getMemberById(Long id);
	public void removeMember(Long id);
	List<Long> listMembersWithPics();
	public void incrementViewCount(Long profile_id);
	public void incrementLikesCount(Long profile_id);
	public void incrementMeetReqsCount(Long profile_id);
	public List<String> findUsernamesLike(String username);
	public Member findProfileByUsername(String username);
	public List<Member> findProfilesWhereUsernameLike(String username);
	public int getNoOfPics(Long profile_id);
	public void setNoOfPics(Long profile_id, int no);
	public void signUp(String username,String email,String password, String gender);
	public boolean checkIfUsernameExists(String username);
	public boolean checkIfEmailExists(String email);
	public boolean confirm(String username);
	public Member forgotLogin(String email);
	public void setOnlineStatus(Long profile_id, String status);
	public boolean addContact(Long profile_id, Long contact_id, String contact_username);
	void addContact(Contact c);
	public List<Contact> getMyContacts(Long profile_id);
	public List<Member> searchProfiles(Search search);
	public boolean removeContact(Long profile_id, Long contact_id);
	public void makeImageMain(Long profile_id, String fileName);
	public String getMainImg(Long id);
	public void report(Long profile_id, Long reporter_id, String reason);
	void addReport(Report r);
}
