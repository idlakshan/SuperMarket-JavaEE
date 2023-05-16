generateCode();
//---------------------Save Item-------------------
$("#btnSaveItem").click(function () {
    let itemCode = $("#txtItemCode").val();
    let itemName = $("#txtItemName").val();
    let itemQty = $("#txtItemQty").val();
    let itemPrice = $("#txtItemPrice").val();

    let ItemObject = new ItemDTO(itemCode, itemName, itemQty, itemPrice);

    itemDB.push(ItemObject);
    loadAllItems();
    clearItemTextFields();
    generateCode();

});

function loadAllItems() {
    $("#tblItem").empty();
    for (let item of itemDB) {
        var row = `<tr><td>${item.code}</td><td>${item.name}</td><td>${item.qty}</td><td>${item.price}</td></tr>`;
        $("#tblItem").append(row);
    }
    bindRowClickEvent();
    loadItemCodeForCombo();
}

//-------------Search Item---------------
$("#btnSearchItem").click(function () {
    let typedCode = $("#txtItemSearch").val();
    let item = searchItem(typedCode);

    if (item != null) {
        $("#txtItemCode").val(item.code);
        $("#txtItemName").val(item.name);
        $("#txtItemQty").val(item.qty);
        $("#txtItemPrice").val(item.price);

        $("#txtItemSearch").val("");
    }else{
        alert("There is no item available for this "+typedCode);
    }

})

function searchItem(itemCode) {
    for (let item of itemDB) {
        if (item.code == itemCode) {
            return item;
        }

    }
    return null
}

//--------------------Row Clicked----------------
function bindRowClickEvent() {
    $("#tblItem>tr").click(function () {
        let itemCode = $(this).children(":eq(0)").text();
        let itemName = $(this).children(":eq(1)").text();
        let itemQty = $(this).children(":eq(2)").text();
        let itemPrice = $(this).children(":eq(3)").text();

        $("#txtItemCode").val(itemCode);
        $("#txtItemName").val(itemName);
        $("#txtItemQty").val(itemQty);
        $("#txtItemPrice").val(itemPrice);

    });
}

//------------------ Delete Item-------------------
$("#btnRemoveItem").click(function () {
    let deleteCode = $("#txtItemCode").val();
    if (deleteItem(deleteCode)) {
        alert("Item Successfully Deleted..");
        clearItemTextFields();
        generateCode();
    } else {
        alert("No such Item to delete, please check Code");
    }
});


function deleteItem(itemCode) {
    let item = searchItem(itemCode);
    if (item != null) {
        let indexNo = itemDB.indexOf(item);
        itemDB.splice(indexNo, 1);
        loadAllItems();
        return true;
    }
    return false;
}

//----------Update Item-----------------

$("#btnUpdateItem").click(function () {
    let updateId = $("#txtItemCode").val();
    let response = updateItem(updateId);
    if (response) {
        alert("Item Updated Successfully..")
        generateCode();
    } else {
        alert("Item failed")
    }
});


function updateItem(itemCode) {
    let item = searchItem(itemCode);
    if (item != null) {
        item.code = $("#txtItemCode").val();
        item.name = $("#txtItemName").val();
        item.qty = $("#txtItemQty").val();
        item.price = $("#txtItemPrice").val();

       loadAllItems();
        return true;

    }
    return false;
}


//-------------------Clear Text Fields---------------
$("#btnClearItemTextField").click(function () {
    clearItemTextFields();
});

function clearItemTextFields() {
    $("#txtItemCode").focus();
    $("#txtItemCode,#txtItemName,#txtItemQty,#txtItemPrice").val("");
}

//-----------Auto generate Id--------------
function generateCode() {
    let index = itemDB.length - 1;
    let code;
    let temp;
    if (index != -1) {
        code = customersDB[customersDB.length - 1].code;
        temp = code.split("-")[1];
        temp++;
    }

    if (index == -1) {
        $("#txtItemCode").val("I00-001");
    } else if (temp <= 9) {
        $("#txtItemCode").val("I00-00" + temp);
    } else if (temp <= 99) {
        $("#txtItemCode").val("I00-0" + temp);
    } else {
        $("#txtItemCode").val("I00-" + temp);
    }

}




