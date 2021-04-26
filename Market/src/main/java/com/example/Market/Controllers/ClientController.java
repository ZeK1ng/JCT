package com.example.Market.Controllers;

import com.example.Market.Constants.StringConstants;
import com.example.Market.Controllers.Services.ClientService;
import com.example.Market.Controllers.Services.MailService;
import com.example.Market.Controllers.Services.ReportService;
import com.example.Market.Entity.Client;
import com.example.Market.Entity.ReportEntity;
import com.example.Market.Responce.ClientResponce;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


@RestController
@RequestMapping("/client")
@SpringBootApplication
@CrossOrigin(origins = "*")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private MailService mailService;

    @GetMapping("/test")
    public String test() {
        return "HIIII";
    }
    @Autowired
    private ReportService reportService;


    @PostMapping("/register/firstPhase")
    public ResponseEntity saveClient(@RequestParam(value = "fname") String fname, @RequestParam(value = "lname") String lname,
                                     @RequestParam(value = "id") String id, @RequestParam(value = "accountNumber") String accNumber,
                                     @RequestParam(value = "email") String mail) {

        if (clientService.getClientById(Long.parseLong(id)) != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        String validationToken = RandomString.make(64);
        Client newClient = new Client(Long.parseLong(id), fname, lname, accNumber, mail, validationToken, false);
        mailService.sendMail(mail, StringConstants.verificationRedirectUrl,Integer.parseInt(id),1);
        clientService.save(newClient);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/register/secondPhase")
    public ResponseEntity registerClient(@RequestParam(value = "id") String id, @RequestParam(value = "password") String pwd) {
        Client client = clientService.getClientById(Long.parseLong(id));
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        client.setPassword(pwd);
        client.setActive(true);
        clientService.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam(value = "email") String mail, @RequestParam(value = "password") String pwd) throws ParseException {
        Client client = clientService.findByMailAndPass(mail, pwd);
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date expDate = calendar.getTime();
        client.setLoginExparDate(expDate);
        client.setLoggedIn(true);
        clientService.save(client);
        ReportEntity reportEntity = reportService.getReport();
        if(reportEntity == null){
            reportService.createEmptyRecord();
        }
        reportEntity.addUser(client.getId());
        reportService.updateReport(reportEntity);
        return new ResponseEntity(client.getId(),HttpStatus.OK);
    }
    @GetMapping("/requestPassReset")
    public ResponseEntity requestPasswordReset(@RequestParam(value = "id") String id){
        Client client = clientService.getClientById(Long.parseLong(id));
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        mailService.sendMail(client.getMail(), StringConstants.verificationRedirectUrl,Integer.parseInt(id),0);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity login(@RequestParam(value = "id") String id) {
        Client client = clientService.getClientById(Long.parseLong(id));
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        client.setLoggedIn(false);
        clientService.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("info")
    public ResponseEntity<ClientResponce> getClientInfo(@RequestParam(value = "id") String id) {
        Client client = clientService.getClientById(Long.parseLong(id));
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @PostMapping("register/setNewPassword")
    public ResponseEntity setNewPassword(@RequestParam(value = "id") String id, @RequestParam(value = "password") String pwd) {
        Client client = clientService.getClientById(Long.parseLong(id));
        if (client == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        client.setPassword(pwd);
        client.setActive(true);
        clientService.save(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/userVisiting")
    public ResponseEntity updateUserVisitReport(){
        ReportEntity reportEntity = reportService.getReport();
        if(reportEntity == null){
            reportService.createEmptyRecord();
        }
        reportEntity.setAllVisitedUserCount(reportEntity.getAllVisitedUserCount()+1);
        reportService.updateReport(reportEntity);
        return new ResponseEntity(HttpStatus.OK);
    }
}
