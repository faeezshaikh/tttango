(function() {

	spaApp.controller('PersonDetailsController', function($scope, $http, userService, $timeout, $location, auth,restService) {

		$scope.personId = "";
		$scope.personSameAsLoggedInUser = false;
		$scope.auth = auth;
		var loggedInUser;
		$scope.lastLoginFormatted = "";
		$scope.contactAddedSuccess = false;
		$scope.addingInProgress = false;
		$scope.reportFlag = false;
		$scope.reportSuccess = false;
		$scope.reportFail = false;
		var loggedInUser = {};
		
		$scope.hoverIn = function() {
			this.hoverEdit = true;
		};

		$scope.hoverOut = function() {
			this.hoverEdit = false;
		};

		findPersonId = function() {
			// Storing in localStorage to handle page refreshes. this needs to happen for all pages.
			var personId = localStorage.getItem("personId");
			if (personId) {
				$scope.personId = personId;
				return;
			}
		}

		isPersonSameAsLoggedInUser = function() {
			findPersonId();

			loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));
			//		  if($scope.personId ==  $scope.auth.profile.identities[0].user_id) {
			if ($scope.personId == loggedInUser.id) {
				$scope.personSameAsLoggedInUser = true;
			} else {
				$scope.personSameAsLoggedInUser = false;
			}

		}

		$scope.modal = function(url) {
			$('.test-popup-link').magnificPopup({
				type : 'image'
			// other options
			});
		}
		$scope.addContact = function(userId) {
			$scope.addingInProgress = true;
			$scope.contactAddedSuccess = false;
			var url = REST_BASE + 'addContact?profile_id=' + $scope.auth.profile.identities[0].user_id + '&contact_id=' + userId + '&contact_username=' + $scope.person.username;
			$http.get(url).success(function(data) {
				$scope.contactAddedSuccess = true;
				$scope.addingInProgress = false;
			}).error(function(data) {

			});

		}
		
		$scope.flagUser = function(userId) {
			$scope.reportFlag = true;
		}
		$scope.reportCancelled = function() {
			$scope.reportFlag = false;
		}
		
		$scope.reportSaved = function() {
		//	alert('Reason : ' + $scope.reason + ' Reporter :' + $scope.auth.profile.identities[0].user_id + ' Reportee:' + $scope.personId);
			
			restService.reportProfile($scope.auth.profile.identities[0].user_id,$scope.personId,$scope.reason).then(
					function(data){
						$scope.reportFlag = false;
						$scope.reportSuccess = true;
					} ,
					function(data){
						$scope.reportFlag = false;
						$scope.reportFail = true;
					});
		}
		$scope.checkClicked = function() {

			findPersonId();
			loggedInUser = userService.getLoggedInUser();
			$scope.isClicked = true;

			if ($scope.chkbox) { // it was liked

				if (userService.getIsPersonLiked()) {
					$scope.counter = "";
				} else {
					$scope.counter = "+1";
				}

				var liked_url = REST_BASE + 'addLike?like_by=' + $scope.auth.profile.identities[0].user_id + '&like_who=' + $scope.personId + '&likers_likes=' + loggedInUser.likes + '&likers_views='
						+ loggedInUser.views + '&likers_meetReqs=' + loggedInUser.meet_requests;
				$http.get(liked_url).success(function(data) {
				});
			} else { // it was unliked

				if ($scope.likeCount != 0) {
					$scope.counter = "-1";
				} else {
					$scope.counter = "";
				}
				var unliked_url = REST_BASE + 'removeLike?unlike_by=' + $scope.auth.profile.identities[0].user_id + '&unlike_who=' + $scope.personId;

				$http.get(unliked_url).success(function(data) {
				});

			}

		}
		
		/*function getOnlineStatus(onlineStatus, lastLogin) {
			if(onlineStatus == 'on') {
				return 'Online';
			} else {
				return jQuery.timeago(lastLogin);
			}
		}*/

		$scope.init = function(source) {

			// variables that will be used in this function  
			$scope.more_pics_url = [];
			loggedInUser = userService.getLoggedInUser();

			// this function also determines what is the origin, ie. from where are we arriving at the details page and set the person's id accordingly
			isPersonSameAsLoggedInUser();

			// this is to set the status of the like button. Find out from the database, if the logged in user has liked this person or not
			$http.get(REST_BASE + 'getLikeStatus?logged_in_user=' + $scope.auth.profile.identities[0].user_id + '&profile_in_question=' + $scope.personId).success(function(data) {
				$scope.chkbox = data;
				if ($scope.chkbox) {
					// if this person was already liked.
					userService.setIsPersonLiked(true);
				} else {
					userService.setIsPersonLiked(false);
				}

			});

			// get the persons details from database
			$http.get(REST_BASE + 'profile/' + $scope.personId).success(function(data) {
				$scope.person = data;
				$scope.likeCount = data.likes;
				$scope.personImgUrl = BUCKET_URL + $scope.person.main_img;
//				$scope.lastLoginFormatted = jQuery.timeago(data.last_login);
				$scope.lastLoginFormatted = getOnlineStatus(data.online_status,data.last_login);

				if (data.no_of_pics > 1) {
					for (i = 2; i <= data.no_of_pics; i++) {

						$scope.more_pics_url.push(BUCKET_URL + $scope.personId + "_" + i + ".jpg");

					}
				}
//				location.reload(true);

			});

			// log in the database, that the logged in person has viewed this profile.
			var url_profile_view = REST_BASE + 'addProfileView?viewed_by=' + $scope.auth.profile.identities[0].user_id + '&viewed_who=' + $scope.personId + '&viewers_likes=' + loggedInUser.likes
					+ '&viewers_views=' + loggedInUser.views + '&viewers_meetReqs=' + loggedInUser.meet_requests;
			$http.get(url_profile_view).success(function(data) {
			});

			
//			

		};

	});
})();