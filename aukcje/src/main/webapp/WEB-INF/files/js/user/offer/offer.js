var obsBtn =  document.getElementById('observe-button');

if(obsBtn!=null){
    var obsIcon = document.getElementById('observe-icon');
    var insertDateParagraph = document.getElementById('insert-date')

    obsBtn.addEventListener("click", function () {
        changeObserveIcon();
    });

    function changeObserveIcon(){
        obsIcon.classList.toggle('bi-eye');
        obsIcon.classList.toggle('bi-eye-fill');
    };

}

var obsBtnAuction =  document.getElementById('observe-button-auction');
if(obsBtnAuction!=null){
    var obsIconAuction = document.getElementById('observe-icon-auction');

    obsBtnAuction.addEventListener("click", function () {
        changeObserveIcon();
    });

    function changeObserveIcon(){
        obsIconAuction.classList.toggle('bi-eye');
        obsIconAuction.classList.toggle('bi-eye-fill');
    };

}

var quantityInput = document.getElementById("quantity");
if(quantityInput!=null){

    var btnQuantityUp =  document.getElementById('button-quantity-up');
    var btnQuantityDown = document.getElementById('button-quantity-down');
    var quantityValue = parseInt(quantityInput.getAttribute('value'));
    var quantityForCart = document.getElementById('cartPcs');

    btnQuantityUp.addEventListener("click", function () {

        if(quantityValue>=parseInt(quantityInput.getAttribute('max'))) {
            quantityValue = parseInt(quantityInput.getAttribute('max'));
        }else {
            quantityValue = parseInt(quantityInput.getAttribute('value')) + 1;
            quantityForCart=quantityValue;
        }

        console.log("up");
        quantityInput.setAttribute('value',quantityValue);
    });

    btnQuantityDown.addEventListener("click", function () {
        if(quantityValue<=parseInt(quantityInput.getAttribute('min'))) {
            quantityValue = parseInt(quantityInput.getAttribute('min'));
        }else{
            quantityValue = parseInt(quantityInput.getAttribute('value')) - 1;
            quantityForCart=quantityValue;
        }
        console.log("down");
        quantityInput.setAttribute('value',quantityValue);

    });

// document.getElementById('photo-height-panel').height = Math.floor(document.getElementById('left-part').height);

    function setQuantityForCart(number){
        quantityForCart = number;
    }

    function addToCart(offerId){
        if(quantityForCart==null) setQuantityForCart(1)
        console.log("Dodawanie do koszyka "+quantityForCart+" sztuk przedmiotu "+offerId)
        let url = "/koszyk/dodaj?szt="+quantityForCart+"&ofertaId="+offerId;
        console.log("DODAWANIE DO KOSZYKA: "+quantityForCart)
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", url, false ); // false for synchronous request
        xmlHttp.send( null );
        if(xmlHttp.status === 202){
            playAlert()
        }

        return xmlHttp.responseText;
    }

}

function createNewAlert(alertId, parentId){
    $("#"+parentId).append("<div id='"+alertId+"' class='alert alert-success alert-dismissible' data-timeout='5000' style='display:none; position:absolute; z-index: 10;'> <button type='button' className='btn-close' data-bs-dismiss='alert' aria-hidden='true'></button> Przedmiot został dodany do koszyka.</div>");
}
function showAlert(alertId){
    $("#"+alertId).css("display", "");
}

function closeAlert(){
    let alert_list = document.querySelectorAll('.alert')
    alert_list.forEach(function(alert) {
        new bootstrap.Alert(alert);

        let alert_timeout = alert.getAttribute('data-timeout');
        setTimeout(() => {
            bootstrap.Alert.getInstance(alert).close();
        }, +alert_timeout);
    });
}

function playAlert(){

    if($("#offer-header").find("div#added-to-cart-alert").length==0){
        createNewAlert('added-to-cart-alert', 'offer-header');
    }

    showAlert('added-to-cart-alert');

    closeAlert();
}