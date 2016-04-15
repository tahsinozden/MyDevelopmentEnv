// TODO: hanlde async responses for some request for them to wait for reponses, using promises.
var app = angular.module('myApp', ['ngMaterial', 'ui.bootstrap']);

// to be able to use global functions, varibale etc,
// rootScope should be initialized as in the following
app.run(function ($rootScope, $http) {
        // to be able to use $rootScope variables inside HTML, $root must be used inside HTML
        // i.e. $root.mySetting
        $rootScope.mySetting = 42;
        $rootScope.currentUserName = "tahsin";
        $rootScope.allSubscriptions = null;
        $rootScope.ThresholdTypes = {'EQUAL': 'EQUAL',
        							 'GREATER_THAN': 'GREATER_THAN',
        							 'LESS_THAN': 'LESS_THAN'};
        ;
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
});
app.controller('MyController', function($scope, $mdSidenav, $rootScope) {
  //   $scope.openLeftMenu = function() {
  //   $mdSidenav('left').toggle();
  // };
  $scope.selectedMenuItem = null;
  // scope.select = function(item) {
  //   scope.selectedMenuItem = item;
  // };
  // for links to be clciked, hanlde all process here.
  // for the data which will be used in general must be in root scope.
  $scope.linkClicked = function(item){
    console.log(item);
    $scope.selectedMenuItem = item;
    if ($scope.selectedMenuItem.MenuIndex == 0){
      $rootScope.showSubscriptions('tahsin');
      console.log("$rootScope.allSubscriptions : " +  $rootScope.allSubscriptions);
    }
    else if ($scope.selectedMenuItem.MenuIndex == 2){
      // TODO: solve single click update problem, after second click, we can see all subscriptions. -> seems fixed now after setting lstUnsubscriptions insode response.
      $rootScope.showSubscriptions('tahsin');
      $rootScope.lstUnsubscriptions = $rootScope.allSubscriptions;
      // create a new mapping for selected items
      for (var i = 0; i < $rootScope.lstUnsubscriptions.length; i++) {
        // set a default value as false, 'not checked'
        $rootScope.lstUnsubscriptions[i]["checked"] = false;
      }
      console.log($rootScope.lstUnsubscriptions);
    }
  };
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
  // set initial menu as subscription list
  $scope.selectedMenuItem = $scope.navLinks[0];
});

app.controller('ctrlSubsNotic', function ($scope, $http, $rootScope) {
    var _selected;
    var selectedNoticPeriod = null;

    $scope.selected = undefined;

    $scope.allCurrencyCodes = [];
    $http.get('http://localhost:8080/currency_service/currencies').then(function(response){
        for (var item in response.data){
            $scope.allCurrencyCodes.push(response.data[item].code);
          }
      console.log($scope.allCurrencyCodes);
     });

    $scope.convertCurrency = function(src, dst, srcAmt){
//      var src = $scope.selectedSrcCurrency;
//      var dst = $scope.selectedDstCurrency;
//      var srcAmt = $scope.srcAmt;

      if ((src !== '' && dst !== '' || srcAmt !== '') && src !== dst){
        $http.get('http://localhost:8080/currency_service/currency', {
          params: {
            'src': src,
            'dst': dst,
            'srcAmt': srcAmt
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
    
    $scope.thresholdValue = 0.0;
    $scope.thresholdType = "GREATER_THAN"
    $scope.createNotic = function(){
        $http.get('http://localhost:8080/notic_reg_serv/create', {
          params: {
            'userName': 'tahsin',
            'srcCur': $scope.selectedSrcCurrency,
            'dstCur': $scope.selectedDstCurrency,
            'noticPeriod': $scope.selectedNoticPeriod,
            'threshold': $scope.thresholdValue,
            'thresholdType': $scope.thresholdType
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
        $http.delete('http://localhost:8080/notic_reg_serv/unsubscribe', {
          params: {
            'userName': 'tahsin',
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

    $rootScope.showSubscriptions('tahsin');
    // $scope.allSubscriptions = $rootScope.allSubscriptions;
    console.log("$rootScope.allSubscriptions : " +  $rootScope.allSubscriptions);

  });
