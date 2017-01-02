'use strict';

angular.module('mainApp')
  .factory('Job', function () {
      function Job(id, name, description, command, creatorUserName){
          this.id = id;
          this.name = name;
          this.description = description;
          this.command = command;
          this.creatorUserName = creatorUserName;
      };
      
      return Job;
  });
