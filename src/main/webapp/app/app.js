//Define an angular module for our app
var spaApp = angular.module('spaApp', [ "ngRoute", "ui.bootstrap", "ngTable", "auth0", "angular-storage", "angular-jwt", "ngFileUpload", "angular-loading-bar" ]);

spaApp.config(

function myAppConfig($routeProvider, authProvider, $httpProvider, $locationProvider, jwtInterceptorProvider) {

	$routeProvider.when('/PersonDetails', {
		templateUrl : 'app/partials/dating_details.html',
		controller : 'PersonDetailsController'

	}).when('/PersonDetailsUnregistered', {
		templateUrl : 'app/partials/dating_details_unregistered.html',
		controller : 'PersonDetailsUnregisteredController'
	}).when('/ListPersons', {
		templateUrl : 'app/partials/dating_list.html',
		controller : 'ListPersonsController',
		requiresLogin : true
	}).when('/HomePage', {
		templateUrl : 'app/partials/home.html',
		controller : 'HomePageController',
		requiresLogin : true
	}).when('/RegisterPage', {
		templateUrl : 'app/partials/register.html',
		controller : 'RegisterController',
		requiresLogin : true
	}).when('/ContactsPage', {
		templateUrl : 'app/partials/contacts.html',
		controller : 'ContactsController',
		requiresLogin : true
	}).when('/UploadPicPage', {
		templateUrl : 'app/partials/register_pics.html',
		controller : 'UploadPicController',
		requiresLogin : true
	}).when('/LikedMePage', {
		templateUrl : 'app/partials/liked_me.html',
		controller : 'LikedMeController',
		requiresLogin : true
	}).when('/ILikedThemPage', {
		templateUrl : 'app/partials/i_liked_them.html',
		controller : 'ILikedController',
		requiresLogin : true
	}).when('/WannaMeetMePage', {
		templateUrl : 'app/partials/wanna_meet_me.html',
		controller : 'MeetMeController',
		requiresLogin : true
	}).when('/IWannaMeetPage', {
		templateUrl : 'app/partials/i_wanna_meet.html',
		controller : 'IWannaMeetController',
		requiresLogin : true
	}).when('/MeetPage', {
		templateUrl : 'app/partials/meet_ppl.html',
		controller : 'MeetController'
	}).when('/ReadMePage', {
		templateUrl : 'app/partials/read_me.html',
		controller : 'LikedMeController',
		requiresLogin : true
	}).when('/ViewedMePage', {
		templateUrl : 'app/partials/viewed_me.html',
		controller : 'ViewedMeController',
		requiresLogin : true
	}).when('/EmailPage', {
		templateUrl : 'app/partials/email.html',
		controller : 'LikedMeController'
	}).when('/SearchProfilesPage', {
		templateUrl : 'app/partials/searchProfiles.html',
		controller : 'SearchProfilesController'
	}).when('/SentItemsPage', {
		templateUrl : 'app/partials/sentItems.html',
		controller : 'EmailController',
		requiresLogin : true

	}).when('/SentEmailPage', {
		templateUrl : 'app/partials/sentEmailPage.html',
		controller : 'SentEmailController',
		requiresLogin : true
	}).when('/login', {
		templateUrl : 'app/partials/login.html',
		controller : 'LoginController',
		pageTitle : 'Login',
		requiresLogin : true

	}).when('/', {
		controller : 'HomePageController',
		templateUrl : 'app/partials/home.html',
		pageTitle : 'Homepage',
		requiresLogin : true
	}).otherwise({
		redirectTo : '/HomePage'
	});

	authProvider.init({
		domain : AUTH0_DOMAIN,
		clientID : AUTH0_CLIENT_ID,
		callbackURL : location.href,
		loginUrl : '/login'
	});

	jwtInterceptorProvider.tokenGetter = function(store) {
		return store.get('token');
	}

	// Add a simple interceptor that will fetch all requests and add the jwt token to its authorization header.
	// NOTE: in case you are calling APIs which expect a token signed with a different secret, you might
	// want to check the delegation-token example
	$httpProvider.interceptors.push('jwtInterceptor');

}

);

spaApp.run(function($rootScope, auth, store, jwtHelper, $location, $templateCache) {
	$rootScope.$on('$locationChangeStart', function() {
		if (!auth.isAuthenticated) {
			var token = store.get('token');
			if (token) {
				if (!jwtHelper.isTokenExpired(token)) {
					auth.authenticate(store.get('profile'), token);
				} else {
					$location.path('/login');
				}
			}
		}

	});

});

