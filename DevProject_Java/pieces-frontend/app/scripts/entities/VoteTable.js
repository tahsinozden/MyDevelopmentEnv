'use strict';

/**
 * @ngdoc function
 * @name mainApp.factory:VoteTable
 * @description
 * # VoteTable
 * Factory of the mainApp
 */
angular.module('mainApp')
  .factory('VoteTable', function () {
      function VoteTable(tableID, voteTableName, creationDate, expiryDate, authKey, tableURL){
          this.tableID = tableID;
          this.voteTableName = voteTableName;
          this.creationDate = creationDate;
          this.expiryDate = expiryDate;
          this.authKey = authKey;
          this.tableURL = tableURL;
      };

      //function VoteTable(){};

      return VoteTable;
  });
