$(document).ready(function(){

    var globalUserReviewID;

    $(".nav-item").click(function(e){
        $('#closeButtonCollapse').click()
    });

    $("[data-trigger]").on("click", function(){
        var trigger_id =  $(this).attr('data-trigger');
        $(trigger_id).toggleClass("show");
        $('body').toggleClass("offcanvas-active");
        $('#closeButtonCollapse').prop("hidden",false);
    });

    $(".btn-close").click(function(e){
        $(".navbar-collapse").removeClass("show");
        $("body").removeClass("offcanvas-active");
        $('#closeButtonCollapse').prop("hidden",true);
    });

    $('.userFav').click(function(e){
        e.preventDefault()

        var strainName = $(this)[0].innerHTML

        window.location.href = "/strain/rest/"+strainName

    })

    $('.userRev').click(function(e){

        $('#reviewModal').modal({backdrop: 'static', keyboard: false})


        var reviewId = $(this)[0].id

        var url = "/reviews/"+reviewId

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json', // added data type
            success: function(res) {
                $("#reviewBodyUpdate")[0].value = res.body
                document.getElementById("updateModalTitle").innerHTML = "Edit or delete your <a href=\"/strain/rest/"+res.strain+"\">"+res.strain+"</a> review"
                globalUserReviewID = res.id
            }
        });
    })

    $('.userRev2').click(function(e){

        $('#reviewModal').modal({backdrop: 'static', keyboard: false})


        var reviewId = $(this)[0].id

        var url = "/reviews/"+reviewId

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json', // added data type
            success: function(res) {
                $("#reviewBodyUpdate")[0].value = res.body
                globalUserReviewID = res.id
            }
        });
    })

    $('#favoriteButton').click(function (e) {

        e.preventDefault();

        if (favLinkCheck()) return;

        var data = [{
            "strain": $('#strainName').text()
        }]

        var buttonText = $('#favoriteButton').text()

        if (buttonText.indexOf("add") != -1){
            $.ajax({
                url: "/strains/favorites",
                headers: {
                    'Content-Type':'application/json;charset=utf-8'
                },
                type: 'POST',
                data: JSON.stringify(data),
                dataType: 'json', // added data type
                success: function(res) {
                    document.getElementById("favoriteButton").innerHTML =
                        "<i class=\"fa fa-heart\" style=\"color: red\" aria-hidden=\"true\"></i> remove from favorites"
                },
                error: function(res){
                    console.log(res)
                }
            });
        } else {
            $.ajax({
                url: "/strains/removeFavorite",
                type: 'DELETE',
                headers: {
                    'Content-Type':'application/json;charset=utf-8'
                },
                data: JSON.stringify(data),
                dataType: 'json', // added data type
                success: function(res) {
                    document.getElementById("favoriteButton").innerHTML =
                        "<i class=\"fa fa-heart-o\" style=\"color: black\" aria-hidden=\"true\"></i> add to favorites"
                },
                error: function(res){
                    console.log(res)
                }
            });
        }

    })

    $('#deleteFav').click(function () {

        var sure = confirm("Are you sure you want to delete?");

        if (sure) {
            deleteFav()
        }

    })

    $('#deleteAcc').click(function () {

        var sure = confirm("Are you sure you want to delete your account? Your account information cannot be recovered.");

        if (sure) {
            deleteAcc()
        }

    })

    $('#reviewBody').on('keyup', function () {

        var reviewBody = $('#reviewBody')[0].value

        if (reviewBody.length == 0 | reviewBody.trim().length == 0 | usernameForRev.length == 0){
            $('#addRevButton').prop("disabled",true);
        } else {
            $('#addRevButton').prop("disabled",false);
        }

    })
    $('#reviewBodyUpdate').on('keyup', function () {

        var reviewUpdateBody = $('#reviewBodyUpdate')[0].value

        if (reviewUpdateBody.length == 0 | reviewUpdateBody.trim().length == 0){
            $('#updateRevButton').prop("disabled",true);
        } else {
            $('#updateRevButton').prop("disabled",false);
        }

    })

    $('#deleteReview').click(function () {
        var sure = confirm("Are you sure you want to delete this review?")
        if (sure) {
            deleteReview()
        }
    })

    $('#addRevButton').click(function() {

        var reviewBody = $('#reviewBody')[0].value
        var date = new Date();//formatDate(new Date());

        var data = {
            "body": reviewBody,
            "user": usernameForRev,
            "strain": strainForRev,
            "createdDate": date.toString(),
            "rating": globalStrainRating
        }

        $.ajax({
            url: "/reviews/add",
            headers: {
                'Content-Type':'application/json;charset=utf-8'
            },
            type: 'POST',
            data: JSON.stringify(data),
            dataType: 'json', // added data type
            success: function(res) {
                window.location.reload()
            },
            error: function(res){
                console.log(res)
            }
        });
    })

    $('#addRev').click(function () {

        $("#rateYo").rateYo("option", "rating", "0");

        $('#addReviewModal').modal({backdrop: 'static', keyboard: false})

        var reviewBody = $('#reviewBody')[0].value

        if (reviewBody.length == 0 | reviewBody.trim().length == 0  | usernameForRev.length == 0){
            $('#addRevButton').prop("disabled",true);
        } else {
            $('#addRevButton').prop("disabled",false);
        }
    })
    $('#updateRev').click(function () {

        $('#reviewModal').modal({backdrop: 'static', keyboard: false})

        var reviewBody = $('#reviewBodyUpdate')[0].value

        if (reviewBody.length == 0 | reviewBody.trim().length == 0  | usernameForRev.length == 0){
            $('#updateRevButton').prop("disabled",true);
        } else {
            $('#updateRevButton').prop("disabled",false);
        }
    })

    $('#registerUser').click(function() {
        console.log("test")
    })

    $('#updateRevButton').click(function () {

        var updatedDate = new Date();

        data = {
            "id" :globalUserReviewID,
            "body": $("#reviewBodyUpdate")[0].value,
            "updatedDate": updatedDate.toString()
        }

        $.ajax({
            url: "/reviews/update",
            headers: {
                'Content-Type':'application/json;charset=utf-8'
            },
            type: 'POST',
            data: JSON.stringify(data),
            dataType: 'json', // added data type
            success: function(res) {
                window.location.reload()
            },
            error: function(res){
                console.log(res)
            }
        });
    })

    $('.userFavCheckbox').click(function () {
        var totalChecked = $('input[name="checkboxName"]:checked').length;

        if (totalChecked > 0){
            $('#deleteFav').prop("hidden",false);
        }
        if (totalChecked == 0){
            $('#deleteFav').prop("hidden",true);
        }

    })

    function deleteReview() {

        var data = {
            "id": globalUserReviewID
        }

        $.ajax({
            url: "/reviews/delete",
            type: 'DELETE',
            headers: {
                'Content-Type':'application/json;charset=utf-8'
            },
            data: JSON.stringify(data),
            success: function(res) {
                window.location.reload()
            },
            error: function(res){
                console.log(res)
            }
        });
    }

})

