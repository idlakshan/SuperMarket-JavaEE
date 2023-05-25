
 //let baseUrl="http://localhost:8080/pos";

 loadAllCustomers();
 loadAllItems();
 setDates();


//------combo Ids--------------------
function loadAllCustomers() {
    $("#cmbCusId").empty();
    $.ajax({
        url:  "http://localhost:8080/pos/customer",
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
         url: "http://localhost:8080/pos/item",
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
         url: "http://localhost:8080/pos/customer",
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
         url:"http://localhost:8080/pos/item",
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
        let price = $("#tblOrder").children().eq(i).children(":eq(2)").text();
        let orderQty = $("#tblOrder").children().eq(i).children(":eq(3)").text();
        array.push({itemCode: itemCode, price: price, orderQty: orderQty});
    }
    return array;


}

 $("#btnPurchase").click(function () {
     let orderID = $("#txtOrderId").val();
     let orderDate = $("#txtOrderDate").val();
     let cusID = $("#cmbCusId").val();
     let orderdetails = getOrderDetails();
     console.log(orderID+" "+orderDate+" "+cusID)

     let ob={
         orderID:orderID,
         orderDate:orderDate,
         cusID:cusID,
         orderdetails:orderdetails,
     }

     $.ajax({
         url: "http://localhost:8080/pos/purchase",
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