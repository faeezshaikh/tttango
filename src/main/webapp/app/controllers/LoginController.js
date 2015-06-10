(function() {
	spaApp.controller('LoginController', function($scope, auth, $location, store, $http, userService) {

		$scope.picprofiles = [];
		$scope.loginMessage = "";
		$scope.requestForgotFlag = false;
		$scope.forgotMessagePass = "";
		$scope.forgotMessageFail = "";
		$scope.forgotMessageInProgress = false;
		
		

		$("#loginField").focus(function() {
			$scope.loginMessage = "";
		});

		$("#passwordField").focus(function() {
			$scope.loginMessage = "";
		});
		
		$("#forgotEmail").focus(function() {
			$scope.forgotMessagePass =	$scope.forgotMessageFail = "";
		});
		
		

		$scope.requestForgot = function() {
			$scope.requestForgotFlag = true;
//			$("#forgotPanel").slideDown();
		}

		$scope.forgotClicked = function() {
			return $scope.requestForgotFlag;
		}
		
		$scope.forgotLoginSubmitted = function() {
			$scope.forgotMessageInProgress = true;
			var url = REST_BASE + 'forgot?email=' + $scope.forgotEmail;
			$http.get(url, {
				cache : true
			}).success(function(data) {
				$scope.forgotEmail = "";
				$scope.forgotMessageInProgress = false;
				if(data) {
					// success
					$scope.forgotMessagePass = "Success! Login information has been sent to the registered email address."
				} else {
					$scope.forgotMessageFail = "Uh-oh! Either email doesnt exist or there was some other problem. Sorry :-("
				}

			});
			
		}
		$scope.login = function() {

			auth.signin({

				connection : 'tangodb',
				username : $scope.loginUsername,
				password : $scope.loginPassword
			}, function(profile, token) {
				store.set('profile', profile);
				store.set('token', token);
				$location.path("/");
			}, function(error) {
				$scope.loginMessage = "Invalid Login!";
				$scope.loginUsername = "";
				$scope.loginPassword = "";

			});
		}

		$scope.random_profile_ids = [];
		$scope.visitor_city = userService.getVisitorLocation();

		$scope.randomPersonSelected = function(pic_url) {
			// Resuse from homepageController
			var res = pic_url.split(BUCKET_URL);
			var tmp1 = res[1].split(".jpg");
			var tmp2 = tmp1[0].split("_");
			profile_id_of_random_member = tmp2[0];

			localStorage.setItem("personId", profile_id_of_random_member);
		}

		getLocation = function() {

			/*jQuery.ajax({
				url : '//freegeoip.net/json/',
				type : 'POST',
				dataType : 'jsonp',
				success : function(location) {
					$scope.visitor_city = location.city;
					// alert($scope.visitor_city );
					userService.setVisitorLocation($scope.visitor_city);
				}
			});*/
		}

		$scope.init = function() {

			getLocation();

			populatePicProfiles();

		}

		var populatePicProfiles = function() {

			// Try to get from cache first
			$scope.picprofiles = JSON.parse(localStorage.getItem("picProfileIds"));

			if ($scope.picprofiles == null) { // if cache empty call service

				var url = REST_BASE + 'picprofiles';
				$http.get(url, {
					cache : true
				}).success(function(data) {
					// then cache it
					$scope.picprofiles = data;
					localStorage.setItem("picProfileIds", JSON.stringify(data));
					populateRandomArray($scope.picprofiles);

				});

			} else {
				populateRandomArray($scope.picprofiles);
			}

		}

		var populateRandomArray = function(array) {
			for (i = 0; i < 8; i++) {
				var randomNum = Math.floor(Math.random() * 450) + 1;
				var imgUrl = BUCKET_URL + array[randomNum] + "_1.jpg";
				if(jQuery.inArray(imgUrl, $scope.random_profile_ids)==-1) {
					$scope.random_profile_ids.push(imgUrl);
				}
			}
		}

	});
})();
