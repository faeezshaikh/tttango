var AUTH0_CLIENT_ID='aARSVavYoIDelNBdscqwEkUQQsAzhd5R'; 
var AUTH0_DOMAIN='faeez.auth0.com'; 
var AUTH0_CALLBACK_URL=location.href;

// Instance 1 = 52.26.66.11
//var REST_BASE = 'http://52.26.66.11:8080/tttango/'
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


// Google tracking

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-63736311-1', 'auto');
ga('send', 'pageview');

