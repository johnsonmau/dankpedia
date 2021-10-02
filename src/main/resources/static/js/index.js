
console.log(document.readyState)
$(document).ready(function(){

    console.log(document.readyState)

    $('body').scrollspy({ target: '.navbar' })

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

    // Add smooth scrolling to all links in navbar + footer link
    $(".navbar-nav a, footer a[href='#myPage']").on('click', function(event) {
        // Make sure this.hash has a value before overriding default behavior
        if (this.hash !== "") {
            // Prevent default anchor click behavior
            event.preventDefault();

            // Store hash
            var hash = this.hash;

            // Using jQuery's animate() method to add smooth page scroll
            // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
            $('html, body').animate({
                scrollTop: $(hash).offset().top
            }, 900, function(){

                // Add hash (#) to URL when done scrolling (default click behavior)
                window.location.hash = hash;
            });
        } // End if
    });

    $(window).scroll(function() {
        $(".slideanim").each(function(){
            var pos = $(this).offset().top;

            var winTop = $(window).scrollTop();
            if (pos < winTop + 600) {
                $(this).addClass("slide");
            }
        });
    });

    var theNum = 1;
    var otherNum = 1;

    $('#next').click(function(){

        var searchStr = $('#strainSearch').val()
        var sortVal = $('#dropdownTitle2').text()


        if (sortVal != "Rating" || sortVal != "Alphabetical Order"){
            sortVal = ""
        }

        if (searchStr.length > 0) {
            getStrainSearch(otherNum, searchStr, sortVal)
            otherNum++
        } else {
            getStrainPage(theNum)
            theNum++
        }

    });

    $('#prev').click(function(){

        var searchStr = $('#strainSearch').val()
        var sortVal = $('#dropdownTitle2').text()

        if (sortVal != "Rating" || sortVal != "Alphabetical Order"){
            sortVal = ""
        }

        if (searchStr.length > 0) {
            var theOtherNum2 = otherNum - 1
            getStrainSearch(--theOtherNum2, searchStr, sortVal)
            otherNum--
            theOtherNum2--
        } else {
            var theNum2 = theNum - 1
            getStrainPage(--theNum2)
            theNum2--
            theNum--
        }
    });

    $('#resetButton').click(function(){

        theNum = 1;
        otherNum = 1;

        $('#prev').prop("disabled",true);

        var url = "/strains/type?page=0&type=&keyword=&sortType="

        document.getElementById("dropdownTitle2").innerHTML = "Sort "+"<span class=\"caret\"></span>"
        document.getElementById("dropdownTitle").innerHTML = "Type "+"<span class=\"caret\"></span>"
        $('#strainSearch').val("")


        ajaxCall(url)
    })

    $('.strainPageLink').click(function(e){
        e.preventDefault()
        window.location.href = "/strain/rest/"+$(this)[0].innerHTML
    })

    $('.aStrain').on("mouseover", function () {
        $(this).css('color', 'green');
        $(this).css('cursor', 'pointer');
        $(this).css('font-size', '16px');
    });
    $('.aStrain').on("mouseout", function () {
        $(this).css('color', 'white');
        $(this).css('font-size', '15px');
    });

    $('.aStrain').click(function(e){
        e.preventDefault()

        var strainData = $(this)[0].innerHTML

        var url = "/strains/strain?strain="+strainData

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json', // added data type
            success: function(res) {
                var strainName = res.strain
                var strainType = res.stype.substr(0,1).toUpperCase()+res.stype.substr(1)
                var strainEffects = res.effects
                var strainRating = res.rating
                var strainFlavor = res.flavor
                var strainDesc = res.descr

                $('#strainName').text(strainName)
                $('#theStrainType').text(strainType)
                $('#theEffects').text(strainEffects)
                $('#theRating').text(strainRating)
                $('#theFlavor').text(strainFlavor)
                $('#theDesc').text(strainDesc)


                if (res.usersFavorite == true){
                    document.getElementById("favoriteButton").innerHTML =
                        "<i class=\"fa fa-heart\" style=\"color: red\" aria-hidden=\"true\"></i> remove from favorites"
                } else {
                    document.getElementById("favoriteButton").innerHTML =
                        "<i class=\"fa fa-heart-o\" style=\"color: black\" aria-hidden=\"true\"></i> add to favorites"
                }
            }
        });
    })

    $('#strainSearch').on('keyup', function () {
        theNum = 1
        otherNum = 1

        var value = $(this).val()
        var sortVal = $('#dropdownTitle2').text()

        if (sortVal != "Rating" || sortVal != "Alphabetical Order"){
            sortVal = ""
        }

        getStrainSearch(0, value, sortVal)
    })

    $('#sortDropdown li').on('click', function(e){

        theNum = 1;
        otherNum = 1;

        e.preventDefault()
        document.getElementById("dropdownTitle").innerHTML = $(this).text();
        var type = $(this).text()
        var strainSearchText = $('#strainSearch').val()

        var sortType = document.getElementById("dropdownTitle2").innerHTML

        if (sortType != "Alphabetical Order"){
            if (sortType != "Rating"){
                sortType = ""
            }
        }


        var url = "/strains/type?page=0&type="+type+"&keyword="+strainSearchText+"&sortType="+sortType

        ajaxCall(url)

    });

    $('#sortDropdown2 li').on('click', function(e){

        theNum = 1;
        otherNum = 1;

        if (document.getElementById("dropdownTitle").innerHTML == "Type"){
            document.getElementById("dropdownTitle").innerHTML = ""
        }
        e.preventDefault()
        document.getElementById("dropdownTitle2").innerHTML = $(this).text();
        var type = document.getElementById("dropdownTitle").innerHTML
        var strainSearchText = $('#strainSearch').val()
        var sortType =$(this).text()


        if (type != "Indica"){
            if (type != "Hybrid"){
                if (type != "Sativa"){
                    type = ""
                }

            }
        }

        var url = "/strains/type?page=0&type="+type+"&keyword="+strainSearchText+"&sortType="+sortType

        ajaxCall(url)

    });

    getStrainPage(0)

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

    $('#sendEmail').click(function() {



        var name = $('#name').val()
        var email = $('#email').val()
        var comments = $('#comments').val()

        console.log(name)

        if ((name == "" || name == undefined) || (email == "" || email == undefined) ||(comments == "" || comments == undefined)){
            $('#message').prop("hidden",false);
            document.getElementById("message").style.color = 'red';
            $('#message').text("You must fill out all fields.")
            return;
        }

        $('#message').prop("hidden",true);
        $('#sendEmail').prop("disabled",true);

        var data = {
            "name": name,
            "email": email,
            "comments": comments
        }

        var jsonData = JSON.stringify(data)

        $.ajax({
            url: "/support/send",
            type: 'POST',
            dataType: 'json', // added data type
            data: jsonData,
            headers: {
                'Content-Type':'application/json;charset=utf-8'
            },
            complete : function (){
                $('#message').prop("hidden",false);
                document.getElementById("message").style.color = 'green';
                $('#message').text("Email sent successfully!")

                setTimeout(function () {
                    $('#message').prop("hidden",true);
                    $('#sendEmail').prop("disabled",false);
                }, 5000);
            }
        });


    })

})

