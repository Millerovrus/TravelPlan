var app = angular.module('appSignUp',[]);
app.controller('controllerSignUp', function signUpUser($scope, $http) {
    $scope.sendUserData = function () {
        $http({
            method: "POST",
            url: "/api/users/adduser",
            /*$.param преобразование данных в строку с кодировкой URL*/
            data: $.param({
                firstname: angular.element($('#inputFirstName')).val(),
                lastname: angular.element($('#inputLastName')).val(),
                birthdate: angular.element($('#inputBirthDate')).val(),
                email: angular.element($('#inputEmail')).val(),
                password: angular.element($('#inputPassword')).val()
            }),
            /*заголовок передаваемого объекта*/
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(
            function success(response) {
                alert("Вы зарегистрированы!");
                location.href="/";
                /*Очистка формы от введённых значений*/
                angular.element($('#inputFirstName')).val('');
                angular.element($('#inputLastName')).val('');
                angular.element($('#inputBirthDate')).val('');
                angular.element($('#inputEmail')).val('');
                angular.element($('#inputPassword')).val('');
                console.log(response.data);
            },
            function error(response, status) {
                console.error('Not send, Error!!!', status, response);
            })
    }
});
