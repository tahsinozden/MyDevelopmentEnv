'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:CreateJobCtrl
 * @description
 * # CreateJobCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('CreateJobCtrl', ['$scope', 'JobScheduleService', 'Job', 'UserService', function ($scope, JobScheduleService, Job, UserService) {
      
      $scope.createJobSubmit = function(){
          if ($scope.jobName == undefined || $scope.jobName == null){
            alert('input a valid job name');
            return;
          }

          if ($scope.jobCommand == undefined || $scope.jobCommand == null){
            alert('input a job command!');
            return;
          }

          var job = new Job('', $scope.jobName, $scope.jobDesc, $scope.jobCommand, UserService.getLoggedUserName());
          JobScheduleService.createJob(job)
            .then(
              function(){
                console.log('inside the promise');
                alert("Job created! You can see it in HOME section.");
              }
            );

      };
  }]);
