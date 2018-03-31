var map;
var interval;
var lines = [];
var markers = [];

//инициализация карты
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 51.675, lng: 39.208},
        zoom: 8,
        gestureHandling: 'cooperative',
        minZoom: 2,
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
}
//установка значений на карту
function setMap(edges) {
    resetMap();
    fillInAll(edges);
}
//обнуление значений с карты
function resetMap() {
    if (markers.length !== 0){
        window.clearInterval(interval);
        for (var i = 0; i < markers.length; i++){
            markers[i].setMap(null);
        }
        for (var j = 0; j < lines.length; j++){
            lines[j].setMap(null);
        }
        lines = [];
        markers = [];
    }
}

function fillInAll(edges) {
    var speed = [];
    for (var i = 0; i < edges.length; i++) {
        if (edges[i].numberOfTransfers === 1){
            var from = new google.maps.LatLng(edges[i].startPointPoint.latitude, edges[i].startPointPoint.longitude);
            var to = new google.maps.LatLng(edges[i].endPointPoint.latitude, edges[i].endPointPoint.longitude);
            switch (edges[i].transportType) {
                case 'plane':
                    speed.push(1);
                    break;
                case 'bus':
                    speed.push(0.4);
                    break;
                case 'train':
                    speed.push(0.6);
                    break;
                case 'car':
                    speed.push(0.6);
                    break;
                default:
                    alert( 'Неверное значение type' );
            }
            createLine(from, to, edges[i].transportType);
        } else {
            // if (edges[i].numberOfTransfers > 1) {
            //     for (var j = 0; j < edges[i].transitPoints.length - 1; j++) {
            //         speed.push(1);
            //         var fromTemp = new google.maps.LatLng(edges[i].transitPoints[j].latitude, edges[i].transitPoints[j].longitude);
            //         var toTemp = new google.maps.LatLng(edges[i].transitPoints[j + 1].latitude, edges[i].transitPoints[j + 1].longitude);
            //         createLine(fromTemp, toTemp, edges[i].transportType);
            //     }
            // }
            if (edges[i].numberOfTransfers > 1) {
                for (var j = 0; j < edges[i].transitEdgeList.length; j++) {
                    speed.push(1);
                    var fromTemp = new google.maps.LatLng(edges[i].transitEdgeList[j].latitudeFrom, edges[i].transitEdgeList[j].longitudeFrom);
                    var toTemp = new google.maps.LatLng(edges[i].transitEdgeList[j].latitudeTo, edges[i].transitEdgeList[j].longitudeTo);
                    createLine(fromTemp, toTemp, edges[i].transportType);
                }
            }
        }
    }
    adaptZoomAndCenter(edges);
    setMarkers(edges);
    animateSymbols(speed);
}

//создаем линии и иконки под тип транспорта
function createLine(from, to, type) {
    var lineSymbol;
    // задаем иконки
    switch (type) {
        case 'bus':
            lineSymbol = {
                path: 'm-.1 9.165c-3.544 0-6.414-.418-6.414-3.343l0-8.358c0-.74.313-1.396.802-1.855l0-1.488c0-.46.361-.836.802-.836l.802 0c.445 0 .802.376 .802.836l0 .836 6.414 0 0-.836c0-.46.357-.836.802-.836l.802 0c.441 0 .802.376 .802.836l0 1.488c.489.46 .802 1.116.802 1.855l0 8.358c0 2.925-2.87 3.343-6.414 3.343zm-3.608-12.537c-.665 0-1.203.56-1.203 1.254s.537 1.254 1.203 1.254 1.203-.56 1.203-1.254-.537-1.254-1.203-1.254zm7.216 0c-.665 0-1.203.56-1.203 1.254s.537 1.254 1.203 1.254 1.203-.56 1.203-1.254-.537-1.254-1.203-1.254zm1.203 5.015-9.621 0 0 4.179 9.621 0 0-4.179z',
                scale: 2,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
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
                fillOpacity: 0.0,
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
                path: 'M3.666 5.77V4.997L.571 3.063V.935c0-.321-.259-.58-.58-.58s-.58.259-.58.58v2.127L-3.684 4.997v.774l3.095-.967v2.127l-.774.58v.58l1.354-.387L1.345 8.09v-.58l-.774-.58V4.803L3.666 5.77z',
                scale: 5,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
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
                fillOpacity: 0.0,
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
        strokeColor: 'black',
        strokeOpacity: 0.5,
        strokeWeight: 3,
        map: map
    });

    lines.push(line);
}

