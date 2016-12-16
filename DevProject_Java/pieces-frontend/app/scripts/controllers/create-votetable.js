'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:MyrouteCtrl
 * @description
 * # MyrouteCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('createVoteTableCtrl', ["$scope", "tableService", function ($scope, tableService) {
    // .controller('createVoteTableCtrl', ["$scope", 'tableService', function ($scope) {
    $scope.tableItems = [];
    
    $scope.addItemToTheTable = function(){
      console.log($scope.itemText);
      if ($scope.itemText != null && $scope.itemText != ''){
        $scope.tableItems.push($scope.itemText);
        $scope.itemText = '';
      }
    };

    $scope.submitCreateTable = function(){
      // if (isNaN($scope.voteTableStartDate.getTime()) || isNaN($scope.voteTableEndDate.getTime())){
      if ($scope.voteTableStartDate == undefined || $scope.voteTableEndDate == undefined){        
        alert('choose dates!');
        return;
      }

      if ($scope.chosenTableName == undefined){
            alert('input a table name!');
            return;
      }


      console.log($scope.voteTableStartDate);
      console.log($scope.voteTableEndDate);
      console.log($scope.chosenTableName);

      var tblObj = {
        'voteTableName': $scope.chosenTableName,
        // 'creationDate': '2016-01-01',
        // 'expiryDate': '2017-01-01'
        'creationDate': $scope.voteTableStartDate,
        'expiryDate': $scope.voteTableEndDate
      };

      tableService.createTable(tblObj);
    }
  }])

  .service('tableService',[ '$http', function( $http){
      return {
        createTable: function(tableObj){
          $http.post('http://localhost:8080/vote-tables/'+tableObj.voteTableName, tableObj)
          .then(
            function(succesData){
              console.log(succesData);
            },
            function(failedData){
              console.log(failedData);
            }
          )
        }
      }

  }]);
