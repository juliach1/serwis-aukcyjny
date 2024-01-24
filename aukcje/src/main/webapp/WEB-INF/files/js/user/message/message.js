function updateScroll(){
    let element = document.getElementById("messages-scrolled");
    element.scrollTop = element.scrollHeight;
}
updateScroll();

function sendMessageRequest(receiver){
    var xhr = new XMLHttpRequest();
    let contentMsg = document.getElementById('contentInput').value;
    dataToSend = {
        receiverId: receiver,
        content: contentMsg
    };

    xhr.open("POST", "/wiadomosci/wyslij", false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(dataToSend));
    location.reload();
}