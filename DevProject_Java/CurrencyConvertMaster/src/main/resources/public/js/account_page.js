var app = angular.module('myApp', ['ngMaterial', 'ui.bootstrap']);
app.controller('MyController', function($scope, $mdSidenav) {
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
  };
  $scope.navLinks = [
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

});

app.controller('ctrlSubsNotic', function ($scope, $http) {
    var _selected;
    var selectedNoticPeriod = null;

    $scope.selected = undefined;

    $scope.allCurrencyCodes = [];
    $http.get('http://localhost:8080/currency_service/currencies').then(function(response){
      console.log('HERE!');
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
    }



  });
