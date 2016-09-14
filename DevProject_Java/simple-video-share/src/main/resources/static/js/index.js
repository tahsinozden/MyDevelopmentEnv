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
.controller('mainVideoCtrl',["$scope", "$http", "mainService", function($scope,$http, mainService){
	//$scope.randVideo = "/allvideos/video1.mp4";
	$scope.randVideo = undefined;
	
	$scope.loadRandVideo = function(){
//		var videoElm = angular.element(document.getElementById('mainVideo'));
//		var videoSrcElm = angular.element(document.getElementById("mainVideoSrc"));
//		console.log(videoElm);
//		console.log(videoSrcElm);
		
		var videoElm = document.getElementById('randVideoElm');
		var videoSrcElm = document.getElementById('randVideoSrc');
		
		$http.get('/randomvideo')
		.then(function(response){
			$scope.randVideo = response.data.url;
			console.log('video loaded' + response.data.url);
			videoSrcElm.src = response.data.url;
			videoElm.load();
			videoElm.play();
		})
	}
	
	var elm = angular.element(document);
	elm = angular.element(angular.element(document).find("mainVideo"));
//	console.log(angular.element(document).find("mainVideo"));
	console.log(elm);
	
	$http.get('/randomvideo')
	.then(function(response){
		$scope.randVideo = response.data.url;
		console.log('video loaded' + response.data.url);
	})
	

}])
.directive('dynamicUrl', function () {
    return {
        restrict: 'A',
        link: function postLink(scope, element, attr) {
        	console.log("new link is " + attr.dynamicUrlSrc);
            element.attr('src', attr.dynamicUrlSrc);
        }
    };
})
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