
setDate();
//----------------Load CustomerId to combo-----------------
function loadCusIdForCombo() {
    $("#cmbCusId").empty();

    let cusHint=`<option disabled selected> Select Customer ID</option>`;

    $("#cmbCusId").append(cusHint);

    for (let i in customersDB) {
        let option = `<option value="${customersDB[i].id}">${customersDB[i].id}</option>`
        $("#cmbCusId").append(option);
    }
}


//----------------Load ItemCode to combo-----------------
function loadItemCodeForCombo() {
    $("#cmbItemCode").empty();

    let itemHint=`<option disabled selected> Select Item Code</option>`;

    $("#cmbItemCode").append(itemHint);

    for (let i in itemDB) {
        let option = `<option value="${itemDB[i].code}">${itemDB[i].code}</option>`
        $("#cmbItemCode").append(option);
    }
}

//----------------Fill other Text fields-----------------
$("#cmbCusId").change(function () {
    let cusID = $("#cmbCusId").val();
    let customer = searchCustomer(cusID);
    if (customer != null) {
        $("#txtOrderCusName").val(customer.name);
        $("#txtOrderCusAddress").val(customer.address);
        $("#txtOrderCusSalary").val(customer.salary);
    }
});

$("#cmbItemCode").change(function () {
    let itemCode = $("#cmbItemCode").val();
    let item = searchItem(itemCode);
    if (item != null) {
        $("#txtOrderItemName").val(item.name);
        $("#txtQtyOnHand").val(item.qty);
        $("#txtOrderPrice").val(item.price);
    }
});

//--------------Set Date------------------
function setDate() {
    let d = new Date();
    let dd = d.toISOString().split("T")[0].split("-");
    $("#txtOrderDate").val(dd[0]+"-"+dd[1]+"-"+dd[2]);
    $("#txtOrderDate").text(dd[0]+"-"+dd[1]+"-"+dd[2]);
}



