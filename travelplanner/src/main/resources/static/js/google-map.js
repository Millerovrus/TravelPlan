var coordinates = [
        {name: 'Voronezh', lat: 51.675, lng: 39.208, type: 'bus'},
        {name: 'Rostov', lat: 47.235, lng: 39.701, type: 'plane'},
        {name: 'Tbilisi', lat: 41.715, lng: 44.827, type: 'finish'}
];

function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        gestureHandling: 'cooperative',
        minZoom: 2,
        styles: [
            {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
            {
                featureType: 'administrative.locality',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'poi',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'poi.park',
                elementType: 'geometry',
                stylers: [{color: '#263c3f'}]
            },
            {
                featureType: 'poi.park',
                elementType: 'labels.text.fill',
                stylers: [{color: '#6b9a76'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry',
                stylers: [{color: '#38414e'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry.stroke',
                stylers: [{color: '#212a37'}]
            },
            {
                featureType: 'road',
                elementType: 'labels.text.fill',
                stylers: [{color: '#9ca5b3'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry',
                stylers: [{color: '#746855'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry.stroke',
                stylers: [{color: '#1f2835'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'labels.text.fill',
                stylers: [{color: '#f3d19c'}]
            },
            {
                featureType: 'transit',
                elementType: 'geometry',
                stylers: [{color: '#2f3948'}]
            },
            {
                featureType: 'transit.station',
                elementType: 'labels.text.fill',
                stylers: [{color: '#d59563'}]
            },
            {
                featureType: 'water',
                elementType: 'geometry',
                stylers: [{color: '#17263c'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.fill',
                stylers: [{color: '#515c6d'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.stroke',
                stylers: [{color: '#17263c'}]
            }
        ]
    });

    var lines = [];
    var speed = [];
    for (var i = 0; i < coordinates.length-1; i++) {
        var from = new google.maps.LatLng(coordinates[i].lat, coordinates[i].lng);
        var to = new google.maps.LatLng(coordinates[i+1].lat, coordinates[i+1].lng);
        switch (coordinates[i].type) {
            case 'plane':
                speed.push(1);
                break;
            case 'bus':
                speed.push(0.5);
                break;
            case 'train':
                speed.push(0.75);
                break;
            case 'finish':
                break;
            default:
                alert( 'Я таких значений не знаю' );
        }
        createLine(map, from, to, coordinates[i].type, lines);
    }

    adaptZoomAndCenter(map, coordinates);
    setMarkers(map, coordinates);
    animateSymbols(lines, speed);
}


function createLine(map, from, to, type, lines) {
    var lineSymbol;
    // задаем иконки
    switch (type) {
        case 'bus':
            lineSymbol = {
                path:'m.275-13.946c-3.544 0-6.414.418-6.414 3.343l0 8.358c0 .74.313 1.396.802 1.855l0 1.488c0 .46.361 .836.802 .836l.802 0c.445 0 .802-.376.802-.836l0-.836 6.414 0 0 .836c0 .46.357 .836.802 .836l.802 0c.441 0 .802-.376.802-.836l0-1.488c.489-.46.802-1.116.802-1.855l0-8.358c0-2.925-2.87-3.343-6.414-3.343zm-3.608 12.537c-.665 0-1.203-.56-1.203-1.254s.537-1.254 1.203-1.254 1.203.56 1.203 1.254-.537 1.254-1.203 1.254zm7.216 0c-.665 0-1.203-.56-1.203-1.254s.537-1.254 1.203-1.254 1.203.56 1.203 1.254-.537 1.254-1.203 1.254zm1.203-5.015-9.621 0 0-4.179 9.621 0 0 4.179z',
                scale: 2,
                rotation: 180,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
                strokeColor: 'yellow',
                fillColor: 'black'
            };
            break;
        case 'plane':
            lineSymbol = {
                path: 'M3.666 4.902V4.129L.571 2.195V.067c0-.321-.259-.58-.58-.58s-.58.259-.58.58v2.127L-3.684 4.129v.774l3.095-.967v2.127l-.774.58v.58l1.354-.387L1.345 7.223v-.58l-.774-.58V3.935L3.666 4.902z',
                scale: 4,
                rotation: 0,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
                strokeColor: 'yellow',
                fillColor: 'black'
            };
            break;
        case 'finish':
            break;
        default:
            lineSymbol = {
                path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
                scale: 5,
                strokeOpacity: 0.0,
                fillOpacity: 0.0,
                strokeColor: 'yellow',
                fillColor: 'white'
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
        strokeColor: '#ffffff',
        strokeOpacity: 0.3,
        strokeWeight: 4,
        map: map
    });

    lines.push(line);
}

//анимируем символ
function animateSymbols(lines, speed) {
    var i = 0;
    window.setInterval(function() {
        if(parseInt(lines[i].icons[0].offset) < 100) {
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
        if (i == lines.length){
            for (var j = 0; j < lines.length; j++){
                var icons3 = lines[j].get('icons');
                icons3[0].offset = '0%';
                lines[j].set('icons', icons3);
                i = 0;
            }
        }
    }, 50);
}

//адаптируем зум и центр карты под координаты
function adaptZoomAndCenter(map, coordinates) {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < coordinates.length; i++) {
        var pointLatLng = new google.maps.LatLng(coordinates[i].lat, coordinates[i].lng);
        bounds.extend(pointLatLng);
    }
    map.fitBounds(bounds);
}

//ставим маркеры
function setMarkers(map, coordinates) {
    for (var i = 0; i < coordinates.length; i++) {
        var pointLatLng = new google.maps.LatLng(coordinates[i].lat, coordinates[i].lng);
        var marker = new google.maps.Marker({
            position: pointLatLng,
            title: coordinates[i].name,
            label: coordinates[i].name.charAt(0),
            opacity: 0.75,
            map: map
        });
    }
}

