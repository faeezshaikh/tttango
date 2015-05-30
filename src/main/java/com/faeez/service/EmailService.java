package com.faeez.service;

import java.util.List;

import com.faeez.ui.model.Email;

public interface EmailService {

	public void addEmail(Long sender_id,Email e);
	public List<Email> retrieveEmailsSentByMe(Long profile_id);
	public List<Email> retrieveEmailsSentToMe(Long profile_id);
	public Email getEmailById(Long id);
	List<Email> getEmailConversations(Long sender_id, Long receiver_id);
}
