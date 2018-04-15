angular.module('appChangeUserData',[])
    .controller('controllerChangeData', function changeUserData($scope, $http, $window) {
    $scope.change = function () {
        $http({
            method: 'POST',
            url: 'api/users/changeUserData',
            /*$.param преобразование данных в строку с кодировкой URL*/
            data: $.param({
                firstname: angular.element($('#first-name')).val(),
                lastname: angular.element($('#last-name')).val(),
                birthdate: angular.element($('#birth-date')).val(),
                avatar: angular.element($('#avatar')).val()
            }),
            /*заголовок передаваемого объекта*/
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(
            function success(response) {
                console.log(response.data);
                location.reload();
            },
            function error(response, status) {
                console.error(status, response);
                alert("Changes has not been saved, Error! :(");
            });
    };
    $scope.printUserRoutes=function () {
        $scope.loaded=false;
        $http({
            method: 'GET',
            url: 'api/routes/findbyuserid',
            params: {
                user: angular.element($('#user_id')).val()
            }
        }).then(
            function success(response) {
                $scope.records = response.data;
                $scope.loaded=true;
            },
            function error(response, status) {
                console.error('Repos error', status, response);
                alert("Something goes wrong :(");
            });
    };

        $scope.openLink = function(purchaseLink) {
            if (purchaseLink === null){
                alert("Нет ссылки на покупку")
            } else $window.open(purchaseLink);
        };
})
    .filter('getMonthNumber',[function () {
        return function (stringDate) {
            var month;
            if(stringDate.charAt(5)=='0'){
                month = stringDate.charAt(6);
            }  else{
                month = stringDate.charAt(5)+stringDate.charAt(6);
            }
            return parseInt(month);
        }
    }])
    .filter('orderObjectBy', function(){
        return function(input, attribute) {
            if (!angular.isObject(input)) return input;

            var array = [];
            for(var objectKey in input) {
                array.push(input[objectKey]);
            }

            if (attribute === "startDate"){
                array.sort(function(a, b) {
                    var startDateA = a.edges[0].startDate.replace(/-/g, "").replace(/:/g, "").replace(" ", "");
                    var startDateB = b.edges[0].startDate.replace(/-/g, "").replace(/:/g, "").replace(" ", "");

                    return startDateA - startDateB
                });
            }

            return array;
        }
    })
    .filter('getMonthValue',[function () {
        return function (monthNumber) {
            var monthNames = [ 'JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN',
                'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC' ];
            return monthNames[monthNumber - 1];
        }
    }])
    .filter('getDayNumber',[function () {
        return function (stringDate) {
            var day = stringDate.charAt(8)+stringDate.charAt(9);
            return  parseInt(day);
        }
    }])
    .filter('getTimeFromDate',[function () {
        return function (stringDate) {
            var i;
            var time='';
            for(i=11; i<stringDate.length; i++){
                time+=stringDate.charAt(i);
            }
            return time;
        }
    }])
;
