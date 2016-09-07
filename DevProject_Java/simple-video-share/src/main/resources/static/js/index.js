var app = angular.module('mainApp', [])
.controller('uploadFileCtrl',["$scope", "$http", "mainService", function($scope,$http, mainService){
	$scope.fileName = "";
	$scope.showFile = function(name){
		var myEl = angular.element(name);
		console.log(myEl);
		$scope.fileName = myEl.val();
	}
	$scope.sendFile = function(){
		var file = $scope.fileName;
	      
		console.log(file);
		var uploadUrl = 'http://localhost:8080/uploader';
		mainService.uploadFile(file, uploadUrl);
	}
	

}])
.service('mainService', function($http) {
  	return {
  		uploadFile : function(file, uploadUrl){
  		        var fd = new FormData();
  		        fd.append('reqBody', file);
  		        //fd.append('name', 'file');
  		        //fd.append('filename', file);
  		        $http.post(uploadUrl, fd, {
  		            transformRequest: angular.identity,
  		            //headers: {'Content-Type': 'multipart/form-data; boundary=newBoundary'}
  		            headers: {'Content-Type': undefined}
  		        })
  		        .success(function(){
  		        	alert("File uploaded!");
  		        })
  		        .error(function(){
  		        	alert("File upload Failed!");
  		        });
  		},
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
  })