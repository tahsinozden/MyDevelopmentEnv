//var inputs = $('input[type="text"],input[type="password"]');

function reset_inputs() {
//    var inputs = $('input[type="text"],input[type="password"]');
    var inputs = $(".container").find('input[type="text"],input[type="password"]');
//    console.log($("#email").css());
    //  clear text box contents
    $(".container").find('input[type="text"],input[type="password"]').val("");
    inputs.css("border", "2px solid #00F5FF");
    inputs.css("box-shadow", "0 0 5px #00F5FF");
}

$(document).ready(function () {
    // slide up and down when button is clicked
    var inputs = $('input[type="text"],input[type="password"]');
    $("#btnLoginOpt").click(function () {
        reset_inputs();
        $("#login").css("display", "inline");
        $(".subsribe_section").css("display", "none");
        $("div.container").slideDown("fast");


    });

    $("#btnSubscribeOpt").click(function () {
        reset_inputs();
        $("#login").css("display", "none");
        $(".subsribe_section").css("display", "inline");
        $("div.container").slideDown("fast");

    });

    $("#login").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        var repassword = $("#repassword").val();
// Checking for blank fields.
        if (email == '' || password == '') {
            inputs.css("border", "2px solid red");
            inputs.css("box-shadow", "0 0 3px red");
//            $('input[type="text"],input[type="password"]').addClass("input_error");
            alert("Please fill all fields...!!!!!!");
        }
        else {
//            $.post("login.php", {email1: email, password1: password},
//            function (data) {
//                if (data == 'Invalid Email.......') {
//                    $('input[type="text"]').css({"border": "2px solid red", "box-shadow": "0 0 3px red"});
//                    $('input[type="password"]').css({"border": "2px solid #00F5FF", "box-shadow": "0 0 5px #00F5FF"});
//                    alert(data);
//                } else if (data == 'Email or Password is wrong...!!!!') {
//                    $('input[type="text"],input[type="password"]').css({"border": "2px solid red", "box-shadow": "0 0 3px red"});
//                    alert(data);
//                } else if (data == 'Successfully Logged in...') {
//                    $("form")[0].reset();
//                    $('input[type="text"],input[type="password"]').css({"border": "2px solid #00F5FF", "box-shadow": "0 0 5px #00F5FF"});
//                    alert(data);
//                } else {
//                    alert(data);
//                }
//            });
        }
    });

    $("#btnSubscribe").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        var repassword = $("#repassword").val();
// Checking for blank fields.
        if (email == '' || password == '') {
            inputs.css("border", "2px solid red");
            inputs.css("box-shadow", "0 0 3px red");
            alert("Please fill all fields...!!!!!!");
        }
        else if (password !== repassword) {
            $('input[type="password"]').css("border", "2px solid red");
            $('input[type="password"]').css("box-shadow", "0 0 3px red");
            alert("Passwords not match!");
        }
        else {
//            $.post("login.php", {email1: email, password1: password},
//            function (data) {
//                if (data == 'Invalid Email.......') {
//                    $('input[type="text"]').css({"border": "2px solid red", "box-shadow": "0 0 3px red"});
//                    $('input[type="password"]').css({"border": "2px solid #00F5FF", "box-shadow": "0 0 5px #00F5FF"});
//                    alert(data);
//                } else if (data == 'Email or Password is wrong...!!!!') {
//                    $('input[type="text"],input[type="password"]').css({"border": "2px solid red", "box-shadow": "0 0 3px red"});
//                    alert(data);
//                } else if (data == 'Successfully Logged in...') {
//                    $("form")[0].reset();
//                    $('input[type="text"],input[type="password"]').css({"border": "2px solid #00F5FF", "box-shadow": "0 0 5px #00F5FF"});
//                    alert(data);
//                } else {
//                    alert(data);
//                }
//            });
        }
    });
});