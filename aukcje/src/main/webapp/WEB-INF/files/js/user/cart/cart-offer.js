
/* QUANTITY & ITEM PRICE CHANGE */

let btnsQuantityUp =  document.querySelectorAll('.button-quantity-up');
let btnsQuantityDown = document.querySelectorAll('.button-quantity-down');
let cartOfferCards = document.querySelectorAll('.cart-offer-card');
let obsButton = document.querySelectorAll('.observe-button');

let allItemsPrice = parseFloat(document.getElementById('allItemsPrice'));
let allItemsPriceText = document.getElementById('all-items-price');

obsButton.forEach(function (obsBtn){
    const objectId = obsBtn.id.split("_")[1]; // Pobranie id obiektu z id
    let obsIcon = document.getElementById('observe-icon_'+objectId)

    obsBtn.addEventListener("click", function (){
        console.log("BUTTON "+objectId+" clicked")

        changeObserveIcon(
        );
    })

    function changeObserveIcon(){
        obsIcon.classList.toggle('bi-eye');
        obsIcon.classList.toggle('bi-eye-fill');
    };
})

btnsQuantityUp.forEach(function (button) {
   button.addEventListener("click", function () {

       var objectId = button.id.split("_")[1]; // Pobranie id obiektu z id przycisku
       var quantityInput = document.getElementById("quantity_"+objectId);
       var quantityValue = parseInt(quantityInput.getAttribute("value"));
       var maxValue = parseInt(quantityInput.getAttribute('max'));

       var cartOfferPrice = document.getElementById("price_"+objectId);
       var onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));

       if(quantityValue>=maxValue) {
            quantityValue = parseInt(quantityInput.getAttribute('max'));
        }else {
            quantityValue = parseInt(quantityInput.getAttribute('value')) + 1
       }
       quantityInput.setAttribute('value',quantityValue);

       let newPrice = onePcPrice * quantityValue;

       cartOfferPrice.innerHTML = newPrice.toFixed(2);
       let priceSpan = createPriceSpan(newPrice);
       cartOfferPrice.appendChild(priceSpan);

       calculateNewAllItemsPrice();
       changeAllItemsPrice();


       console.log("NEW all items price "+allItemsPrice);
    });
})


btnsQuantityDown.forEach(function (button) {
    button.addEventListener("click", function () {

        var objectId = button.id.split("_")[1]; // Pobranie id obiektu z id przycisku
        var quantityInput = document.getElementById("quantity_"+objectId);
        var quantityValue = parseInt(quantityInput.getAttribute("value"));
        var minValue = parseInt(quantityInput.getAttribute('min'));
        var cartOfferPrice = document.getElementById("price_"+objectId);


        var onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));

        if(quantityValue<=minValue) {
            quantityValue = parseInt(quantityInput.getAttribute('min'));
        }else {
            quantityValue = parseInt(quantityInput.getAttribute('value')) - 1;
        }

        quantityInput.setAttribute('value',quantityValue);


        let newPrice = onePcPrice * quantityValue;
        cartOfferPrice.innerHTML = newPrice.toFixed(2);

        let priceSpan = createPriceSpan(newPrice);

        cartOfferPrice.appendChild(priceSpan);

        calculateNewAllItemsPrice();
        changeAllItemsPrice();

        console.log("NEW all items price "+allItemsPrice);
    });
})

function createPriceSpan(price){
    var priceSpan = document.createElement('span')
    priceSpan.innerHTML = " zł"

    return priceSpan;
}


/* FULL PRICE CHANGE */

function calculateNewAllItemsPrice(){
    allItemsPrice = 0;

    cartOfferCards.forEach((card) => {
        const objectId = card.id.split("_")[1]; // Pobranie id obiektu z id przycisku

        const cartOfferPrice = document.getElementById("price_"+objectId);
        const onePcPrice = parseFloat(cartOfferPrice.getAttribute("data-pc-price_"+objectId));
        const quantityInput = document.getElementById("quantity_"+objectId);
        let quantityValue = parseInt(quantityInput.getAttribute("value"));

        allItemsPrice = allItemsPrice + onePcPrice * quantityValue;
    });
}

function changeAllItemsPrice() {
    allItemsPriceText.innerHTML = allItemsPrice.toFixed(2);

    let priceSpan = createPriceSpan(allItemsPrice);
    allItemsPriceText.appendChild( priceSpan );
}

/* ADDING TO CART */
function addToCart(offerId){
    let url = "/koszyk/dodaj?szt="+quantityForCart+"&ofertaId="+offerId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );
    if(xmlHttp.status === 202){
        playAlert()
    }

    return xmlHttp.responseText;
}