function deleteFav() {
    var data = []

    $('.userFavCheckbox:checked').each(function () {
        var obj = {"strain": $(this)[0].value}
        data.push(obj);
    })

    $.ajax({
        url: "/strains/removeFavorite",
        type: 'DELETE',
        headers: {
            'Content-Type':'application/json;charset=utf-8'
        },
        data: JSON.stringify(data),
        dataType: 'json', // added data type
        success: function(res) {
            $('.userFavCheckbox:checked').each(function () {
                $(this).remove();
            })

            for (var i = 0; i < data.length; i++){
                var val = data[i].strain
                var elem = document.getElementById(val)
                elem.parentNode.removeChild(elem)
            }

            var totalChecked = $('input[name="checkboxName"]:checked').length;

            if (totalChecked == 0) $('#deleteFav').prop("hidden",true);
        },
        error: function(res){
            console.log(res)
        }
    });
}

function deleteAcc() {

    var email = document.getElementById("email").innerHTML

    $.ajax({
        url: "/delete/acc",
        type: 'DELETE',
        headers: {
            'Content-Type':'application/json;charset=utf-8'
        },
        data: JSON.stringify(email),
        success: function(res) {
            window.location.href = "/"
        },
        error: function(res){
            console.log(res)
        }
    });
}

function favLinkCheck() {
    if (disabledFavLink){
        window.location.href = "/login"
    }

    return disabledFavLink
}

function checkPassword(str)
{
    // at least one number, one lowercase and one uppercase letter
    // at least six characters
    var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
    return re.test(str);
}

function formatDate(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + " " + strTime;
}