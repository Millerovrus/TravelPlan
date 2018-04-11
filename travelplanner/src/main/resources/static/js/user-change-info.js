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
                alert("Changes has been saved! Please update the page!");
            },
            function error(response, status) {
                console.error('Changes has not been saved, Error!!!', status, response);
                alert("Something goes wrong :(");
            });
    }
});
