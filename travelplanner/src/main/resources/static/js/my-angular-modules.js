
angular.module('controllerModule',['ngSanitize']);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: '/navbar/navbar-template.html'
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
                   /*passengers: angular.element($('#demo2')).val()*/
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
            initMap();
        };

    })
    .filter('secondsToTime', [function() {
        return function(seconds) {
            var days = Math.floor((seconds / 86400));
            var hours = Math.floor((seconds % 86400) / 3600);
            var mins = Math.floor(((seconds % 86400) % 3600) / 60);
            if (days !== 0) {
                return (days) + ' days ' + ('0'+hours).slice(-2) + ' hours ' + ('0'+mins).slice(-2) + ' minutes';
            } else if (hours !== 0) {
                return ('0' + hours).slice(-2) + ' hours ' + ('0' + mins).slice(-2) + ' minutes';
            }
            return ('0' + mins).slice(-2) + ' minutes';
        };
    }])
    .filter('transportTypeToIcon', [function () {
        return function (transportType) {
            switch(transportType) {
                case "bus":
                    return '<i class="fa fa-bus"></i>';
                case "plane":
                    return '<i class="fa fa-plane"></i>';
                case "train":
                    return '<i class="fa fa-train"></i>';
                default:
                    return 'some other type: ' + transportType;
            }
        };
    }])
    .controller('appController', function ($scope) {
        $scope.$on('LOAD', function () { $scope.loading = true });
        $scope.$on('UNLOAD', function () { $scope.loading = false });
    })
    .controller('mapController', function ($scope) {
        $scope.setMap = function (id) {
           // setMap(id);
            $('.panel').on('hidden.bs.collapse', function () {
                resetMap();
            });
            $('.panel').on('shown.bs.collapse', function () {
                setMap(id);
            });

        };
        $scope.resetMap = function () {
            resetMap();
        };

    });

