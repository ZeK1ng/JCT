document.getElementById('signUpButton').addEventListener('click',signUp)
document.addEventListener('keypress', function(e){
  if(e.key === 'Enter'){
      signUp()
  }
});
function signUp(){
    const fname = document.getElementById('firstNameID').value.trim()
    const lname = document.getElementById('lastNameID').value.trim()
    const id = document.getElementById('IDID').value.trim()
    const acc_number = document.getElementById('ACCNUMID').value.trim()
    const email = document.getElementById('EMAILID').value.trim()
    const values= [fname,lname,id,acc_number,email]
    const inputs = [document.getElementById('firstNameID'),
                    document.getElementById('lastNameID'),
                    document.getElementById('IDID'),
                    document.getElementById('ACCNUMID'),
                    document.getElementById('EMAILID')]
    console.log(values)
    let allGood = true
    values.forEach(function(item,index){
        if(item.length == 0 ){
            inputs[index].style.borderColor ='red'
            allGood=false
        }
    });
    if(allGood){
        
    }else{
        console.log("NNN")
    }
}
document.getElementById('loginButton').addEventListener('click' ,function(){
    window.location.href = 'index.html'
});