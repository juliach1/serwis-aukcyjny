// $(function() {
//     var div = $('.dynamic-height');
//     var width = div.width();
//
//     div.css('height', width);
// });

var obsBtn =  document.getElementById('observe-button');
var obsIcon = document.getElementById('observe-icon');


obsBtn.addEventListener("click", function () {
    changeObserveIcon();
});

function changeObserveIcon(){
    obsIcon.classList.toggle('bi-eye');
    obsIcon.classList.toggle('bi-eye-fill');
};

// var obsBtnAuction =  document.getElementById('observe-button-auction');
// var obsIconAuction = document.getElementById('observe-icon-auction');
//
// obsBtnAuction.addEventListener("click", function () {
//     changeObserveIconAuction();
// });
//
// function changeObserveIconAuction(){
//     obsIconAuction.classList.toggle('bi-eye');
//     obsIconAuction.classList.toggle('bi-eye-fill');
// };




var btnQuantityUp =  document.getElementById('button-quantity-up');
var btnQuantityDown = document.getElementById('button-quantity-down');
var quantityInput = document.getElementById('quantity');
var quantityValue = parseInt(quantityInput.getAttribute('value'));


btnQuantityUp.addEventListener("click", function () {

    if(quantityValue>=parseInt(quantityInput.getAttribute('max'))) {
        quantityValue = parseInt(quantityInput.getAttribute('max'));
    }else {
        quantityValue = parseInt(quantityInput.getAttribute('value')) + 1;
    }

    quantityInput.setAttribute('value',quantityValue);
});

btnQuantityDown.addEventListener("click", function () {
    if(quantityValue<=parseInt(quantityInput.getAttribute('min'))) {
        quantityValue = parseInt(quantityInput.getAttribute('min'));
    }else{
        quantityValue = parseInt(quantityInput.getAttribute('value')) - 1;
    }
    quantityInput.setAttribute('value',quantityValue);
});