spaApp.controller('AppCtrl', function AppCtrl($scope, $location, $rootScope, $route) {
	$scope.isAuthenticated = false;
	$scope.$on('$routeChangeSuccess', function(e, nextRoute) {
		if (nextRoute.$$route && angular.isDefined(nextRoute.$$route.pageTitle)) {
			$scope.pageTitle = nextRoute.$$route.pageTitle + ' | Auth0 Sample';
		}
	});

});

spaApp.factory('userService', function() {

	var userData = {};
	var profile_id_of_random_member;
	var email_senders_id;
	var sourceForDetailsController;
	var email = {};
	var originOfEmailPage;
	var loggedInUser = {};
	var visitorLocation;
	var is_person_already_liked;
	return {

		setIsPersonLiked : function(val) {
			is_person_already_liked = val;
		},
		getIsPersonLiked : function() {
			return is_person_already_liked;
		},
		setSendersId : function(id) {
			email_senders_id = id;
		},
		getSendersId : function() {
			return email_senders_id;
		},
		setIdForRandomlySelectedPerson : function(pic_url) {
			var res = pic_url.split(BUCKET_URL);
			var tmp1 = res[1].split(".jpg");
			var tmp2 = tmp1[0].split("_");
			profile_id_of_random_member = tmp2[0];

			// settin to null, so other controllers know it was a random user
			userData.senders_profile_id = null;

		},
		getIdOfRandomMember : function() {
			return profile_id_of_random_member;
		},

		setPerson : function(user) {
			userData = user;
		},
		getPerson : function() {
			return userData;
		},
		setEmail : function(emailObj) {
			email = emailObj;
		},
		getEmail : function() {
			return email;
		},
		setSourceForDetailsController : function(source) {
			sourceForDetailsController = source;
		},

		getSourceForDetailsController : function() {
			return sourceForDetailsController;
		},
		setOriginOfEmailPage : function(source) {
			originOfEmailPage = source;
		},
		getOriginOfEmailPage : function() {
			return originOfEmailPage;
		},
		setLoggedInUser : function(usr) {
			loggedInUser = usr;
			localStorage.setItem("loggedInUser", JSON.stringify(usr));
		},
		getLoggedInUser : function() {
			return JSON.parse(localStorage.getItem("loggedInUser"));
		},
		getVisitorLocation : function() {
			return visitorLocation;
		},
		setVisitorLocation : function(loc) {
			visitorLocation = loc;
		}
	};
});

spaApp.factory('restService', function($http, $q) {
	return {
		searchProfiles : function(obj) {

			var deferred = $q.defer();

			$http({

				url : REST_BASE + 'searchProfiles',
				method : "POST",
				data : obj,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config) {
				console.log("Search successful");
				deferred.resolve(data);
			}).error(function(data, status, headers, config) {
				console.log("Search failed");
				deferred.reject(data);
			});

			return deferred.promise;
		},

		removeContact : function(profileId, contactId) {
			var deferred = $q.defer();

			var url = REST_BASE + 'removeContact?profile_id=' + profileId + '&contact_id=' + contactId;
			$http.get(url, {
				cache : true
			}).success(function(data) {
				console.log("Delete contact successful");
				deferred.resolve(data);
			}).error(function(data, status, headers, config) {
				console.log("Delete contact failed");
				deferred.reject(data);
			});

			return deferred.promise;

		},
		
		reportProfile : function(reporterId,profileId,reason) {
			var deferred = $q.defer();

			var url = REST_BASE + 'report?profile_id=' + profileId + '&reporter_id=' + reporterId + '&reason=' + reason;
			$http.get(url, {
				cache : true
			}).success(function(data) {
				console.log("Profile reported successfully");
				deferred.resolve(data);
			}).error(function(data, status, headers, config) {
				console.log("Profile reporting failed");
				deferred.reject(data);
			});

			return deferred.promise;
		}
	}
});

spaApp.factory('Utils', function($q) {
	return {
		isImage : function(src) {

			var deferred = $q.defer();

			var image = new Image();
			image.onerror = function() {
				deferred.resolve(false);
			};
			image.onload = function() {
				deferred.resolve(true);
			};
			image.src = src;

			return deferred.promise;
		}
	};
});


// this is for default img if original img not found
spaApp.directive('errSrc', function() {
	return {
		link : function(scope, element, attrs) {
			element.bind('error', function() {
				if (attrs.src != attrs.errSrc) {
					attrs.$set('src', attrs.errSrc);
				}
			});
		}
	}
});

//Custom filter to reverse order of contacts in contacts page. We want the last contact added to appear on top.
spaApp.filter('reverse', function() {
	  return function(items) {
	    return items.slice().reverse();
	  };
	});
