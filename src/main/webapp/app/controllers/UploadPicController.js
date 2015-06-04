(function() {
	spaApp.controller('UploadPicController', function($scope, Upload, auth, $http, $window, $route, $location, userService) {
		$scope.auth = auth;
		$scope.uploaded_imgs = [];
		$scope.inprogress = false;
		$scope.isUploadAllowedFlag = true;
		$scope.noOfPics = 0;

		var loggedInUser = userService.getLoggedInUser();

		var url = REST_BASE + 'getNoOfPics/' + $scope.auth.profile.identities[0].user_id
		//	var url = REST_BASE + 'getNoOfPics/' +  loggedInUser.id
		$http.get(url).success(function(data) {
			$scope.log = 'Total pics uploaded = ' + data;
			$scope.noOfPics = data;
			if (data == 12) {
				$scope.isUploadAllowedFlag = false;
				$scope.log = 'Max no of pics reached. Total = 12'
			}
			for (i = 1; i <= data; i++) {
				var url = BUCKET_URL + $scope.auth.profile.identities[0].user_id + "_" + i + ".jpg";
				//			  var url = BUCKET_URL + loggedInUser.id + "_" + i + ".jpg";
				$scope.uploaded_imgs.push(url);
			}
		});

		$scope.$watch('files', function() {
			$scope.upload($scope.files);
		});
		$scope.log = '';

		$scope.isUploadAllowed = function() {
			return $scope.isUploadAllowedFlag;
		}

		$scope.isUploadInProgress = function() {
			return $scope.inprogress;
		}
		function getPicNameFromUrl(picUrl) {
			var res = picUrl.split(BUCKET_URL);
			var picName = res[1];
			return picName;

		}

		function getPicNumberFromUrl(picUrl) {
			var res = picUrl.split(BUCKET_URL);
			var tmp1 = res[1].split(".jpg");
			var tmp2 = tmp1[0].split("_");
			var picNumber = tmp2[1];

		}

		$scope.makeMainImage = function(picUrl) {
			var picName = getPicNameFromUrl(picUrl);

			var url = REST_BASE + 'makeImageMain/' + picName
			$http.get(url).success(function(data) {

				alert('Main picture successfully updated.');
//				$route.reload();
				//location.reload(true);
			});

		}
		$scope.deleteImage = function(picUrl) {
			var picName = getPicNameFromUrl(picUrl);

			var picNo = getPicNumberFromUrl(picUrl);

			var picNoOfLastImage = $scope.noOfPics;
			var nameOfLastImage = $scope.auth.profile.identities[0].user_id + "_" + picNoOfLastImage + ".jpg";
			//    	var nameOfLastImage = loggedInUser.id + "_" + picNoOfLastImage + ".jpg";
			var url = REST_BASE + 'deleteImage/' + picName + '?replacement=' + nameOfLastImage;

			//    	  alert(url);
			var deleteUser = $window.confirm('Are you sure you want to delete ' + picName + '?');

			if (deleteUser) {
				var url = REST_BASE + 'deleteImage/' + picName + '?replacement=' + nameOfLastImage;

				$http.get(url).success(function(data) {
					$route.reload();
				});

			}
		}
		$scope.upload = function(files) {
			if (files && files.length) {
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					Upload.upload({
						url : REST_BASE + 'fileUpload/' + $scope.auth.profile.identities[0].user_id,
						//                    url: REST_BASE + 'fileUpload/' + loggedInUser.id,
						/*fields: {
						    'username': $scope.username
						},*/
						file : file,
						dataType : 'json'
					}).progress(function(evt) {
						var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
						//                    $scope.log = 'progress: ' + progressPercentage + '% ' + evt.config.file.name + '\n' + $scope.log;
						$scope.log = 'Upload in progress...Please wait..';
						$scope.inprogress = true;
					}).success(function(data, status, headers, config) {
						$scope.log = 'file ' + config.file.name + 'uploaded successfully. Response from server: ' + JSON.stringify(data) + '\n';
						$scope.$apply();
						var url = BUCKET_URL + data;
						console.log('Pushing' + url);
						$scope.uploaded_imgs.push(url);
						$scope.inprogress = false;
						$route.reload();
					});
				}
			}
		};
	});

})();