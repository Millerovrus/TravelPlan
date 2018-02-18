

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
            $scope.$emit('LOAD')
            $scope.loaded=false;
            $http({
                method: 'GET',
                url: 'api/rest/get-routes/date/',
                 params: {
                     from: angular.element($('#inputFrom')).val(),
                     to: angular.element($('#inputTo')).val(),
                     date: angular.element($('#inputDate')).val()
                }
            }).then(
                function success(response) {
                    $scope.records = response.data;
                    $scope.loaded=true;
                    $scope.$emit('UNLOAD')
                },
                function error(response, status) {
                    console.error('Repos error', status, response);
                    $scope.$emit('UNLOAD')
                });
        }
    })
    .controller('appController', function ($scope) {
        $scope.$on('LOAD', function () { $scope.loading = true });
        $scope.$on('UNLOAD', function () { $scope.loading = false });
    });

