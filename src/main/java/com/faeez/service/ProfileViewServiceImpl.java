package com.faeez.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faeez.dao.MemberDao;
import com.faeez.dao.ProfileViewDao;
import com.faeez.model.ProfileView;
import com.faeez.ui.model.ProfileViewer;
import com.faeez.ui.model.UiMember;

@Service
@Transactional
public class ProfileViewServiceImpl implements ProfileViewService {

	private ProfileViewDao profileViewDao;
	private MemberDao memberDao;
	

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setProfileViewDao(ProfileViewDao profileViewDao) {
		this.profileViewDao = profileViewDao;
	}


	@Override
	public List<ProfileViewer> getProfilesWhoViewedMe(Long profile_id) {
		List<ProfileView> profilesWhoViewedMe = profileViewDao.getProfilesWhoViewedMe(profile_id);
		List<ProfileViewer> viewers = transform(profilesWhoViewedMe);
		return viewers;
	}

	private List<ProfileViewer> transform(List<ProfileView> profilesWhoViewedMe) {
		List<ProfileViewer> viewers = new ArrayList<ProfileViewer>();
		for (ProfileView profileView : profilesWhoViewedMe) {
			
			//Member viewer = memberDao.getMemberById(profileView.getViewed_by());
			UiMember viewer = TangoUtil.transformToUiMember(memberDao.getMemberById(profileView.getViewed_by()));
			if(viewer==null) continue;
			ProfileViewer uiViewer = new ProfileViewer();

			
			uiViewer.setView_time(profileView.getViewed_when().toString());
			uiViewer.setViewers_id(profileView.getViewed_by());
//			uiViewer.setViewers_pic_url(TangoUtil.base_img_url + profileView.getViewed_by() + "_1.jpg");
			uiViewer.setViewers_pic_url(TangoUtil.base_img_url + viewer.getMain_img());
			uiViewer.setViewers_likes(viewer.getLikes());
			uiViewer.setViewers_views(viewer.getViews());
			uiViewer.setViewers_meetReqs(viewer.getMeet_requests());
			
			String online_status = viewer.getOnline_status();
			if(online_status!=null && online_status.equalsIgnoreCase("on")){
				uiViewer.setViewers_onlineStatus("Online");
			} else {
				uiViewer.setViewers_onlineStatus("Offline");
			}
			uiViewer.setViewers_age(viewer.calculateAgeInYears());
			uiViewer.setViewers_city(viewer.getCity());
			uiViewer.setViewers_occupation(viewer.getOccupation());
			viewers.add(uiViewer);
			
		}
		return viewers;
	}

	@Override
	public List<ProfileView> getProfilesWhoIViewed(Long profile_id) {
		return profileViewDao.getProfilesWhoIViewed(profile_id);
	}


}
