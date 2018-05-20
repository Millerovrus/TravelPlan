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

function firstSetMap(record) {
    resetMap();
    var speed = [0.5];
    var from = new google.maps.LatLng(record.edges[0].startPoint.latitude, record.edges[0].startPoint.longitude);
    var to = new google.maps.LatLng(record.edges[record.edges.length - 1].endPoint.latitude, record.edges[record.edges.length - 1].endPoint.longitude);
    var nameFrom = record.edges[0].startPoint.name;
    var nameTo = record.edges[record.edges.length - 1].endPoint.name;

    createLine(from, to, 'spaceship');
    setTimeout(function() {
        var bounds = new google.maps.LatLngBounds();
        bounds.extend(from);
        bounds.extend(to);
        map.fitBounds(bounds);
    }, 10);

    var marker = new google.maps.Marker({
        position: from,
        title: nameFrom,
        label: nameFrom.charAt(0),
        opacity: 0.75,
        map: map
    });
    markers.push(marker);
    var marker2 = new google.maps.Marker({
        position: to,
        title: nameTo,
        label: nameTo.charAt(0),
        opacity: 0.75,
        map: map
    });
    markers.push(marker2);

    animateSymbols(speed);
}

function fillInAll(edges) {
    var speed = [];
    for (var i = 0; i < edges.length; i++) {
        for (var j = 0; j < edges[i].transitEdgeList.length ; j++) {
            var from = new google.maps.LatLng(edges[i].transitEdgeList[j].startPoint.latitude, edges[i].transitEdgeList[j].startPoint.longitude);
            var to = new google.maps.LatLng(edges[i].transitEdgeList[j].endPoint.latitude, edges[i].transitEdgeList[j].endPoint.longitude);
            switch (edges[i].transportType) {
                case 'plane':
                    speed.push(1);
                    break;
                case 'bus':
                    speed.push(0.4);
                    break;
                case 'suburban':
                case 'train':
                    speed.push(0.6);
                    break;
                case 'car':
                    speed.push(0.6);
                    break;
                default:
                    speed.push(0.5);
                    break;
            }
            createLine(from, to, edges[i].transportType);
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
        case 'spaceship':
            lineSymbol = {
                path: 'm -8.0763216,45.535617 -8.7051244,1.38206 c -0.692152,0.10903 -1.380818,-0.22192 -1.747812,-0.84223 l -3.246001,-5.48884 c -0.181057,-0.30628 -0.2638,-0.65489 -0.25427,-1.00179 -0.0014,-0.0291 -0.0042,-0.0573 -0.0042,-0.0865 v -11.61106 c 0,-1.00156 0.780472,-1.81309 1.743163,-1.81309 0.962691,0 1.743163,0.81153 1.743163,1.81309 v 4.81725 l 6.968933,-12.90871 3.5021314,6.21648 z m 28.6241306,-19.4617 c -0.962923,0 -1.743163,0.81154 -1.743163,1.81309 v 4.81726 l -6.968701,-12.90847 -3.5021296,6.21672 v 19.52334 l 8.7051226,1.38206 c 0.691687,0.10928 1.380817,-0.22192 1.748044,-0.84247 l 3.245769,-5.48884 c 0.181057,-0.30605 0.263567,-0.65464 0.254037,-1.00155 0.0014,-0.0289 0.0042,-0.0573 0.0042,-0.0865 v -11.61103 c 0,-1.00179 -0.780472,-1.81358 -1.743162,-1.81358 z m -15.7003206,19.53834 -2.975695,2.30794 v 3.00586 c 0,1.00155 -0.78024,1.81309 -1.74316298,1.81309 -0.962691,0 -1.74316402,-0.81154 -1.74316402,-1.81309 v -3.00586 l -2.975695,-2.30794 v -20.09193 c 0,-0.32225 -0.08251,-0.63869 -0.239162,-0.9167 l -4.771851,-8.47076 8.209833,-15.20747021 c 0.308657,-0.57148 0.88994402,-0.9254000005238 1.52003902,-0.9254000005238 0.630328,0 1.21138098,0.3539200005238 1.52003798,0.9254000005238 l 8.209833,15.20747021 -4.77185,8.47076 c -0.156653,0.27801 -0.239163,0.59445 -0.239163,0.9167 z m -0.0081,-29.02539 c 0.742355,-0.63724 0.847642,-1.77997 0.235211,-2.55258 -0.201743,-0.25456 -2.054376,-2.48611 -4.90177498,-2.48611 -2.81764802,0 -4.74628402,2.19577 -4.95685802,2.44622 -0.634047,0.75352 -0.565249,1.90422 0.159441,2.56346 0.332131,0.30218 0.742123,0.45086 1.150256,0.45086 0.482274,0 0.961761,-0.20766 1.305047,-0.61597 0.294711,-0.33893 1.28994102,-1.21864 2.34234602,-1.21864 1.23555398,0 2.21242298,1.16787 2.21242298,1.16787 0.612664,0.77262 1.711322,0.88213 2.453909,0.24489 z',
                scale: 1,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
                strokeColor: 'white',
                strokeWeight: 1,
                fillColor: 'black'
            };
            break;
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
            if (ratio > 315 || ratio <= 45){
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
        case 'suburban':
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
            if (ratio > 315 || ratio <= 45){
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
            lineSymbol = {
                path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
                scale: 6,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
                strokeColor: 'white',
                strokeWeight: 1,
                fillColor: 'black'
            };
            lineSymbol = {
                path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
                    scale: 6,
                    strokeOpacity: 0.0,
                    fillOpacity: 0.0,
                    strokeColor: 'white',
                    strokeWeight: 1,
                fillColor: 'black'
            };
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
        for (var j = 0; j < edges[i].transitEdgeList.length ; j++) {
            var pointLatLng1 = new google.maps.LatLng(edges[i].transitEdgeList[j].startPoint.latitude, edges[i].transitEdgeList[j].startPoint.longitude);
            bounds.extend(pointLatLng1);
        }
        if (i === edges.length - 1){
            var pointLatLng2 = new google.maps.LatLng(edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.latitude, edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.longitude);
            bounds.extend(pointLatLng2);
        }
    }
    map.fitBounds(bounds);
}

//ставим маркеры
function setMarkers(edges) {
    for (var i = 0; i < edges.length; i++) {
        for (var j = 0; j < edges[i].transitEdgeList.length; j++) {
            var pointLatLng = new google.maps.LatLng(edges[i].transitEdgeList[j].startPoint.latitude, edges[i].transitEdgeList[j].startPoint.longitude);
            var marker = new google.maps.Marker({
                position: pointLatLng,
                title: edges[i].transitEdgeList[j].startPoint.name,
                label: edges[i].transitEdgeList[j].startPoint.name.charAt(0),
                opacity: 0.75,
                map: map
            });
            markers.push(marker);
        }
        if (i === edges.length - 1){
            var pointLatLng2 = new google.maps.LatLng(edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.latitude, edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.longitude);
            var marker2 = new google.maps.Marker({
                position: pointLatLng2,
                title: edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.name,
                label: edges[i].transitEdgeList[edges[i].transitEdgeList.length - 1].endPoint.name.charAt(0),
                opacity: 0.75,
                map: map
            });
            markers.push(marker2);
        }
    }
}
