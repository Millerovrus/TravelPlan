/*
var checkme = angular.element($('#checker22')).val();
var userName = angular.element($('#first-name')).val();
var userBirth = angular.element($('#birth-date')).val();
var userEmail = angular.element($('#user-email')).val();
var userLastname = angular.element($('#last-name')).val();
var fakeInput = angular.element($('#fake-input')).val();
var baseInput = angular.element($('#base-input')).val();
var editHeader = angular.element($('#edit-header')).val();
var UserSend = angular.element($('#submit-edi')).val();
*/
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
                alert("Changes has been saved!");
            },
            function error(response, status) {
                console.error('Changes has not been saved, Error!!!', status, response);
                alert("Something goes wrong :(");
            });
    }
});
