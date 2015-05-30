package com.faeez.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
	public List<ProfileView> getProfilesWhoViewedMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ProfileView> viewsList = session.createQuery("from ProfileView where viewed_who = " + profile_id + " order by viewed_when desc").list();
		logger.info("Successfully retrieved " + viewsList.size() + " views for :"+profile_id);
		return viewsList;
	}



	@Override
	public List<ProfileView> getProfilesWhoIViewed(Long profile_id) {
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
