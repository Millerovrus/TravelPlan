var coordinates = [
        {name: 'Voronezh', lat: 51.675, lng: 39.208},
        {name: 'Rostov', lat: 47.235, lng: 39.701},
        {name: 'Tbilisi', lat: 41.715, lng: 44.827}
];

function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        gestureHandling: 'cooperative',
        mapTypeControl: false,
        streetViewControl: false,

        //можно запретить пользователям двигать и зуммировать карту
        // draggable: false,
        //отключает весь интерфейс карты
        // disableDefaultUI: true,
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

    // задаем стрелку
    var lineSymbol = {
        path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
        scale: 5,
        strokeOpacity: 0.7,
        strokeColor: '#fff000'
    };

    // создаем путь
    var line = new google.maps.Polyline({
        path: coordinates,
        icons: [{
            icon: lineSymbol,
            offset: '100%'
        }],
        strokeColor: '#ffffff',
        strokeOpacity: 0.3,
        strokeWeight: 4,
        map: map
    });

    adaptZoomAndCenter(map, coordinates);
    setMarkers(map, coordinates);
    animateArrow(line);
}

//анимируем символ
function animateArrow(line) {
    var count = 0;
    window.setInterval(function() {
        count = (count + 1) % 200;
        var icons = line.get('icons');
        icons[0].offset = (count / 2) + '%';
        line.set('icons', icons);
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

//ставим маркеры и адаптивный зум и центр
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

