
/* OBSERVATION */
function changeObservationStatus(objectId){
    let obsIcon = document.getElementById('observe-icon_'+objectId)
    changeObserveIcon();
    function changeObserveIcon(){
        obsIcon.classList.toggle('bi-eye');
        obsIcon.classList.toggle('bi-eye-fill');
    }
}

/* REMOVAL */

function deleteOffer(offerId){

        let url = "/oferta/usun/"+offerId;
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", url, false ); // false for synchronous request
        xmlHttp.send( null );

        return xmlHttp.responseText;
}