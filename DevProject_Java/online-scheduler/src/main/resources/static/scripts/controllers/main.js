'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('MainCtrl', ['$scope', 'JobScheduleService', 'UserService', function ($scope, JobScheduleService, UserService) {
    // $scope.currentLoggedUser = "tahsin";
    $scope.currentUserJobs = [];
  
    $scope.currentLoggedUser = UserService.getLoggedUserName();

    $scope.login = function(){
      if ($scope.userName == undefined || $scope.password == undefined){
        alert("Fill login form!");
        return;
      }
      UserService.login($scope.userName, $scope.password )
        .then(
          function(data){
            console.log('login promise returned');
            if (data.status != undefined && data.status != 200){
              alert("User Login failed! " + data.message);
            }
            else{
              // get current user
              $scope.currentLoggedUser = UserService.getLoggedUserName();
              window.location.reload();
            }

          }
        )
    }

    $scope.logout = function(){
      UserService.logout();
      $scope.currentLoggedUser = undefined;
      $scope.currentUserJobs = [];
      $scope.currentUserJobSchedules = [];
      // window.location.reload();
    }

    $scope.createUser = function(){
      if ($scope.newUserName == undefined || $scope.newPassword == undefined || $scope.newRepassword == undefined){
        alert("Fill the form!");
        return;
      }

      if ($scope.newPassword != $scope.newRepassword){
        alert("Passwords not matched!");
        return;                
      }

      UserService.createUser($scope.newUserName, $scope.newPassword)
        .then(
          function(data){
            console.log("create user promise returned!");
            console.log(data);
            if (data.status != undefined && data.status != 200){
              alert("User creation failed! " + data.message);
            }
            else{
              alert("User created!");
              window.location.reload();
            }

          }
        )
    };

    $scope.getUserJobs = function(){
      JobScheduleService.getAllJobsByUserName(UserService.getLoggedUserName())
        .then(
          function(data){
            console.log("jobs received!");
            $scope.currentUserJobs = data;
          }
        )
    }

    // load jobs
    $scope.getUserJobs();

    $scope.currentUserJobSchedules = [];
    $scope.getAllUserJobSchedules = function(){
        JobScheduleService.getJobScheduleByUserName(UserService.getLoggedUserName())
          .then(
            function(data){
              $scope.currentUserJobSchedules = data;
            }
          );
      };
      // get all user job schedules
      $scope.getAllUserJobSchedules();

      $scope.removeJobSchedule = function(data){
        // alert(data.id);
        JobScheduleService.deleteJobScheduleById(data.id)
          .then(
            function(res){
              console.log(res);
              // window.location.reload();
              // reload user's all job schedules.
              $scope.getAllUserJobSchedules();
            }
          )
      }

      $scope.removeJob = function(data){
        // alert(data.id);
        console.log("data.id : " + data.id);
        JobScheduleService.deleteJobById(data.id)
          .then(
            function(res){
              console.log(res);
              // window.location.reload();
              // reload user's all job schedules.
              $scope.getUserJobs();
            }
          )
      };

      $scope.jobScheduleLogs = [];
      $scope.getJobScheduleLog = function(data){
        JobScheduleService.getJobScheduleLogsByTaskId(data.id)
          .then(
            function(res){
              console.log(res);
              // $scope.jobScheduleLogs = res;
              // convert epoc time to date
              // for (var i in $scope.jobScheduleLogs){
              //   $scope.jobScheduleLogs.executionDate = new Date(1482950220001);
              // }
              for (var i in res){
                res[i].executionDate = new Date(res[i].executionDate);
              }
              $scope.jobScheduleLogs = res;
            }
          )
      }

      $scope.changeJobScheduleStatus = function(data){
        JobScheduleService.changeJobScheduleById(data.id, !data.active)
          .then(
            function(res){
              console.log(res);
              // reload user's all job schedules.
              $scope.getAllUserJobSchedules();
          })
      }

      $scope.setSelectedJobScheduleForDetails = function(data){
        $scope.selectedJobScheduleForDetails = data;
        console.log(data);
      }

     $scope.setSelectedJobForDetails = function(data){
        $scope.selectedJobForDetails = data;
        console.log(data);
      }
      
  }]);
