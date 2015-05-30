(function() {
	spaApp.controller('ViewedMeController', function($scope, $http, ngTableParams, $filter, userService, auth) {

		$scope.auth = auth;
		$scope.isLoading = true;
		$scope.responseSize = 1;
		$scope.serviceErrored = false;

		$scope.areResultsPresent = function() {
			if ($scope.responseSize > 0) {
				return true;
			}
			return false;
		}

		$scope.pretty = function(val) {
			return prettyTime(val);
		}
		$scope.init = function() {

			var url = REST_BASE + 'getWhoViewedMe/' + $scope.auth.profile.identities[0].user_id;
			$http.get(url).success(function(data) {
				var resp = data;
				$scope.isLoading = false;
				$scope.responseSize = data.length;

				$scope.tableParams = new ngTableParams({
					page : 1, // show first page
					count : 10, // count per page
					sorting : {
						view_time : 'desc' // initial sorting
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

		$scope.personFromListSelected = function(viewer_profile_id) {
			localStorage.setItem("personId", viewer_profile_id);
		}
		
		$scope.getImgUrl = function(main_image) {
			return getMainImgUrl(main_image);
		}

		$scope.processDate = function(val) {
			return handleDate(val);
		}

		$scope.processOnlineStatus = function(onlineStatus, lastLogin) {
			var d = new Date(lastLogin);
			return getOnlineStatus(onlineStatus, d.toLocaleString());
		}

		$scope.getSize = function() {
			if ($scope.responseSize >= 100) {
				return 'More than 100';
			} else {
				return $scope.responseSize;
			}
		}
	});

})();