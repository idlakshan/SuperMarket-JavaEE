
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