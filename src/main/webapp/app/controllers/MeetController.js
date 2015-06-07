(function() {
spaApp.controller('MeetController', function($scope, $http, $location,$route,userService,auth) {

  $scope.message = 'This is Home Page screen';
  $scope.auth = auth;

  $scope.meetRandom = function() {
     
		// Try to get from cache first
		picprofiles = JSON.parse(localStorage.getItem("picProfileIds"));
		
		if(picprofiles == null) { // if cache empty call service
			alert('Cache empty..calling service')
		 	var url = REST_BASE + 'picprofiles';
			  $http.get(url, {
				  cache: true
				})
				  .success(function(data) {
					  picprofiles = data;
					  localStorage.setItem("picProfileIds",  JSON.stringify(data));
						alert('Service success.. picprofile= ' + picprofiles)
					
				  });
			
		}
		
		  var randomNum = Math.floor(Math.random() * 450) + 1;
		  $scope.randomPersonsPicUrl = BUCKET_URL + picprofiles[randomNum] + "_1.jpg";
		  $scope.randomPersonsId = picprofiles[randomNum];
	 
  }

  $scope.yesClicked = function() {
    // Calling the page again, to load it with another random person.
//	  alert('The id clicked was --> '  + $scope.randomPersonsId);
	  
		var loggedInUser = userService.getLoggedInUser();
		var url_profile_meet = REST_BASE + 'addProfileMeet?meet_request_by=' + $scope.auth.profile.identities[0].user_id + '&meet_who=' + $scope.randomPersonsId
		 							+ '&meet_requesters_likes=' + loggedInUser.likes + '&meet_requesters_views=' + loggedInUser.views + '&meet_requesters_meetReqs=' + loggedInUser.meet_requests ;
		$http.get(url_profile_meet).
		success(function(data) {
		});

	  
	  
    $location.path('/MeetPage');
    $route.reload();
  }


  $scope.noClicked = function() {
    // Calling the page again, to load it with another random person.
//	  alert('The id clicked was --> '  + $scope.randomPersonsId);
    $location.path('/MeetPage');
    $route.reload();
  }
  
  $scope.isLoggedIn = function() {
	  if($scope.auth.profile) {
		  return true;
	  } else {
		  return false;
	  }
  }


});

})();