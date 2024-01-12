

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



function rateSeller(purchaseId, sellerId){
    let ratingSelectElement = document.getElementById("seller-rating-select-"+purchaseId);
    let ratingSelectBtn = document.getElementById("seller-rating-btn-"+purchaseId);
    let selectRating = ratingSelectElement.value;
    let selectRatingNumb = parseInt(selectRating);

    dataToSend = {
        userId: sellerId,
        purchaseId: purchaseId,
        rating: selectRatingNumb
    };

    if(Number.isInteger(selectRatingNumb) && numberBetween(selectRatingNumb, 1, 5)){
        let url = "/ocen-sprzedawce";
        var xhr = new XMLHttpRequest();

        xhr.open("POST", url, false);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(JSON.stringify(dataToSend));

        if(xhr.status === 202){
            ratingSelectElement.disabled = true;
            ratingSelectBtn.disabled = true;
        }
    }

}

function numberBetween(number, minNumber, maxNumber){
    return number >= minNumber && number <= maxNumber;
}