package com.faeez.service;

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
		
		db_email.setReceiver_main_img(memberDao.getMainImg(email.getReceivers_profile_id()));
		db_email.setSender_main_img(email.getSenders_pic_url());
		emailDao.addEmail(db_email);
		
		// Every time a new email is sent, mark the conversation as unread .
		// update EMAILS set is_conversation_new = 'y' where receiver_id = xx and sender_id = yy; 
		// true means not read.
		emailDao.markConversation(email.getSenders_profile_id(), email.getReceivers_profile_id(),true);
		
		
		// this is because we are retrieving emails after doing a Group by(), so we want the time and the recepient for each grp.
		emailDao.markConversationsTimeAndNewRecepient(sender_id, email.getReceivers_profile_id());
	
	}


	@Override
	public com.faeez.model.Email getEmailById(Long id) {
		Email emailObj = emailDao.getEmailById(id);
		return emailObj;
	}
	
	@Override
	public List<com.faeez.model.Email> getEmailConversations(Long sender_id, Long receiver_id) {
		
		List<com.faeez.model.Email> emailObj = emailDao.retrieveEmailConversations(sender_id, receiver_id);
		// Mark the conversation as read. hence false;
		emailDao.markConversation(sender_id, receiver_id,false);
		emailDao.markEmailAsRead(sender_id,receiver_id);
		return emailObj;
	}


	@Override
	public List<com.faeez.model.Email> retrieveEmailsSentByMe(Long profile_id) {
		 List<Email> daoEmails = emailDao.retrieveEmailsSentByMe(profile_id);
		 return daoEmails;
	}

	@Override
	public List<Email> retrieveEmailsSentToMe(Long profile_id) {
		List<Email> daoEmails = emailDao.retrieveEmailsSentToMe(profile_id);
		return daoEmails;
		
	}


	@Override
	public void updateMainImage(Long profile_id, String fileName) {
		emailDao.updateMainImage(profile_id,fileName);
		
	}


}
