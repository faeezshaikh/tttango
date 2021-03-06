package com.faeez.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.faeez.model.Email;

@Repository
public class EmailDaoImpl implements EmailDao {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailDaoImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}



	@Override
	public void addEmail(Email e) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(e);
		logger.debug("Member saved successfully, Member Details="+e);
	}

	@Override
	public Email getEmailById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();		
		//Member p = (Member) session.load(Member.class, id);
		@SuppressWarnings("rawtypes")
		List list = session.createQuery("from Email where id = "+id).list();
		if(list!=null && list.size()>0) {
			Email e = (Email)list.get(0);
			logger.debug("Email loaded successfully, Email details="+e.getReceiver_id());
			return e;
		} else {
			logger.debug("Email not found for id: " + id);
			return null;
		}
	}

	public void updateEmail(Email e) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(e);
		logger.debug("Email updated successfully, Member Details=" + e);
	}



	@Override
	public List<Email> retrieveEmailsSentByMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Email> EmailsList = session.createQuery("from Email where sender_id = " + profile_id).list();
		logger.debug("Successfully retrieved " + EmailsList.size() + " emails sent by   :"+profile_id);
		return EmailsList;
	}


	/**
	 * select * from EMAILS where receiver_id = 65510 group by(sender_id) order by email_time ;
	 * Note: No order by clause since ordering will happen in datatable.
	 */
	
	
	
	
	
	/**
	 * 
	select t1.receiver_img, t2.receiver_id, t2.sender_id, t2.sender_img, t2.msg, t2.emailId, t2.emailTime, t2.isOpened, t2.isConvNew,
t2.newConvTime, t2.newEmailFor

  from  (

(select MUSLIMS.main_img as receiver_img 
from MUSLIMS, EMAILS 
where EMAILS.receiver_id = '14447434' and MUSLIMS.profile_id = EMAILS.receiver_id 
 ) as t1
join

(select MUSLIMS.main_img as sender_img ,EMAILS.message as msg ,EMAILS.receiver_id as receiver_id, EMAILS.sender_id as sender_id,
		EMAILS.email_id as emailId, EMAILS.email_time as emailTime, EMAILS.is_opened as isOpened, 
        EMAILS.is_conversation_new as isConvNew, EMAILS.new_conversation_time as newConvTime, EMAILS.new_email_for as newEmailFor
from MUSLIMS, EMAILS 
 where EMAILS.receiver_id = '14447434' and MUSLIMS.profile_id = EMAILS.sender_id 
 ) as t2

) group by t2.sender_img order by t2.newConvTime desc;

	 */

	@Override
	public List<Email> retrieveEmailsSentToMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Email> EmailsList = session.createQuery("from Email where receiver_id = " + profile_id + " group by(sender_id) order by new_conversation_time desc").list();
		logger.debug("Successfully retrieved  " + EmailsList.size() + " emails received for   :"+profile_id);
		return EmailsList;
	}



	@Override
	public void markConversation(Long sender_id, Long receiver_id,boolean isConvNew) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Email set is_conversation_new = :readStatus where (receiver_id = :receivers_id and sender_id = :senders_id)"
				+ "  or (receiver_id = :senders_id and  sender_id = :receivers_id)" );
		query.setParameter("receivers_id", receiver_id);
		query.setParameter("senders_id", sender_id);
		if(isConvNew) {
			query.setParameter("readStatus", "y");
		} else {
			query.setParameter("readStatus", "n");
		}
		int result = query.executeUpdate();
		logger.debug("Successfully marked conversation as read between receiver = " + receiver_id + " and sender = " + sender_id);
		return;
	}
	
	@Override
	public void markConversationsTimeAndNewRecepient(Long sender_id, Long receiver_id) {
		Session session = this.sessionFactory.getCurrentSession();
		
		
		org.hibernate.Query query = session.createQuery("update Email set new_conversation_time = :now where (receiver_id = :receivers_id and sender_id = :senders_id)"
				+ "  or (receiver_id = :senders_id and  sender_id = :receivers_id)" );
		query.setParameter("receivers_id", receiver_id);
		query.setParameter("senders_id", sender_id);
		query.setParameter("now", new Date());
		int result = query.executeUpdate();
		
		org.hibernate.Query query2 = session.createQuery("update Email set new_email_for = :newRecepient where (receiver_id = :receivers_id and sender_id = :senders_id)"
				+ "  or (receiver_id = :senders_id and  sender_id = :receivers_id)" );
		query2.setParameter("receivers_id", receiver_id);
		query2.setParameter("senders_id", sender_id);
		query2.setParameter("newRecepient", receiver_id);
		int result2 = query2.executeUpdate();
		
		
		
		logger.debug("Successfully marked conversations new time and new recepient : " + receiver_id + " and sender = " + sender_id);
		return;
	}
	
	@Override
	public List<Email> retrieveEmailConversations(Long sender_id, Long receiver_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("from Email where (receiver_id = :receivers_id and  sender_id = :senders_id) "
				+ " or (receiver_id = :senders_id and  sender_id = :receivers_id) order by email_time desc " );
		query.setParameter("receivers_id", receiver_id);
		query.setParameter("senders_id", sender_id);
		query.setMaxResults(20);
		 List<Email> mails = (List<Email>)query.list();
		logger.debug("Successfully retrived conversations between receiver = " + receiver_id + " and sender = " + sender_id);
		
		// While retrieving u need to fetch the latest email in the thread and mark it as read ..is_opened=y for that email.
		
		
		return mails;
	}

	@Override
	public void markEmailAsRead(Long sender_id, Long receiver_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Email set is_opened = 'y' where (receiver_id = :receivers_id and sender_id = :senders_id)"
				+ "  or (receiver_id = :senders_id and  sender_id = :receivers_id)" );
		query.setParameter("receivers_id", receiver_id);
		query.setParameter("senders_id", sender_id);
		int result = query.executeUpdate();
		logger.debug("Successfully marked email as read ");
		return;
	}



	@Override
	public void updateMainImage(Long profile_id, String fileName) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Email set receiver_main_img = :fileName where receiver_id = :receiverId" );
		query.setParameter("fileName", fileName);
		query.setParameter("receiverId", profile_id);
		query.executeUpdate();
		
		org.hibernate.Query query2 = session.createQuery("update Email set sender_main_img = :fileName where sender_id = :senderId" );
		query2.setParameter("fileName", fileName);
		query2.setParameter("senderId", profile_id);
		query2.executeUpdate();
		
		logger.debug("Successfully updated main image in EMAILS table for " + profile_id);
		return;
	}


}
