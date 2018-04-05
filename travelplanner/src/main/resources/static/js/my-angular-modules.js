
angular.module('controllerModule',['ngSanitize']);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: '/navbar/navbar-template.html'
        };
    });
angular.module('controllerModule')

    .controller('myParameterController', function requestFunc($scope, $http, $window) {

        /*$scope.saved=function(value) {
            return true
        };*/
        // $scope.saved = true;
        $scope.saveRoute=function (record) {
            
            $http({
                /*method: 'GET',
                url: 'api/routes/saveroutes',
                params: {
                    startpoint: record.startPoint,
                    destinationpoint: record.destinationPoint,
                    cost: record.cost,
                    duration: record.duration,
                    idrouteforview: record.idRouteForView
                }*/
                method: 'POST',
                url: 'api/routes/saveroutes',
                data: record,
                headers: {'Content-Type': 'application/json'}
            }).then(
                function success(response, status) {
                    console.log('Route had been saved', status, response);
                    $scope.saved=function(value) {
                        if (value == record.idRouteForView)
                            return true;
                    };
                    alert("Route had been saved :)");

                },
                function error(response, status) {
                    console.error('error', status, response);
                    alert("Routes hadn't been save :(");
                });
        };

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
                     date: angular.element($('#inputDate')).val(),
                     numberOfPassengers: angular.element($('#spin-passengers')).val()
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
        $scope.optimalRoutes = function(records) {
            return records.optimalRoute;
        };
        $scope.allRoutes = function(records) {
            return records;
        };
        $scope.routesWithoutBus = function (records) {
            for (var i = 0; i < records.edges.length; i++){
                if (records.edges[i].transportType === "bus"){
                    return false;
                }
            }
            return true;
        };
        $scope.routesWithCostFromTo = function (records) {
            var from = parseInt(document.getElementById('cost_from').value);
            var to = parseInt(document.getElementById('cost_to').value);
            return (records.cost > from && records.cost < to)
        };

        $scope.openLink = function(purchaseLink) {
            if (purchaseLink === null){
                alert("Нет ссылки на покупку")
            } else $window.open(purchaseLink);
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
                case "suburban":
                case "train":
                    return '<i class="fa fa-train"></i>';
                default:
                    return '- ' + transportType + ' -';
            }
        };
    }])
    .filter('localDateTimeToString', [function () {
        return function (dateTime) {
            return dateTime.year + '-'
                + ('0' + dateTime.monthValue).slice(-2) + '-'
                + ('0' + dateTime.dayOfMonth).slice(-2) + ' at '
                + ('0' + dateTime.hour).slice(-2) + ':'
                + ('0' + dateTime.minute).slice(-2);
        }
    }])
    .filter('orderObjectBy', function(){
        return function(input, attribute) {
            if (!angular.isObject(input)) return input;

            var array = [];
            for(var objectKey in input) {
                array.push(input[objectKey]);
            }

            switch (attribute){
                case 'startDate':
                    array.sort(function(a, b) {
                        if ((a.edges[0].startDate.hour * 60 + a.edges[0].startDate.minute) > (b.edges[0].startDate.hour * 60 + b.edges[0].startDate.minute)){
                            return 1;
                        }
                        if ((a.edges[0].startDate.hour * 60 + a.edges[0].startDate.minute) < (b.edges[0].startDate.hour * 60 + b.edges[0].startDate.minute)){
                            return -1;
                        }
                        return 0;
                    });
                    break;
                case 'transfers':
                    array.sort(function(a, b) {
                        var transfersA = 0;
                        var transfersB = 0;
                        for (var i = 0; i < a.edges.length; i++) {
                            transfersA += a.edges[i].numberOfTransfers;
                        }
                        for (var j = 0; j < b.edges.length; j++) {
                            transfersB += b.edges[j].numberOfTransfers;
                        }
                        return transfersA - transfersB;
                    });
                    break;
                default:
                    array.sort(function (a, b) {
                        a = parseInt(a[attribute]);
                        b = parseInt(b[attribute]);
                        return a - b;
                    });

            }
            return array;
        }
    })
    .controller('appController', function ($scope) {
        $scope.$on('LOAD', function () { $scope.loading = true });
        $scope.$on('UNLOAD', function () { $scope.loading = false });
    })
    .controller('mapController', function ($scope) {
        $scope.setMap = function (record) {

                resetMap();

            $('.panel').on('shown.bs.collapse', function () {
                setMap(record);
            });
        };
    });

