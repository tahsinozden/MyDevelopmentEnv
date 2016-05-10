// TODO: hanlde async responses for some request for them to wait for reponses, using promises.
var app = angular.module('myApp', ['ngMaterial', 'ui.bootstrap']);

// to be able to use global functions, varibale etc,
// rootScope should be initialized as in the following
app.run(function ($rootScope, $http) {
        // to be able to use $rootScope variables inside HTML, $root must be used inside HTML
        // i.e. $root.mySetting
        $rootScope.mySetting = 42;
        // get current user
        $rootScope.currentUserName = sessionStorage.getItem('currentUser');
        console.log("Current User : " + $rootScope.currentUserName);
        // get JWT token
        var token = sessionStorage.getItem("token");
        $rootScope.AuthHeader = 'Bearer ' + token;
        
        
        $http.defaults.headers.common.Authorization = $rootScope.AuthHeader;
        $rootScope.allSubscriptions = null;
        
        // valid threshold types
        $rootScope.ThresholdTypes = {'EQUAL': 'EQUAL',
        							 'GREATER_THAN': 'GREATER_THAN',
        							 'LESS_THAN': 'LESS_THAN',
        							 'NO_THRESHOLD': 'NO_THRESHOLD'};
        
        // list of data prepared for unsubscription selection of currencies.
        $rootScope.lstUnsubscriptions = [];
        $rootScope.showSubscriptions = function(userName){
          $http.get('http://localhost:8080/notic_reg_serv/query', {
            params: {
              'userName': userName,
            }
          }).then(function(response){
            // success function
            console.log(response.data);
            // set response data, it is JSON
            $rootScope.allSubscriptions = response.data;
            $rootScope.lstUnsubscriptions = $rootScope.allSubscriptions;
          }, function(response){
            // error function
            alert(response.data.message);
          });
      };
      $rootScope.showUnSubscriptionsForMenu = function(userName){
          $rootScope.showSubscriptions(userName);
          // $rootScope.lstUnsubscriptions = $rootScope.allSubscriptions;
          // create a new mapping for selected items
          for (var i = 0; i < $rootScope.lstUnsubscriptions.length; i++) {
            // set a default value as false, 'not checked'
            $rootScope.lstUnsubscriptions[i]["checked"] = false;
          }
      };
      
      // user logged in checker function
      $rootScope.userLoggedIn = function(){
    	  console.log(sessionStorage.getItem('currentUser'));
    	  console.log(sessionStorage.getItem('token'));
    	  if(sessionStorage.getItem('currentUser') !== "" && sessionStorage.getItem('token')!== ""){
    		  console.log("User is logged in!");
    		  return true;
    	  }
    	  else{
    		  return false;
    	  }
      };
});
app.controller('MyController', function($scope, $mdSidenav, $rootScope) {
	$scope.loggedIn = function(){
		var curUser = sessionStorage.getItem('currentUser');
		if(curUser != null)
			return true;
		else{
			return false;
		}

	}
  //   $scope.openLeftMenu = function() {
  //   $mdSidenav('left').toggle();
  // };
  $scope.selectedMenuItem = null;
  // scope.select = function(item) {
  //   scope.selectedMenuItem = item;
  // };
  // for links to be clicked, hanlde all process here.
  // for the data which will be used in general must be in root scope.
  $scope.linkClicked = function(item){
    console.log(item);
    $scope.selectedMenuItem = item;
    if ($scope.selectedMenuItem.MenuIndex == 0){
      $rootScope.showSubscriptions($rootScope.currentUserName);
      console.log("$rootScope.allSubscriptions : " +  $rootScope.allSubscriptions);
    }
    else if ($scope.selectedMenuItem.MenuIndex == 2){
      // TODO: solve single click update problem, after second click, we can see all subscriptions. -> seems fixed now after setting lstUnsubscriptions inside response.
      $rootScope.showSubscriptions($rootScope.currentUserName);
      $rootScope.lstUnsubscriptions = $rootScope.allSubscriptions;
      // create a new mapping for selected items
      for (var i = 0; i < $rootScope.lstUnsubscriptions.length; i++) {
        // set a default value as false, 'not checked'
        $rootScope.lstUnsubscriptions[i]["checked"] = false;
      }
      console.log($rootScope.lstUnsubscriptions);
    }
    // do logout action
    else if ($scope.selectedMenuItem.MenuIndex == 3){
    	// delete current user and token from session storage
    	sessionStorage.setItem("token", "");
    	sessionStorage.setItem("currentUser", "");
    	$rootScope.currentUserName = "";
    	$rootScope.AuthHeader = "";
    	// redirect to the main page
    	alert("Logout success!");
    	window.location = "http://localhost:8080";
    }
  };
  
  // navigation menu here
  $scope.navLinks = [
    {
      Title: "Show Subscriptions",
      LinkText: "Show Subscriptions",
      MenuIndex: 0
    },
    {
      Title: "Subscribe Currency",
      LinkText: "Subscribe Currency",
      MenuIndex: 1
    },
    {
      Title: "Unsubscribe Currency",
      LinkText: "Unsubscribe Currency",
      MenuIndex: 2
    },
    {
        Title: "Logout",
        LinkText: "Logout",
        MenuIndex: 3
    }
//    },
//    {
//      Title: "Subscribe Currency Threshold Notification",
//      LinkText: "Subscribe Currency Threshold Notification",
//      MenuIndex: 3
//    },
//    {
//      Title: "Unsubscribe Currency Threshold Notification",
//      LinkText: "Unsubscribe Currency Threshold Notification",
//      MenuIndex: 4
//    }

  ];
  
  // check if the user logged in or not
  if ($rootScope.userLoggedIn()){
	  // set initial menu as subscription list
	  $scope.selectedMenuItem = $scope.navLinks[0];
  }
  else{
	  console.log("User is not logged in, redirect to main page!")
  	  window.location = "http://localhost:8080";
  }

});

