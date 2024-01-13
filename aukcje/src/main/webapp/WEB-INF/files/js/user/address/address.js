function deleteAddress(addressId){
    let deletedAddress = document.getElementById("address_" + addressId);
    const status = sendDeleteRequest(addressId);

    if(status === 202){
        $('#myModal_'+addressId).modal('hide');
        deletedAddress.hidden = "none";
    }
}

function sendDeleteRequest(addressId){
    let xhr = new XMLHttpRequest();
    const url = "/uzytkownik/adres/usun/" + addressId;

    xhr.open("GET", url, false);
    xhr.send(null);

    return xhr.status;
}