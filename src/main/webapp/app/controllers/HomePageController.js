(function() {
	spaApp.controller('HomePageController', function($scope, $http, ngTableParams, $filter, userService, Utils, auth, $location, store, $route) {
		$scope.picprofiles = [];
		$scope.isLoading = true;
		$scope.serviceErrored = false;
		$scope.responseSize = 1;
		$scope.auth = auth;
		$scope.random_profile_pic_urls = [];

		
		$scope.areResultsPresent = function() {
			if ($scope.responseSize > 0) {
				return true;
			}
			return false;
		}
		
		
		$scope.isloggedin = function() {

			if (auth.profile) {
				return true;
			} else {
				return false;
			}
		}
		
		  $scope.pretty = function(val) {
			  return prettyTime(val);
		  }
		$scope.init = function() {
			$scope.isAuthenticated = true;
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
				
				if(jQuery.inArray(imgUrl, $scope.random_profile_pic_urls)==-1) {
					$scope.random_profile_pic_urls.push(imgUrl);
				}
			}
		}


		$scope.logout = function() {
			
			// Set login_status in DB to "off"
			var url = REST_BASE + 'logout/' + $scope.auth.profile.identities[0].user_id;
			$http.get(url).success(function(data) {
				localStorage.removeItem("loggedInUser");
			});
			
			
			auth.signout();
			$scope.isAuthenticated = false;
			
			
			store.remove('profile');
			store.remove('token');
			$location.path('/login');
		}

		// this function is called from home page, when a random pic is clicked.
		$scope.randomPersonSelected = function(pic_url) {
			var res = pic_url.split(BUCKET_URL);
			var tmp1 = res[1].split(".jpg");
			var tmp2 = tmp1[0].split("_");
			profile_id_of_random_member = tmp2[0];

			localStorage.setItem("personId", profile_id_of_random_member);

		}

		// this function is called when a pic is clicked from inside the email page (inbox or sent items) 
		$scope.myProfileClicked = function(profile_id) {
			localStorage.setItem("personId", profile_id);

			// The below 2 lines are for cases, when a user is already viewing someone else's profile and then clicks "My Profile" link
			$location.path('/PersonDetails');
			$route.reload();
		}



		// this function is called when an email is opened both from inbox on home page
		$scope.openEmailFromInbox = function(emailConversation) {
			userService.setEmail(emailConversation);
		}

		$scope.isUserConfirmed = function() {
			$scope.user = userService.getLoggedInUser();
			if ($scope.user.is_signup_confirmed == "n") {
				return false;
			} else {
				return true;
			}
		}

		$scope.inboxme = function() {

			// First thing set the profile of the logged in user in Service.
			var url = REST_BASE + 'profile/' + $scope.auth.profile.identities[0].user_id;
			$http.get(url, {
				cache : true
			}).success(function(data) {
				userService.setLoggedInUser(data);
				localStorage.setItem("loggedInUser", JSON.stringify(data));
			}).error(function(data) {
				console.log("Error occured while retrieving user profile details -> "  + data) ;
			});
			
			
			
			// The Rest Service will also set online_status = 'on' and set the last_login date in DB as well.
			var url = REST_BASE + 'getInbox/' + $scope.auth.profile.identities[0].user_id
			$http.get(url).success(function(data) {
				var resp = data;
				 $scope.isLoading = false;
				 $scope.responseSize = data.length;
				$scope.tableParams = new ngTableParams({
					page : 1, // show first page
					count : 10, // count per page
					sorting : {
						new_conversation_time : 'desc' // initial sorting
					}
				}, {
					total : resp.length, // length of data
					getData : function($defer, params) {
						// use build-in angular filter
						var orderedData = params.sorting() ? $filter('orderBy')(resp, params.orderBy()) : resp;

						$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
					}
				});

			}).error(function(data) {
				$scope.isLoading = false;
				$scope.serviceErrored = true;
			});

		}


		var persons = new Bloodhound({
			datumTokenizer : Bloodhound.tokenizers.obj.whitespace('value'),
			queryTokenizer : Bloodhound.tokenizers.whitespace,
			remote : REST_BASE + 'findUsernamesLike?name=%QUERY'
		});

		persons.initialize();

		$('#searchBar').typeahead(null, {
			name : 'persons',
			displayKey : 'name',
			source : persons.ttAdapter()
		});
		
		
	/*	var url = REST_BASE + "findUsernamesLike?name="
		
		jQuery("#searchBar").autocomplete({
			source : function(request, response) {
				jQuery.getJSON(url + request.term, function(data) {
					response(data);
					//alert(data)
				});
			},
			minLength : 3,
			select : function(event, ui) {
				var selectedObj = ui.item;
				jQuery("#searchBar").val(selectedObj.value);
				//getcitydetails(selectedObj.value);
				alert(selectedObj.value)
				return false;
			},
			open : function() {
				jQuery(this).removeClass("ui-corner-all").addClass("ui-corner-top");
			},
			close : function() {
				jQuery(this).removeClass("ui-corner-top").addClass("ui-corner-all");
			}
		});
		jQuery("#searchBar").autocomplete("option", "delay", 100);*/
		
		
		$scope.getImgUrl = function(main_image) {
			return getMainImgUrl(main_image);
		}

		$scope.processDate = function(val) {
			return handleDate(val);
		}
		
		$scope.isConversationNew = function(email) {
			return (email.is_conversation_new == 'y' && email.new_email_for == $scope.auth.profile.identities[0].user_id); 
		}
		

	});
})();