//анимируем символ
function animateSymbols(speed) {
    var i = 0;
    interval = window.setInterval(function() {
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
}

//адаптируем зум и центр карты под координаты
function adaptZoomAndCenter(edges) {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < edges.length; i++) {
        if (edges[i].numberOfTransfers === 1) {
            var pointLatLng = new google.maps.LatLng(edges[i].startPointPoint.latitude, edges[i].startPointPoint.longitude);
            bounds.extend(pointLatLng);
            if (i === edges.length - 1) {
                var pointLatLng2 = new google.maps.LatLng(edges[i].endPointPoint.latitude, edges[i].endPointPoint.longitude);
                bounds.extend(pointLatLng2);
            }
        } else {
            if (edges[i].numberOfTransfers > 1 && i === edges.length - 1){
                for (var j = 0; j < edges[i].transitPoints.length; j++) {
                    var pointLatLng3 = new google.maps.LatLng(edges[i].transitPoints[j].latitude, edges[i].transitPoints[j].longitude);
                    bounds.extend(pointLatLng3);
                }
            } else {
                if (edges[i].numberOfTransfers > 1 && i !== edges.length - 1){
                    for (var k = 0; k < edges[i].transitPoints.length - 1; k++) {
                        var pointLatLng4 = new google.maps.LatLng(edges[i].transitPoints[k].latitude, edges[i].transitPoints[k].longitude);
                        bounds.extend(pointLatLng4);
                    }
                }
            }
        }
    }
    map.fitBounds(bounds);
}

//ставим маркеры
function setMarkers(edges) {
    for (var i = 0; i < edges.length; i++) {
        if (edges[i].numberOfTransfers === 1) {
            var pointLatLng = new google.maps.LatLng(edges[i].startPointPoint.latitude, edges[i].startPointPoint.longitude);
            var marker = new google.maps.Marker({
                position: pointLatLng,
                title: edges[i].startPointPoint.name,
                label: edges[i].startPointPoint.name.charAt(0),
                opacity: 0.75,
                map: map
            });
            markers.push(marker);
            if (i === edges.length - 1) {
                var pointLatLng2 = new google.maps.LatLng(edges[i].endPointPoint.latitude, edges[i].endPointPoint.longitude);
                var marker2 = new google.maps.Marker({
                    position: pointLatLng2,
                    title: edges[i].endPointPoint.name,
                    label: edges[i].endPointPoint.name.charAt(0),
                    opacity: 0.75,
                    map: map
                });
                markers.push(marker2);
            }
        } else {
            if (edges[i].numberOfTransfers > 1 && i === edges.length - 1){
                for (var j = 0; j < edges[i].transitPoints.length; j++) {
                    var pointLatLng3 = new google.maps.LatLng(edges[i].transitPoints[j].latitude, edges[i].transitPoints[j].longitude);
                    var marker3 = new google.maps.Marker({
                        position: pointLatLng3,
                        title: edges[i].transitPoints[j].name,
                        label: edges[i].transitPoints[j].name.charAt(0),
                        opacity: 0.75,
                        map: map
                    });
                    markers.push(marker3);
                }
            } else {
                if (edges[i].numberOfTransfers > 1 && i !== edges.length - 1){
                    for (var k = 0; k < edges[i].transitPoints.length - 1; k++) {
                        var pointLatLng4 = new google.maps.LatLng(edges[i].transitPoints[k].latitude, edges[i].transitPoints[k].longitude);
                        var marker4 = new google.maps.Marker({
                            position: pointLatLng4,
                            title: edges[i].transitPoints[k].name,
                            label: edges[i].transitPoints[k].name.charAt(0),
                            opacity: 0.75,
                            map: map
                        });
                        markers.push(marker4);
                    }
                }
            }
        }
    }
}
