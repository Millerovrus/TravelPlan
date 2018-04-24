/* datetimepicker */
$(function () {
    $('#inputBirthDate').datetimepicker({
        locale: 'en',
        stepping: 10,
        format: 'YYYY-MM-DD',
        viewMode: 'years',
        widgetPositioning: {
            horizontal: 'right',
            vertical: 'bottom'
        }
    });
});

$(function () {
    $('#birth-date').datetimepicker({
        locale: 'en',
        stepping: 10,
        format: 'YYYY-MM-DD',
        viewMode: 'years',
        widgetPositioning: {
            horizontal: 'right',
            vertical: 'top'
        }
    });
});