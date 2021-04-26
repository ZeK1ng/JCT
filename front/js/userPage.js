loadWindow();
setupPage();
let myItems=null;
async function loadWindow(){
    window.onload = function(){
        showData();
    }
   
}
function showData(){
    const xhr = new XMLHttpRequest();
    xhr.open('GET','http://127.0.0.1:8080/item/getAllItems')
    xhr.responseType = 'json'

    xhr.onload = () => {
        const data = xhr.response;
        console.log(data)
        document.getElementById("items").innerHTML = "";
        for(let i = 0; i< data.length; i++ ){
            let item = data[i];
            const div= document.createElement("div");
            div.className = "item-wrapper";
            const image = new Image();
            image.className = "item-image";
            image.src = item.imageUrl; 
            div.appendChild(image);
            const name = document.createElement("span");
            name.className = "item-name";
            name.innerHTML = item.name
            div.appendChild(name);
            const price = document.createElement("span");
            price.className ="item-price";
            price.innerHTML=item.price;
            div.appendChild(price);
            const popupOpener = document.createElement("input");
            popupOpener.className = "button";
            popupOpener.type="button";
            popupOpener.value = "buy";
            popupOpener.setAttribute("id","popopener")
            popupOpener.addEventListener('click',function(e){openpopup(item.id,item.amount)})
            div.appendChild(popupOpener);
            document.getElementById("items").append(div);
        }
    }
    xhr.send()

}
async function setupPage(){
    await loadWindow();
}

function openpopup(itemid,itemAmount){
    console.log(itemid);
    document.getElementById("modal").style.display='block'
    document.getElementById("amountInput").setAttribute('max',itemAmount);
    document.getElementById("buybutton").setAttribute("itemId",itemid);
    document.getElementById("buybutton").addEventListener('click',initiateItemBuy);

}

function initiateItemBuy(){
   
    let itemId=  document.getElementById("buybutton").getAttribute('itemId');
    let itemAmount = document.getElementById("amountInput").value;
    const xhr = new XMLHttpRequest();
    let requestUrl = 'http://127.0.0.1:8080/item/sellItem?id='+itemId +"&amount="+itemAmount;
    xhr.open('POST',requestUrl)
    xhr.responseType = 'json'
    xhr.onload = () => {
        showData();
        closeModal();
    }
    xhr.send()
    console.log(itemId,itemAmount);
} 

document.getElementById("addItem").addEventListener('click',openAddButton);
function openAddButton(){
    document.getElementById("modal1").style.display='block'
}

document.getElementById("addButton").addEventListener('click',callAddService);
function callAddService(){
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const imageUrl = document.getElementById('itemImage').value;
    const name = document.getElementById('itemName').value
    const amount = document.getElementById('itemAmount').value
    const price  = document.getElementById('itemPrice').value
    const xhr = new XMLHttpRequest();
    let requestUrl = 'http://127.0.0.1:8080/item/addItem?ownerId='+id+'&name='+name+'&price='+price+'&amount='+amount + '&imageUrl='+imageUrl;
    xhr.open('POST',requestUrl)
    xhr.responseType = 'json'
    xhr.onload = () => {
        showData();
        closeModal1();
        clearInputs();
    }
    xhr.send()
}
function clearInputs(){
    document.getElementById('itemImage').value ="";
    document.getElementById('itemName').value =""
    document.getElementById('itemAmount').value=""
    document.getElementById('itemPrice').value=""
}


document.getElementById("closeButton1").addEventListener('click', closeModal1);
function closeModal1(){
    document.getElementById("modal1").style.display='none'

}
document.getElementById("closeButton").addEventListener('click', closeModal);
function closeModal(){
    document.getElementById("modal").style.display='none'

}
document.getElementById("logout").addEventListener('click', logout);
function logout(){
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    const xhr = new XMLHttpRequest();
    let requestUrl = 'http://127.0.0.1:8080/client/logout?id='+id;
    xhr.open('POST',requestUrl)
    xhr.responseType = 'json'
    xhr.onload = () => {
       window.location.href="index.html";
    }
    xhr.send()
}