'use strict';

angular.module('mainApp')
    .service('JobScheduleService', ['$http', '$q', function ($http, $q) {
        return {
            createJob: function (jobObj) {
                var defer = $q.defer();

                $http.post('/api/job/' + jobObj.name, jobObj)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },
            deleteJobById: function (jobId) {
                var defer = $q.defer();
                console.log("job id: " + jobId);
                $http.delete('/api/job/' + jobId)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            getJobById: function (jobObj) {
                var defer = $q.defer();

                $http.get('/api/job/' + jobObj.id, jobObj)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            getAllJobsByUserName: function (creatorUserName) {
                var defer = $q.defer();

                $http.get('/api/job/byuser/' + creatorUserName)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            createJobSchedule: function (jobSchedObj) {
                var defer = $q.defer();

                $http.post('/api/schedule/' + jobSchedObj.jobId, jobSchedObj)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            deleteJobScheduleById: function (jobSchedId) {
                var defer = $q.defer();

                $http.delete('/api/schedule/' + jobSchedId)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            getJobScheduleById: function (jobSchedId) {
                var defer = $q.defer();

                $http.get('/api/schedule/' + jobSchedId)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            getJobScheduleByUserName: function (userName) {
                var defer = $q.defer();

                $http.get('/api/schedule/byuser/' + userName)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            changeJobScheduleById: function (jobSchedId, status) {
                var defer = $q.defer();

                 $http(
                    {
                        url: '/api/schedule/' + jobSchedId,
                        // dataType: 'json',
                        method: 'PUT',
                        // data: tableObj,
                        params: {'status': status},
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        }
                    })
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },
    

            getJobScheduleLogsByUserName: function (userName) {
                var defer = $q.defer();

                $http.get('/api/log/byuser/' + userName)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            },

            getJobScheduleLogsByTaskId: function (taskId) {
                var defer = $q.defer();

                $http.get('/api/log/' + taskId)
                    .then(
                    function (succesRes) {
                        console.log(succesRes);
                        // resolving the promise does the job
                        defer.resolve(succesRes.data);
                        // localStorage.setItem('currentjobObject', JSON.stringify(succesRes.data));
                        console.log(succesRes.data);
                    },
                    function (failedData) {
                        // resolving the promise does the job
                        defer.resolve(failedData.data);
                        console.log(failedData);
                    }
                    )

                return defer.promise;
            }


        }
    }]);
