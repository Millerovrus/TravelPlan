
var autocomplete1, autocomplete2;

var componentForm = {
    locality: 'long_name'
    // administrative_area_level_1: 'short_name'
};

function initAutocompleteFields() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete1 = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('inputFrom')),
        {types: ['(cities)']});
    // When the user selects an address from the dropdown, populate the address
    autocomplete2 = new google.maps.places.Autocomplete(
        document.getElementById('inputTo'),
        {types: ['(cities)']});

    // fields in the form.

    autocomplete1.addListener('place_changed', fillInAddress);
    autocomplete2.addListener('place_changed', fillInAddress1);

}

function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete1.getPlace();

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
function fillInAddress1() {
    // Get the place details from the autocomplete object.
    var place = autocomplete2.getPlace();

    document.getElementById('latit_longit_to').value = '';
    document.getElementById('latit_longit_to').disabled = false;

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
            autocomplete1.setBounds(circle.getBounds());
        });
    }
}

