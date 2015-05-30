(function() {
	spaApp.controller('RegisterController', function($scope, $http, $location, $route, userService, auth, ngTableParams, $filter) {

		$scope.auth = auth;
		$scope.submitted = false;
		$scope.isFormValid= false;
		$scope.registrationResponsePass = "";
		$scope.registrationResponseFail = "";
		$scope.selectedValues = [];
		$scope.countries = [];
		
	//	$scope.member = JSON.parse(localStorage.getItem("loggedInUser"));

		
		$("#registerUsername").focus(function() {
			$scope.registrationResponsePass = $scope.registrationResponseFail = "";
		});
		$("#registerEmail").focus(function() {
			$scope.registrationResponsePass = $scope.registrationResponseFail = "";
		});
		$("#registerPassword").focus(function() {
			$scope.registrationResponsePass = $scope.registrationResponseFail = "";
		});
		
		
		//function to submit the form after all validation has occurred            
		$scope.register = function(isValid) {
			
			$scope.submitted = true;
			// check to make sure the form is completely valid
			if (isValid) {
				$scope.isFormValid= true;
				var url = REST_BASE + 'signUpNewUser?username=' + $scope.registerUsername + '&email=' + $scope.registerEmail + '&password=' + $scope.registerPassword + '&gender=' + $scope.gender
				$http.get(url).success(function(data) {

					if (data.status) {
						 $scope.registerUsername =  $scope.registerEmail =	  $scope.registerPassword =  $scope.checkboxValue =  $scope.gender = "";

						$scope.registerForm.$setPristine();
						$scope.submitted = false;
						$scope.registrationResponsePass = data.reason + " Check your email to confirm and login."
						$scope.registrationResponseFail = "";
					} else {
						$scope.submitted = false;
						$scope.registerForm.$setPristine();
						$scope.registrationResponseFail = data.reason;
						$scope.registrationResponsePass = "";
						
					}
				}).error(function(data) {
					$scope.registrationResponseFail = "Oops. Cannot register at this time" + data;
				});

			}

		};

		function getcitydetails(fqcn) {

			if (typeof fqcn == "undefined")
				fqcn = jQuery("#f_elem_city").val();

			cityfqcn = fqcn;

			if (cityfqcn) {

				jQuery.getJSON("http://gd.geobytes.com/GetCityDetails?callback=?&fqcn=" + cityfqcn, function(data) {
					jQuery("#f_elem_country").val(data.geobytescountry);
					jQuery("#f_elem_region").val(data.geobytesregion);
					$scope.member.state = data.geobytesregion;
					$scope.member.country = data.geobytescountry;
					/* jQuery("#geobytesinternet").val(data.geobytesinternet);
					 jQuery("#geobytescountry").val(data.geobytescountry);
					 jQuery("#geobytesregionlocationcode").val(data.geobytesregionlocationcode);
					 jQuery("#geobytesregion").val(data.geobytesregion);
					 jQuery("#geobyteslocationcode").val(data.geobyteslocationcode);
					 jQuery("#geobytescity").val(data.geobytescity);
					 jQuery("#geobytescityid").val(data.geobytescityid);
					 jQuery("#geobytesfqcn").val(data.geobytesfqcn);
					 jQuery("#geobyteslatitude").val(data.geobyteslatitude);
					 jQuery("#geobyteslongitude").val(data.geobyteslongitude);
					 jQuery("#geobytescapital").val(data.geobytescapital);
					 jQuery("#geobytestimezone").val(data.geobytestimezone);
					 jQuery("#geobytesnationalitysingular").val(data.geobytesnationalitysingular);
					 jQuery("#geobytespopulation").val(data.geobytespopulation);
					 jQuery("#geobytesnationalityplural").val(data.geobytesnationalityplural);
					 jQuery("#geobytesmapreference").val(data.geobytesmapreference);
					 jQuery("#geobytescurrency").val(data.geobytescurrency);
					 jQuery("#geobytescurrencycode").val(data.geobytescurrencycode);*/
				});
			}
		}

		function getCity() {

		
			jQuery("#f_elem_city").autocomplete({
				source : function(request, response) {
					jQuery.getJSON("http://gd.geobytes.com/AutoCompleteCity?callback=?&q=" + request.term, function(data) {
						response(data);
					});
				},
				minLength : 3,
				select : function(event, ui) {
					var selectedObj = ui.item;
					jQuery("#f_elem_city").val(selectedObj.value);
					getcitydetails(selectedObj.value);
					return false;
				},
				open : function() {
					jQuery(this).removeClass("ui-corner-all").addClass("ui-corner-top");
				},
				close : function() {
					jQuery(this).removeClass("ui-corner-top").addClass("ui-corner-all");
				}
			});
			jQuery("#f_elem_city").autocomplete("option", "delay", 100);

		}

		$scope.init = function() {

			
			$scope.member = JSON.parse(localStorage.getItem("loggedInUser"));
			///
			/*http://www.geobytes.com/free-ajax-cities-jsonp-api */
			getCity();

			/////

			
			
			$http.get('app/json/countries.json')
				.success(function(data) {
				var arr = data.RestResponse.result;
				for(i=0;i<=arr.length;i++) {
					$scope.countries.push(arr[i].name);
				}
			})
			.error(function(data) {
					console.log("Error getting list of countries.");
			});
			
			
			
			
			
		    $scope.$watch('selected', function(nowSelected){
		        $scope.selectedValues = [];
		        
		        if( ! nowSelected ){
		            // here we've initialized selected already
		            // but sometimes that's not the case
		            // then we get null or undefined
		            return;
		        }
		        angular.forEach(nowSelected, function(val){
		            $scope.selectedValues.push( val.id.toString() );
		        });
		        
		    });
		    
			
			var birthdays = [];
			for(i=1;i<=31;i++) {
				birthdays.push(i);
			}
			var j = jQuery.inArray($scope.member.day, birthdays);
			if(j==-1) {
				birthdays.push($scope.member.day);
			}
			$scope.birthday_prop = {
				"type" : "select",
				"value" : $scope.member.day,
				"values" : birthdays
			};

			
			var birthmonths = ['January','February','March','April','May','June','July','August','September','October','November','December'];
			var j = jQuery.inArray($scope.member.month_name, birthmonths);
			if(j==-1) {
				birthmonths.push($scope.member.month_name);
			}
			$scope.birthmonth_prop = {
				"type" : "select",
				"value" : $scope.member.month_name,
				"values" : birthmonths
			};
			
			
			var finalYear = new Date().getFullYear() - 18;
			var birthyears = [];
			for(i=1900;i<=finalYear;i++) {
				birthyears.push(i);
			}
			var j = jQuery.inArray($scope.member.year, birthyears);
			if(j==-1) {
				birthyears.push($scope.member.year);
			}
			$scope.birthyear_prop = {
				"type" : "select",
				"value" : $scope.member.year,
				"values" : birthyears
			};
			
			
			var heights = ['< 4\'6"(137cm)','4\'7"(140cm)','4\'8"(142cm)','4\'9"(145cm)','4\'10"(147cm)','4\'11"(150cm)','5\'0"(152cm)','5\'1"(155cm)','5\'2"(157cm)','5\'3"(160cm)',
			               '5\'4"(163cm)','5\'5"(165cm)','5\'6"(168cm)','5\'7"(170cm)','5\'8"(173cm)','5\'9"(175cm)','6\'0"(183cm)','6\'1"(185cm)','6\'2"(188cm)','6\'3"(191cm)',
			               '6\'4"(193cm)'];
			/*var j = jQuery.inArray($scope.member.height, heights);
			if(j==-1) {
				heights.push($scope.member.height);
			}*/
			$scope.height_prop = {
				"type" : "select",
				"value" : $scope.member.height,
				"values" : heights
			};

			var languages = [ 'Arabic', 'Urdu','French','English','Bangla','Somali','Spanish','Tagalog','Other' ];
			//languages.push($scope.member.language);
			$scope.language_prop = {
				"type" : "select",
				"value" : $scope.member.language,
				"values" : languages
			};



			var bodytypes = [ 'Athletic/Toned', 'About Average','A Few Extra Pounds','Slim/Petite','Big/Tall/Beautiful' ]; // DB cleaned up and matching this list
			
			if(jQuery.inArray($scope.member.bodytype, bodytypes) == -1) {
				bodytypes.push($scope.member.bodytype);
			}
			$scope.bodytype_prop = {
				"type" : "select",
				"value" : $scope.member.bodytype,
				"values" : bodytypes
			};

			var haircolors = [ 'Light Brown','Black','Dark Brown', 'Dark Blonde', 'Blonde', 'Auburn / Red','Salt and pepper grey','Bald / Shaved','White or grey' ]; // DB cleaned up and matching this list
			//haircolors.push($scope.member.haircolor);
			
			$scope.haircolor_prop = {
				"type" : "select",
				"value" : $scope.member.haircolor,
				"values" : haircolors
			};

			var eyecolors = [ 'Hazel', 'Black', 'Green', 'Brown','Blue','Gray' ]; // DB cleaned up and matching this list
			//eyecolors.push($scope.member.eyecolor);
			$scope.eyecolor_prop = {
				"type" : "select",
				"value" : $scope.member.eyecolor,
				"values" : eyecolors
			};

			var educations = [ 'Some College','Master\'s Degree','Bachelor\'s Degree','Elementary','High School','JD/Ph.D/Doctorate' ]; // DB cleaned up and matching this list
			//educations.push($scope.member.education);
			$scope.education_prop = {
				"type" : "select",
				"value" : $scope.member.education,
				"values" : educations
			};

			var children = [ 'No, and I\'m not sure I want any.','No, but I want kids.', 'Yes, and they live with me.','Yes, and they don\'t live with me.','No, and I definitely don\'t want kids.' ]; // DB cleaned up and matching this list
//			children.push($scope.member.children);
			$scope.children_prop = {
				"type" : "select",
				"value" : $scope.member.children,
				"values" : children
			};

			var marital = [ 'Single','Divorced','Separated','Widowed' ]; // DB cleaned up and matching this list
//			marital.push($scope.member.marital_status);
			$scope.marital_prop = {
				"type" : "select",
				"value" : $scope.member.marital_status,
				"values" : marital
			};

			// Chnaged value.. Need to sync up DB?
			var ethnicities = [ 'Arab (Middle Eastern)', 'African American/Black','Hispanic/Latino' ,'Asian','Indian','Pacific Islander','Native American/Alaskan','Mixed','Other']; // DB cleaned up and matching this list
			ethnicities.push($scope.member.ethnicity);
			$scope.ethnicity_prop = {
				"type" : "select",
				"value" : $scope.member.ethnicity,
				"values" : ethnicities
			};

			// Not used
			var drinks = [ 'Drink Socially', 'Dont Drink','Drink Regularly' ,'Trying to quit']; // DB cleaned up and matching this list
			drinks.push($scope.member.drink);
			$scope.drink_prop = {
				"type" : "select",
				"value" : $scope.member.drink,
				"values" : drinks
			};
			
			var incomes = [ '$0 - $30,000 (USD)', '$30,001 - $60,000 (USD)','$60,001 - $120,000 (USD)' ,'$120,000 (USD) +']; // DB cleaned up and matching this list
//			incomes.push($scope.member.income);
			$scope.income_prop = {
				"type" : "select",
				"value" : $scope.member.income,
				"values" : incomes
			};
			
			
			// New to add to DB
			var nationalities = [ 'Afghanistan', 'Albania','Algeria' ,'American Samoa','Andorra','Angola','Anguilla','Antarctica','Antigua and Barbuda','Argentina','Armenia','Aruba','Australia',
			                      'Austria','Azerbaijan','Bahamas','Bahrain','Bangladesh','Barbados','Belarus','Belgium','Belize','Benin','Bermuda','Bhutan','Bolivia','Bosnia-Herzegovina','Botswana',
			                      'Bouvet Island','Brazil','British Indian Ocean']; 
			//nationalities.push($scope.member.nationality);
			$scope.nationality_prop = {
				"type" : "select",
				"value" : $scope.member.nationality,
				"values" : $scope.countries
			};
			
			// New to add to DB
			var qurans = [ 'Daily', 'Ocassionally','Only During Ramadan' ,'Only on Jummah / Fridays','Read translated version','Never Read']; 
			qurans.push($scope.member.quran);
			$scope.quran_prop = {
				"type" : "select",
				"value" : $scope.member.quran,
				"values" : qurans
			};
			
			// New to add to DB
			var religions = [ 'Islam - Sunni', 'Islam - Shiite','Islam - Sufism' ,'Islam - Ahmadiyya','Islam - Other','Willing to revert to Islam','Other']; 
			religions.push($scope.member.religion);
			$scope.religion_prop = {
				"type" : "select",
				"value" : $scope.member.religion,
				"values" : religions
			};
			
			// New to add to DB
			var relocates = [ 'Willing to relocate within my country', 'Willing to relocate to another country','Not willing to relocate' ,'Not sure at this time']; 
			relocates.push($scope.member.relocate);
			$scope.relocate_prop = {
				"type" : "select",
				"value" : $scope.member.relocate,
				"values" : relocates
			};
			
			
			// New to add to DB
			var occupations = [ 'Science / Tech / Engineering', 'Computer / Hardware / Software','Sales / Marketing / Biz Dev' ,'Clerical / Administrative','Rather Not Say','Medicine / Health','Student','Banking / Financial / Real Estate','Construction / Craftsmanship','Unemployed','Executive / Management','Hospitality / Travel','Education / Academia','Transportation','Law / Legal Services','Artistic / Musical / Writer','Other']; 
//			occupations.push($scope.member.occupation);
			$scope.occupation_prop = {
				"type" : "select",
				"value" : $scope.member.occupation,
				"values" : occupations
			};
			
			
			var homes = [ 'Own my home, apartment', 'Rent home, apartment' ,'Live with parents','Live with roommate(s)','Live in hostel ']; // DB cleaned up and matching this list
			//homes.push($scope.member.home);
			$scope.home_prop = {
				"type" : "select",
				"value" : $scope.member.home,
				"values" : homes
			};
			
			var smokes = [ 'Non-smoker', 'Occasionally','Regularly' ,'Trying to Quit']; // DB cleaned up and matching this list
//			smokes.push($scope.member.smoke);
			$scope.smoke_prop = {
				"type" : "select",
				"value" : $scope.member.smoke,
				"values" : smokes
			};
			
			var drugs = [ 'Never used', 'Never used and never will','Use Occasionally' ,'Trying to quit','Use Regularly']; // DB cleaned up and matching this list
			drugs.push($scope.member.drugs);
			$scope.drug_prop = {
				"type" : "select",
				"value" : $scope.member.drugs,
				"values" : drugs
			};
		}
		
		$scope.saveClicked = function() {

		/*	for(i=0;i<$scope.selectedValues.length;i++) {
				$scope.member.languages.push($scope.selectedValues[i]);
			}*/
							
			$http({
			
		            url: REST_BASE + 'updateProfile/' + $scope.auth.profile.identities[0].user_id,
		            method: "POST",
		            data: $scope.member,
		            headers: {'Content-Type': 'application/json'}
		        }).success(function (data, status, headers, config) {
					   console.log("Member successfully updated");
					   localStorage.setItem("loggedInUser", JSON.stringify($scope.member));
					   $location.path('/PersonDetails');
		            }).error(function (data, status, headers, config) {
		            	console.log("Error updating profile " + data);
		            });
			
		}

		$scope.cancelClicked = function() {
			$location.path('/PersonDetails');
		}

	});

})();