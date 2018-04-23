
angular.module('controllerModule',['ngSanitize','duScroll']);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: '/navbar/navbar-template.html'
        };
    });
angular.module('controllerModule')
    .controller('myParameterController', function requestFunc($scope, $http, $window,  $location, $anchorScroll, $document, $timeout) {

        $scope.saveRoute=function (record) {
            $http({
                method: 'POST',
                url: 'api/routes/saveroutes',
                data: record,
                headers: {'Content-Type': 'application/json'}
            }).then(
                function success(response, status) {
                    console.log('Route had been saved', status, response);
                    $scope.saved=function(value) {
                        if (value === record.idRouteForView)
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
            $scope.submitted = true;

            if (!$scope.myForm.$valid) {
                $event.preventDefault();
            }
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
                    numberOfAdults: angular.element($('#adults-number')).val(),
                    numberOfChildren: angular.element($('#children-number')).val(),
                    numberOfInfants: angular.element($('#infants-number')).val()
                }
            }).then(
                function success(response) {
                    $scope.records = response.data;
                    $scope.$emit('UNLOAD');
                    $scope.loaded=true;
                    initMap();
                },
                function error(response, status) {
                    console.error('Repos error', status, response);
                    $scope.$emit('UNLOAD');
                    alert("Something goes wrong :(");
                }).finally(function () {
                $scope.goToLoaded();
            });
        };

        /*autoscroll */
        $scope.goToLoaded = function() {
            // set the location.hash to the id of the element you wish to scroll to
            // works fine, not animated
            // $timeout(function (){
            //     $location.hash('scroll-to');
            //     $anchorScroll();
            //     $location.hash('');
            //     $location.replace();
            // });

            // animated scroll
            $timeout(function(){
                $document.duScrollToElementAnimated(angular.element(document.getElementById('scroll-to')));
            });
        };

        /* form validation */
        $scope.checkIfEnterKeyWasPressed = function(event) {
            // var keyCode = $event.which || $event.keyCode;
            if (event.charCode === 13) {
                // Do that thing you finally wanted to do
                event.preventDefault(); // Doesn't work at all
                window.stop(); // Works in all browsers but IE...
                document.execCommand('Stop'); // Works in IE
                return false; // Don't even know why it's here. Does nothing.
            }
        };
        $scope.showMessage = function(input) {
            var show = input.$invalid && (input.$dirty || input.$touched || input.$untouched);
            return show;
        };
        $scope.testHiddenDataFrom = function () {
            $timeout(function () {
                $scope.inpFrom = $('#inputFromHidden').val();
                $scope.cityFrom = $('#inputFrom').val();
            });
            return $scope.inpFrom==='' && $scope.cityFrom!=='';
        };
        $scope.testHiddenDataTo = function () {
            $timeout(function () {
                $scope.inpTo = $('#inputToHidden').val();
                $scope.cityTo = $('#inputTo').val();
            });
            return $scope.inpTo==='' && $scope.cityTo!=='';
        };
        $scope.bindingCalendar = function() {
            $scope.dateFrom = $('#inputDate').val(); ///?
        };
        $scope.showCalendarMessage = function (input) {
            return input.$invalid && (input.$untouched || input.$dirty);
        };

        /* filters */
        $scope.optimalRoutes = function(records) {
            return records.optimalRoute;
        };
        $scope.allRoutes = function(records) {
            return records;
        };
        //для того, чтобы изначально чекбоксы были выбраны
        //ng-checked="true" работает не так как нужно
        //checked не работает с ng-model
        $scope.checkboxPlane = true;
        $scope.checkboxBus = true;
        $scope.checkboxTrain = true;
        //переменные, содержащие текущие значения чекбоксов
        var planeCheckboxValue = true;
        var busCheckboxValue = true;
        var trainCheckboxValue = true;
        //функции, которые меняют значения чекбоксов
        $scope.changePlaneCheckbox = function () {
            planeCheckboxValue = !planeCheckboxValue;
        };
        $scope.changeTrainCheckbox = function () {
            trainCheckboxValue = !trainCheckboxValue;
        };
        $scope.changeBusCheckbox = function () {
            busCheckboxValue = !busCheckboxValue;
        };
        //фильтр по транспорту
        $scope.transportTypeFilter = function (records) {
            for (var i = 0; i < records.edges.length; i++){
                if (!planeCheckboxValue){
                    if (records.edges[i].transportType === "plane"){
                        return false;
                    }
                }
                if (!busCheckboxValue){
                    if (records.edges[i].transportType === "bus"){
                        return false;
                    }
                }
                if (!trainCheckboxValue){
                    if (records.edges[i].transportType === "train" || records.edges[i].transportType === "suburban"){
                        return false;
                    }
                }
            }
            return true;
        };

        $scope.openLink = function(purchaseLink) {
            if (purchaseLink === null){
                alert("Нет ссылки на покупку")
            } else $window.open(purchaseLink);
            return false;
        };

        $scope.swapFromTo = function () {
            var tempCity = $('#inputFrom').val();
            var tempInp = $('#inputFromHidden').val();
            var tempLatLong = $('#latit_longit_from').val();

            $scope.cityFrom = $('#inputTo').val();
            $scope.cityTo = tempCity;
            $scope.inpFrom = $('#inputToHidden').val();;
            $scope.inpTo = tempInp;
            $scope.latLongFrom = $('#latit_longit_to').val();
            $scope.latLongTo = tempLatLong;
        }

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
                        var startDateA = a.edges[0].startDate.replace(/:/g,'').slice(11, 17);
                        var startDateB = b.edges[0].startDate.replace(/:/g,'').slice(11, 17);

                        return startDateA - startDateB
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
