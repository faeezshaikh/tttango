(function() {
spaApp.controller('EmailController', function($scope, $http, ngTableParams, $filter, userService,auth) {

  $scope.auth = auth;
  $scope.isLoading = true;
  $scope.responseSize = 1;
  $scope.serviceErrored = false;
  $scope.senders_pic_url = "";
  $scope.emailid = 	"";
  $scope.other_persons_id = "";
  var loggedInUser = {} ;
  
  
  $scope.areResultsPresent = function() {
		if($scope.responseSize>0) {
			return true;
		}
		return false;
	}
  
  // this function is called when an email is opened both from sent items page "sentItems.html"
  $scope.openSentItem = function(email) {
		userService.setEmail(email);
  }


  // this function is called when the "email.html" loads. "email.html" is the page that opens when an email in inbox is clicked.
  $scope.init = function() {
	
	/*$scope.senders_pic_url = userService.getEmail().senders_pic_url;
	$scope.senders_id = userService.getEmail().senders_profile_id;*/
	  
	var url = REST_BASE + 'getEmailConversations?sender_id=' + userService.getEmail().sender_id + '&receiver_id=' + userService.getEmail().receiver_id;
	$http.get(url).
		success(function(data) {
		  $scope.emailConversations = data;
		  $scope.isLoading = false;
		});
  }
	
	
  // this function is called when a pic is clicked from inside the email page "email.html" 
  $scope.picInsideEmailClicked = function(senders_profile_id) {
		localStorage.setItem("personId",  senders_profile_id);
	}
	
  

  // This is called when message sent from "dating_details.html" page or from the "email.html" page.
  // "email.html" calls this will null person id.
  // In both cases, sender is loggedInUser
  $scope.sendMessage = function(random_persons_id) {
	
	  var other_person_id;
	  if(random_persons_id == null) {
		  // that means it came from email page.. recepient is replying to the sender. // this CANNOT be localStorage.personId
		  other_person_id = userService.getEmail().sender_id; 
	  } else {
		  other_person_id = random_persons_id;
	  }
	  
	  
	  
	loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));
	
	
	var emailObj = {"email_time_millis": "",
					"message" : $scope.emailContent,
					"is_conversation_new" : "y",  // value ignored by server. always new on server side
					"receivers_profile_id": other_person_id,
					"senders_pic_url" : loggedInUser.main_img,
					"receivers_pic_url" : "",
					"has_receiver_opened" : "",
					"senders_profile_id" :  $scope.auth.profile.identities[0].user_id,
					"emailId": 0};
					
	$http({
	
            url: REST_BASE + 'sendmail/' + $scope.auth.profile.identities[0].user_id,
            method: "POST",
            data: emailObj,
            headers: {'Content-Type': 'application/json'}
        }).success(function (data, status, headers, config) {
			   console.log("Email successfully sent");
            }).error(function (data, status, headers, config) {
            	console.log("Error sending email > " + data);
            });

  }

 
  // This function is called on load of "sentItems.html" 
  $scope.getSentItems = function() {
	  
	var url = REST_BASE + 'getSentItems/' + $scope.auth.profile.identities[0].user_id;
	$http.get(url).
		success(function(data) {
		  var resp = data;
		    $scope.isLoading = false;
			$scope.responseSize = data.length;
		    $scope.tableParams = new ngTableParams({
			page: 1, // show first page
			count: 10, // count per page
			sorting: {
			  email_time: 'desc' // initial sorting
			}
		  }, {
			total: resp.length, // length of data
			getData: function($defer, params) {
			  // use build-in angular filter
			  var orderedData = params.sorting() ?
				$filter('orderBy')(resp, params.orderBy()) :
				resp;

			  $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			}
		  });
		  
		}).error(function(data) {
			$scope.isLoading = false;
			$scope.serviceErrored = true;
		});
  }
  
  
	
	$scope.getImgUrl = function(main_image) {
		return getMainImgUrl(main_image);
	}


	$scope.processDateForEmailPage = function(val) {
		var d = new Date(val);
		return d.toLocaleString()
	}
 
	$scope.processDate = function(val) {
		return handleDate(val);
	}
	
	$scope.getReadStatus = function(isOpened) {
		if(isOpened) {
			if(isOpened == 'y')
				return "Yes";
		}
		return "No";
	}
});

})();