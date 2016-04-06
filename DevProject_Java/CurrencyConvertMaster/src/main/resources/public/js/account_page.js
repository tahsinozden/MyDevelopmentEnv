
var app = angular.module('myApp', ['ngMaterial', 'ui.bootstrap']);

// to be able to use global functions, varibale etc,
// rootScope should be initialized as in the following
app.run(function ($rootScope, $http) {
        // to be able to use $rootScope variables inside HTML, $root must be used inside HTML
        // i.e. $root.mySetting
        $rootScope.mySetting = 42;
        $rootScope.allSubscriptions = null;
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
          }, function(response){
            // error function
            alert(response.data.message);
          });
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
  $scope.linkClicked = function(item){
    console.log(item);
    $scope.selectedMenuItem = item;
    if ($scope.selectedMenuItem.MenuIndex == 0){
      $rootScope.showSubscriptions('tahsin');
      console.log("$rootScope.allSubscriptions : " +  $rootScope.allSubscriptions);
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
    },
    {
      Title: "Subscribe Currency Threshold Notification",
      LinkText: "Subscribe Currency Threshold Notification",
      MenuIndex: 3
    },
    {
      Title: "Unsubscribe Currency Threshold Notification",
      LinkText: "Unsubscribe Currency Threshold Notification",
      MenuIndex: 4
    }

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

    $scope.convert = function(){
      var src = $scope.selectedSrcCurrency;
      var dst = $scope.selectedDstCurrency;
      var srcAmt = $scope.srcAmt;

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

    $scope.createNotic = function(){
        $http.get('http://localhost:8080/notic_reg_serv/create', {
          params: {
            'userName': 'tahsin',
            'srcCur': $scope.selectedSrcCurrency,
            'dstCur': $scope.selectedDstCurrency,
            'noticPeriod': $scope.selectedNoticPeriod
          }
        }).then(function(response){
          // success function
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
