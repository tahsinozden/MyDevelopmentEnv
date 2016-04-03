var app = angular.module('currencyApp', ['ui.bootstrap', 'ngCookies']);
app.constant("CSRF_TOKEN", '{{ csrf_token() }}');
app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.xsrfCookieName = '_csrf';
    $httpProvider.defaults.xsrfHeaderName = 'XSRF-TOKEN';
}]);
app.controller('TypeaheadCtrl', function($scope, $http) {

  var _selected;

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
//  $scope.getCurrencies = function() {
//	  return ['USD', 'TRY', 'PLN'];
//  };

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


});




app.controller("loginCtl", function($scope, $http, $cookies, $cookieStore, CSRF_TOKEN){
  console.log("constant -> " + CSRF_TOKEN);
  $scope.credentials = {};
  //$httpProvider.defaults.headers.post['My-Header']='value'   (or)
  // $http.defaults.headers.post['X-CSRF-TOKEN']='taasas';
  // $http.defaults.headers.post['X-CSRF-TOKEN'] = $cookies['csrftoken'];
  console.log($cookies);
  console.log($cookieStore);
  $scope.formData = {'username': $scope.credentials.username, 'password': $scope.credentials.paasword};
  $scope.submitForm = function() {

  $http({
     method: "POST",
     url: 'http://localhost:8080/login',
    //  data: {
    //   'username': 'tahsin',
    //   'password': 'tahsin'
    // },
    data: $scope.credentials,
    params: $scope.credentials,
    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
}).then(function(response){
    // success function
    // $scope.conversion = response.data.conversionResult;
    console.log("success!");
  }, function(response){
    // error function
    console.log("we have error!");
  });
  };


});
