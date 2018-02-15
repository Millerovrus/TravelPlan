

angular.module('controllerModule',[]);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: 'navbar-template.html'
        };
    });
angular.module('controllerModule')
    .controller("myParameterController", function requestFunc($scope, $http) {
        $scope.loaded=false;
        $scope.sendRequestParameters=function () {
            $http({
                method: 'GET',
                url: 'api/rest/get-routes/',
                 params: {
                     from: angular.element($('#inputFrom')).val(),
                     to: angular.element($('#inputTo')).val()
                  //   date: angular.element($('#inputDate')).val()
                }
            }).then(
                function success(response) {
                    $scope.records = response.data;
                    $scope.loaded=true;
                },
                function error(response, status) {
                    console.error('Repos error', status, response);
                });
        }
    });

