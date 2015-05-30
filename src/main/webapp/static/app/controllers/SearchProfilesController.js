(function() {
	spaApp.controller('SearchProfilesController', function($scope, $http, restService, auth, ngTableParams, $filter, auth, $location, $route) {

		$scope.auth = auth;
		$scope.countries = [];
		$scope.isSubmitted = false;
		$scope.responseSize = 1;
		$scope.serviceErrored = false;
		$scope.searchResults = [];

		$scope.areResultsPresent = function() {
			if ($scope.responseSize > 0) {
				return true;
			}
			return false;
		}

		$scope.search = {
			'gender' : 'Any',
			'min' : 18,
			'max' : 99,
			'bodytype' : 'Any',
			'marital' : 'Any',
			'ethnicity' : 'Any',
			'picture' : 'Doesnt matter',
			'religion' : 'Any',
			'like' : 'Any',
			'country' : 'Any'
		};

		$scope.init = function() {

			$scope.member = JSON.parse(localStorage.getItem("loggedInUser"));

			$http.get('app/json/countries.json').success(function(data) {
				var arr = data.RestResponse.result;
				$scope.countries.push('Any');
				$scope.countries.push('-------------------');
				$scope.countries.push('United States');
				$scope.countries.push('Saudi Arabia');
				$scope.countries.push('Morocco');
				$scope.countries.push('United Arab Emirates');
				$scope.countries.push('Jordan');
				$scope.countries.push('-------------------');
				for (i = 0; i < arr.length; i++) {
					$scope.countries.push(arr[i].name);
				}

			}).error(function(data) {
				console.log("Error getting list of countries.");
			});

			$scope.country_prop = {
				"type" : "select",
				"values" : $scope.countries
			};

			var genders = [ 'Any', 'Male', 'Female' ];
			$scope.gender_prop = {
				"type" : "select",
				"values" : genders
			};

			var bodytypes = [ 'Any', 'Athletic/Toned', 'About Average', 'A Few Extra Pounds', 'Slim/Petite', 'Big/Tall/Beautiful' ];

			if (jQuery.inArray($scope.member.bodytype, bodytypes) == -1) {
				bodytypes.push($scope.member.bodytype);
			}
			$scope.bodytype_prop = {
				"type" : "select",
				"values" : bodytypes
			};

			var marital = [ 'Any', 'Single', 'Divorced', 'Separated', 'Widowed' ];
			$scope.marital_prop = {
				"type" : "select",
				"values" : marital
			};

			var ethnicities = [ 'Any', 'Arab (Middle Eastern)', 'African American/Black', 'Hispanic/Latino', 'Asian', 'Indian', 'Pacific Islander', 'Native American/Alaskan', 'Mixed', 'Other' ]; // DB cleaned up and matching this list
			$scope.ethnicity_prop = {
				"type" : "select",
				"values" : ethnicities
			};

			var pictures = [ 'Doesnt matter', 'Yes' ]
			$scope.picture_prop = {
				"type" : "select",
				"values" : pictures
			};

			var religions = [ 'Any', 'Islam - Sunni', 'Islam - Shiite', 'Islam - Sufism', 'Islam - Ahmadiyya', 'Islam - Other', 'Willing to revert to Islam', 'Other' ];
			$scope.religion_prop = {
				"type" : "select",
				"values" : religions
			};

			var likes = [ 'Any', '1', '2', '3', '4', '5' ];
			$scope.like_prop = {
				"type" : "select",
				"values" : likes
			};

			var ages = [];

			for(i=18;i<=99;i++){
				ages.push(i);
			}
			$scope.age_prop = {
				"type" : "select",
				"values" : ages
//				"value" : 18
				
			};

			updateSearchTable();

		}

		function updateSearchTable() {

			var searchedItems = JSON.parse(localStorage.getItem("searchedItems"));
			if (searchedItems != null) {

				var resp = searchedItems;
				$scope.tableParams = new ngTableParams();
				$scope.tableParams = new ngTableParams({
					page : 1, // show first page
					count : 10, // count per page
					sorting : {
						age : 'asc' // initial sorting
					}
				}, {
					total : resp.length, // length of data
					getData : function($defer, params) {
						// use build-in angular filter
						var orderedData = params.sorting() ? $filter('orderBy')(resp, params.orderBy()) : resp;
						$defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
					}
				});
			}

		}
		
		$scope.getStatus = function(onlineStatus,lastLogin){
			return getOnlineStatus(onlineStatus,lastLogin);
		}

		$scope.submit = function(search) {

			$scope.isSubmitted = true;
			restService.searchProfiles(search).then(function(data) {

				$scope.searchResults = data;
				$scope.responseSize = data.length;
				$scope.isSubmitted = false;
				$scope.search = search;

				localStorage.setItem("searchedItems", JSON.stringify(data));
				$route.reload();
			}, function(data) {
				$scope.serviceErrored = true;
				$scope.isSubmitted = false;
			});

		}

		$scope.personFromListSelected = function(viewer_profile_id) {
			localStorage.setItem("personId", viewer_profile_id);
		}
		$scope.getImgUrl = function(main_image) {
			return BUCKET_URL + main_image;
		}

	});

})();