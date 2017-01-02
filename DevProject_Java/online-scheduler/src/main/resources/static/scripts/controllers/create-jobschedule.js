'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:CreateJobscheduleCtrl
 * @description
 * # CreateJobscheduleCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('CreateJobscheduleCtrl', ['$scope', 'JobScheduleService', 'JobSchedule', 'UserService', function ($scope, JobScheduleService, JobSchedule, UserService) {
      $scope.items = [
        {'label': 'tahsin'},
        {'label': 'tahsin'},
        {'label': 'tahsin'}
      ]

      $scope.currentUserName = "tahsin";
      $scope.userJobs = [];

      JobScheduleService.getAllJobsByUserName(UserService.getLoggedUserName())
        .then(
          function(data){
            console.log("user jobs promise returned.");
            $scope.userJobs = data;
          }
        );

      $scope.createJobScheduleSubmit = function(){
        console.log($scope.selectedJob);
        if ($scope.selectedJob == undefined || $scope.selectedJob == null){
          alert('select a job id!!!');
          return;
        }

        if ($scope.selectedName == undefined || $scope.selectedName == null){
          alert('Type a name!!!');
          return;
        }

        if ($scope.cronString == undefined || $scope.cronString == ""){
          alert('Type a CRON expression!!!');
          return;
        }

        console.log($scope.selectedStartDate);
        console.log($scope.selectedEndDate);

        if ( $scope.selectedStartDate == undefined || $scope.selectedEndDate == undefined){
          alert("Check date values, ensure that you also set the time!");
          return;
        }

        var schedule = new JobSchedule('', $scope.selectedJob.id, $scope.selectedName, UserService.getLoggedUserName(), $scope.selectedStartDate, $scope.selectedEndDate, 0, 0, 1, $scope.cronString);
        JobScheduleService.createJobSchedule(schedule)
          .then(
            function(data){
              console.log('data received : ' + JSON.stringify(data));
              alert("Job Schedule is created. You can see it in HOME section.");
            }
          )
      }
      
  }]);
