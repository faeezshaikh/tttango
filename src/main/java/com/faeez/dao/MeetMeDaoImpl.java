package com.faeez.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.faeez.model.MeetMe;
import com.faeez.model.MeetRequester;

@Repository
public class MeetMeDaoImpl implements MeetMeDao {
	
	private static final Logger logger = LoggerFactory.getLogger(MeetMeDaoImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public List<MeetRequester> getProfilesWhoWannaMeetMe(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country,b.meet_req_when) from Member a, MeetMe b where a.id = b.meet_by and b.meet_who = :meetwho order by b.meet_req_when desc");
		query.setParameter("meetwho", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " meet requests for :"+profile_id);
		return meetList;
	}
	

	@Override
	public List<MeetRequester> getProfilesWhoIWannaMeet(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select new com.faeez.model.MeetRequester(a.id,a.main_img,a.likes,a.views,a.meet_requests,a.username,a.online_status,a.birth,a.occupation,a.city,a.last_login, a.country, b.meet_req_when) from Member a, MeetMe b where a.id = b.meet_who and b.meet_by = :meetby order by b.meet_req_when desc");
		query.setParameter("meetby", profile_id);
		query.setMaxResults(100);
		List<MeetRequester> meetList = query.list();
		logger.info("Successfully retrieved " + meetList.size() + " 'I wanna meets' for : " + profile_id);
		return meetList;
	}
	

	@Override
	public boolean addMeetMeRecord(Long meet_request_by, Long meet_who) {
		Session session = this.sessionFactory.getCurrentSession();
		List<MeetMe> meets = session.createQuery("from MeetMe where meet_by = " + meet_request_by + " and meet_who = " + meet_who).list();
		if(meets!=null && meets.size()>0) {
			return false; // record already present..do nothing
		} else {
			MeetMe meetMe = new MeetMe();
			meetMe.setMeet_by(meet_request_by);
			meetMe.setMeet_req_when(new Date());
			meetMe.setMeet_who(meet_who);
			addMeetMe(meetMe);
			return true;
		}
	}
	
	
	@Override
	public void addMeetMe(MeetMe e) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(e);
		logger.info("Meetme record saved successfully,  Details="+e);
	}

}
