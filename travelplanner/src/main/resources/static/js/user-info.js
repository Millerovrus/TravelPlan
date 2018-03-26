var checkme = document.getElementById('checker22');
var userName = document.getElementById('first-name');
var userPhone = document.getElementById('birth-date');
var userEmail = document.getElementById('user-email');
var userPlace = document.getElementById('last-name');
var fakeInput = document.getElementById('fake-input');
var baseInput = document.getElementById('base-input');
var editHeader = document.getElementById('edit-header');
var UserSend = document.getElementById('submit-edit');
function enableEdit() {
    if(checkme.checked){
        UserSend.disabled =0;
        userName.disabled =0;
        userPhone.disabled =0;
        userEmail.disabled =0;
        userPlace.disabled =0;
        fakeInput.style.display = 'block';
        baseInput.style.display = 'block';
        editHeader.style.display = 'none';
        UserSend.style.display = 'block';
    }
    else{
        UserSend.disabled =1;
        userName.disabled =1;
        userPhone.disabled =1;
        userEmail.disabled =1;
        userPlace.disabled =1;
        UserSend.style.display = 'none';
        fakeInput.style.display = 'none';
        baseInput.style.display = 'none';
    }
}
function saveEdit() {
    UserSend.disabled =1;
    userName.disabled =1;
    userPhone.disabled =1;
    userEmail.disabled =1;
    userPlace.disabled =1;
    editHeader.style.display = 'block';
    $("#checker22").attr("checked", false);
    fakeInput.style.display = 'none';
    baseInput.style.display = 'none';
    UserSend.style.display = 'none';
    document.getElementById('file-name').style.display = 'none';
}