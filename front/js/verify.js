document.getElementById('signUpButton').addEventListener('click',signUp)
document.addEventListener('keypress', function(e){
  if(e.key === 'Enter'){
      signUp()
  }
});
function signUp(){
    const pwd = document.getElementById('pwd').value
    const pwd1 = document.getElementById('pwd1').value
    const params = new URLSearchParams(window.location.search);
    const id = params.get("userid");
    console.log(id);
    console.log(pwd,pwd1);
    if(pwd.length == 0 || pwd1.length==0 || pwd1 != pwd){
        document.getElementById('pwd').style.borderColor='red'
        document.getElementById('pwd1').style.borderColor='red'
    }else{
        const xhr = new XMLHttpRequest();
        let requestUrl = 'http://127.0.0.1:8080/client/register/secondPhase?id='+id+'&password='+pwd;
        xhr.open('POST',requestUrl)
        xhr.responseType = 'json'
        xhr.onload = () => {
            window.location.href = 'userPage.html?id='+id
        }
        xhr.send()
    }

}
