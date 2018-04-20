
var autocompleteFrom, autocompleteTo;

var componentForm = {
    locality: 'long_name'
};

function initAutocompleteFields() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocompleteFrom = new google.maps.places.Autocomplete(
        document.getElementById('inputFrom'),
        {types: ['(cities)']}
    );
    // When the user selects an address from the dropdown, populate the address
    autocompleteTo = new google.maps.places.Autocomplete(
        document.getElementById('inputTo'),
        {types: ['(cities)']}
    );

    autocompleteFrom.addListener('place_changed', fillInAddressFrom);
    autocompleteTo.addListener('place_changed', fillInAddressTo);
}

function fillInAddressFrom() {
    // Get the place details from the autocomplete object.
    var place = autocompleteFrom.getPlace();

    document.getElementById('latit_longit_from').value = '';
    document.getElementById('latit_longit_from').disabled = false;

    var temp = place.geometry.location;
    document.getElementById('latit_longit_from').value = temp;

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById('inputFromHidden').value = val;
        }
    }

}
function fillInAddressTo() {
    // Get the place details from the autocomplete object.
    var place = autocompleteTo.getPlace();

    document.getElementById('latit_longit_to').value = '';

    var temp = place.geometry.location;
    document.getElementById('latit_longit_to').value = temp;

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById('inputToHidden').value = val;
        }
    }


}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocompleteFrom.setBounds(circle.getBounds());
            autocompleteTo.setBounds(circle.getBounds());
        });
    }
}

function clearFrom() {
    document.getElementById('latit_longit_from').value = '';
    document.getElementById('inputFromHidden').value = '';
    document.getElementById('inputFrom').value = '';
}

function clearTo() {
    document.getElementById('latit_longit_to').value = '';
    document.getElementById('inputToHidden').value = '';
    document.getElementById('inputTo').value = '';
}

