package com.faeez.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.EmailDao;
import com.faeez.dao.MemberDao;
import com.faeez.model.Email;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {


	private EmailDao emailDao;
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setEmailDao(EmailDao emailDao) {
		this.emailDao = emailDao;
	}

	@Override
	public void addEmail(Long sender_id,com.faeez.ui.model.Email email) {


		Email db_email = new Email();

		Date date = TangoUtil.getNow();
		db_email.setEmail_time(date);
		
		
		db_email.setMessage(email.getMessage());
		db_email.setReceiver_id(email.getReceivers_profile_id());
		db_email.setSender_id(sender_id);
		
//		db_email.setReceiver_main_img(memberDao.getMemberById(email.getReceivers_profile_id()).getMain_img());
//		db_email.setSender_main_img(memberDao.getMemberById(sender_id).getMain_img());
		emailDao.addEmail(db_email);
		
		// Every time a new email is sent, mark the conversation as unread .
		// update EMAILS set is_conversation_new = 'y' where receiver_id = xx and sender_id = yy; 
		// true means not read.
		emailDao.markConversation(email.getSenders_profile_id(), email.getReceivers_profile_id(),true);
		
		
		// this is because we are retrieving emails after doing a Group by(), so we want the time and the recepient for each grp.
		emailDao.markConversationsTimeAndNewRecepient(sender_id, email.getReceivers_profile_id());
	
	}


	@Override
	public com.faeez.ui.model.Email getEmailById(Long id) {
		
		Email emailObj = emailDao.getEmailById(id);
		// this method is only called when a user is opening a sent item..hence we dont wanna mark it as read
		// Mark the conversation as read. hence true;
		//emailDao.markConversation(emailObj.getSender_id(), emailObj.getReceiver_id(),true);
		
		com.faeez.ui.model.Email uiEmail = new com.faeez.ui.model.Email();
		transformSingleObject(emailObj, uiEmail,-1L);
		return uiEmail;
	}
	
	@Override
	public List<com.faeez.ui.model.Email> getEmailConversations(Long sender_id, Long receiver_id) {
		
		List<Email> emailObj = emailDao.retrieveEmailConversations(sender_id, receiver_id);
		// Mark the conversation as read. hence false;
		emailDao.markConversation(sender_id, receiver_id,false);
		emailDao.markEmailAsRead(sender_id,receiver_id);
		List<com.faeez.ui.model.Email> uiEmails = transformEmailObjects(emailObj,-1L);
		return uiEmails;
	}


	@Override
	public List<com.faeez.ui.model.Email> retrieveEmailsSentByMe(Long profile_id) {
		 List<Email> daoEmails = emailDao.retrieveEmailsSentByMe(profile_id);
		 List<com.faeez.ui.model.Email> emails = transformEmailObjects(daoEmails,-1L);
		 return emails;
	}

	@Override
	public List<com.faeez.ui.model.Email> retrieveEmailsSentToMe(Long profile_id) {
		List<Email> daoEmails = emailDao.retrieveEmailsSentToMe(profile_id);
		List<com.faeez.ui.model.Email> emails = transformEmailObjects(daoEmails,profile_id);
		return emails;
		
	}

	private List<com.faeez.ui.model.Email> transformEmailObjects(List<Email> daoEmails,Long profile_id) {
		List<com.faeez.ui.model.Email> emails = new ArrayList<com.faeez.ui.model.Email>();
		for (Email email : daoEmails) {
			com.faeez.ui.model.Email uiEmail = new com.faeez.ui.model.Email();
			transformSingleObject(email, uiEmail,profile_id);
			emails.add(uiEmail);
		}
		return emails;
	}

	private void transformSingleObject(Email email, com.faeez.ui.model.Email uiEmail, Long profile_id) {
		if(profile_id.compareTo(-1L) ==0) {
			uiEmail.setEmail_time_millis(new SimpleDateFormat("MMM dd yyyy hh:mm:ss a z").format(email.getEmail_time()));
		} else {
			if(email.getEmail_time()!=null) {
				//uiEmail.setEmail_time_millis(new SimpleDateFormat("MMM dd yyyy hh:mm:ss a z").format(email.getNew_conversation_time())); 
				// notice we are setting the time to the conversation time and not individual email time
				uiEmail.setEmail_time_millis(email.getNew_conversation_time().toString());
			}	
		}
		
		// Tricky BuG!
		// when replying to an email, the conversation is set to "y", this causes both the sender and recepient to see green ..
		// fix - mark conversation as new only if field == 'y' and receiver id = me
		if(email.getIs_conversation_new().equalsIgnoreCase("y") && email.getNew_email_for()!=null && email.getNew_email_for().compareTo(profile_id) == 0) {
			uiEmail.setIs_conversation_new("y");
		} else {
			uiEmail.setIs_conversation_new("n");
		}
		uiEmail.setMessage(email.getMessage());
		uiEmail.setReceivers_profile_id(email.getReceiver_id());
//		uiEmail.setSenders_pic_url(TangoUtil.base_img_url+ email.getSender_id()+ "_1.jpg");
		String senderMainImg = memberDao.getMainImg(email.getSender_id());
		if(senderMainImg!=null) {
			uiEmail.setSenders_pic_url(TangoUtil.base_img_url+ senderMainImg);
		}
//		uiEmail.setReceivers_pic_url(TangoUtil.base_img_url+ email.getReceiver_id() + "_1.jpg");
		String receiverMainImg = memberDao.getMainImg(email.getReceiver_id());
		if(receiverMainImg!=null) {
			uiEmail.setReceivers_pic_url(TangoUtil.base_img_url+ receiverMainImg);
		}
		uiEmail.setSenders_profile_id(email.getSender_id());
		uiEmail.setEmailId(email.getId());
		if(email.getIs_opened()!=null) {
			uiEmail.setHas_receiver_opened(email.getIs_opened().equalsIgnoreCase("n")?"No":"Yes");
		} else {
			uiEmail.setHas_receiver_opened("No");
		}
	}


}
