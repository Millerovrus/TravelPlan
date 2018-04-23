
var autocompleteFrom, autocompleteTo;

var componentForm = {
    locality: 'long_name'
};

function initAutocompleteFields() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocompleteFrom = new google.maps.places.Autocomplete(
        document.getElementById('inputFrom'),
        // $('#inputFrom'),
        {types: ['(cities)']}
    );
    // When the user selects an address from the dropdown, populate the address
    autocompleteTo = new google.maps.places.Autocomplete(
        document.getElementById('inputTo'),
        // $('#inputTo'),
        {types: ['(cities)']}
    );

    autocompleteFrom.addListener('place_changed', fillInAddressFrom);
    autocompleteTo.addListener('place_changed', fillInAddressTo);
}

function fillInAddressFrom() {
    // Get the place details from the autocomplete object.
    var place = autocompleteFrom.getPlace();

    // document.getElementById('latit_longit_from').value = '';
    $('#latit_longit_from').val('');
    // document.getElementById('latit_longit_from').disabled = false;
    $('#latit_longit_from').prop('disabled', false);

    var temp = place.geometry.location;
    // document.getElementById('latit_longit_from').value = temp;
    $('#latit_longit_from').val(temp);

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            // document.getElementById('inputFromHidden').value = val;
            $('#inputFromHidden').val(val);
        }
    }

}
function fillInAddressTo() {
    // Get the place details from the autocomplete object.
    var place = autocompleteTo.getPlace();

    // document.getElementById('latit_longit_to').value = '';
    $('#latit_longit_to').val('');

    var temp = place.geometry.location;
    // document.getElementById('latit_longit_to').value = temp;
    $('#latit_longit_to').val(temp);

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            // document.getElementById('inputToHidden').value = val;
            $('#inputToHidden').val(val);
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
    // document.getElementById('latit_longit_from').value = '';
    $('#latit_longit_from').val('');
    // document.getElementById('inputFromHidden').value = '';
    $('#inputFromHidden').val('');
    // document.getElementById('inputFrom').value = '';
    $('#inputFrom').val('');
}

function clearTo() {
    // document.getElementById('latit_longit_to').value = '';
    $('#latit_longit_to').val('');
    // document.getElementById('inputToHidden').value = '';
    $('#inputToHidden').val('');
    // document.getElementById('inputTo').value = '';
    $('#inputTo').val('');
}

