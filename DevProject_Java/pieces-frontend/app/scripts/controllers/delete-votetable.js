'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:DeleteVotetableCtrl
 * @description
 * # DeleteVotetableCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('DeleteVotetableCtrl', ['tableService', '$scope', 'VoteTable', function (tableService, $scope, VoteTable) {
    $scope.tableID = undefined;
    $scope.authKey = undefined;

    $scope.submitDeletion = function () {
      if ($scope.tableID == undefined || $scope.authKey == undefined) {
        alert('input something!');
        return;
      };

      var tbl = new VoteTable($scope.tableID, '', '', '', $scope.authKey, '');
      tableService.deleteTable(tbl)
        .then(
        function () {
          console.log('promise returned');
        }
        )
    }

  }])
  .service('tableService', ['$http', '$q', function ($http, $q) {
    return {
      deleteTable: function (tableObj) {
        var defer = $q.defer();

        // $http.delete('http://localhost:8080/vote-tables/' + tableObj.tableID, tableObj)
        $http(
					{
						url: 'http://localhost:8080/vote-tables/' + tableObj.tableID, 
            // dataType: 'json',
						method: 'DELETE',
						data: tableObj,
            headers: {
              "Content-Type": "application/json"
            }
					})		
          .then(
          function (succesRes) {
            console.log(succesRes);
            // resolving the promise does the job
            defer.resolve(succesRes.data);
            var tbl = succesRes.data;
            console.log(succesRes.data);
            alert('table deleted!');
          },
          function (failedData) {
            // resolving the promise does the job
            defer.resolve(failedData.data);
            console.log(failedData);
            alert("deletion of table failed!" + failedData.data.message);
          }
          )

        return defer.promise;
      }
    }
  }]);