$(document).ready(function() {
    $('#info_alter').hide();
    $('#myCarousel').carousel({
        interval: 3000
    })
});

function login_btn() {
    $('#info_alter').hide();
    var username = $("#username").val();
    var userpass = $("#userpass").val();
        $.ajax({
            type: "GET",
            url : "/checkLogin",
            data : {"username":username,"userpass":userpass},
            success : function(data) {
                if(data) {
                    window.location.href="/dosyxx";
                }else {
                    $("#username").val("");
                    $("#userpass").val("");
                    $('#info_alter').show();
                    //alert("注意:密码或账户输入错误!")
                }

            }
        });
}