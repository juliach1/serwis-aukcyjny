// $(function() {
//     var div = $('.dynamic-height');
//     var width = div.width();
//
//     div.css('height', width);
// });

var obsBtn =  document.querySelector('#observe-button');
var obsIcon = document.querySelector('#observe-icon');

obsBtn.addEventListener('click', function () {
    changeObserveIcon();
})


function changeObserveIcon(){
    obsIcon.classList.toggle('bi-eye');
    obsIcon.classList.toggle('bi-eye-fill');
}