package com.faeez.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.LikeMeDao;
import com.faeez.dao.MemberDao;
import com.faeez.model.LikeMe;
import com.faeez.ui.model.Liker;
import com.faeez.ui.model.UiMember;

@Service
@Transactional
public class LikeMeServiceImpl implements LikeMeService {

	private LikeMeDao likeMeDao;
	private MemberDao memberDao;
	
	public void setLikeMeDao(LikeMeDao likeMeDao) {
		this.likeMeDao = likeMeDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}


	@Override
	public void addLikeMe(Long liked_by, Long liked_who) {
		LikeMe likeMe = new LikeMe();
		likeMe.setLiked_by(liked_by);
		likeMe.setLiked_who(liked_who);
		likeMeDao.addLikeMe(likeMe);
	}

	@Override
	public List<Liker> getProfilesWhoLikedMe(Long profile_id) {
		List<LikeMe> profilesWhoLikedMe = likeMeDao.getProfilesWhoLikedMe(profile_id);
		List<Liker> list = transform(profilesWhoLikedMe);
		return list;
	}

	private List<Liker> transform(List<LikeMe> profilesWhoLikedMe) {
		List<Liker> list = new ArrayList<Liker>();
		
		// TODO: Performance. Eliminate for loop 
		for (LikeMe likeMe : profilesWhoLikedMe) {
			Long likersId = likeMe.getLiked_by();

			/*Member liker = memberDao.getMemberById(likersId);
			Liker l = new Liker();
			l.setLikers_id(likersId);
			l.setLikers_occupation(liker.getOccupation());
			String online_status = liker.getOnline_status();
			if(online_status!=null && online_status.equalsIgnoreCase("on")){
				l.setLikers_onlineStatus("Online");
			} else {
				l.setLikers_onlineStatus("Offline");
			}
			l.setLikers_city(liker.getCity());
			l.setLikers_age(liker.calculateAgeInYears());
			l.setLike_time(new SimpleDateFormat("MMM dd yyyy hh:mm a z").format(likeMe.getLiked_when()));
			l.setLikers_pic_url(base_img_url + likersId+ "_1.jpg");
			l.setLikers_likes(liker.getLikes());
			l.setLikers_meetReqs(liker.getMeet_requests());
			l.setLikers_views(liker.getViews());*/
			Liker l = foo(likeMe, likersId);
			list.add(l);
		}
		return list;
	}

	@Override
	public List<Liker> getProfilesWhoILiked(Long profile_id) {
		List<LikeMe> profilesWhoILiked = likeMeDao.getProfilesWhoILiked(profile_id); 
		List<Liker> list = transform2(profilesWhoILiked);
		return list;
	}
	
	private List<Liker> transform2(List<LikeMe> profilesWhoLikedMe) {
		List<Liker> list = new ArrayList<Liker>();
		
		// TODO: Performance. Eliminate for loop 
		for (LikeMe likeMe : profilesWhoLikedMe) {
			Long likersId = likeMe.getLiked_who();

			Liker l = foo(likeMe, likersId);
			list.add(l);
		}
		return list;
	}

	private Liker foo(LikeMe likeMe, Long likersId) {
		Liker l = new Liker();
	//	Member liker = memberDao.getMemberById(likersId);
		UiMember liker = TangoUtil.transformToUiMember(memberDao.getMemberById(likersId));
		l.setLikers_id(likersId);
		l.setLikers_occupation(liker.getOccupation());
		String online_status = liker.getOnline_status();
		if(online_status!=null && online_status.equalsIgnoreCase("on")){
			l.setLikers_onlineStatus("Online");
		} else {
			l.setLikers_onlineStatus("Offline");
		}
		l.setLikers_city(liker.getCity());
		l.setLikers_age(liker.calculateAgeInYears());
		l.setLike_time(likeMe.getLiked_when().toString());
//		l.setLike_time(new SimpleDateFormat("MMM dd yyyy hh:mm a z").format(likeMe.getLiked_when()));
//		l.setLikers_pic_url(TangoUtil.base_img_url + likersId+ "_1.jpg");
		l.setLikers_pic_url(TangoUtil.base_img_url + liker.getMain_img());
		l.setLikers_likes(liker.getLikes());
		l.setLikers_meetReqs(liker.getMeet_requests());
		l.setLikers_views(liker.getViews());
		return l;
	}


}
