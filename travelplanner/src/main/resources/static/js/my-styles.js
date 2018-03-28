
/* datetimepicker */
$(function () {
    $('#datetimepicker1').datetimepicker({
        locale: 'en',
        stepping: 10,
        minDate: new Date(),
        format: 'YYYY-MM-DD'
    });
});

$(function () {
    $('#inputDate').datetimepicker({
        // orientation: "auto",
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
        document.getElementById('file-name').style.display = 'block';
    }
}

/* parameter menu*/
jQuery(document).on('click', '.mega-dropdown', function(e) {
    e.stopPropagation()
});

$(function () {
    $(".input").focus(function() {
        $(this).parent().addClass("focus");
    })
});

/* slider */
$(document).ready(function() {

    var $element = $('input[type="range"]');
    var $handle;

    $element.rangeslider({
        polyfill: false,
        onInit: function() {
            $handle = $('.rangeslider__handle', this.$range);
            updateHandle($handle[0], this.value);
            $("#amount-label").html('<span class="pricing__dollar">€</span>' + this.value);
        }
    }).on('input', function() {
        updateHandle($handle[0], this.value);
        $("#amount-label").html('<span class="pricing__dollar">€</span>' + this.value);
    });

    function updateHandle(el, val) {
        el.textContent = val;
    }

    $('input[type="range"]').rangeslider();

});

/* */
function getAlert() {
    alert("Ошибочка блин");
}

