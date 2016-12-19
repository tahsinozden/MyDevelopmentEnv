'use strict';

/**
 * @ngdoc function
 * @name mainApp.factory:TableItem
 * @description
 * # TableItem
 * Factory of the mainApp
 */
angular.module('mainApp')
  .factory('TableItem', function () {
      function TableItem(itemID, voteTableID, itemName, itemScore){
          this.itemID = itemID;
          this.voteTableID = voteTableID;
          this.itemName = itemName;
          this.itemScore = itemScore;
      };
      
      return TableItem;
  });
