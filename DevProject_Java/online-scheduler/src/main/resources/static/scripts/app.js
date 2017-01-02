'use strict';

/**
 * @ngdoc overview
 * @name mainApp
 * @description
 * # mainApp
 *
 * Main module of the application.
 */
angular
  .module('mainApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider, $locationProvider) {
     // I added to make routing work
     $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/create-votetable', {
        templateUrl: 'views/create-votetable.html',
        controller: 'createVoteTableCtrl',
        controllerAs: 'createVoteTable'
      })
      .when('/contact', {
        templateUrl: 'views/contact.html',
        controller: 'ContactCtrl',
        controllerAs: 'contact'
      })
      .when('/delete-votetable', {
        templateUrl: 'views/delete-votetable.html',
        controller: 'DeleteVotetableCtrl',
        controllerAs: 'deleteVotetable'
      })
      .when('/create-job', {
        templateUrl: 'views/create-job.html',
        controller: 'CreateJobCtrl',
        controllerAs: 'createJob'
      })
      .when('/create-jobschedule', {
        templateUrl: 'views/create-jobschedule.html',
        controller: 'CreateJobscheduleCtrl',
        controllerAs: 'createJobschedule'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
