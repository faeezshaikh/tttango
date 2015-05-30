package com.faeez.ui.model;


public class Email {

		private String email_time_millis;
		private String message;
		private String is_conversation_new;
		private Long receivers_profile_id;
		private Long senders_profile_id;
		private String senders_pic_url;
		private String receivers_pic_url;
		private String has_receiver_opened;
		
		
		public String getHas_receiver_opened() {
			return has_receiver_opened;
		}
		public void setHas_receiver_opened(String has_receiver_opened) {
			this.has_receiver_opened = has_receiver_opened;
		}
		private Long emailId;
		
		
		public String getReceivers_pic_url() {
			return receivers_pic_url;
		}
		public void setReceivers_pic_url(String receivers_pic_url) {
			this.receivers_pic_url = receivers_pic_url;
		}
		public Long getEmailId() {
			return emailId;
		}
		public void setEmailId(Long emailId) {
			this.emailId = emailId;
		}
		public Long getSenders_profile_id() {
			return senders_profile_id;
		}
		public void setSenders_profile_id(Long senders_profile_id) {
			this.senders_profile_id = senders_profile_id;
		}
		public String getSenders_pic_url() {
			return senders_pic_url;
		}
		public void setSenders_pic_url(String senders_pic_url) {
			this.senders_pic_url = senders_pic_url;
		}
	
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	
		public String getIs_conversation_new() {
			return is_conversation_new;
		}
		public void setIs_conversation_new(String is_conversation_new) {
			this.is_conversation_new = is_conversation_new;
		}
		public Long getReceivers_profile_id() {
			return receivers_profile_id;
		}
		public void setReceivers_profile_id(Long receivers_profile_id) {
			this.receivers_profile_id = receivers_profile_id;
		}
		public String getEmail_time_millis() {
			return email_time_millis;
		}
		public void setEmail_time_millis(String email_time_millis) {
			this.email_time_millis = email_time_millis;
		}
}
