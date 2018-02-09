import template from '../templates/menu.html';

angular.module('myApp',[])
    .directive('myMenu', function () {
        return {
            template: template
        };
    });
