(function() {

	spaApp.controller('PersonDetailsUnregisteredController', function($scope, $http, userService, $timeout, $location) {
		

		$scope.personId = "";
		$scope.lastLoginFormatted = "";
		
		   $scope.hoverIn = function(){
			   console.log('in')
		        this.hoverEdit = true;
		    };

		    $scope.hoverOut = function(){
		    	console.log('out')
		        this.hoverEdit = false;
		    };

		
	  findPersonId = function() {
		  
		  // Storing in localStorage to handle page refreshes. this needs to happen for all pages.
		  var personId = localStorage.getItem("personId");
		  if(personId) {
			  $scope.personId = personId;
			  return;
		  }
		  
	  }
		

	  $scope.likeClicked = function() {
		  $scope.signup_msg = "Your likes wont count until you sign up";
		  
	  }
	  
	  $scope.init = function(source) {
		  

		// variables that will be used in this function  
  	    $scope.more_pics_url = [];
		$scope.showMessageSent = false;
		
		// Determine what is the origin, ie. from where are we arriving at the details page and set the person's id accordingly
		findPersonId();
		
		$scope.visitor_loc = userService.getVisitorLocation();
		
		
		
		// get the persons details from database
		$http.get(REST_BASE + 'profile/' +  $scope.personId).
		success(function(data) {
			  $scope.person = data;
			  $scope.likeCount = data.likes;
//			  $scope.personImgUrl = BUCKET_URL + $scope.personId + "_1.jpg";
			  $scope.personImgUrl = BUCKET_URL + $scope.person.main_img;
			  $scope.lastLoginFormatted = getOnlineStatus(data.online_status,data.last_login);

			  
			  if(data.no_of_pics>1) {
				  for(i=2;i<=data.no_of_pics;i++) {
						
						$scope.more_pics_url.push(BUCKET_URL + $scope.personId + "_" + i + ".jpg");
						
				}
			}
		  		  
		});
		


	  };
	});
})();