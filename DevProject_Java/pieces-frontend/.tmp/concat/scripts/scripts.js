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
  .config(["$routeProvider", "$locationProvider", function ($routeProvider, $locationProvider) {
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
      .otherwise({
        redirectTo: '/'
      });
  }]);

'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('MainCtrl', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('AboutCtrl', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:MyrouteCtrl
 * @description
 * # MyrouteCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('createVoteTableCtrl', ["$scope", "tableService", function ($scope, tableService) {
    // .controller('createVoteTableCtrl', ["$scope", 'tableService', function ($scope) {
    $scope.tableItems = [];
    
    $scope.addItemToTheTable = function(){
      console.log($scope.itemText);
      if ($scope.itemText != null && $scope.itemText != ''){
        $scope.tableItems.push($scope.itemText);
        $scope.itemText = '';
      }
    };

    $scope.submitCreateTable = function(){
      // if (isNaN($scope.voteTableStartDate.getTime()) || isNaN($scope.voteTableEndDate.getTime())){
      if ($scope.voteTableStartDate == undefined || $scope.voteTableEndDate == undefined){        
        alert('choose dates!');
        return;
      }

      if ($scope.chosenTableName == undefined){
            alert('input a table name!');
            return;
      }


      console.log($scope.voteTableStartDate);
      console.log($scope.voteTableEndDate);
      console.log($scope.chosenTableName);

      var tblObj = {
        'voteTableName': $scope.chosenTableName,
        // 'creationDate': '2016-01-01',
        // 'expiryDate': '2017-01-01'
        'creationDate': $scope.voteTableStartDate,
        'expiryDate': $scope.voteTableEndDate
      };

      tableService.createTable(tblObj);
    }
  }])

  .service('tableService',[ '$http', function( $http){
      return {
        createTable: function(tableObj){
          $http.post('http://localhost:8080/vote-tables/'+tableObj.voteTableName, tableObj)
          .then(
            function(succesData){
              console.log(succesData);
            },
            function(failedData){
              console.log(failedData);
            }
          )
        }
      }

  }]);

'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:ContactCtrl
 * @description
 * # ContactCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('ContactCtrl', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

angular.module('mainApp').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('views/about.html',
    "<p>This is the about view.</p>"
  );


  $templateCache.put('views/contact.html',
    "<p>Tahsin was here!</p>"
  );


  $templateCache.put('views/create-votetable.html',
    "<ul class=\"list-group\"> <li class=\"list-group-item\"> <span class=\"badge\">14</span> Cras justo odio </li> </ul> <form> Start Date: <input type=\"date\" name=\"sDate\" ng-model=\"voteTableStartDate\" required> End Date: <input type=\"date\" name=\"eDate\" ng-model=\"voteTableEndDate\" required> </form> <!-- table name here--> <div class=\"input-group\"> <input type=\"text\" class=\"form-control\" aria-label=\"...\" placeholder=\"Table name here.\" ng-model=\"tableName\"> <div class=\"input-group-btn\"> <!-- Buttons --> <button type=\"button\" class=\"btn btn-default\" ng-click=\"chosenTableName = tableName; tableName = '';\">+</button> </div> </div> <!-- Table items here --> <div class=\"input-group\"> <input type=\"text\" class=\"form-control\" aria-label=\"...\" ng-model=\"itemText\"> <div class=\"input-group-btn\"> <!-- Buttons --> <button type=\"button\" class=\"btn btn-default\" ng-click=\"addItemToTheTable();\">+</button> </div> </div> <!-- table content will be filled here--> <div class=\"panel panel-default\"> <!-- Default panel contents --> <div class=\"panel-heading\"><h2>{{ chosenTableName }}</h2></div> <div class=\"panel-body\"> </div> <!-- List group --> <ul class=\"list-group\"> <li ng-repeat=\"item in tableItems track by $index\" class=\"list-group-item\">{{ item }}</li> </ul> </div> <button type=\"button\" class=\"btn btn-default\" ng-click=\"submitCreateTable();\">Create Table!</button>"
  );


  $templateCache.put('views/main.html',
    "<div class=\"jumbotron\"> <p class=\"lead\"> </p> </div> <div class=\"dropdown\"> <button class=\"btn btn-default dropdown-toggle\" type=\"button\" id=\"dropdownMenu1\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\"> Choose an action! <span class=\"caret\"></span> </button> <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu1\"> <li><a href=\"/create-votetable\">Create Vote Table</a></li> <li><a href=\"#\">Delete Vote Table</a></li> </ul> </div>"
  );

}]);
