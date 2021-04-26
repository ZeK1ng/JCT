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
            price.innerHTML=item.price+" ₾";
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
    if(itemAmount.length!=""){
        const xhr = new XMLHttpRequest();
        let requestUrl = 'http://127.0.0.1:8080/item/sellItem?id='+itemId +"&amount="+itemAmount;
        xhr.open('POST',requestUrl)
        xhr.responseType = 'json'
        xhr.onload = () => {
            showData();
            closeModal();
        }
        xhr.send()
    }
    console.log(itemId,itemAmount);

    
} 
document.getElementById("resetPwd").addEventListener('click',initiatePasswordReset);
function initiatePasswordReset(){
    document.getElementById("modal1").style.display='block';    
}
document.getElementById("resetButtonNext").addEventListener('click',sendPwdResetRequest);
function sendPwdResetRequest(){
    const id = document.getElementById("idNumberInp").value;
    if(id.length != 0) {
        const xhr = new XMLHttpRequest();
        let requestUrl = 'http://127.0.0.1:8080/client/requestPassReset?id='+id;
        xhr.open('GET',requestUrl)
        xhr.responseType = 'json'
        xhr.onload = () => {
            document.getElementById("mailText").style.display ='block';
        }
        xhr.send()
    }else{
        document.getElementById("idNumberInp").style.borderColor = 'red';
    }
}
document.getElementById("closeButton1").addEventListener('click',closeModal1);
function closeModal1(){
    document.getElementById("modal1").style.display='none'
}


document.getElementById("closeButton").addEventListener('click', closeModal);
function closeModal(){
    document.getElementById("modal").style.display='none'
}

document.getElementById('LogIn').addEventListener('click', login);
    document.addEventListener('keypress', function(e){
        if(e.key === 'Enter'){
            login()
        }
    });
    function login(){
        const email = document.getElementById("emailHolder").value;
        // აქ პასვორდის დაჰეშვა და რაღაცეები შეიძლება, მაგრამ ამ ეტაპზე ჩავთვალოთ რომ დაცულია .
        const pwd = document.getElementById("passwordHolder").value;
        if(email.length == 0 || pwd.length==0){
            document.getElementById("emailHolder").style.borderColor='red';
            document.getElementById("passwordHolder").style.borderColor='red';
        }else{
            const xhr = new XMLHttpRequest();
            const requestUrl = 'http://127.0.0.1:8080/client/login?email='+email +"&password="+pwd;
            xhr.open('POST',requestUrl);
            xhr.responseType = 'json'
        
            xhr.onload = () => {
                const data = xhr.response;
                console.log(data);
                if(data!=null){
                    if(data == 404){
                        alert("USER NOT FOUND");
                    }else{
                        window.location.href = 'userPage.html?id='+data
                    }
                }
            }
            xhr.send()
        }
     
    }

document.getElementById('signUp').addEventListener('click' ,function(){
    window.location.href = 'signup.html'
});