var app = angular.module('appSignIn',[]);
app.controller('controllerSignIn', function signUpUser($scope, $http) {
    $scope.sendUserData = function () {
        $http({
            method: "get",
            url: "/singIn",
            /*$.param преобразование данных в строку с кодировкой URL*/
            data: $.param({
                email: angular.element($('#email')).val(),
                password: angular.element($('#password')).val()
            }),
            /*заголовок передаваемого объекта*/
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        })
        .then(
            function success(response) {
                console.log(response.data);
                /*Очистка формы от введённых значений*/
                angular.element($('#email')).val('');
                angular.element($('#password')).val('');
            },
            function error(response, status) {
                console.error('User has not been loggin, error!', status, response);
            }
        )
    }
});
