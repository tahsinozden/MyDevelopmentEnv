/**
 *	Create Account helper script 
 */

var app = angular.module('ngCreateAcctApp', ['ui.bootstrap']);

app.controller('ctrlCreateAcctForm', ['mainService', '$scope', '$http',
                                      function(mainService, $scope, $http){
	$scope.submitFormData = function(){			  
		console.log("going to send data...");
		// TODO: find out why syntax error is thrown -> Implemented, the reason was because the backend returns String object
		// I changed it to a user object
		mainService.createAccount($scope.username, $scope.password).then(
				
				function(createdUser){
//					console.log("Created User : " + createdUser.toString());
//					alert("Created User : " + createdUser.toString());
//					console.log("User created!");
					alert("User created!");
					window.location = "http://localhost:8080";
				},
				function(error){
					console.log("Error occured " + error.toString());
				});
				
	};
	
	$scope.disableSubmit = function(){
		console.log("inside disable submit function");
		return( $scope.password == undefined || $scope.repassword == undefined || 
				$scope.password == '' || $scope.repassword == '' || $scope.username == undefined ||
				$scope.username == '' || $scope.password != $scope.repassword);
	}
	
}]);

// main service
app.service('mainService', function($http) {
	return {
		createAccount : function(name, pass) {
			return $http.post('/create-account', {username: name, password: pass}).then(function(response) {
				return response;
			});
		}
	};
});

