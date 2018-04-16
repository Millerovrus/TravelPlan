
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
// $(function () {
//     $("input[name='spinner-adults']").TouchSpin({
//         min: 1,
//         max: 9,
//         step: 1
//         // buttondown_class: "btn button-spin",
//         // buttonup_class: "btn button-spin",
//         // verticalupclass: 'glyphicon glyphicon-chevron-up',
//         // verticaldownclass: 'glyphicon glyphicon-chevron-down'
//     });
// });
//
// $(function () {
//     $("input[name='spinner-children']").TouchSpin({
//         min: 0,
//         max: 5,
//         step: 1
//         // buttondown_class: "btn button-spin",
//         // buttonup_class: "btn button-spin",
//         // verticalupclass: 'glyphicon glyphicon-chevron-up',
//         // verticaldownclass: 'glyphicon glyphicon-chevron-down'
//     });
// });

/*fix map*/
$(function(){
    $('#map-margin').scrollToFixed({
        marginTop: 65,
        limit: $('#div-to-fix'),
        zIndex: 0
    });
});
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

/*accordion */
$(document).ready(function() {

    var bodyEl = $('body'),
        accordionDT = $('.accordion').find('dt'),
        accordionDD = accordionDT.next('dd'),
        parentHeight = accordionDD.height(),
        childHeight = accordionDD.children('.content').outerHeight(true),
        newHeight = parentHeight > 0 ? 0 : childHeight,
        accordionPanel = $('.accordion-panel'),
        buttonsWrapper = accordionPanel.find('.buttons-wrapper'),
        openBtn = accordionPanel.find('.open-btn'),
        closeBtn = accordionPanel.find('.close-btn');

    bodyEl.on('click', function(argument) {
        var totalItems = $('.accordion').children('dt').length;
        var totalItemsOpen = $('.accordion').children('dt.is-open').length;

        if (totalItems == totalItemsOpen) {
            openBtn.addClass('hidden');
            closeBtn.removeClass('hidden');
            buttonsWrapper.addClass('is-open');
        } else {
            openBtn.removeClass('hidden');
            closeBtn.addClass('hidden');
            buttonsWrapper.removeClass('is-open');
        }
    });

    function openAll() {

        openBtn.on('click', function(argument) {

            accordionDD.each(function(argument) {
                var eachNewHeight = $(this).children('.content').outerHeight(true);
                $(this).css({
                    height: eachNewHeight
                });
            });
            accordionDT.addClass('is-open');
        });
    }

    function closeAll() {

        closeBtn.on('click', function(argument) {
            accordionDD.css({
                height: 0
            });
            accordionDT.removeClass('is-open');
        });
    }

    function openCloseItem() {
        accordionDT.on('click', function() {

            var el = $(this),
                target = el.next('dd'),
                parentHeight = target.height(),
                childHeight = target.children('.content').outerHeight(true),
                newHeight = parentHeight > 0 ? 0 : childHeight;

            // animate to new height
            target.css({
                height: newHeight
            });

            // remove existing classes & add class to clicked target
            if (!el.hasClass('is-open')) {
                el.addClass('is-open');
            }

            // if we are on clicked target then remove the class
            else {
                el.removeClass('is-open');
            }
        });
    }

    openAll();
    closeAll();
    openCloseItem();
});

/* spinner + - */
$(document).ready(function(){
    $("#num-picker-adults").dpNumberPicker({
        min: 1, // Minimum value.
        max: 7, // Maximum value.
        value: 1, // Initial value
        step: 1, // Incremental/decremental step on up/down change.
        format: false,
        editable: true,
        addText: "+",
        subText: "-",
        formatter: function(val){return val;},
        beforeIncrease: function(){},
        afterIncrease: function(){},
        beforeDecrease: function(){},
        afterDecrease: function(){},
        beforeChange: function(){},
        afterChange: function(){},
        onMin: function(){},
        onMax: function(){}
})});

$(document).ready(function(){
    $("#num-picker-children").dpNumberPicker({
        min: 0, // Minimum value.
        max: 5, // Maximum value.
        value: 0, // Initial value
        step: 1, // Incremental/decremental step on up/down change.
        format: false,
        editable: true,
        addText: "+",
        subText: "-",
        formatter: function(val){return val;},
        beforeIncrease: function(){},
        afterIncrease: function(){},
        beforeDecrease: function(){},
        afterDecrease: function(){},
        beforeChange: function(){},
        afterChange: function(){},
        onMin: function(){},
        onMax: function(){}
    })});

$(document).ready(function(){
    $("#num-picker-infants").dpNumberPicker({
        min: 0, // Minimum value.
        max: 5, // Maximum value.
        value: 0, // Initial value
        step: 1, // Incremental/decremental step on up/down change.
        format: false,
        editable: true,
        addText: "+",
        subText: "-",
        formatter: function(val){return val;},
        beforeIncrease: function(){},
        afterIncrease: function(){},
        beforeDecrease: function(){},
        afterDecrease: function(){},
        beforeChange: function(){},
        afterChange: function(){},
        onMin: function(){},
        onMax: function(){}
    })});


/* test */
function getValue() {
    var valuee = document.getElementById('testval').value;
    alert(valuee);

}
