var radioButtons = document.querySelectorAll('input[name="addressSelection"]');
var chodenAddressId = null;

radioButtons.forEach(function (radioButton) {
    radioButton.addEventListener('change', function () {
        addressNotSelectedError.style.display = "none";
        if (this.checked) {
            chodenAddressId = parseInt(this.value);
            console.log("Wybrany adres ID: " + chodenAddressId);
        }
    });
});

const offerCards =  document.querySelectorAll('.cart-offer-card');
var dataToSend = [];

function buyItems(){
    offerCards.forEach(function (card){ createDataToSend(card) });

    let addressNotSelectedError = document.getElementById("addressNotSelectedError");

    if(chodenAddressId!=null){
        hide(addressNotSelectedError);
        sendPurchaseRequest();
    }else {
        show(addressNotSelectedError);
    }
}

function createDataToSend(card){
    let cartOfferId = card.id.split("_")[1]; // Pobranie id pozycji koszyka
    let offerId = parseInt(card.getAttribute('data-offer-id_'+cartOfferId));
    const quantityInput = document.getElementById("quantity_"+cartOfferId);
    let quantityValue = parseInt(quantityInput.getAttribute("value"));
    let onePcprice = parseFloat(document.getElementById("data-pc-price_"+cartOfferId));

    dataToSend.push({
        offerId: offerId,
        quantity: quantityValue,
        price: (onePcprice*quantityValue).toFixed(),
    });
}

function sendPurchaseRequest(){
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/zakup/przetworz?adresId="+chodenAddressId, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(dataToSend));
}
function hide(error){
    error.style.display = "none"
}

function show(error){
    error.style.display = ""
}