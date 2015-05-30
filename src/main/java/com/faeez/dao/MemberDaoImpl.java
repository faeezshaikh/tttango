package com.faeez.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.faeez.model.Contact;
import com.faeez.model.MeetMe;
import com.faeez.model.Member;
import com.faeez.model.Report;
import com.faeez.service.TangoUtil;
import com.faeez.ui.model.Search;

@Repository
public class MemberDaoImpl implements MemberDao {

	private static final Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);

	private SessionFactory sessionFactory;
	private static String defaultAnswer = "Not answered yet"; // stored in db

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addMember(Member m) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(m);
		logger.info("Member saved successfully, Member Details=" + m);
	}

	@Override
	public void updateMember(Member p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Member updated successfully, Member Details=" + p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> listMembers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Member> MembersList = session.createQuery("from Member").list();
		for (Member p : MembersList) {
			logger.info("Member List::" + p);
		}
		return MembersList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listMembersWithPics() {
		Session session = this.sessionFactory.getCurrentSession();
		//List<Long> MembersList = session.createQuery("select id from Member where year < 1966 and no_of_pics > 1").list();
		List<Long> MembersList = session.createQuery("select id from  Member").list();
		for (Long p : MembersList) {
			logger.info("Member List with pics::" + p);
		}
		return MembersList;
	}

	@Override
	public Member getMemberById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		List list = session.createQuery("from Member where id = " + id).list();
		if (list != null && list.size() > 0) {
			Member p = (Member) list.get(0);
			logger.info("Member loaded successfully, Member details=" + p);
			return p;
		} else {
			logger.info("Member not found for id: " + id);
			return null;
		}
	}


	@Override
	public void removeMember(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Member p = (Member) session.load(Member.class, id);
		if (null != p) {
			session.delete(p);
		}
		logger.info("Member deleted successfully, Member details=" + p);
	}

	@Override
	public void incrementViewCount(Long profile_id) {
		Member member = getMemberById(profile_id);
		Long views = member.getViews();
		views++;
		member.setViews(views);
		updateMember(member);
	}

	@Override
	public void incrementLikesCount(Long profile_id) {
		Member member = getMemberById(profile_id);
		Long likes = member.getLikes();
		likes++;
		member.setViews(likes);
		updateMember(member);
	}

	@Override
	public void incrementMeetReqsCount(Long profile_id) {
		Member member = getMemberById(profile_id);
		Long meets = member.getMeet_requests();
		meets++;
		member.setViews(meets);
		updateMember(member);
	}

	@Override
	public List<String> findUsernamesLike(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		List<String> usernames = session.createQuery("select username from Member where username like '%" + username + "%'").list();
		for (String p : usernames) {
			logger.info("Member  username ::" + p);
		}
		return usernames;
	}

	@Override
	public Member findProfileByUsername(String usernameString) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		List list = session.createQuery("from Member where username = '" + usernameString + "'").list();
		if (list != null && list.size() > 0) {
			Member p = (Member) list.get(0);
			logger.info("Member loaded successfully, Member details=" + p);
			return p;
		} else {
			logger.info("Member not found for username: " + usernameString);
			return null;
		}
	}

	@Override
	public List<Member> findProfilesWhereUsernameLike(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Member> profiles = session.createQuery("from Member where username like '%" + username + "%'").list();
		for (Member p : profiles) {
			logger.info("Member  username ::" + p.getUsername());
		}
		return profiles;
	}

	@Override
	public int getNoOfPics(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Integer> nos = session.createQuery("select no_of_pics from Member where profile_id = " + profile_id).list();
		if(nos==null || nos.size() == 0) {
			logger.info("No of pics for profile id = " + profile_id + " are : 0 or null");
			return 0; 
		}
		logger.info("No of pics for profile id = " + profile_id + " are : " + nos.get(0));
		
		return nos.get(0);
	}

	@Override
	public void setNoOfPics(Long profile_id, int no) {
		/*Member member = getMemberById(profile_id);
		member.setNo_of_pics(no);
		updateMember(member);
		
		*/
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Member set no_of_pics = :no where profile_id = :profile_id");
		query.setParameter("no", no);
		query.setParameter("profile_id", profile_id);
		int result = query.executeUpdate();
		logger.info("Successfully updated no_of_pics for profile " + profile_id);
		return;
	
	}

	@Override
	public void signUp(String username, String email, String password, String gender) {
		Member m = new Member();
		m.setEmail(email);
		m.setUsername(username);
		m.setPassword(password);
		if(gender!=null && gender.equals("1")) {
			m.setSex("Male");
		} else {
			m.setSex("Female");
		}
		m.setLikes(0L);
		m.setViews(0L);
		m.setNo_of_pics(0);
		m.setMeet_requests(0L);
		m.setIs_seed("n");
		m.setIs_signup_confirmed("n");
		
		LocalDate now = new LocalDate(1,1,1);
//		m.setBirthday(now.toDate());
		m.setBirth(new Date());
		m.setFirstname(defaultAnswer);
		m.setSurname(defaultAnswer);
		m.setCountry(defaultAnswer);
		m.setOccupation(defaultAnswer);
		m.setEthnicity(defaultAnswer);
		m.setHeight(defaultAnswer);
		m.setBodytype(defaultAnswer);
		m.setHaircolor(defaultAnswer);
		m.setEyecolor(defaultAnswer);
		m.setEducation(defaultAnswer);
		m.setChildren(defaultAnswer);
		m.setDrink(defaultAnswer);
		m.setSmoke(defaultAnswer);
		/*m.setDrugs(defaultAnswer);
		m.setCar(defaultAnswer);
		m.setStarsign(defaultAnswer);*/
		m.setState(defaultAnswer);
		m.setHome(defaultAnswer);
		m.setCity(defaultAnswer);
		m.setZip(defaultAnswer);
		m.setMymatch(defaultAnswer);
		m.setDescription(defaultAnswer);
//		m.setLooking_for(defaultAnswer);
		m.setMarital_status(defaultAnswer);
//		m.setDay(1);
//		m.setMonth(1);
//		m.setYear(0);
		m.setNationality(defaultAnswer);
		m.setReligion(defaultAnswer);
		m.setIncome(defaultAnswer);
		m.setQuran(defaultAnswer);
		m.setRelocate(defaultAnswer);
		m.setLanguage(defaultAnswer);
	//	m.setMonth_name(defaultAnswer);
		
		addMember(m);
	
	}

	public boolean checkIfUsernameExists(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select username from Member where username = :ip_username ");
		query.setParameter("ip_username", username);
		List list = query.list();
		if(list!=null && list.size()>0) {
			logger.error("Attempted to signup username which already exists : " + username);
			return true; 
		}
		return false;
	}
	
	public boolean checkIfEmailExists(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select email from Member where email = :ip_email ");
		query.setParameter("ip_email", email);
		List list = query.list();
		if(list!=null && list.size()>0) {
			logger.error("Attempted to signup email which already exists : " + email);
			return true; 
		}
		return false;
	}

	@Override
	public boolean confirm(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Member set is_signup_confirmed = 'y' where username = :userName");
		query.setParameter("userName", username);
		int result = query.executeUpdate();
		if(result==0) return false;
		logger.info("Successfully confirmed :  " + username);
		return true;
	}

	@Override
	public Member forgotLogin(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("from Member where email = :ip_email ");
		query.setParameter("ip_email", email);
		List list = query.list();
		if(list!=null && list.size()>0) {
			logger.error("Successfully found email for forgotLogin: " + email);
			Member member = (Member)list.get(0);
			return member;
		}
		return null;
	}

	@Override
	public void setOnlineStatus(Long profile_id,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("update Member set online_status = :stat where profile_id = :pfid");
		query.setParameter("stat", status);
		query.setParameter("pfid", profile_id);
		query.executeUpdate();
		logger.info("Successfully set online status for profile :  " + profile_id + " to = " + status);

		if(status.equalsIgnoreCase("on")) {
			org.hibernate.Query query2 = session.createQuery("update Member set last_login = :now where profile_id = :pfid");
			Date now = TangoUtil.getNow();
			query2.setParameter("now", now);
			query2.setParameter("pfid", profile_id);
			query2.executeUpdate();
			logger.info("Successfully set last_login for profile :  " + profile_id + " to = " + now);
		}
	}

	@Override
	public boolean addContact(Long profile_id, Long contact_id, String contact_username) {
		Session session = this.sessionFactory.getCurrentSession();
		List<MeetMe> meets = session.createQuery("from Contact where profile_id = " + profile_id + " and contact_id = " + contact_id).list();
		if(meets!=null && meets.size()>0) {
			// record already present..do nothing
			return false;
		} else {
			Contact c = new Contact();
			c.setContact_id(contact_id);
			c.setProfile_id(profile_id);
			c.setContact_username(contact_username);
			addContact(c);
			return true;
		}
	}


	@Override
	public void addContact(Contact c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(c);
		logger.info("Contact saved successfully,  Details="+c);
	}
	
	@Override
	public void addReport(Report r) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(r);
		logger.info("Report saved successfully,  Details="+r);
	}

	@Override
	public List<Contact> getMyContacts(Long profile_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("from Contact where profile_id = :profileId ");
		query.setParameter("profileId", profile_id);
		List list = query.list();
		logger.error("Retrieved Contact list is empty for: " + profile_id + ". Size = " + list.size());
		return list; 
	}
	@Override
	public boolean removeContact(Long profile_id, Long contact_id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("delete from Contact where profile_id = :profileId and  contact_id = :contactId");
		query.setParameter("profileId", profile_id);
		query.setParameter("contactId", contact_id);
		int result = query.executeUpdate();
		logger.info("Successfully deleted contact: " + contact_id + " for profile :" + profile_id);
		return true;
	}
	@Override
	public List<Member> searchProfiles(Search search) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class);
		criteria.setMaxResults(100);
		if(search.getGender()!=null && !search.getGender().equalsIgnoreCase("Any")) {
			criteria.add(Restrictions.eq("sex", search.getGender()) ); // works on assumption that value coming from ui is same as in DB
		}
		
		if(search.getReligion()!=null && !search.getReligion().equalsIgnoreCase("Any")) {
			criteria.add(Restrictions.eq("religion", search.getReligion()) ); // works on assumption that value coming from ui is same as in DB
		}
		
		if(search.getBodytype()!=null && !search.getBodytype().equalsIgnoreCase("Any")) {
			criteria.add(Restrictions.eq("bodytype", search.getBodytype()) ); // works on assumption that value coming from ui is same as in DB
		}
		if(search.getCountry()!=null && !search.getCountry().equalsIgnoreCase("Any")) {
			Disjunction or = Restrictions.disjunction();
			or.add(Restrictions.eq("country",search.getCountry()));
			or.add(Restrictions.eq("nationality",search.getCountry()));
			criteria.add(or);
		}
		
		if(search.getEthnicity()!=null && !search.getEthnicity().equalsIgnoreCase("Any")) {
			criteria.add(Restrictions.eq("ethnicity", search.getEthnicity()) ); // works on assumption that value coming from ui is same as in DB
		}
		
		if(search.getPicture()!=null && !search.getPicture().equalsIgnoreCase("Doesnt matter")) {
			criteria.add(Restrictions.gt("no_of_pics", 0) ); // works on assumption that value coming from ui is same as in DB
		}
		
		if(search.getLike()!=null && !search.getLike().equalsIgnoreCase("Any")) {
			criteria.add(Restrictions.ge("likes", Long.valueOf(search.getLike())) ); // works on assumption that value coming from ui is same as in DB
		}
		
		LocalDate now = new LocalDate();         
		int currentYear = now.getYear();
		int min = Integer.parseInt(search.getMin());
		int max = Integer.parseInt(search.getMax());

		Integer startDate = currentYear - min;
		Integer endDate = currentYear - max;
		
		
		
		 SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
		 if(startDate.equals(endDate)) {
			 startDate = startDate + 1;
		 } else {
		 }
		 String sDate = startDate.toString() + "-01-01";
			 String eDate = endDate.toString() + "-01-01";
			try {
				Date minDate = formatter.parse(sDate);
				Date maxDate = formatter.parse(eDate);
				Conjunction and = Restrictions.conjunction();
				
				// remember its ulta
				and.add(Restrictions.ge("birth", maxDate));
				and.add(Restrictions.lt("birth", minDate));
				criteria.add(and);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		 
		
		List<Member> list = criteria.list();
		return list;
	}

	@Override
	public void makeImageMain(Long profile_id, String fileName) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("from Member where profile_id = :profileId ");
		query.setParameter("profileId", profile_id);
		List list = query.list();
		if(list!=null && list.size()>0) {
			Member member = (Member)list.get(0);
			member.setMain_img(fileName);
			updateMember(member);
			logger.info("Successfully updated main image for profile :" + profile_id);
		}
		return;
	}

	@Override
	public String getMainImg(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("select main_img from Member where profile_id = :profileId ");
		query.setParameter("profileId", id);
		List list = query.list();
		if(list!=null && list.size()>0) {
			return (String)list.get(0);
		}
		return null;
	}

	@Override
	public void report(Long profile_id, Long reporter_id, String reason) {
		Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.Query query = session.createQuery("from Report where profile_id = :profileId");
		query.setParameter("profileId", profile_id);
//		query.setParameter("reporterId", reporter_id);
		List list = query.list();
		if(list!=null && list.size()>0) {
			// record already exists, check if reporter is same.
			Report report = (Report) list.get(0);
			if(report.getReporter_id().equals(reporter_id)) {
				// if yes do nothing..same reporter reporting again.
			} else {
				// if not, then someone else reporting.
				Report r = createNewReport(profile_id, reporter_id, reason);
				addReport(r);
			}
			
		} else {
			// if no report exists, brand new report
			Report r = createNewReport(profile_id, reporter_id, reason);
			addReport(r);
		}
		logger.error("Retrieved Contact list is empty for: " + profile_id + ". Size = " + list.size());
		return ; 
	}

	private Report createNewReport(Long profile_id, Long reporter_id,	String reason) {
		Report r = new Report();
		r.setProfile_id(profile_id);
		r.setReporter_id(reporter_id);
		r.setReason(reason);
		return r;
	}



}
