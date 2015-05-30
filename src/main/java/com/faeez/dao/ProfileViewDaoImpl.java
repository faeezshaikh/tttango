package com.faeez.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.faeez.model.MeetRequester;
import com.faeez.model.ProfileView;

@Repository
public class ProfileViewDaoImpl implements ProfileViewDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileViewDaoImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public void addProfileView(ProfileView e) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(e);
		logger.info("Profile saved successfully, Member Details="+e);
	}

	@Override
	public void updateProfileView(ProfileView p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Profile updated successfully, Profile Details="+p);
	}

/*	public ProfileView getExistingProfileView(Long viewed_by,Long viewed_who) {
		ProfileView pv = null;
		Session session = this.sessionFactory.getCurrentSession();
		List list = session.createQuery("from ProfileView where viewed_who = " + viewed_who + " and viewed_by = " + viewed_by).list();
		if(list!=null && list.size()>0) {
			pv = (ProfileView) list.get(0);
			logger.info("Successfully retrieved Profile View for viewer: " + viewed_by);
		} else {
			logger.info("View doesnt exist for viewer: " + viewed_by);
		}
		return pv;
	}*/

	@Override
	public List<MeetRequester> getProfilesWhoViewedMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country,b.viewed_when) from Member a, ProfileView b where a.id = b.viewed_by and b.viewed_who = :viewedwho order by b.viewed_when desc");
		query.setParameter("viewedwho", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " views for :"+profile_id);
		return meetList;
	}

	@Override
	public List<MeetRequester> getProfilesWhoIViewed(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country,b.viewed_when) from Member a, ProfileView b where a.id = b.viewed_who and b.viewed_by = :viewedby order by b.viewed_when desc");
		query.setParameter("viewedby", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " views for :"+profile_id);
		return meetList;
	}
	
	public List<ProfileView> getProfilesWhoIViewed_old(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ProfileView> viewsList = session.createQuery("from ProfileView where viewed_by = " + profile_id).list();
		logger.info("Successfully retrieved " + viewsList.size() + " views for :"+profile_id);
		return viewsList;
	}

	@Override
	public boolean addProfileView(Long viewed_by, Long viewed_who) {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProfileView> views = session.createQuery("from ProfileView where viewed_by = " + viewed_by + " and viewed_who = " + viewed_who).list();
		if(views!=null && views.size()>0) {
			return false; // record already present..do nothing
		} else {
			ProfileView pView = new ProfileView();
			pView.setViewed_by(viewed_by);
			pView.setViewed_who(viewed_who);
			pView.setViewed_when(new Date());
			addProfileView(pView);
			return true;
		}
	}
}
