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