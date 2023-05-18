loadAllItems();
//---------------------Save Item-------------------
$("#btnSaveItem").click(function () {
    let formData = $("#itemForm").serialize();

    $.ajax({
        url:"http://localhost:8080/pos/item",
        method:"post",
        data:formData,
        dataType:"json",
        success:function (resp){
            alert(resp.message);
            loadAllItems();

        }
    });
    clearItemTextFields();

});

function loadAllItems() {
    $("#tblItem").empty();

    $.ajax({
        url:"http://localhost:8080/pos/item",
        method: "get",
        dataType: "json",
        success:function (resp){
            for (let item of resp.data) {
                var row = `<tr><td>${item.itemCode}</td><td>${item.itemName}</td><td>${item.price}</td><td>${item.qty}</td></tr>`;
                $("#tblItem").append(row);

            }
            bindRowClickEvent();
        }
    });



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
        let itemPrice = $(this).children(":eq(2)").text();
        let itemQty = $(this).children(":eq(3)").text();


        $("#txtItemCode").val(itemCode);
        $("#txtItemName").val(itemName);
        $("#txtItemQty").val(itemQty);
        $("#txtItemPrice").val(itemPrice);

    });
}

//------------------ Delete Item-------------------
$("#btnRemoveItem").click(function () {
    let deleteCode = $("#txtItemCode").val();

    $.ajax({
        url:"http://localhost:8080/pos/item?itemCode="+deleteCode,
        method:"delete",
        dataType:"json",
        success:function (resp){
            alert(resp.message);
            loadAllItems();
        }

    });
});


//----------Update Item-----------------

$("#btnUpdateItem").click(function () {
    let id = $("#txtItemCode").val();
    let name = $("#txtItemName").val();
    let uprice = $("#txtItemPrice").val();
    let Qty = $("#txtItemQty").val();

    let item={
        ItemCode:id,
        itemName:name,
        price:uprice,
        qty:Qty,
    }

    $.ajax({
        url:"http://localhost:8080/pos/item",
        method:"put",
        contentType:"application/json",
        data: JSON.stringify(item),
        dataType:"json",
        success:function (resp){
            alert(resp.message);
            loadAllItems()
        },
        error:function (error) {
            alert(JSON.parse(error.responseText).message);
        }
    });

});


//-------------------Clear Text Fields---------------
$("#btnClearItemTextField").click(function () {
    clearItemTextFields();
});

function clearItemTextFields() {
    $("#txtItemCode").focus();
    $("#txtItemCode,#txtItemName,#txtItemQty,#txtItemPrice").val("");
}








