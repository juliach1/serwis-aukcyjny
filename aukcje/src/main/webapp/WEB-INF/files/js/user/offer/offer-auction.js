
/* AUCTIONS */

function bid(offerId){
    console.log("inside bid")

    let badBidMessage = document.getElementById("bad-bid-message");
    let currentPrice = document.getElementById("current-price");
    let currentPriceTxt = document.getElementById("current-price-txt");

    let response = placeBid(offerId);

    if(response==="too_low"){
        badBidMessage.style.display = '';
        badBidMessage.innerText = "Ktoś już złożył wyższą ofertę! Spróbuj zwiększyć kwotę."
    }else if(response==="ended"){
        badBidMessage.style.display = '';
        badBidMessage.innerText = "Licytacja już się skończyła. Sprawdź, czy wygrałeś w zakładce \"Moje zakupy\"."
    }else if(response==="ok"){
        currentPriceTxt.innerText = parseFloat(currentPrice.value).toFixed(2) + ' zł';
        currentPrice.value = (parseFloat(currentPrice.value)+1).toFixed(2);

        badBidMessage.style.display = 'none';
    }
}

function placeBid(offerId){
    let valueInput = document.getElementById('current-price').value;

    console.log("inside placeBid")
    let url = "/oferta/licytuj?ofertaId="+offerId+"&kwota="+valueInput;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}