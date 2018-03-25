
/* datetimepicker */
$(function () {
    $('#datetimepicker1').datetimepicker({
        locale: 'en',
        stepping: 10,
        minDate: new Date(),
        format: 'YYYY-MM-DD'
    });
});

/* stop dropdown closing while choosing number of passengers*/
$(function () {
    $('.dropdown-menu').click(function(e) {
        e.stopPropagation();
    });
});

/* spinner + -*/
$(function () {
    $("input[name='spinner-adult']").TouchSpin({
        min: 1,
        max: 5,
        step: 1,
        verticalupclass: 'fas fa-plus'
    });
});
$(function () {
    $("input[name='spinner-children']").TouchSpin({
        min: 0,
        max: 4,
        step: 1,
        verticalupclass: 'fas fa-plus'
    });
});

/*
$(function(){
    $("#accordion1").accordion({
        collapsible: true,
        active: false
    });
});*/

/*fix map*/
$(function(){
    $('#map-margin').scrollToFixed({
        marginTop: 65,
        limit: $('#div-to-fix'),
        zIndex: 0
    });
});
/* todo to fix limit in div without jumping out from container after limit */


/* user information */

$('input[id=base-input]').change(function() {
    $('#fake-input').val($(this).val().replace("C:\\", ""));
});

<!--==================Javascript code for custom input type file on button ================-->

$('input[id=main-input]').change(function() {
    console.log($(this).val());
    var mainValue = $(this).val();
    if(mainValue == ""){
        document.getElementById("fake-btn").innerHTML = "Choose File";
    }else{
        document.getElementById("fake-btn").innerHTML = mainValue.replace("C:\\fakepath\\", "");
    }
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('.img-circle')
                .attr('src', e.target.result)
                .width(128)
                .height(128);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$(function() {
    $('#profile-image1').on('click', function() {
        $('#profile-image-upload').click();
    });
})