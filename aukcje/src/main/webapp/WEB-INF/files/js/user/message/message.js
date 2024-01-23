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
    // createDateDiv();
    // createMessageDiv(contentMsg, userId, hasAvatar);

}

// function createDateDiv() {
//     let divElement = document.createElement("div");
//     const paragraphElement = document.createElement("p");
//     paragraphElement.classList.add("d-flex");
//     paragraphElement.classList.add("justify-content-center");
//     paragraphElement.classList.add("mb-4");
//     paragraphElement.classList.add("text-muted");
//     const textNode = document.createTextNode("Dzisiaj");
//
//     paragraphElement.appendChild(textNode);
//     const dateElement = divElement.appendChild(paragraphElement);
//     let messagesWindow = document.getElementById('messages-scrolled');
//     messagesWindow.appendChild(dateElement);
// }
//
// function createMessageDiv(msg, userId, hasAvatar){
//     let outerDiv = document.createElement("div");
//     outerDiv.classList.add("d-flex")
//     outerDiv.classList.add("flex-row")
//     outerDiv.classList.add("justify-content-end")
//
//     let innerDiv = document.createElement("div")
//     innerDiv.classList.add("me-3");
//
//     let msgContentParagraph = document.createElement("p");
//     const contentText = document.createTextNode(msg);
//     msgContentParagraph.appendChild(contentText);
//     msgContentParagraph.classList.add("sender-message-text");
//     msgContentParagraph.classList.add("bg-light-purple");
//
//     let timeParagraph = document.createElement("p")
//     timeParagraph.classList.add("d-flex")
//     timeParagraph.classList.add("flex-row")
//     timeParagraph.classList.add("justify-content-end")
//
//     const userPhoto = createUserPhotoIcon(userId, hasAvatar);
//     innerDiv.appendChild(msgContentParagraph);
//     innerDiv.appendChild(timeParagraph)
//     outerDiv.appendChild(innerDiv)
//     outerDiv.appendChild(userPhoto);
//
//     let messagesWindow = document.getElementById('messages-scrolled');
//     messagesWindow.appendChild(outerDiv);
//     updateScroll();
// }
//
// function createUserPhotoIcon(userId, hasAvatar){
//     let img = new Image();
//     if(!hasAvatar){
//         img.src = '/files/img/users/avatar-placeholder.png';
//     }else {
//         img.src = '/files/img/users/'+userId+'/'+userId+'.png';
//     }
//     img.classList.add("user-icon")
//     return img;
// }