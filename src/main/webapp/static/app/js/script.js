var AUTH0_CLIENT_ID='aARSVavYoIDelNBdscqwEkUQQsAzhd5R'; 
var AUTH0_DOMAIN='faeez.auth0.com'; 
var AUTH0_CALLBACK_URL=location.href;

//var REST_BASE = 'http://ec2-52-24-106-16.us-west-2.compute.amazonaws.com:8080/tttango/'
	var REST_BASE = 'http://localhost:8080/tttango/'
	
var BUCKET_URL = 'https://s3-us-west-2.amazonaws.com/mbuds/'



function prettyTime(val) {
	  return jQuery.timeago(val);
}


function getOnlineStatus(onlineStatus, lastLogin) {
	if(onlineStatus == 'on') {
		return 'Online';
	} else {
		return jQuery.timeago(lastLogin);
	}
}


function handleDate(val) {

	var d = new Date(val);
	var str = prettyTime(d.toLocaleString());
	var result = str;
	if (str.indexOf("years ago") != -1) {
		var age = str.split("years");
		result = age[0];
	}
	return result;
}

function getMainImgUrl(main_image) {
	return BUCKET_URL + main_image;
}