app.controller('ctrlSubsNotic', function ($scope, $http, $rootScope) {
    var _selected;
    var selectedNoticPeriod = null;
    // defualt currency rates service
    $scope.selectedCurrencyServMode = "NBP";
    
    $scope.selected = undefined;

//    $scope.allCurrencyCodes = [];
//    $http.get('http://localhost:8080/currency_service/currencies').then(function(response){
//        for (var item in response.data){
//            $scope.allCurrencyCodes.push(response.data[item].code);
//          }
//      console.log($scope.allCurrencyCodes);
//     });
    
	$scope.allCurrencyCodes = [];
	function getAllCurrenciesFromRemoteService(){
		$scope.allCurrencyCodes = [];
		console.log("Getting all currencies from remote service...");
		$http.get('http://localhost:8080/currency_service/currencies', 
				{params: 
				{ 
					currencyServiceMode: $scope.selectedCurrencyServMode
				}
			}).then(function(response){

			for (var item in response.data){
				$scope.allCurrencyCodes.push(response.data[item].code);
			}
			console.log($scope.allCurrencyCodes);
		});
	}
	
	// run get all service function
	getAllCurrenciesFromRemoteService();

	$scope.resetFields = function(){
		$scope.selectedSrcCurrency = "";
		$scope.selectedDstCurrency = "";
		
		// get all currencies from remote service again 
		getAllCurrenciesFromRemoteService();
	}
	
    $scope.convertCurrency = function(src, dst, srcAmt, currencyServiceMode){
//      var src = $scope.selectedSrcCurrency;
//      var dst = $scope.selectedDstCurrency;
//      var srcAmt = $scope.srcAmt;

      if ((src !== '' && dst !== '' || srcAmt !== '') && src !== dst){
        $http.get('http://localhost:8080/currency_service/currency', {
          params: {
            'src': src,
            'dst': dst,
            'srcAmt': srcAmt,
			'currencyServiceMode': currencyServiceMode
          }
        }).then(function(response){
          // success function
          $scope.conversion = response.data.conversionResult;
        }, function(response){
          // error function
          alert(response.data.message);
        });
      }
    };


    $scope.ngModelOptionsSelected = function(value) {
      if (arguments.length) {
        _selected = value;
      } else {
        return _selected;
      }
    };

    $scope.modelOptions = {
      debounce: {
        default: 500,
        blur: 250
      },
      getterSetter: true
    };
    
    //$scope.thresholdValue = 0.0;
    $scope.thresholdType = "NO_THRESHOLD"
    $scope.createNotic = function(){
    	if ($scope.thresholdValue == undefined || $scope.thresholdValue == null){
    		$scope.thresholdValue = 0.0;
    	}
    	// check input values
    	if ($scope.selectedSrcCurrency == undefined || $scope.selectedSrcCurrency == null ||
    		$scope.selectedDstCurrency == undefined || $scope.selectedDstCurrency == null || 
    		$scope.selectedNoticPeriod == undefined || $scope.selectedNoticPeriod == null ||
    		$scope.thresholdType == undefined || $scope.thresholdType == null ||
    		$scope.selectedCurrencyServMode == undefined || $scope.selectedCurrencyServMode == null
    		){
    		alert("Please check values in the form!!!");
    		return;
    	}
        $http.defaults.headers.common.Authorization = $rootScope.AuthHeader;
        $http.get('http://localhost:8080/notic_reg_serv/create', {
          params: {
            'userName': $rootScope.currentUserName,
            'srcCur': $scope.selectedSrcCurrency,
            'dstCur': $scope.selectedDstCurrency,
            'noticPeriod': $scope.selectedNoticPeriod,
            'threshold': $scope.thresholdValue,
            'thresholdType': $scope.thresholdType,
            'currencyServiceMode': $scope.selectedCurrencyServMode
          }
        }).then(function(response){
          // success function
          console.log(response);
        }, function(response){
          // error function
          alert(response.data.message);
        });
    };

    // function for unsubscribtion from currency notification
    $scope.deleteNotic = function(){
      // find selected records from the table
      var lstRecords = [];
      for (var i = 0; i < $rootScope.lstUnsubscriptions.length; i++) {
        // add the selected records to the list
        if( $rootScope.lstUnsubscriptions[i].checked)
          lstRecords.push($rootScope.lstUnsubscriptions[i].recId);
      }
      if (lstRecords === undefined || lstRecords.length == 0) {
        alert("No records selected!");
        return;
      }
      console.log("Selected Records :" + String(lstRecords));
      	$http.defaults.headers.common.Authorization = $rootScope.AuthHeader;
        $http.delete('http://localhost:8080/notic_reg_serv/unsubscribe', {
          params: {
            'userName': $rootScope.currentUserName,
            // in order to send multiple values of recID,
            // a js list can be set to recID.
            'recID': lstRecords,
          }
        }).then(function(response){
          // success function
          console.log("Unsubscription successful!");
          $rootScope.showUnSubscriptionsForMenu($rootScope.currentUserName);
          console.log(response);
        }, function(response){
          // error function
          alert(response.data.message);
        });
    };

    $scope.allSubscriptions = null;
    // $scope.showSubscriptions = function(){
    //   $http.get('http://localhost:8080/notic_reg_serv/query', {
    //     params: {
    //       'userName': 'tahsin',
    //     }
    //   }).then(function(response){
    //     // success function
    //     console.log(response.data);
    //     // set response data, it is JSON
    //     $scope.allSubscriptions = response.data;
    //   }, function(response){
    //     // error function
    //     alert(response.data.message);
    //   });
    // };

    // check if the user logged in or not
    if ($rootScope.userLoggedIn()){
  	  // set initial menu as subscription list
        $rootScope.showSubscriptions($rootScope.currentUserName);
        // $scope.allSubscriptions = $rootScope.allSubscriptions;
        console.log("$rootScope.allSubscriptions : " +  $rootScope.allSubscriptions);
    }
    else{
  	  	console.log("User is not logged in, redirect to main page!")
    	window.location = "http://localhost:8080";
    }
    


  });
