/**
 * 
 */

var app = angular.module("votePresenterApp", ['ngCookies']);
app.controller('voteCtrl', ['$scope', '$http', '$q','$cookies', '$cookieStore', 'voteService', function($scope, $http, $q, $cookies, $cookieStore, voteService){

	// check if the user voted before or not
//	if ($cookieStore.get('VoteStatus') == 1){
//		alert('You voted before!');
//		return;
//	}
	
	$scope.selectedTableID = $("#tableID").html();
	let votedTableIDs = $cookieStore.get('VotedTableIDs');
	if (votedTableIDs != undefined && votedTableIDs != null){
		var ids = $cookieStore.get('VotedTableIDs');
		for (var i in ids ){
			if (ids[i] == $scope.selectedTableID){
				alert('You have already voted before!');
				return;
			}
		}
	}
	
	$scope.selectedItemID = undefined;
	$scope.selectedTableID = undefined;
	$scope.currentIPaddr = undefined;
	
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
		
		
		var ipPromise = voteService.getCurrentIPInfo();
		ipPromise.then(function(data){
			console.log('data from promise ' + JSON.stringify(data));
			$scope.currentIPaddr = data.ip;
			console.log('current ip addr. : ' + $scope.currentIPaddr);
			
			// after getting ip, process the request
			var promise = voteService.doVote($scope.selectedTableID, $scope.selectedItemID, $scope.currentIPaddr);
			promise.then(
					function(data){
						console.log('here');
						console.log(data);
						// add the vote table id into cookies to prevent revoting
						$cookieStore.put('VoteStatus', 1);
						console.log($cookieStore.get('VotedTableIDs'));
						let votedTableIDs = $cookieStore.get('VotedTableIDs');
						if (votedTableIDs != undefined && votedTableIDs != null){
							votedTableIDs.push($scope.selectedTableID);
							$cookieStore.put('VotedTableIDs', votedTableIDs);
						}
						else {
							$cookieStore.put('VotedTableIDs', [$scope.selectedTableID]);
						}
						
						alert('Voting SUCCESS!');
						
						// reload the page
//						window.location.reload();
						
					});
		});
		


	};
}])
.service('voteService', ['$http', '$q', function($http, $q){
	return {
		doVote: function(tableID, itemID, curIPaddr){
			var defer = $q.defer();
//			$http.post('/vote-tables/'
//				    + tableID 
//					+ '/' + itemID, 
//					{'ip': '188.72.98.441'})
			$http(
					{
						url: '/vote-tables/' + tableID + '/' + itemID, 
						method: 'POST',
						params: {'ip': curIPaddr, 'tableID': tableID, 'checkIP': true}
					})			
					.then(
							function(success){
								console.log(success.data);
								defer.resolve(success.data);
							},
							function(error){
								console.log('error!');
								console.log(error.data);
								alert("ERROR: " + error.data.message);
								defer.reject(error.data);
							}
					);
			return defer.promise;
		},
	   getCurrentIPInfo: function(){
		   var defer = $q.defer();
		   $http.get('http://ipinfo.io/?callback')
		   	.then(
		   			function(success){
		   				defer.resolve(success.data);
		   				console.log(success.data);
		   			},
		   			function(error){
		   				defer.reject(error.data);
		   				console.log("error!");
		   				console.log(error.data);
		   			}
		   		  );
		   return defer.promise;
	   }
	}
}])