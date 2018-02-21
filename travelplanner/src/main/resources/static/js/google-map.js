var coordinates = [
        {name: 'Voronezh', lat: 51.675, lng: 39.208},
        {name: 'Rostov', lat: 47.235, lng: 39.701},
        {name: 'Tbilisi', lat: 41.715, lng: 44.827}
];

function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: getCenter(coordinates),
        zoom: 5,
        gestureHandling: 'cooperative',
        minZoom: 3
    });

    // задаем стрелку
    var lineSymbol = {
        path: google.maps.SymbolPath.FORWARD_OPEN_ARROW,
        scale: 5,
        strokeColor: '#676780'
    };

    // создаем путь
    var line = new google.maps.Polyline({
        path: coordinates,
        icons: [{
            icon: lineSymbol,
            offset: '100%'
        }],
        strokeColor: '#ff0000',
        map: map
    });

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

//высчитываем центр карты
function getCenter(coordinates) {
    var center = {lat: 0.0, lng : 0.0};
    for (var i = 0; i < coordinates.length; i++){
        center.lat += coordinates[i].lat;
        center.lng += coordinates[i].lng;
    }
    center.lat /= coordinates.length;
    center.lng /= coordinates.length;
    return center;
}

//ставим маркеры
function setMarkers(map, coordinates) {
    for (var i = 0; i < coordinates.length; i++) {
        var marker = new google.maps.Marker({
            position: coordinates[i],
            title: coordinates[i].name,
            label: coordinates[i].name.charAt(0),
            opacity: 0.75,
            map: map
        });
    }
}

