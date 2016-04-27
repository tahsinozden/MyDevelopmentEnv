/**
 *	Create Account helper script 
 */

var app = angular.module('ngCreateAcctApp', ['ui.bootstrap']);

app.controller('ctrlCreateAcctForm', ['mainService', '$scope', '$http',
                                      function(mainService, $scope, $http){
	$scope.submitFormData = function(){			  
		console.log("inside form submit");
//		$http({
//			method: "POST",
//			url: 'http://localhost:8080/create-account',
//			params: {
//				'username': $scope.username,
//				'password': $scope.password
//			},
//			data: {
//				'username': $scope.username,
//				'password': $scope.password
//			}
//		}).then(
//				// success function
//				function(response){
//					console.log("SUCCESS - " + response.data);
//					var user = response.data;
//					// redirect to main page for login
////					window.location = "/";
//				},
//				// error function
//				function(response){
//					console.log("ERROR - " + response);
//					console.log(response);
//				});
		// TODO: redirect to main page after successful user creation
		// TODO: find out why syntax error is thrown
		mainService.createAccount($scope.username, $scope.password).then(
				function(createdUser){
//					console.log("Created User : " + createdUser.toString());
//					alert("Created User : " + createdUser.toString());
//					console.log("User created!");
					alert("User created!");
//					window.location = "http://localhost:8080";
				},
				function(error){
//					console.log("Error occured " + error.toString());
					console.log("Error occured ");
				});
	};
	
	$scope.disableSubmit = function(){
		console.log("inside disable submit function");
		return( $scope.password == undefined || $scope.repassword == undefined || 
				$scope.password == '' || $scope.repassword == '' || $scope.username == undefined ||
				$scope.username == '' || $scope.password != $scope.repassword);
	}
	
	// watch values
//	$scope.$watch("username", function(){
//		console.log("watched");
//	});
//	$scope.$digest();
}]);


app.service('mainService', function($http) {
	return {
		createAccount : function(name, pass) {
			return $http.post('/create-account', {username: name, password: pass}).then(function(response) {
				return response;
			});
		}
	};
});

