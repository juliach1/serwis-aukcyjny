
/* OBSERVATION */
function changeObservationStatus(objectId){
    let obsIcon = document.getElementById('observe-icon_'+objectId)
    let obsButton = document.getElementById('observe-button_'+objectId);

    changeObserveIcon();
    changeObserveButtonTitle();
    toggleObservation(objectId);

    function changeObserveIcon(){
        obsIcon.classList.toggle('bi-eye');
        obsIcon.classList.toggle('bi-eye-fill');
    }
    function changeObserveButtonTitle(){
        if(obsButton.getAttribute('title') === "Dodaj do obserwowanych"){
            obsButton.setAttribute('title', "Usuń z obserwowanych");
        }else{
            obsButton.setAttribute('title', "Dodaj do obserwowanych");
        }
    }
}

function changeObserveIcon(obsIcon){
    obsIcon.classList.toggle('bi-eye');
    obsIcon.classList.toggle('bi-eye-fill');
}

function toggleObservation(objectId){
    let url = "/ulubione/zmien?ofertaId="+objectId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}

function addObservation(objectId){
    let url = "/ulubione/dodaj?ofertaId="+objectId;
    console.log(url);
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false ); // false for synchronous request
    xmlHttp.send( null );

    return xmlHttp.responseText;
}

/* REMOVAL */
function deleteOffer(offerId){
     let url = "/oferta/usun/"+offerId;
     var xmlHttp = new XMLHttpRequest();
     xmlHttp.open( "GET", url, false ); // false for synchronous request
     xmlHttp.send( null );

     return xmlHttp.responseText;
}