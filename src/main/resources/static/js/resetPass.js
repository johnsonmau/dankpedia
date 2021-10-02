$(document).ready(function() {

    $('#changePassButton').prop("disabled", true);


    $('#changePassButton').click(function (e) {

        var pass = $('#password').val()
        var tokenParam = get("token")

        if (tokenParam == undefined){
            tokenParam = "emptytoken"
        }

        var data = {
            "password": pass,
            "token": tokenParam
        }

        $.ajax({
            url: "/reset-password",
            type: 'POST',
            headers: {
                'Content-Type':'application/json;charset=utf-8'
            },
            data: JSON.stringify(data),
            success: function(res) {
                if (res == "Invalid token."){
                    window.location.href = "/reset-password?invalid"
                }
                if (res == "Token expired."){
                    window.location.href = "/reset-password?expired"
                }
                if (res == "Your password successfully updated."){
                    window.location.href = "/reset-password?success"
                }
            },
            error: function(res){
                console.log("error")
                console.log(res)
            }
        });
    })

    $('#password').on('keyup', function () {

        var pass = $('#password').val()
        var confirmPass = $('#confirmPassword').val()

        if (pass.length < 8 | confirmPass.length < 8) {
            $('#changePassButton').prop("disabled", true);
        } else if (pass != confirmPass){
            $('#changePassButton').prop("disabled", true);
            $('#changePassError').prop("hidden", false);
            document.getElementById("changePassError").innerHTML = "Passwords must be equal.";
            document.getElementById("changePassError").style.color = "red";
        } else {
            $('#changePassError').prop("hidden", true);
            $('#changePassButton').prop("disabled", false);
        }

    })
    $('#confirmPassword').on('keyup', function () {

        var pass = $('#password').val()
        var confirmPass = $('#confirmPassword').val()

        if (pass.length < 8 | confirmPass.length < 8) {
            $('#changePassButton').prop("disabled", true);
        } else if (pass != confirmPass){
            $('#changePassButton').prop("disabled", true);
            $('#changePassError').prop("hidden", false);
            document.getElementById("changePassError").innerHTML = "Passwords must be equal.";
            document.getElementById("changePassError").style.color = "red";
        } else {
            $('#changePassError').prop("hidden", true);
            $('#changePassButton').prop("disabled", false);
        }
    })

})

function get(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}