
angular.module('controllerModule',['ngSanitize']);
angular.module('myApp',['controllerModule'])
    .directive('myNavbar', function () {
        return {
            templateUrl: '/navbar/navbar-template.html'
        };
    });
angular.module('controllerModule')
    .controller('myParameterController', function requestFunc($scope, $http) {

        $scope.sendRequestParameters=function () {
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
                     date: angular.element($('#inputDate')).val()
                   /*passengers: angular.element($('#demo2')).val()*/
                }
            }).then(
                function success(response) {
                    $scope.records = response.data;
                    $scope.loaded=true;
                    $scope.$emit('UNLOAD');
                },
                function error(response, status) {
                    console.error('Repos error', status, response);
                    $scope.$emit('UNLOAD');
                    alert("Something goes wrong :(");
                });
        };

    })
    .filter('secondsToTime', [function() {
        return function(seconds) {
            var hours = Math.floor((seconds % 86400) / 3600);
            var mins = Math.floor(((seconds % 86400) % 3600) / 60);
            return ('00'+hours).slice(-2) +' hours ' + ('00'+mins).slice(-2)+' minutes';
        };
    }])
   /* .filter('outputJson', [function () {
        return function (hello) {
            var routes = hello.length;
            return 'json length is '+routes;
        };
    }])*/
    .filter('transportTypeToIcon', [function () {
        return function (transportType) {
            if(transportType =="bus"){
                var bus = '      <i class="fa fa-bus"></i>      ';
                return bus;
            }
            if(transportType =="plane"){
                var plane = '      <i class="fa fa-plane"></i>      ';
                return plane;
            }
            if(transportType =="train"){
                var train= '      <i class="fa fa-train"></i>      ';
                return train;
            }

            var output = 'some other type: ' + transportType;
            return output;
        };
    }])
    .controller('appController', function ($scope) {
        $scope.$on('LOAD', function () { $scope.loading = true });
        $scope.$on('UNLOAD', function () { $scope.loading = false });
    })
    .controller('mapController', function ($scope) {

        /* /////////////////////////////////////// */
        $scope.initMap =function() {
            var map = new google.maps.Map(document.getElementById('map'), {
                center: {lat: 51.675, lng: 39.208},
                zoom: 8,
                gestureHandling: 'cooperative',
                minZoom: 2,
                // styles: [
                //     {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
                //     {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
                //     {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
                //     {
                //         featureType: 'administrative.locality',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'poi',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'poi.park',
                //         elementType: 'geometry',
                //         stylers: [{color: '#263c3f'}]
                //     },
                //     {
                //         featureType: 'poi.park',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#6b9a76'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'geometry',
                //         stylers: [{color: '#38414e'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'geometry.stroke',
                //         stylers: [{color: '#212a37'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#9ca5b3'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'geometry',
                //         stylers: [{color: '#746855'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'geometry.stroke',
                //         stylers: [{color: '#1f2835'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#f3d19c'}]
                //     },
                //     {
                //         featureType: 'transit',
                //         elementType: 'geometry',
                //         stylers: [{color: '#2f3948'}]
                //     },
                //     {
                //         featureType: 'transit.station',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'geometry',
                //         stylers: [{color: '#17263c'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#515c6d'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'labels.text.stroke',
                //         stylers: [{color: '#17263c'}]
                //     }
                // ]
                styles:[
                    {elementType: 'geometry', stylers: [{color: '#ebe3cd'}]},
                    {elementType: 'labels.text.fill', stylers: [{color: '#523735'}]},
                    {elementType: 'labels.text.stroke', stylers: [{color: '#f5f1e6'}]},
                    {
                        featureType: 'administrative',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#c9b2a6'}]
                    },
                    {
                        featureType: 'administrative.land_parcel',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#dcd2be'}]
                    },
                    {
                        featureType: 'administrative.land_parcel',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#ae9e90'}]
                    },
                    {
                        featureType: 'landscape.natural',
                        elementType: 'geometry',
                        stylers: [{color: '#fff6d4'}]
                    },
                    {
                        featureType: 'poi',
                        elementType: 'geometry',
                        stylers: [{color: '#fff6d4'}]
                    },
                    {
                        featureType: 'poi',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#93817c'}]
                    },
                    {
                        featureType: 'poi.park',
                        elementType: 'geometry.fill',
                        stylers: [{color: '#a5b076'}]
                    },
                    {
                        featureType: 'poi.park',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#629e4a'}]
                    },
                    {
                        featureType: 'road',
                        elementType: 'geometry',
                        stylers: [{color: '#f5f1e6'}]
                    },
                    {
                        featureType: 'road.arterial',
                        elementType: 'geometry',
                        stylers: [{color: '#fdfcf8'}]
                    },
                    {
                        featureType: 'road.highway',
                        elementType: 'geometry',
                        stylers: [{color: '#f8c967'}]
                    },
                    {
                        featureType: 'road.highway',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#e9bc62'}]
                    },
                    {
                        featureType: 'road.highway.controlled_access',
                        elementType: 'geometry',
                        stylers: [{color: '#e98d58'}]
                    },
                    {
                        featureType: 'road.highway.controlled_access',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#db8555'}]
                    },
                    {
                        featureType: 'road.local',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#806b63'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'geometry',
                        stylers: [{color: '#dfd2ae'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#8f7d77'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'labels.text.stroke',
                        stylers: [{color: '#ebe3cd'}]
                    },
                    {
                        featureType: 'transit.station',
                        elementType: 'geometry',
                        stylers: [{color: '#dfd2ae'}]
                    },
                    {
                        featureType: 'water',
                        elementType: 'geometry.fill',
                        stylers: [{color: '#B0C4DE'}]
                    },
                    {
                        featureType: 'water',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#92998d'}]
                    }
                ]
            });

        };
        $scope.reinitMap = function(id) {
            // var id = document.getElementById("id-for-view").value;
            var routes = JSON.parse(document.getElementById("json-for-map").innerHTML);
            var map = new google.maps.Map(document.getElementById('map'), {
                gestureHandling: 'cooperative',
                minZoom: 2,
                // styles: [
                //     {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
                //     {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
                //     {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
                //     {
                //         featureType: 'administrative.locality',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'poi',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'poi.park',
                //         elementType: 'geometry',
                //         stylers: [{color: '#263c3f'}]
                //     },
                //     {
                //         featureType: 'poi.park',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#6b9a76'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'geometry',
                //         stylers: [{color: '#38414e'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'geometry.stroke',
                //         stylers: [{color: '#212a37'}]
                //     },
                //     {
                //         featureType: 'road',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#9ca5b3'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'geometry',
                //         stylers: [{color: '#746855'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'geometry.stroke',
                //         stylers: [{color: '#1f2835'}]
                //     },
                //     {
                //         featureType: 'road.highway',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#f3d19c'}]
                //     },
                //     {
                //         featureType: 'transit',
                //         elementType: 'geometry',
                //         stylers: [{color: '#2f3948'}]
                //     },
                //     {
                //         featureType: 'transit.station',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#d59563'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'geometry',
                //         stylers: [{color: '#17263c'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'labels.text.fill',
                //         stylers: [{color: '#515c6d'}]
                //     },
                //     {
                //         featureType: 'water',
                //         elementType: 'labels.text.stroke',
                //         stylers: [{color: '#17263c'}]
                //     }
                // ]
                styles:[
                    {elementType: 'geometry', stylers: [{color: '#ebe3cd'}]},
                    {elementType: 'labels.text.fill', stylers: [{color: '#523735'}]},
                    {elementType: 'labels.text.stroke', stylers: [{color: '#f5f1e6'}]},
                    {
                        featureType: 'administrative',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#c9b2a6'}]
                    },
                    {
                        featureType: 'administrative.land_parcel',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#dcd2be'}]
                    },
                    {
                        featureType: 'administrative.land_parcel',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#ae9e90'}]
                    },
                    {
                        featureType: 'landscape.natural',
                        elementType: 'geometry',
                        stylers: [{color: '#fff6d4'}]
                    },
                    {
                        featureType: 'poi',
                        elementType: 'geometry',
                        stylers: [{color: '#fff6d4'}]
                    },
                    {
                        featureType: 'poi',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#93817c'}]
                    },
                    {
                        featureType: 'poi.park',
                        elementType: 'geometry.fill',
                        stylers: [{color: '#a5b076'}]
                    },
                    {
                        featureType: 'poi.park',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#629e4a'}]
                    },
                    {
                        featureType: 'road',
                        elementType: 'geometry',
                        stylers: [{color: '#f5f1e6'}]
                    },
                    {
                        featureType: 'road.arterial',
                        elementType: 'geometry',
                        stylers: [{color: '#fdfcf8'}]
                    },
                    {
                        featureType: 'road.highway',
                        elementType: 'geometry',
                        stylers: [{color: '#f8c967'}]
                    },
                    {
                        featureType: 'road.highway',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#e9bc62'}]
                    },
                    {
                        featureType: 'road.highway.controlled_access',
                        elementType: 'geometry',
                        stylers: [{color: '#e98d58'}]
                    },
                    {
                        featureType: 'road.highway.controlled_access',
                        elementType: 'geometry.stroke',
                        stylers: [{color: '#db8555'}]
                    },
                    {
                        featureType: 'road.local',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#806b63'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'geometry',
                        stylers: [{color: '#dfd2ae'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#8f7d77'}]
                    },
                    {
                        featureType: 'transit.line',
                        elementType: 'labels.text.stroke',
                        stylers: [{color: '#ebe3cd'}]
                    },
                    {
                        featureType: 'transit.station',
                        elementType: 'geometry',
                        stylers: [{color: '#dfd2ae'}]
                    },
                    {
                        featureType: 'water',
                        elementType: 'geometry.fill',
                        stylers: [{color: '#B0C4DE'}]
                    },
                    {
                        featureType: 'water',
                        elementType: 'labels.text.fill',
                        stylers: [{color: '#92998d'}]
                    }
                ]
            });
            fillInAll(map, routes[id].edges);
        };

        $scope.fillInAll = function(map, edges) {
            var lines = [];
            var speed = [];
            for (var i = 0; i < edges.length; i++) {
                var from = new google.maps.LatLng(edges[i].latitudeFrom, edges[i].longitudeFrom);
                var to = new google.maps.LatLng(edges[i].latitudeTo, edges[i].longitudeTo);
                switch (edges[i].transportType) {
                    case 'plane':
                        speed.push(1);
                        break;
                    case 'bus':
                        speed.push(0.4);
                        break;
                    case 'train':
                        speed.push(0.7);
                        break;
                    case 'car':
                        speed.push(0.6);
                        break;
                    default:
                        alert( 'Неверное значение type' );
                }
                createLine(map, from, to, edges[i].transportType, lines);
            }
            adaptZoomAndCenter(map, edges);
            setMarkers(map, edges);
            animateSymbols(lines, speed);
        };

//создаем линии и иконки под тип транспорта
        $scope.createLine = function(map, from, to, type, lines) {
            var lineSymbol;
            // задаем иконки
            switch (type) {
                case 'bus':
                    lineSymbol = {
                        path: 'm-.1 9.165c-3.544 0-6.414-.418-6.414-3.343l0-8.358c0-.74.313-1.396.802-1.855l0-1.488c0-.46.361-.836.802-.836l.802 0c.445 0 .802.376 .802.836l0 .836 6.414 0 0-.836c0-.46.357-.836.802-.836l.802 0c.441 0 .802.376 .802.836l0 1.488c.489.46 .802 1.116.802 1.855l0 8.358c0 2.925-2.87 3.343-6.414 3.343zm-3.608-12.537c-.665 0-1.203.56-1.203 1.254s.537 1.254 1.203 1.254 1.203-.56 1.203-1.254-.537-1.254-1.203-1.254zm7.216 0c-.665 0-1.203.56-1.203 1.254s.537 1.254 1.203 1.254 1.203-.56 1.203-1.254-.537-1.254-1.203-1.254zm1.203 5.015-9.621 0 0 4.179 9.621 0 0-4.179z',
                        scale: 2,
                        strokeOpacity: 0.0,
                        fillOpacity: 0.8,
                        strokeColor: 'white',
                        strokeWeight: 1,
                        fillColor: 'black',
                        rotation: 0
                    };
                    var ratio = (parseFloat(google.maps.geometry.spherical.computeHeading(from, to)) + 180);
                    if (ratio > 315 && ratio <= 45){
                        lineSymbol.rotation = 0;
                    } else {
                        if (ratio > 45 && ratio <= 135) {
                            lineSymbol.rotation = 270;
                        } else {
                            if (ratio > 135 && ratio <= 225) {
                                lineSymbol.rotation = 180;
                            } else {
                                if (ratio > 225 && ratio <= 315) {
                                    lineSymbol.rotation = 90;
                                }
                            }
                        }
                    }
                    break;
                case 'train':
                    lineSymbol = {
                        path: 'M-.07 9.094c-3.832 0-6.967-.435-6.967-3.484v-8.273c0-1.655 1.393-3.048 3.048-3.048l-1.306-1.306v-.435h10.451v.435L3.849-5.711c1.655 0 3.048 1.393 3.048 3.048V5.61C6.897 8.658 3.762 9.094-.07 9.094zM-3.989-3.97c-.697 0-1.306.61-1.306 1.306S-4.686-1.357-3.989-1.357s1.306-.61 1.306-1.306S-3.293-3.97-3.989-3.97zM-.941 1.256H-5.296V5.61h4.354V1.256zM3.849-3.97c-.697 0-1.306.61-1.306 1.306S3.152-1.357 3.849-1.357s1.306-.61 1.306-1.306S4.545-3.97 3.849-3.97zM5.155 1.256h-4.354V5.61h4.354V1.256z',
                        scale: 1.95,
                        strokeOpacity: 0.0,
                        fillOpacity: 0.8,
                        strokeColor: 'white',
                        strokeWeight: 1,
                        fillColor: 'black',
                        rotation: 0
                    };
                    ratio = (parseFloat(google.maps.geometry.spherical.computeHeading(from, to)) + 180);
                    if (ratio > 315 && ratio <= 45){
                        lineSymbol.rotation = 0;
                    } else {
                        if (ratio > 45 && ratio <= 135) {
                            lineSymbol.rotation = 270;
                        } else {
                            if (ratio > 135 && ratio <= 225) {
                                lineSymbol.rotation = 180;
                            } else {
                                if (ratio > 225 && ratio <= 315) {
                                    lineSymbol.rotation = 90;
                                }
                            }
                        }
                    }
                    break;
                case 'plane':
                    lineSymbol = {
                        path: 'M3.76 5.84V5.067L.665 3.133V1.005c0-.321-.259-.58-.58-.58s-.58.259-.58.58v2.127L-3.59 5.067v.774l3.095-.967v2.127l-.774.58v.58l1.354-.387L1.439 8.16v-.58l-.774-.58V4.873L3.76 5.84z',
                        scale: 5,
                        strokeOpacity: 0.0,
                        fillOpacity: 0.8,
                        strokeColor: 'white',
                        strokeWeight: 1,
                        fillColor: 'black'
                    };
                    break;
                case 'car':
                    lineSymbol = {
                        path: 'M4.617-5.156H-4.711C-7.184-5.156-9.188-2.406-9.188.067v27.609c0 2.472 2.004 4.477 4.476 4.477h9.328c2.472 0 4.477-2.005 4.477-4.477V.067C9.092-2.406 7.088-5.156 4.617-5.156zM8.309 6.098v9.253l-2.165.278v-3.812L8.309 6.098zM7.173 3.39c-.806 3.094-1.76 6.751-1.76 6.751H-5.509l-1.763-6.751C-7.27 3.39-.224.995 7.173 3.39zM-6.215 12.068v3.563l-2.166-.277V6.348L-6.215 12.068zM-8.38 24.938V16.721l2.166.272v6.502L-8.38 24.938zM-7.145 27.274l1.759-2.646h10.924l1.76 2.646H-7.145zM6.144 23.246v-6.245l2.165-.282v7.971L6.144 23.246z',
                        scale: 1.3,
                        strokeOpacity: 0.0,
                        fillOpacity: 0.8,
                        strokeColor: 'white',
                        strokeWeight: 1,
                        fillColor: 'black'
                    };
                    break;
                default:
                    alert( 'Неверное значение type' );
                    break;
            }

            // создаем путь
            var line = new google.maps.Polyline({
                path: [from, to],
                icons: [{
                    icon: lineSymbol,
                    offset: '0%'
                }],
                strokeColor: '#2b2b2b',
                strokeOpacity: 0.3,
                strokeWeight: 2,
                map: map
            });

            lines.push(line);
        };

//анимируем символ
        $scope.animateSymbols = function(lines, speed) {
            var i = 0;
            window.setInterval(function() {
                if(parseInt(lines[i].icons[0].offset) + speed[i]< 100) {
                    var icons = lines[i].get('icons');
                    icons[0].offset = parseFloat(icons[0].offset) + speed[i] + '%';
                    icons[0].icon.strokeOpacity = 0.5;
                    icons[0].icon.fillOpacity = 0.5;
                    lines[i].set('icons', icons);
                } else {
                    var icons2 = lines[i].get('icons');
                    icons2[0].icon.strokeOpacity = 0.0;
                    icons2[0].icon.fillOpacity = 0.0;
                    lines[i].set('icons', icons2);
                    i++;
                }
                if (i === lines.length){
                    for (var j = 0; j < lines.length; j++){
                        var icons3 = lines[j].get('icons');
                        icons3[0].offset = '0%';
                        lines[j].set('icons', icons3);
                        i = 0;
                    }
                }
            }, 30);
        };

//адаптируем зум и центр карты под координаты
        $scope.adaptZoomAndCenter = function(map, edges) {
            var bounds = new google.maps.LatLngBounds();
            for (var i = 0; i < edges.length; i++) {
                var pointLatLng = new google.maps.LatLng(edges[i].latitudeFrom, edges[i].longitudeFrom);
                bounds.extend(pointLatLng);
                if (i === edges.length - 1){
                    var pointLatLng2 = new google.maps.LatLng(edges[i].latitudeTo, edges[i].longitudeTo);
                    bounds.extend(pointLatLng2);
                }
            }
            map.fitBounds(bounds);
        };

//ставим маркеры
        $scope.setMarkers = function(map, edges) {
            for (var i = 0; i < edges.length; i++) {
                var pointLatLng = new google.maps.LatLng(edges[i].latitudeFrom, edges[i].longitudeFrom);
                var marker = new google.maps.Marker({
                    position: pointLatLng,
                    title: edges[i].startPoint,
                    label: edges[i].startPoint.charAt(0),
                    opacity: 0.75,
                    map: map
                });
                if (i === edges.length - 1){
                    var pointLatLng2 = new google.maps.LatLng(edges[i].latitudeTo, edges[i].longitudeTo);
                    var marker2 = new google.maps.Marker({
                        position: pointLatLng2,
                        title: edges[i].destinationPoint,
                        label: edges[i].destinationPoint.charAt(0),
                        opacity: 0.75,
                        map: map
                    });
                }
            }
        }

    });

