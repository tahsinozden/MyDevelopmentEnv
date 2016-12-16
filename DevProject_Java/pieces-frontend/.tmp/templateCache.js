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
