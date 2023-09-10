
const offerCards =  document.querySelectorAll('.cart-offer-card');
var dataToSend = [];
function buyItems(){
    console.log("BUY ITEMS BUTTON CLICKED. ITEMS BOUGHT:")
    offerCards.forEach(
        function (card){

            let cartOfferId = card.id.split("_")[1]; // Pobranie id pozycji koszyka
            let offerId = parseInt(card.getAttribute('data-offer-id_'+cartOfferId));
            const quantityInput = document.getElementById("quantity_"+cartOfferId);
            let quantityValue = parseInt(quantityInput.getAttribute("value"));
            let onePcprice = parseFloat(document.getElementById("data-pc-price_"+cartOfferId));

            dataToSend.push({
                offerId: offerId,
                quantity: quantityValue,
                price: (onePcprice*quantityValue).toFixed()
            });
            console.log("Offer id: " + offerId + " quantity to buy: "+quantityValue)
        });

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/zakup/przetworz", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(dataToSend));
}



 document.getElementByC("purchaseBtn").addEventListener("click", function() {

    // Pobierz wszystkie pozycje w koszyku
    var cartItems = document.querySelectorAll("[data-offer-id]");

    // Inicjalizuj pusty obiekt do przechowywania danych
    var dataToSend = [];

    // Przejdź przez każdą pozycję w koszyku i zbierz potrzebne dane
    cartItems.forEach(function(item) {
    var offerId = item.getAttribute("data-offer-id");
    var itemId = item.getAttribute("id");
    var quantity = document.getElementById("quantity_" + itemId).value; // Zakładam, że masz input z liczbą egzemplarzy

    // Dodaj dane do tablicy
    dataToSend.push({
    offerId: offerId,
    itemId: itemId,
    quantity: quantity
});
});

    // Wyślij dane do kontrolera za pomocą zapytania AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/twoj-kontroler-url", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(dataToSend));
});