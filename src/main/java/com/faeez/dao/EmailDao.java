package com.faeez.dao;

import java.util.List;

import com.faeez.model.Email;

public interface EmailDao {

	public void addEmail(Email e);
	public List<Email> retrieveEmailsSentByMe(Long profile_id);
	public List<Email> retrieveEmailsSentToMe(Long profile_id);
	public Email getEmailById(Long id);
	public void markConversation(Long sender_id, Long receiver_id, boolean isRead);
	List<Email> retrieveEmailConversations(Long sender_id, Long receiver_id);
	void markEmailAsRead(Long sender_id, Long receiver_id);
	void markConversationsTimeAndNewRecepient(Long sender_id, Long receiver_id);
	public void updateMainImage(Long profile_id, String fileName);
}
