/*var myApp = angular.module('myApp',[]);

//var dataFrom = document.getElementById('inputFrom').value; // откуда
//var dataTo = document.getElementById('inputTo').value;  // куда

myApp.controller('myParameterController', function ($scope, $http) {
    /!*$http({
        method: "GET",
        url: "../js/data.json" //"https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1qZ3sTT_fYt7y5_9WSetqYCNanvhwL-c0",
        /!* params: {
             from: dataFrom, https://drive.google.com/file/d/1qZ3sTT_fYt7y5_9WSetqYCNanvhwL-c0/view?usp=sharing
             to: dataTo
         }*!/
    }).then(
        function(response) {
            $scope.myData = response;
            return myData;
        },
        function(response) {
            $scope.myData = response;

        }
    );*!/
    /!*$http.get('js/data.json').success(function (response) {
        $scope.myData = JSON.parse(response);
    })*!/
    $http.get('data.json').then(function (response) {
        $scope.myData = response.data;
    })
});*/
/*angular.module('myApp', [])
    .controller('myParameterController', function($scope, $http) {
        $http.get('http://localhost:8888/api/getEdges')
            .success(function (data) {
                $scope.myData = data
            })
            .error(function (data){

            })
    });*/
var app = angular.module('MyApp', []);
app.controller("myParameterController", function ($scope, $http) {
    $http.get('http://localhost:8888/api/getEdges')
        .success(function (data) {
            $scope.records = data
        })
        .error(function (data) {

        })
});