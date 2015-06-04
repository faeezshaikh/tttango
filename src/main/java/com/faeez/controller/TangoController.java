package com.faeez.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faeez.model.Contact;
import com.faeez.model.MeetRequester;
import com.faeez.model.Member;
import com.faeez.service.AwsService;
import com.faeez.service.EmailService;
import com.faeez.service.LikeMeService;
import com.faeez.service.MeetMeService;
import com.faeez.service.MemberService;
import com.faeez.service.ProfileViewService;
import com.faeez.ui.model.Email;
import com.faeez.ui.model.Search;
import com.faeez.ui.model.SignupResponse;
import com.faeez.ui.model.UiMember;
import com.faeez.ui.model.Username;

@RestController
public class TangoController {
	private static final Logger logger = LoggerFactory.getLogger(TangoController.class);
	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ProfileViewService profileViewService;

	@Autowired
	private LikeMeService likeMeService;

	@Autowired
	private MeetMeService meetMeService;

	@Autowired
	private AwsService awsService;
	

	///////// [ Login, SignUp, Forgot from  here ] /////////////
	@RequestMapping(value = "/signUpNewUser", method = RequestMethod.GET)
	public SignupResponse  signUpNewUser(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "gender", required = true) String gender)  {
		try {
			return memberService.signUp(username, email, password, gender);
		} catch (Exception e) {
			SignupResponse resp = new SignupResponse();
			resp.setStatus(false);
			resp.setReason("Somethings wrong. Could not sign up at this time. Please try again later");
			e.printStackTrace();
			return resp;
		}
	}
	
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String confirm(@RequestParam(value = "username", required = true) String username)  {
		try {
			String homePage = "www.muslimbuds.com";
			if(memberService.confirm(username)) {
				return "Congratulations! You have successfully completed the registration process. Start cruising by signing in on the <a href=\""+ homePage +"\">www.muslimbuds.com</a>";
			}
		} catch (Exception e) {
			return "Ooops! Something went wrong. You are not confirmed as of this time";
			
		}
		return "Ooops! Something went wrong. You are not confirmed as of this time";
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public boolean forgot(@RequestParam(value = "email", required = true) String email)  {
		try {
			return  memberService.forgot(email);
		} catch (Exception e) {
			e.printStackTrace();
			//logger.debug(e);
			return false;
		}
		
	}
	
	///////// [ Login, SignUp, Forgot till  here ] /////////////
	
	
	
	
	///////// [ Upload Pics from  here ] /////////////
	@RequestMapping(value = "/makeImageMain/{fileName}", method = RequestMethod.GET)
	public void  makeImageMain(@PathVariable String fileName) throws IOException {
		fileName = fileName + ".jpg";
		Long profile_id = getProfileIdFromFileName(fileName);
		if(profile_id==null) return;
		// Original was aws manipulation. Changed: added column to table.
//		awsService.makeImageMain(profile_id, fileName);
		memberService.makeImageMain(profile_id, fileName);
		emailService.updateMainImage(profile_id, fileName);
	}
	
	
	/**
	 * 
	 * @param fileName is in the "65510_5" without the .jpg format
	 * @param replacement is in the jpg format eg: "65510_9.jpg"
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteImage/{fileName}", method = RequestMethod.GET)
	public void  deleteImage(@PathVariable String fileName,
			@RequestParam(value = "replacement", required = false) String replacement) throws IOException {
		 fileName = fileName + ".jpg";
		Long profile_id = getProfileIdFromFileName(fileName);
		if(profile_id==null) return;
		
		awsService.deleteObject(fileName,replacement);
		
		int count = memberService.getNoOfPics(profile_id);
		int nc = count-1;
		memberService.setNoOfPics(profile_id, nc);
		logger.debug("Successfully deleted -> " + fileName + ".jpg");
	}

	@RequestMapping(value="/fileUpload/{profile_id}", method=RequestMethod.POST,produces = "text/plain; charset=utf-8")
	@ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@PathVariable Long profile_id){
		int count = memberService.getNoOfPics(profile_id);
		int nc = count + 1;
		String keyName = profile_id.toString() + "_" + nc + ".jpg";
		String response = awsService.upload(file, profile_id,keyName);
		if(!response.contains("failed")) {
			memberService.setNoOfPics(profile_id, nc);
		}
		return response;
		
	}
	
	private Long getProfileIdFromFileName(String keyName) {
		if(keyName == null) return null;
		String[] array = keyName.split("_");
		Long profile_id = new Long(array[0]);
		return profile_id;
	}
	
	
	///////// [ Upload Pics till here ] /////////////
	
	


	/*@RequestMapping(value = "/findUsernamesLike/{username}", method = RequestMethod.GET)
	public List<Username> findUsernamesLike_old(@PathVariable String username) {
		List<String> usernames = memberService.findUsernamesLike(username);
		List<Username> usernameList = new ArrayList<Username>();
		for (String user : usernames) {
			Username name = new Username();
			name.setName(user);
			usernameList.add(name);
		}
		return usernameList;
	}*/

	
	
	// TODO: Replace this with picprofiles. Remove muslimDaoImpl
	/*@RequestMapping(value = "/picprofilesMuslim", method = RequestMethod.GET)
	public List<Long> getAllMuslimPictureProfiles() {
		return memberService.listMuslimsWithPics();
	}*/


	//////////// [ Member object related start ] ///////////
	
	@RequestMapping(value = "/updateProfile/{profile_id}", method = RequestMethod.POST)
	public void  updateProfile(@PathVariable Long profile_id,@RequestBody UiMember uiMember) throws IOException {
		logger.debug("Updating profile for : " + profile_id);
		memberService.updateMember(uiMember);
		return;
		
	}
	
	@RequestMapping("/profile/{id}")
	public UiMember getProfileDetails(@PathVariable Long id) {
		logger.debug("Getting details for id = " + id);
		return memberService.getMemberById(id);
	}


	// Due to performance reasons not converting to UI member.
	@RequestMapping(value = "/findProfilesWhereUsernameLike", method = RequestMethod.GET)
	public List<Member> findProfilesWhereUsernameLike(@RequestParam(value = "name", required = false) String name) {
		return memberService.findProfilesWhereUsernameLike(name);
	}

	
	// Convenience method for testing only. Not used anywhere in UI.
	@RequestMapping("/findProfileByUsername/{username}")
	public Member findProfileByUsername(@PathVariable String username) {
		return memberService.findProfileByUsername(username);
	}
	

	//////////// [ Member object related end ] ///////////
	
	
	 ////////// [ Misc start ] //////////
	@RequestMapping(value = "/findUsernamesLike", method = RequestMethod.GET)
	public List<Username> findUsernamesLike(@RequestParam(value = "name", required = false) String name) {
		List<String> usernames = memberService.findUsernamesLike(name);
		List<Username> usernameList = new ArrayList<Username>();
		for (String user : usernames) {
			Username nameObj = new Username();
			nameObj.setName(user);
			usernameList.add(nameObj);
		}
		return usernameList;
	}


	@RequestMapping(value = "/picprofiles", method = RequestMethod.GET)
	public List<Long> getAllPictureProfiles() {
		return memberService.listMembersWithPics();
	}
	
	@RequestMapping("/getNoOfPics/{id}")
	public int getNoOfPics(@PathVariable Long id) {
		logger.debug("Getting no of pics for id = " + id);
		return memberService.getNoOfPics(id);
	}
	
	@RequestMapping("/getPicNames/{bucket}")
	public void getPicNames(@PathVariable String bucket) {
		logger.debug("Getting pics for bucket = " + bucket);
		awsService.listObects(bucket);
	}
	
	
	@RequestMapping(value = "/searchProfiles", method = RequestMethod.POST)
	public List<UiMember> searchProfiles(@RequestBody Search search) {
		return memberService.searchProfiles(search);
	}
	
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public void report(@RequestParam(value = "profile_id", required = false) Long profile_id,
					   @RequestParam(value = "reporter_id", required = false) Long reporter_id,
					   @RequestParam(value = "reason", required = false) String reason) {
		memberService.report(profile_id, reporter_id,reason);
	}
	
	 ////////// [ Misc end ] //////////	
	
	
	//// [ Email start ] ///
	// 1. For sending email
	// Whenever a email is sent, marks the conversation as unread.
	@RequestMapping(value = "/sendmail/{sender_id}", method = RequestMethod.POST)
	public void sendEmail(@PathVariable Long sender_id, @RequestBody Email email) {
		emailService.addEmail(sender_id, email);
	}

	// 2. Gets all emails grouped by sender. For Inbox
	@RequestMapping(value = "/getInbox/{profile_id}", method = RequestMethod.GET)
	public List<com.faeez.model.Email> getAllEmailsSentToMe(@PathVariable Long profile_id) {
		memberService.setOnlineStatus(profile_id,"on");
		return emailService.retrieveEmailsSentToMe(profile_id);
	}

	// 3. When an email on inbox is clicked. Gets all conversations between that sender and receiver
	// Also marks conversation as read.
	@RequestMapping(value = "/getEmailConversations", method = RequestMethod.GET)
	public List<com.faeez.model.Email> getEmailConversations(@RequestParam(value = "sender_id", required = false) Long sender_id, @RequestParam(value = "receiver_id", required = false) Long receiver_id) {
		return emailService.getEmailConversations(sender_id, receiver_id);
	}
	
	
	// 4. Sets login_status column in DB to "off"
	@RequestMapping(value = "/logout/{profile_id}", method = RequestMethod.GET)
	public void logout(@PathVariable Long profile_id) {
			memberService.setOnlineStatus(profile_id,"off");
	}

	
	@RequestMapping(value = "/getSentItems/{profile_id}", method = RequestMethod.GET)
	public List<com.faeez.model.Email> getAllEmailsSentByMe(@PathVariable Long profile_id) {
		return emailService.retrieveEmailsSentByMe(profile_id);
	}
	
	
	@RequestMapping(value = "/getEmailByEmailId/{email_id}", method = RequestMethod.GET)
	public com.faeez.model.Email getEmailByEmailId(@PathVariable Long email_id) {
		return emailService.getEmailById(email_id);
	}

	//// [ Email end ] ///
	
	//// [ Profile View Start ] ////////
	@RequestMapping(value = "/addProfileView", method = RequestMethod.GET)
	public void addProfileView(@RequestParam(value = "viewed_by", required = false) Long viewed_by, 
							   @RequestParam(value = "viewed_who", required = false) Long viewed_who) {
		if(viewed_by.equals(viewed_who)) return; 
		memberService.addProfileView(viewed_by, viewed_who);
	}
	
	
	@RequestMapping(value = "/getWhoViewedMe/{profile_id}", method = RequestMethod.GET)
	public List<MeetRequester>  getWhoViewedMe(@PathVariable Long profile_id) {
		return profileViewService.getProfilesWhoViewedMe(profile_id);
	}

	@RequestMapping(value = "/getWhoIViewed/{profile_id}", method = RequestMethod.GET)
	public List<MeetRequester> getWhoIViewed(@PathVariable Long profile_id) {
		return profileViewService.getProfilesWhoIViewed(profile_id);
	}

	
	//// [ Profile View End ] ////////

	//// [Like Mes Start ]  ////
	@RequestMapping(value = "/getWhoLikedMe/{profile_id}", method = RequestMethod.GET)
	public List<MeetRequester> getWhoLikedMe(@PathVariable Long profile_id) {
		return likeMeService.getProfilesWhoLikedMe(profile_id);
	}

	@RequestMapping(value = "/getWhoILiked/{profile_id}", method = RequestMethod.GET)
	public List<MeetRequester> getWhoILiked(@PathVariable Long profile_id) {
		return likeMeService.getProfilesWhoILiked(profile_id);
	}
	
	@RequestMapping(value = "/addLike", method = RequestMethod.GET)
	public void addLikeMe(@RequestParam(value = "like_by", required = false) Long like_by, @RequestParam(value = "like_who", required = false) Long like_who) {
		logger.debug("In the add like me controller. like by =  " + like_by + " like who = " + like_who);
		memberService.addLike(like_by, like_who);
	}
	
	@RequestMapping(value = "/removeLike", method = RequestMethod.GET)
	public void removeLike(@RequestParam(value = "unlike_by", required = false) Long unlike_by,  @RequestParam(value = "unlike_who", required = false) Long unlike_who) {
		logger.debug("In the remove like controller. unlike by =  " + unlike_by + " unlike who = " + unlike_who);
		memberService.removeLike(unlike_by, unlike_who);
	}

	@RequestMapping(value = "/getLikeStatus", method = RequestMethod.GET)
	public boolean getLikeStatus(@RequestParam(value = "logged_in_user", required = false) Long logged_in_user, 
							   @RequestParam(value = "profile_in_question", required = false) Long profile_in_question) {
		return memberService.getLikeStatus(logged_in_user, profile_in_question);
	}
	//// [Like Mes End ]  ////

	//// [ Meet Mes Start here ] ////
	@RequestMapping(value = "/getWhoWannaMeetMe/{profile_id}", method = RequestMethod.GET)
	public List<com.faeez.model.MeetRequester> getWhoWannaMeetMe(@PathVariable Long profile_id) {
		return meetMeService.getProfilesWhoWannaMeetMe(profile_id);
	}

	@RequestMapping(value = "/getWhoIWannaMeet/{profile_id}", method = RequestMethod.GET)
	public List<com.faeez.model.MeetRequester> getWhoIWannaMeet(@PathVariable Long profile_id) {
		return meetMeService.getProfilesWhoIWannaMeet(profile_id);
	}

	@RequestMapping(value = "/addProfileMeet", method = RequestMethod.GET)
	public void addProfileMeet(@RequestParam(value = "meet_request_by", required = false) Long meet_request_by, 
							   @RequestParam(value = "meet_who", required = false) Long meet_who) {
		memberService.addProfileMeet(meet_request_by, meet_who);
	}

	//// [ Meet Mes End here ] ////
	
	
	//// [ Contacts Start here ] ////
	
	
	@RequestMapping(value = "/addContact", method = RequestMethod.GET)
	public boolean addContact(@RequestParam(value = "profile_id", required = false) Long profile_id, 
							   @RequestParam(value = "contact_id", required = false) Long contact_id,
							   @RequestParam(value = "contact_username", required = false) String contact_username) {
		return memberService.addContact(profile_id, contact_id,contact_username);
	}
	
	
	@RequestMapping(value = "/getMyContacts/{profile_id}", method = RequestMethod.GET)
	public List<Contact> getMyContacts(@PathVariable Long profile_id) {
		return memberService.getMyContacts(profile_id);
	}
	
	
	
	@RequestMapping(value = "/removeContact", method = RequestMethod.GET)
	public boolean removeContact(@RequestParam(value = "profile_id", required = false) Long profile_id, 
							   @RequestParam(value = "contact_id", required = false) Long contact_id) {
		return memberService.removeContact(profile_id, contact_id);
	}
	
	//// [ Contact End here ] ////
	
	
	@RequestMapping(value = "/updatePicNumber/", method = RequestMethod.GET)
	public void updatePicNumber() {
		memberService.updatePicNumber();
	}
	
	@RequestMapping(value = "/updateMemberPartial/", method = RequestMethod.GET)
	public void updateMemberPartial() {
		memberService.updateMemberPartial();
	}
	
}