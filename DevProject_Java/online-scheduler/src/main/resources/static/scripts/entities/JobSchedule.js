'use strict';

angular.module('mainApp')
  .factory('JobSchedule', function () {
      function JobSchedule(id, jobId, name, creatorUserName, startDate, endDate, recurring, recurringPeriod, active, cronExpression){
          this.id = id;
          this.jobId = jobId;
          this.name = name;
          this.creatorUserName = creatorUserName;
          this.startDate = startDate;
          this.endDate = endDate;
          this.recurring = recurring;
          this.recurringPeriod = recurringPeriod;
          this.active = active;
          this.cronExpression = cronExpression;          
      };
      
      return JobSchedule;
  });
