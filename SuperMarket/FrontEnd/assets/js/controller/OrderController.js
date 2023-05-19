
 let baseUrl="http://localhost:8080/pos/";

 loadAllCustomers();
 loadAllItems();
 setDates();


//------combo Ids--------------------
function loadAllCustomers() {
    $("#cmbCusId").empty();
    $.ajax({
        url: baseUrl + "customer",
        dataType: "json",
        success: function (resp) {
            console.log(resp);
            for (let cus of resp.data) {
                $("#cmbCusId").append(`<option value="${cus.cusId}">${cus.cusId}</option>`);
            }
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
}

 function loadAllItems() {
     $("#cmbItemCode").empty();
     $.ajax({
         url: baseUrl + "item",
         dataType: "json",
         success: function (res) {
             for (let item of res.data) {
                 $("#cmbItemCode").append(`<option value="${item.itemCode}">${item.itemCode}</option>`);
             }
         },
         error: function (error) {
             let message = JSON.parse(error.responseText).message;
             alert(message);
         }
     });
 }

 function setDates() {
     let date = new Date().toJSON().split("T")[0];
     $("#txtOrderDate").val(date);
 }


 $("#cmbCusId").change(function () {

     let customerId = $("#cmbCusId").val();

     $.ajax({
         url: baseUrl+"customer",
         method: "get",
         success(resp) {
             for (const i of resp.data) {
                 if (customerId == i.cusId) {

                     $("#txtOrderCusName").val(i.cusName);
                     $("#txtOrderCusAddress").val(i.cusAddress);
                     $("#txtOrderCusSalary").val(i.cusSalary);
                 }
             }
         }
     });
 });

 $("#cmbItemCode").change(function () {

     let itemCode = $("#cmbItemCode").val();

     $.ajax({
         url: baseUrl+"item",
         method: "get",
         dataType:"json",
         success(resp) {
             for (const i of resp.data) {
                 if (itemCode == i.itemCode) {

                     $("#txtOrderItemName").val(i.itemName);
                     $("#txtQtyOnHand").val(i.qty);
                     $("#txtOrderPrice").val(i.price);
                 }
             }
         }
     });

 });

 //------Add Table--------------------

 $("#btnAdd").click(function () {
     let code = $("#cmbItemCode").val();
     let name = $("#txtOrderItemName").val();
     let price = $("#txtOrderPrice").val();
     let buyQty = $("#txtOrderQty").val();
     let total = parseFloat(price) * parseFloat(buyQty);
     $("#tblOrder").append(`<tr><td>${code}</td><td>${name}</td><td>${price}</td><td>${buyQty}</td><td>${total}</td></tr>`);
 });


function getOrderDetails(){
    let rows = $("#tblOrder").children().length;
    var array = [];
    for (let i = 0; i < rows; i++) {
        let itemCode = $("#tblOrder").children().eq(i).children(":eq(0)").text();
        let itemPrice = $("#tblOrder").children().eq(i).children(":eq(2)").text();
        let itemQty = $("#tblOrder").children().eq(i).children(":eq(3)").text();
        array.push({code: itemCode, price: itemPrice, qty: itemQty});
    }
    return array;


}

 $("#btnPurchase").click(function () {
     let orderID = $("#txtOrder").val();
     let customerID = $("#cmbCusId").val();
     let orderDate = $("#txtOrderDate").val();
     let orderDetails = getOrderDetails();

     let ob={
         oid:orderID,
         date:orderDate,
         cusID:customerID,
         orderDetails:orderDetails
     }

     $.ajax({
         url: baseUrl + "purchase",
         method:"post",
         dataType: "json",
         data:JSON.stringify(ob),
         contentType:"application/json",
         success: function (resp) {
             alert(resp.message);
         },
         error:function (error){
             alert(JSON.parse(error.responseText).message);
         }
     });


 });