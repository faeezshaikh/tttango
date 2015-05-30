(function() {
	spaApp.controller('ContactsController', function($scope, $http, ngTableParams, $filter, userService, auth,$window,restService,$route) {

		$scope.auth = auth;
		$scope.isLoading = true;
		$scope.responseSize = 1;
		$scope.serviceErrored = false;
		$scope.contactList = [];

		$scope.areResultsPresent = function() {
			if ($scope.responseSize > 0) {
				return true;
			}
			return false;
		}

		$scope.pretty = function(val) {
			return prettyTime(val);
		}

		$scope.getImgUrl = function(id) {
			return BUCKET_URL + id + "_1.jpg";
		}
		$scope.init = function() {

			var url = REST_BASE + 'getMyContacts/' + $scope.auth.profile.identities[0].user_id;
			$http.get(url).success(function(data) {
				/*for(i=0;i<=data.length;i++) {
					$scope.contactList.push(data[i]);
				}*/
				$scope.contactList = data;

				$scope.isLoading = false;
				$scope.responseSize = data.length;

			}).error(function(data) {
				$scope.isLoading = false;
				$scope.serviceErrored = true;
			});
		}

		$scope.deleteContact = function(contactId) {
//			alert('profile : ' + $scope.auth.profile.identities[0].user_id + ' contact:' + contactId);
			
			 var deleteContact= $window.confirm('Are you sure you want to delete this contact?');
      	     if (deleteContact) {
	      	    	 
	      	   	restService.removeContact($scope.auth.profile.identities[0].user_id, contactId).then(function(data) {
					$route.reload();
				}, function(data) {
				});
	      	    	 
      	     }

		

		}

		$scope.personFromListSelected = function(viewer_profile_id) {
			localStorage.setItem("personId", viewer_profile_id);
		}
	});

})();