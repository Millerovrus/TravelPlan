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

    $scope.deleteSavedRoute = function(input) {
        $http({
            method: 'GET',
            url: 'api/routes/deletebyid',
            params: {
                id: input
            }
        }).then(
            function success(response) {
                $scope.records = response.data;
                $scope.loaded = true;
                $scope.printUserRoutes();
            },
            function error(response, status) {
                console.error('Repos error', status, response);
                alert("Something goes wrong :(");
            });
    };

        $scope.openLink = function(item) {
            var date = item.startDate.replace(/-/g, '/');
            if (item.purchaseLink === null){
                alert("Нет ссылки на покупку");
            } else
            if(new Date(date).getTime() < Date.now()){
                alert("Ссылка на покупку недействительна")
            } else $window.open(item.purchaseLink);
        };

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

    $scope.checkme = $('#checker22');
    $scope.userName = $('#first-name');
    $scope.userBirth = $('#birth-date');
    $scope.userAvatar = $('#avatar');
    $scope.userAvatarClass = $('#avatar-class');
    $scope.userLastName = $('#last-name');
    $scope.editHeader = $('#edit-header');
    $scope.userSend = $('#submit-edit');
    $scope.fileName = $('#file-name');

    $scope.enableEdit = function () {
        if($scope.checkme.is(':checked')){
            $scope.userSend.prop('disabled', false);
            $scope.userName.prop('disabled', false);
            $scope.userBirth.prop('disabled', false);
            $scope.userAvatar.prop('disabled', false);
            $scope.userAvatar.css('display', 'block');
            $scope.userAvatarClass.css('display', 'block');
            $scope.userLastName.prop('disabled', false);
            $scope.editHeader.css('display', 'none');
            $scope.userSend.css('display', 'block');
        }
        else{
            $scope.userSend.prop('disabled', true);
            $scope.userName.prop('disabled', true);
            $scope.userBirth.prop('disabled', true);
            $scope.userAvatar.prop('disabled', true);
            $scope.userAvatar.css('display', 'none');
            $scope.userAvatarClass.css('display', 'none');
            $scope.userLastName.prop('disabled', true);
            $scope.userSend.css('display', 'none');
        }
    };
    $scope.saveEdit = function () {
        $scope.userSend.prop('disabled', true);
        $scope.userName.prop('disabled', true);
        $scope.userBirth.prop('disabled', true);
        $scope.userAvatar.prop('disabled', true);
        $scope.userAvatar.css('display', 'none');
        $scope.userAvatarClass.css('display', 'none');
        $scope.userLastName.prop('disabled', true);
        $scope.editHeader.css('display', 'block');
        $scope.checkme.attr('checked', false);
        $scope.userSend.css('display', 'none');
        $scope.fileName.css('display', 'none');

        $scope.change();
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
            } else {
                return array.reverse()
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
