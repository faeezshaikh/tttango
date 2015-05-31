package com.faeez.service;

import java.util.List;

import com.faeez.ui.model.Email;

public interface EmailService {

	public void addEmail(Long sender_id,Email e);
	public List<com.faeez.model.Email> retrieveEmailsSentByMe(Long profile_id);
	public List<com.faeez.model.Email> retrieveEmailsSentToMe(Long profile_id);
	public com.faeez.model.Email getEmailById(Long id);
	List<com.faeez.model.Email> getEmailConversations(Long sender_id, Long receiver_id);
	public void updateMainImage(Long profile_id, String fileName);
}
