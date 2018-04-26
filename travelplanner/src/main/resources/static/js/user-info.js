// можно удалить весь файл

var checkme = document.getElementById('checker22');
var userName = document.getElementById('first-name');
var userPhone = document.getElementById('birth-date');
var userAvatar = document.getElementById('avatar');
var userAvatarClass = document.getElementById('avatar-class');
var userPlace = document.getElementById('last-name');
var editHeader = document.getElementById('edit-header');
var UserSend = document.getElementById('submit-edit');
function enableEdit() {
    if(checkme.checked){
        UserSend.disabled =0;
        userName.disabled =0;
        userPhone.disabled =0;
        userAvatar.disabled =0;
        userAvatar.style.display = 'block';
        userAvatarClass.style.display='block';
        userPlace.disabled =0;
        editHeader.style.display = 'none';
        UserSend.style.display = 'block';
    }
    else{
        UserSend.disabled =1;
        userName.disabled =1;
        userPhone.disabled =1;
        userAvatar.disabled =1;
        userAvatar.style.display = 'none';
        userAvatarClass.style.display='none';
        userPlace.disabled =1;
        UserSend.style.display = 'none';
    }
}
function saveEdit() {
    UserSend.disabled =1;
    userName.disabled =1;
    userPhone.disabled =1;
    //userEmail.disabled =1;
    userAvatar.disabled =1;
    userAvatar.style.display = 'none';
    userAvatarClass.style.display='none';
    userPlace.disabled =1;
    editHeader.style.display = 'block';
    $("#checker22").attr("checked", false);
    UserSend.style.display = 'none';
    document.getElementById('file-name').style.display = 'none';
}

