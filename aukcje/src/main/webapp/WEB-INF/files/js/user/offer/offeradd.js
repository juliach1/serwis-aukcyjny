//--------------------CREATING CATEGORIES POPOVER--------------------

var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl)
})



//---------DISPLAYING PRICE INPUTS (AUCTION, BUYNOW)-----

let auctionCheckbox = document.getElementById('auction-check')
let buyNowCheckbox = document.getElementById('buy-now-check')

let auctionPriceLabel = document.getElementById('auction-price-label')
let buyNowPriceLabel = document.getElementById('buy-now-price-label')



function setPriceInputVisibility(checkboxElem) {
    if (checkboxElem === auctionCheckbox) {
        auctionPriceLabel.classList.remove('d-none')
        buyNowPriceLabel.classList.add('d-none')
    } else if(checkboxElem === buyNowCheckbox) {
        auctionPriceLabel.classList.add('d-none')
        buyNowPriceLabel.classList.remove('d-none')
    }
}


//--------------------SELECTING CATEGORY--------------------

let lastSelectedCategory=null;
let chosenCategoryDisplay = document.getElementById('chosen-category-display');
let chosenCategoryId = document.getElementById('chosen-category-id');
let isBaseCategoryChosen = document.getElementById('base-category-chosen');


function setChosenCategoryId(chosenCatId){
    if(chosenCatId.value  !== null){
        chosenCategoryId.value = chosenCatId;
        console.log("WCZYTANA KATEGORIA TO "+chosenCatId)
    }
}




document.addEventListener('click', e =>
    {
        console.log("WYBRANO BAZOWĄ KATEGORIE: "+isBaseCategoryChosen.value)
        let clickedItem = e.target;
        let isCategory = clickedItem.classList.contains('list-group-item')
        let isAnyCategorySelected = lastSelectedCategory!==null;

        if(isCategory){

            if(isBaseCategory(clickedItem)){
                isBaseCategoryChosen.value = true;
                console.log("WYBRANO BAZOWĄ KATEGORIE: "+isBaseCategoryChosen.value)

                //TODO: DODAĆ ODZNACZANIE PO KLIKNIĘCIU TEJ SAMEJ KATEGORII DRUGI RAZ!
                if(isAnyCategorySelected){
                    toggleCategorySelection(lastSelectedCategory);
                }

                toggleCategorySelection(clickedItem);
                setChosenCategory(clickedItem);
                lastSelectedCategory = clickedItem;
            }

            if(!isBaseCategory(clickedItem)){
                console.log("zaznaczono kategorie ktora ma dzieci")
                isBaseCategoryChosen.value = false;
            }

        }
    }
);

if (chosenCategoryId.value !== null) {
    let lastSelectedCat = document.getElementById(chosenCategoryId.value);
    lastSelectedCategory = lastSelectedCat;
    setChosenCategory(lastSelectedCat)
    toggleCategorySelection(lastSelectedCat);
}



function setChosenCategory(selectedCategory){
    chosenCategoryDisplay.innerText = selectedCategory.textContent;
    chosenCategoryId.value = selectedCategory.id;
    console.log(selectedCategory.id)

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