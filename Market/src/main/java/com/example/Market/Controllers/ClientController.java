package com.example.Market.Controllers;

import com.example.Market.Controllers.Services.MailService;
import com.example.Market.Controllers.Services.UserService;
import com.example.Market.Model.Client;
import com.example.Market.Repository.ClientRepository;
import com.example.Market.Responce.ClientResponce;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client")
@SpringBootApplication
@CrossOrigin(origins = "*")
public class ClientController {
//    @Autowired
//    private ClientRepository rep;
    @Autowired
    private UserService userService ;


    @GetMapping("/test")
    public String test() {
        return "HIIII";
    }

    @PostMapping("/register/firstPhase")
    public ResponseEntity saveClient(@RequestParam(value = "fname") String fname, @RequestParam(value = "lname") String lname,
                                     @RequestParam(value = "id") String id, @RequestParam(value = "accountNumber") String accNumber,
                                     @RequestParam(value = "email") String mail) {
//        if (rep.findById(Long.parseLong(id)).isPresent()){
//            return new ResponseEntity(HttpStatus.CONFLICT);
//        }
        if (userService.getClientById(Long.parseLong(id)) != null){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        String validationToken = RandomString.make(64);
        Client newClient = new Client(Long.parseLong(id),fname,lname,Integer.parseInt(accNumber),mail,validationToken,false);
//        MailService.sendMail(mail, StringConstants.verificationRedirectUrl,Integer.parseInt(id));
        userService.save(newClient);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/register/secondPhase")
    public ResponseEntity registerClient(@RequestParam(value = "id") String id,@RequestParam(value = "password")String pwd) {
        Client client = userService.getClientById(Long.parseLong(id));
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        client.setPassword(pwd);
        client.setActive(true);
        userService.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("info")
    public ResponseEntity<ClientResponce> getClientInfo(@RequestParam(value = "id")String id){
        Client client = userService.getClientById(Long.parseLong(id));
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(client,HttpStatus.OK);

    }

    @PostMapping("register/setNewPassword")
    public ResponseEntity setNewPassword(@RequestParam(value = "id")String id,@RequestParam(value = "password") String pwd){
        Client client = userService.getClientById(Long.parseLong(id));
        if (client == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        client.setPassword(pwd);
        client.setActive(true);
        userService.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

}
