var app = angular.module('currencyApp', ['ui.bootstrap', 'ngCookies']);
app.constant("CSRF_TOKEN", '{{ csrf_token() }}');
app.config(['$httpProvider', function($httpProvider) {
	$httpProvider.defaults.xsrfCookieName = '_csrf';
	$httpProvider.defaults.xsrfHeaderName = 'XSRF-TOKEN';
}]);
app.controller('TypeaheadCtrl', function($scope, $http) {

	var _selected;
	$scope.selectedCurrencyServMode = "NBP";
	
	$scope.selected = undefined;

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

	$scope.convert = function(){
		var src = $scope.selectedSrcCurrency;
		var dst = $scope.selectedDstCurrency;
		var srcAmt = $scope.srcAmt;
		var currencyServiceMode = $scope.selectedCurrencyServMode;
		console.log("currencyServiceMode : " + currencyServiceMode);
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
		else {
			alert("Conversion will not be performed!!!. Check your inputs!");
			$scope.conversion = "";
		}
	};
	
	$scope.resetFields = function(){
		$scope.selectedSrcCurrency = "";
		$scope.selectedDstCurrency = "";
		$scope.srcAmt = "";
		
		// get all currencies from remote service again 
		getAllCurrenciesFromRemoteService();
	}
//	$scope.getCurrencies = function() {
//	return ['USD', 'TRY', 'PLN'];
//	};

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
	// $httpProvider.defaults.headers.post['My-Header']='value' (or)
	// $http.defaults.headers.post['X-CSRF-TOKEN']='taasas';
	// $http.defaults.headers.post['X-CSRF-TOKEN'] = $cookies['csrftoken'];
	console.log($cookies);
	console.log($cookieStore);
	$scope.formData = {'username': $scope.credentials.username, 'password': $scope.credentials.paasword};
	$scope.submitForm = function() {

		$http({
			method: "POST",
			url: 'http://localhost:8080/login',
			// data: {
			// 'username': 'tahsin',
			// 'password': 'tahsin'
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


app.controller('jwtLoginCtrl', ['mainService','$scope','$http', '$cookies',
                                  function(mainService, $scope, $http, $window, $cookies) {
	$scope.greeting = '';
	$scope.token = null;
	$scope.error = null;
	$scope.roleUser = false;
	$scope.roleAdmin = false;
	$scope.roleFoo = false;

	$scope.login = function() {
		$scope.error = null;
		if ($scope.userName == "" || $scope.userName == null || $scope.userName == undefined ||
				$scope.password == "" || $scope.password == null || $scope.password == undefined){
			alert("Input some values to username and password!");
			return;
		}
			
		mainService.login($scope.userName, $scope.password).then(function(token) {
			$scope.token = token;
			$http.defaults.headers.common.Authorization = 'Bearer ' + token;
//			$window.sessionStorage.accessToken = token;
//			$cookies["token"] = token;
//			console.log($cookies);
////			$cookieStore.put('token', token);
//			console.log($cookies.get("token"));
			sessionStorage.setItem('token', token);
			sessionStorage.setItem('currentUser', $scope.userName);
			$scope.checkRoles();
			window.location="/pages/account_page.html";
		},
		function(error){
			$scope.error = error
			$scope.userName = '';
			$scope.password = '';
		});
	}
	
	$scope.yourName = function(){
		$scope.error = null;
		
		$http.post('/api/name-srv', {name: $scope.userName}).then(function(response) {
			$http.defaults.headers.common.Authorization = 'Bearer ' + $scope.token;
			console.log(response.data);
		},
		function(error){
			$scope.error = error
		});
		
	}

	$scope.checkRoles = function() {
		mainService.hasRole('user').then(function(user) {$scope.roleUser = user});
		mainService.hasRole('admin').then(function(admin) {$scope.roleAdmin = admin});
		mainService.hasRole('foo').then(function(foo) {$scope.roleFoo = foo});
	}

	$scope.logout = function() {
		$scope.userName = '';
		$scope.token = null;
		$http.defaults.headers.common.Authorization = '';
	}

	$scope.loggedIn = function() {
		return $scope.token !== null;
	}
} ]);



app.service('mainService', function($http) {
	return {
		login : function(username, pass) {
			return $http.post('/user/login', {name: username, password: pass}).then(function(response) {
				return response.data.token;
			});
		},

		hasRole : function(role) {
			return $http.get('/api/role/' + role).then(function(response){
				console.log(response);
				return response.data;
			});
		}
	};
});
