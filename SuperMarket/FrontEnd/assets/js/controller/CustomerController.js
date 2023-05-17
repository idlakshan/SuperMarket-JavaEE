loadAllCustomer();
//----------------------Save Customer-----------------------
$("#btnSaveCustomer").click(function (){
    saveCustomer();
    clearCustomerTextFields();
   // loadCusIdForCombo();


 });

function saveCustomer(){
    let formData = $("#cusForm").serialize();

    $.ajax({
        url:"http://localhost:8080/pos/customer",
        method:"post",
        data:formData,
        dataType:"json",
        success:function (resp){
            alert(resp.message)
            loadAllCustomer();
        },
        error:function (error){
            alert(JSON.parse(error.responseText).message);
        }
    });

}

 function loadAllCustomer(){
    $("#tblCustomer").empty();

     $.ajax({
         url:"http://localhost:8080/pos/customer",
         method: "get",
         dataType:"json",
         success:function (resp){
             for (let customer of resp.data) {
                 let row = `<tr><td>${customer.cusId}</td><td>${customer.cusName}</td><td>${customer.cusAddress}</td><td>${customer.cusSalary}</td></tr>`;
                 $("#tblCustomer").append(row);
             }
             bindRowCustomerClickEvent();
         }

     });


   // loadCusIdForCombo();


}

//-----------------Search Customer----------------
$("#btnSearchCustomer").click(function (){
  let searchId=$("#txtCusSearch").val();
  /*let customer=searchCustomer(TypedId);
  if (customer!=null){
      $("#txtCusID").val(customer.id);
      $("#txtCusName").val(customer.name);
      $("#txtCusAddress").val(customer.address);
      $("#txtCusSalary").val(customer.salary);

      $("#txtCusSearch").val("");
  }else{
      alert("There is no customer available for this "+TypedId);
  }
*/

    $.ajax({
        url:"http://localhost:8080/pos/customer?cusId"+searchId+"",
        method:"get",
        dataType:"json",
        success:function (resp){

          /*  console.log(resp.data.indexOf(searchId))*/
        }
    });
});
function searchCustomer(cusId){
    for (let customer of customersDB) {
        if(customer.id==cusId){
            return customer;
        }
    }
     return null;
}
//-------------------Clear Text Fields---------------
$("#btnClearTextField").click(function (){
    clearCustomerTextFields();
});

//--------------------Row Clicked----------------
function bindRowCustomerClickEvent(){
    $("#tblCustomer>tr").click(function (){
        let cusId=$(this).children(":eq(0)").text();
        let cusName=$(this).children(":eq(1)").text();
        let cusAddress=$(this).children(":eq(2)").text();
        let cusSalary=$(this).children(":eq(3)").text();

        $("#txtCusID").val(cusId);
        $("#txtCusName").val(cusName);
        $("#txtCusAddress").val(cusAddress);
        $("#txtCusSalary").val(cusSalary);

    });
}
//--------------------Delete Customer-----------------
$("#btnRemoveCustomer").click(function (){
    let deleteId=$("#txtCusID").val();
    $.ajax({
        url:"http://localhost:8080/pos/customer?cusId="+deleteId+"",
        method:"delete",
        dataType:"json",
        success:function (resp){
            alert(resp.message);
            loadAllCustomer();
        },
        error:function (error){
            alert(JSON.parse(error.responseText).message);
        }
    });

});

//----------Update Customer-----------------

$("#btnUpdateCustomer").click(function (){

    let id = $("#txtCusID").val();
    let name = $("#txtCusName").val();
    let address = $("#txtCusAddress").val();
    let salary = $("#txtCusSalary").val();

    let customer = {
        cusId:id,
        cusName:name,
        cusAddress:address,
        cusSalary:salary,
    }

    $.ajax({
        url:"http://localhost:8080/pos/customer",
        method:"put",
        contentType:"application/json",
        data: JSON.stringify(customer),
        dataType:"json",
        success:function (resp){
            alert(resp.message);
            loadAllCustomer();
        },
        error:function (error){
            alert(JSON.parse(error.responseText).message);
        }

    });


});

function clearCustomerTextFields(){
    $("#txtCusID").focus();
    $("#txtCusID,#txtCusName,#txtCusAddress,#txtCusSalary").val("");
}


