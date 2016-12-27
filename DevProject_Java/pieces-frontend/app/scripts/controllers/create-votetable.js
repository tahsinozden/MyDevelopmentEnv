'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:MyrouteCtrl
 * @description
 * # MyrouteCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('createVoteTableCtrl', ["$scope", "tableService", 'VoteTable', 'TableItem', function ($scope, tableService, VoteTable, TableItem) {
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

      // var tblObj = {
      //   'voteTableName': $scope.chosenTableName,
      //   // 'creationDate': '2016-01-01',
      //   // 'expiryDate': '2017-01-01'
      //   'creationDate': $scope.voteTableStartDate,
      //   'expiryDate': $scope.voteTableEndDate
      // };


      var tblObj = new VoteTable('', $scope.chosenTableName, $scope.voteTableStartDate, $scope.voteTableEndDate, '', '');
      var tableItemsInTable = [];

      var promise = tableService.createTable(tblObj);
      // send table items after creation of table request
      promise.then(function(){
        console.log('response received from promise!');
        if (localStorage.getItem('currentTableID') == undefined){
          console.log('currentTableID is null!');
          return;
        }
        
        $scope.currentTableID = localStorage.getItem('currentTableID');
        $scope.currentTableObject = JSON.parse(localStorage.getItem('currentTableObject'));
        console.log("current table object");
        console.log($scope.currentTableObject);
        // build table items for the request
        for (var i in $scope.tableItems){
          tableItemsInTable.push(new TableItem('', $scope.currentTableID, $scope.tableItems[i], '0'));
        }

        // add vote items to the table
        tableService.addItemsToTable(tableItemsInTable);

        });
    }
  }])

  .service('tableService',[ '$http', '$q', function($http, $q){
      return {
        createTable: function(tableObj){
          var defer = $q.defer();

          $http.post('http://localhost:8080/vote-tables/' + tableObj.voteTableName, tableObj)
          .then(
            function(succesRes){
              console.log(succesRes);
              // resolving the promise does the job
               defer.resolve('data received successfully!');
              var tbl = succesRes.data;
              localStorage.setItem('currentTableID', succesRes.data.tableID);
              localStorage.setItem('currentTableObject', JSON.stringify(succesRes.data));  
              console.log( succesRes.data);            
              alert('TableID: ' + tbl.tableID + ", authKey: " + tbl.authKey);
            },
            function(failedData){
              // resolving the promise does the job
              defer.resolve('data received with errors!');
              console.log(failedData);
              alert("creation of table failed!" + failedData.data);
            }
          )

          return defer.promise;
        },

        addItemsToTable: function(tableItems){
          var tableID = undefined;
          if ( !(tableItems instanceof Array) || tableItems.length <= 0){
            console.log('tableItems is not an array or it is empty!');
            return;
          }
          
          tableID = tableItems[0].voteTableID;
          $http.put('http://localhost:8080/vote-tables/' + tableID, tableItems)
            .then(
              function(success){
                console.log("items added successfully" + success.data);
              },
              function(error){
                console.log("items couldnt be added!" + error.data);
              }
            )
        }
      }

  }]);
