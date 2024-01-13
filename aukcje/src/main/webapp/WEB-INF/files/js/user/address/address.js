

function deleteAddress(addressId){

    let deleteModal = document.getElementById("myModal_" + addressId)
    let deletedAddress = document.getElementById("address_" + addressId);

    const status = sendDeleteRequest(addressId);

    if(status === 202){
        $('#myModal_'+addressId).modal('hide');
        deletedAddress.hidden = "none";
    }
}

function sendDeleteRequest(addressId){
    console.log("WYSYLANIE REQUESTA USUWANIA")
    let xhr = new XMLHttpRequest();
    const url = "/uzytkownik/adres/usun/" + addressId;

    xhr.open("GET", url, false);
    xhr.send(null);
    console.log("status: "+xhr.status)
    return xhr.status;
}

$('#myModal').modal('hide');