function getStrainSearch(num, value, type){

    var strainSearchText = $('#strainSearch').val()
    var sortType =$('#dropdownTitle2').text()
    var type = $('#dropdownTitle').text()

    if (sortType != "Alphabetical Order"){
        if (sortType != "Rating"){
            sortType = ""
        }
    }


    if (type != "Indica"){
        if (type != "Hybrid"){
            if (type != "Sativa"){
                type = ""
            }

        }
    }

    var url = "/strains/type?page="+num+"&type="+type+"&keyword="+strainSearchText+"&sortType="+sortType

    if (num > 0){
        $('#prev').prop("disabled",false);
    }
    if (num == 0){
        $('#prev').prop("disabled",true);
    }

    ajaxCall(url)
}

function getStrainPage(num){

    var strainSearchText = $('#strainSearch').val()
    var sortType =$('#dropdownTitle2').text()
    var type = $('#dropdownTitle').text()

    if (sortType != "Alphabetical Order"){
        if (sortType != "Rating"){
            sortType = ""
        }
    }


    if (type != "Indica"){
        if (type != "Hybrid"){
            if (type != "Sativa"){
                type = ""
            }

        }
    }

    if (num -1 < 0 && $('#strainSearch').val().length == 0){
        $('#prev').prop("disabled",true);
    } else {
        $('#prev').prop("disabled",false);
    }

    var url = "/strains/type?page="+num+"&type="+type+"&keyword="+strainSearchText+"&sortType="+sortType

    ajaxCall(url)
}

function ajaxCall(url){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json', // added data type
        success: function(res) {

            if (res.length < 10){
                $('#next').prop("disabled",true);
            } else {
                $('#next').prop("disabled",false);
            }

            for (var i = 0; i < 10; i++){
                var iExtra = i+1
                document.getElementById("strain"+iExtra).innerHTML = ""
            }
            for (var i = 0; i < res.length; i++){
                var iExtra = i+1
                var strain = res[i].strain.toUpperCase()
                document.getElementById("strain"+iExtra).innerHTML = strain
            }
        }
    });
}

function favLinkCheck() {
    if (disabledFavLink){
        window.location.href = "/login"
    }
    return disabledFavLink
}