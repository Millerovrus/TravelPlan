
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
    $("input[name='demo_vertical']").TouchSpin({
        min: 0,
        max: 10,
        step: 1,
        verticalupclass: 'fas fa-plus'
        /* verticalbuttons: true,
        verticalupclass: 'fa fa-caret-up',
        verticaldownclass: 'fa fa-caret-down'*/
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
    $('#fake-input').val($(this).val().replace("C:\\fakepath\\", ""));
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

<!--=========================input type file change on button ends here====================-->

//    ===================== snippet for profile picture change ============================ //

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('.imgCircle')
                .attr('src', e.target.result)
                .width(200)
                .height(200);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

//    =================================== ends here ============================================ //

var checkme = document.getElementById('checker');
var userImage = document.getElementById('image-input');
var userName = document.getElementById('name');
var userPhone = document.getElementById('phone');
var userEmail = document.getElementById('email');
var userPlace = document.getElementById('place');
var UserSend = document.getElementById('submit');
var editPic = document.getElementById('PicUpload');
checkme.onchange = function() {
    UserSend.disabled = !this.checked;
    userImage.disabled = !this.checked;
    userName.disabled = !this.checked;
    userPhone.disabled = !this.checked;
    userEmail.disabled = !this.checked;
    userPlace.disabled = !this.checked;
    editPic.style.display = this.checked ? 'block' : 'none';
};
