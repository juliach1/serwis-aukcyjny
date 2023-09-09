/* QUANTITY & ITEM PRICE CHANGE */
let cartOfferCards = document.querySelectorAll('.cart-offer-card');

let allItemsPriceText = document.getElementById('all-items-price');
let allItemsPrice = parseFloat(allItemsPriceText);

let allItemsPcsText = document.getElementById('all-pcs');
let allItemsPcs = parseInt(allItemsPcsText.textContent);


/* OBSERVATION */
function changeObservationStatus(objectId){
    let obsIcon = document.getElementById('observe-icon_'+objectId)
    changeObserveIcon();
    function changeObserveIcon(){
        obsIcon.classList.toggle('bi-eye');
        obsIcon.classList.toggle('bi-eye-fill');
    }
}

/*QUANTITY CHANGES*/
function incrementQuantity(objectId){
    const quantityInput = document.getElementById("quantity_"+objectId);
    let quantityValue = parseInt(quantityInput.getAttribute("value"));
    let maxValue = parseInt(quantityInput.getAttribute('max'));
    let cartOfferPrice = document.getElementById("price_"+objectId);
    let cartOffer = document.getElementById("cart-offer_"+objectId);
    const onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));
    let offerId = parseInt(cartOffer.getAttribute("data-offer-id_"+objectId));

    if(quantityValue>=maxValue) {
        quantityValue = maxValue;
    }else {
        addOneToCart(offerId);
        quantityValue = parseInt(quantityInput.getAttribute('value')) + 1
    }
    quantityInput.setAttribute('value',quantityValue);

    let newPrice = onePcPrice * quantityValue;
    cartOfferPrice.innerHTML = newPrice.toFixed(2);
    let priceSpan = createPriceSpan(newPrice);
    cartOfferPrice.appendChild(priceSpan);

    calculateNewAllItemsPrice();
    changeAllItemsPrice();
}

function decrementQuantity(objectId) {
    var quantityInput = document.getElementById("quantity_"+objectId);
    var quantityValue = parseInt(quantityInput.getAttribute("value"));
    var minValue = parseInt(quantityInput.getAttribute('min'));
    var cartOfferPrice = document.getElementById("price_"+objectId);
    let cartOffer = document.getElementById("cart-offer_"+objectId);
    var onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));

    if(quantityValue<=minValue) {
        cartOffer.style.display = 'none';
        removeAllFromCart(objectId);
        allItemsPcs--;
        changeAllItemsQuantity();
    }else {
        quantityValue = parseInt(quantityInput.getAttribute('value')) - 1;
        removeOneFromCart(objectId);
        quantityInput.setAttribute('value',quantityValue);

        let newPrice = onePcPrice * quantityValue;
        cartOfferPrice.innerHTML = newPrice.toFixed(2);

        let priceSpan = createPriceSpan(newPrice);
        cartOfferPrice.appendChild(priceSpan);
    }
    calculateNewAllItemsPrice();
    changeAllItemsPrice();
}



/* PRICE CHANGES */
function calculateNewAllItemsPrice(){
    allItemsPrice = 0;

    cartOfferCards.forEach((card) => {
        if( card.style.display !== 'none'){
            const objectId = card.id.split("_")[1]; // Pobranie id obiektu z id przycisku
            const cartOfferPrice = document.getElementById("price_"+objectId);
            const onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));
            const quantityInput = document.getElementById("quantity_"+objectId);
            let quantityValue = parseInt(quantityInput.getAttribute("value"));

            allItemsPrice = allItemsPrice + onePcPrice * quantityValue;
        }
    });
}

function changeAllItemsPrice() {
    allItemsPriceText.innerHTML = allItemsPrice.toFixed(2);

    let priceSpan = createPriceSpan(allItemsPrice);
    allItemsPriceText.appendChild( priceSpan );
}

function createPriceSpan(price){
    var priceSpan = document.createElement('span')
    priceSpan.innerHTML = " zł"
    return priceSpan;
}

/* QUANTITY CHANGES */
function changeAllItemsQuantity() {
    allItemsPcsText.textContent = allItemsPcs;
}

/* ADDING TO CART */

function addOneToCart(offerId){
    let url = "/koszyk/dodaj?ofertaId="+offerId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}

/* REMOVING FROM CART */

function removeCartOffer(objectId) {
    let cartOffer = document.getElementById("cart-offer_" + objectId);
    cartOffer.style.display = 'none';

    allItemsPcs--;
    changeAllItemsQuantity();

    calculateNewAllItemsPrice();
    changeAllItemsPrice()

    removeAllFromCart(objectId);
}

function removeOneFromCart(offerId){
    let url = "/koszyk/usun?szt=1&ofertaKoszykaId="+offerId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}

function removeAllFromCart(offerId){
    let url = "/koszyk/usun?ofertaKoszykaId="+offerId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}