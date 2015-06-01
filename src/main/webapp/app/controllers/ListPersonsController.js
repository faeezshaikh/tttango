(function() {
	spaApp.controller('ListPersonsController', function($scope, $http, $filter, userService, ngTableParams, $location, $route, auth) {

		$scope.searchString = "";

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
		$scope.personFromListSelected = function(userId) {
			localStorage.setItem("personId", userId);
		}

		// Once the user is on the List page and searches again, we want the page (ie. this view) to reload
		$scope.reloadRoute = function() {
			var val = $('#searchBar').val();
			if (val) {
				localStorage.setItem("searchTerm", val);
				$route.reload();

			}
		}

		$scope.getStatus = function(onlineStatus,lastLogin){
			return getOnlineStatus(onlineStatus,lastLogin);
		}

		$scope.getImgUrl = function(main_image) {
			return getMainImgUrl(main_image);
		}

		$scope.init = function() {

			var val = localStorage.getItem("searchTerm");
			$location.path('/ListPersons');

			var url = REST_BASE + 'findProfilesWhereUsernameLike?name=' + val;
			$http.get(url).success(function(data) {
				var resp = data;
				$scope.isLoading = false;
				$scope.responseSize = data.length;

				$scope.tableParams = new ngTableParams({
					page : 1, // show first page
					count : 10, // count per page
					sorting : {
						username : 'asc' // initial sorting
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

	});
})();
