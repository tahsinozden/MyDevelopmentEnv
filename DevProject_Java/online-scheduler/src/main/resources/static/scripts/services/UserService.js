'use strict';


angular.module('mainApp')
    .service('UserService', ['$http', '$q', '$cookies', function ($http, $q, $cookies) {
        return {
            login : function(username, pass) {
                var defer = $q.defer();
                $http.post('/user/login', {userName: username, password: pass})
                    .then(
                            function(success) {
                                defer.resolve(success.data);
                                var token = success.data.token;
                                // ste bearer header for JWT
                                $http.defaults.headers.common.Authorization = 'Bearer ' + token;

                                // save token came from server
                                // sessionStorage.setItem('token', token);
			                    // sessionStorage.setItem('currentUserName', username);
                                $cookies.put('token', token);
                                $cookies.put('currentUserName', username);
                            },
                            function(error){
                                defer.resolve(error.data);
                                console.log('Login Failure! ' + error.data.message);
                            }
                        );

                return defer.promise;
            },
            
            logout: function(){
                $http.defaults.headers.common.Authorization = '';
                // sessionStorage.removeItem('token');
			    // sessionStorage.removeItem('currentUserName');   
                $cookies.remove('token');
                $cookies.remove('currentUserName');
                // console.log("Reomved " + $cookies.get('token'));
            },

            getLoggedUserName: function(){
                // var token = sessionStorage.getItem('token');
			    // var user = sessionStorage.getItem('currentUserName');

                var token = $cookies.get('token');
			    var user = $cookies.get('currentUserName');

                if (token != undefined && user != undefined){
                    return user;
                }
                
                return undefined;
            },

            createUser : function(username, pass) {
                var defer = $q.defer();
                $http.post('/user/create-user', {userName: username, password: pass})
                    .then(
                            function(success) {
                                defer.resolve(success.data);
                                var token = success.data.token;
                                // ste bearer header for JWT
                                $http.defaults.headers.common.Authorization = 'Bearer ' + token;

                            },
                            function(error){
                                defer.resolve(error.data);
                                console.log('Creation of user Failure! ' + error.data.message);
                            }
                        );

                return defer.promise;
            }

        }
    }]);
