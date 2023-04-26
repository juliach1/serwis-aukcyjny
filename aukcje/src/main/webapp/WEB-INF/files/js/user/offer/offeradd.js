//--------------------CREATING CATEGORIES POPOVER--------------------

var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl)
})



//--------------------SELECTING CATEGORY--------------------

let lastSelectedCategory=null;
let chosenCategoryDisplay = document.getElementById('chosen-category-display');

document.addEventListener('click', e =>
    {
        let clickedItem = e.target;
        let isCategory = clickedItem.classList.contains('list-group-item')
        let isAnyCategorySelected = lastSelectedCategory!==null;

        if(isCategory){

            if(isBaseCategory(clickedItem)){

                //TODO: DODAĆ ODZNACZANIE PO KLIKNIĘCIU TEJ SAMEJ KATEGORII DRUGI RAZ!
                if(isAnyCategorySelected){
                    toggleCategorySelection(lastSelectedCategory);
                }

                toggleCategorySelection(clickedItem);
                setChosenCategory(clickedItem);
                lastSelectedCategory = clickedItem;
            }
        }
    }
);

function setChosenCategory(selectedCategory){
    chosenCategoryDisplay.innerText = selectedCategory.textContent;
}

function isBaseCategory(category){
    let siblingCategory = category.nextElementSibling;
    return siblingCategory.innerText==='';

}
function toggleCategorySelection(category){
    category.classList.toggle('selected-category')
}

const attrObserver = new MutationObserver((mutations) => {
    mutations.forEach(mu => {
        if (mu.type !== "attributes" && mu.attributeName !== "class") return;
        console.log("class was modified!");
    });
});

const ELS_test = document.querySelectorAll(".list-group-item");
ELS_test.forEach(el => attrObserver.observe(el, {attributes: true}));


// Example of Buttons toggling several .test classNames
//lgi.target.nextElementSibling.classList.add('show');
document.querySelectorAll(".list-group-item").forEach(lgi => {
    lgi.addEventListener("click", () => {

        if(lgi.nextElementSibling.classList.contains('show')){
            lgi.nextElementSibling.classList.toggle('show');
        }
    });
});