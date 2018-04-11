angular.module('appChangeUserData',[]).controller('controllerChangeData', function changeUserData($scope, $http) {
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
});
