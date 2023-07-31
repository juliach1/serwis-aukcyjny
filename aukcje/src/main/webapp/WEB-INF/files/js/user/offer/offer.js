var obsBtn =  document.getElementById('observe-button');
var obsIcon = document.getElementById('observe-icon');
var insertDateParagraph = document.getElementById('insert-date')

obsBtn.addEventListener("click", function () {
    changeObserveIcon();
});

function changeObserveIcon(){
    obsIcon.classList.toggle('bi-eye');
    obsIcon.classList.toggle('bi-eye-fill');
};



var btnQuantityUp =  document.getElementById('button-quantity-up');
var btnQuantityDown = document.getElementById('button-quantity-down');
var quantityInput = document.getElementById("quantity");
var quantityValue = parseInt(quantityInput.getAttribute('value'));
var quantityForCart = document.getElementById('cartPcs');

btnQuantityUp.addEventListener("click", function () {

    if(quantityValue>=parseInt(quantityInput.getAttribute('max'))) {
        quantityValue = parseInt(quantityInput.getAttribute('max'));
    }else {
        quantityValue = parseInt(quantityInput.getAttribute('value')) + 1;
        quantityForCart=quantityForCart+1;
    }

    console.log("up");
    quantityInput.setAttribute('value',quantityValue);
});

btnQuantityDown.addEventListener("click", function () {
    if(quantityValue<=parseInt(quantityInput.getAttribute('min'))) {
        quantityValue = parseInt(quantityInput.getAttribute('min'));
    }else{
        quantityValue = parseInt(quantityInput.getAttribute('value')) - 1;
        quantityForCart=quantityForCart-1;
    }
    console.log("down");
    console.log(parseInt(quantityInput.getAttribute('value')));

});

document.getElementById('photo-height-panel').height = Math.floor(document.getElementById('left-part').height);

function setQuantityForCart(number){
    quantityForCart = number;
}

function addToCart(offerId){
    // let url = "/koszyk/dodaj?szt="+quantityForCart+"&ofertaId="+offerId;
    // var xmlHttp = new XMLHttpRequest();
    // xmlHttp.open( "GET", url, false ); // false for synchronous request
    // xmlHttp.send( null );
    // return xmlHttp.responseText;
    window.location = "/koszyk/dodaj?szt="+quantityForCart+"&ofertaId="+offerId;
}

