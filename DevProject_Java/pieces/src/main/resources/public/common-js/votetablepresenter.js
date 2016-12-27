/**
 * 
 */

var app = angular.module("votePresenterApp", ['ngCookies']);
app.controller('voteCtrl', ['$scope', '$http', '$q','$cookies', '$cookieStore', 'voteService', function($scope, $http, $q, $cookies, $cookieStore, voteService){

	// check if the user voted before or not
	if ($cookieStore.get('VoteStatus') == 1){
		alert('You voted before!');
		return;
	}
	
	$scope.selectedItemID = undefined;
	$scope.selectedTableID = undefined;
	$scope.submit = function(){
//		var elm = angular.element(document.getElementById("tableName"));
//		console.log(elm.html());
		$scope.selectedTableID = $("#tableID").html();
		console.log($scope.selectedTableID);
		
		// get checked checkboxes
		var allElems = $( "input:checkbox:checked" );
		if (allElems.length == 0){
			alert('No items selected!');
			return;
		}
		if (allElems.length > 1){
			alert('Choose only one item!');
			return;
		}
		

		console.log($(".input-group").each(function(){
			var checkBox = $(this).find("input:checkbox:checked");
			if (checkBox.length){
				var label = $(this).find("label")[0];
				$scope.selectedItemID = $(label).text();
				console.log($(label).text());
			}
		}));
		
		
		
		var promise = voteService.doVote($scope.selectedTableID, $scope.selectedItemID);
		promise.then(
				function(){
					console.log('here');
					// reload the page
//					window.location.reload();
					
					$cookieStore.put('VoteStatus', 1);
				});

	};
}])
.service('voteService', ['$http', '$q', function($http, $q){
	return {
		doVote: function(tableID, itemID){
			var defer = $q.defer();
			$http.post('/vote-tables/'
				    + tableID 
					+ '/' + itemID)
					.then(
							function(success){
								console.log(success.data);
								defer.resolve('received!');
//								document.cookie = "VoteStatus=1;"
							},
							function(error){
								console.log('error!');
								console.log(error.data);
								defer.reject('rejected!');
							}
					);
			return defer.promise;
		}
		
	}
}])