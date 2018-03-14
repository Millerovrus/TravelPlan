
/* datetimepicker */
$(function () {
    $('#datetimepicker1').datetimepicker({
        locale: 'en',
        stepping: 10,
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

/*
$(function(){
    $('.panel').on('hidden.bs.collapse', function (e) {
        resetMap();
    });
});*/

