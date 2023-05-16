$("#mainContent").css("display","block");
$("#customerContent").css("display","none");
$("#itemContent").css("display","none");
$("#orderContent").css("display","none");

$("#linkCustomer").click(function (){
    $("#mainContent").css("display","none");
    $("#customerContent").css("display","block");
    $("#itemContent").css("display","none");
    $("#orderContent").css("display","none");
});

$("#linkItem").click(function (){
    $("#mainContent").css("display","none");
    $("#customerContent").css("display","none");
    $("#itemContent").css("display","block");
    $("#orderContent").css("display","none");
});

$("#linkOrder").click(function (){
    $("#mainContent").css("display","none");
    $("#customerContent").css("display","none");
    $("#itemContent").css("display","none");
    $("#orderContent").css("display","block");
});

$("#linkHome").click(function (){
    $("#mainContent").css("display","block");
    $("#customerContent").css("display","none");
    $("#itemContent").css("display","none");
    $("#orderContent").css("display","none");
});