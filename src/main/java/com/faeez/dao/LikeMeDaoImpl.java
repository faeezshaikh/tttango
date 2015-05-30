package com.faeez.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.faeez.model.LikeMe;
import com.faeez.model.MeetRequester;

@Repository
public class LikeMeDaoImpl implements LikeMeDao {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeMeDaoImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public List<MeetRequester> getProfilesWhoLikedMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country, b.liked_when) from Member a, LikeMe b where a.id = b.liked_by and b.liked_who = :likedwho");
		query.setParameter("likedwho", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " likes by for : " + profile_id);
		return meetList;
	}


	@Override
	public List<MeetRequester> getProfilesWhoILiked(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country, b.liked_when) from Member a, LikeMe b where a.id = b.liked_who and b.liked_by = :likedby");
		query.setParameter("likedby", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " likes by for : " + profile_id);
		return meetList;
	}

	@Override
	public void addLikeMe(LikeMe likeMe) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(likeMe);
		logger.debug("Like saved successfully,  Details="+likeMe);
	}

	
	/**
	 * this method is different from meet me..since from UI , user can also unlike. so we dont check if the record exists or not
	 * we simply add it.
	 */
	@Override
	public boolean addLikeMeRecord(Long like_by, Long like_who) {
			LikeMe likeMe = new LikeMe();
			likeMe.setLiked_by(like_by);
			likeMe.setLiked_when(new Date());
			likeMe.setLiked_who(like_who);
			addLikeMe(likeMe);
			return true;
		
	}

	@Override
	public boolean removeLikeRecord(Long unlike_by, Long unlike_who) {
		Session session = this.sessionFactory.getCurrentSession();
		// In order to unlike, the like record shud be present in the first place.
		List<LikeMe> likes = session.createQuery("from LikeMe where liked_by = " + unlike_by + " and liked_who = " + unlike_who).list();
		if(likes!=null && likes.size()>0) {
			
			Query query = session.createQuery("delete LikeMe where liked_by = :unlike_by  and liked_who = :unlike_who");
			query.setParameter("unlike_by", unlike_by);
			query.setParameter("unlike_who", unlike_who);
			int result = query.executeUpdate();
			if(result>0) {
				logger.debug("Successfully removed Like for " + unlike_who.toString() + " by " + unlike_by.toString());				
				return true;
			}
			else
				return false;
		} else {
			logger.debug("Unsuccessful attemp to remove Like for " + unlike_who.toString() + " by " + unlike_by.toString() + " because record doesnt exist");
			return false;
		}
	}

	@Override
	public boolean getLikeStatus(Long logged_in_user, Long profile_in_question) {
		Session session = this.sessionFactory.getCurrentSession();
		List<LikeMe> likes = session.createQuery("from LikeMe where liked_by = " + logged_in_user + " and liked_who = " + profile_in_question).list();
		if(likes!=null && likes.size()>0) {
			logger.debug("Successfully returning Like for loggedInUser =  " + logged_in_user.toString() + " profile in question" + profile_in_question.toString());
			return true;
		} else {
			logger.debug("Unsuccessful attempt for returning Like for loggedInUser =  " + logged_in_user.toString() + " profile in question" + profile_in_question.toString());
			return false;
		}
	}

}
