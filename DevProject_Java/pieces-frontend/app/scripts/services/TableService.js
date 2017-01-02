'use strict';

/**
 * @ngdoc function
 * @name mainApp.service:TableService
 * @description
 * # TableService
 * Service of the mainApp
 */
angular.module('mainApp')
    .service('tableService', ['$http', '$q', function ($http, $q) {
        return {
            createTable: function (tableObj) {
                var defer = $q.defer();

                $http.post('http://localhost:8080/vote-tables/' + tableObj.voteTableName, tableObj)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve('data received successfully!');
                        var tbl = succesRes.data;
                        localStorage.setItem('currentTableID', succesRes.data.tableID);
                        localStorage.setItem('currentTableObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                        alert('TableID: ' + tbl.tableID + ", authKey: " + tbl.authKey);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve('data received with errors!');
                        console.log(failedData);
                        alert("creation of table failed!" + failedData.data);
                    }
                    )

                return defer.promise;
            },

            addItemsToTable: function (tableItems) {
                var tableID = undefined;
                if (!(tableItems instanceof Array) || tableItems.length <= 0) {
                    console.log('tableItems is not an array or it is empty!');
                    return;
                }

                tableID = tableItems[0].voteTableID;
                $http.put('http://localhost:8080/vote-tables/' + tableID, tableItems)
                    .then(
                    function (success) {
                        console.log("items added successfully" + success.data);
                    },
                    function (error) {
                        console.log("items couldnt be added!" + error.data);
                    }
                    )
            },
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
