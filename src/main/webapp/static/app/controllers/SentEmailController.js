(function() {
spaApp.controller('SentEmailController', function($scope, $http, ngTableParams, $filter, userService,auth) {

  
  $scope.init = function() {
	  
	  var email = userService.getEmail();
	  
		var url = REST_BASE + 'getEmailByEmailId/' + email.emailId;
		$http.get(url).
			success(function(data) {
			  $scope.sentEmail = data;
			});
  }
  
  $scope.picInsideSentEmailClicked = function(receivers_profile_id) {
		localStorage.setItem("personId",  receivers_profile_id);
	}
	

 
});

})();