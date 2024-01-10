

function changeOfferStatus(purchaseId, statusId){
    let url = "/zakup/zmien-status/"+purchaseId+"/?statusId="+statusId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    if(xmlHttp.status === 202){
        var purchaseStatusTxt = document.getElementById("purchase-status-name-"+purchaseId);
        var changeStatusBtn = document.getElementById("change-status-btn-"+purchaseId);

        if(statusId===2){
            purchaseStatusTxt.innerHTML = "Wysłane"
        }else if(statusId===3){
            purchaseStatusTxt.innerHTML = "Zrealizowane"
        }

        changeStatusBtn.disabled = true;

    }

    return xmlHttp.responseText;
}