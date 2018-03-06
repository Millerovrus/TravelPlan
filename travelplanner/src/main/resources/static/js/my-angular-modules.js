
angular.module('controllerModule',[]);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: 'navbar-template.html'
        };
    });
angular.module('controllerModule')
    .controller('myParameterController', function requestFunc($scope, $http) {

        $scope.sendRequestParameters=function () {
            $scope.$emit('LOAD');
            $scope.loaded=false;
            $http({
                method: 'GET',
                url: 'api/rest/get-routes/date/',
                 params: {
                     from: angular.element($('#inputFromHidden')).val(),
                     to: angular.element($('#inputToHidden')).val(),
                     longLatFrom: angular.element($('#latit_longit_from')).val(),
                     longLatTo: angular.element($('#latit_longit_to')).val(),
                     date: angular.element($('#inputDate')).val()
                }
            }).then(
                function success(response) {
                    $scope.records = response.data;
                    $scope.loaded=true;
                    $scope.$emit('UNLOAD');
                },
                function error(response, status) {
                    console.error('Repos error', status, response);
                    $scope.$emit('UNLOAD');
                    alert("Something goes wrong :(");
                });
        };

    })
    .filter('secondsToTime', [function() {
        return function(seconds) {
            var hours = Math.floor((seconds % 86400) / 3600);
            var mins = Math.floor(((seconds % 86400) % 3600) / 60);
            return ('00'+hours).slice(-2) +' hours ' + ('00'+mins).slice(-2)+' minutes';
        };
    }])
    .filter('outputJson', [function () {
        return function (records) {
            var routes = records.length;
            return 'json length is '+routes;
        };
    }])
    .controller('appController', function ($scope) {
        $scope.$on('LOAD', function () { $scope.loading = true });
        $scope.$on('UNLOAD', function () { $scope.loading = false });
    });

