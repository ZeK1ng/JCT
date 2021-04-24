package com.example.Market.Controllers;

import com.example.Market.Controllers.Services.ClientService;
import com.example.Market.Controllers.Services.DBSequenceGenerator;
import com.example.Market.Controllers.Services.ItemService;
import com.example.Market.Entity.Client;
import com.example.Market.Entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@SpringBootApplication
@CrossOrigin(origins = "*")
public class ItemController {


    @Autowired
    private ItemService itemService;

    @Autowired
    private DBSequenceGenerator seqGen;

    @Autowired
    private ClientService clientService;

    @PostMapping("/addItem")
    public ResponseEntity addItem(@RequestParam(value = "name") String productName, @RequestParam(value = "amount") int amount,
                                  @RequestParam(value = "price") double price, @RequestParam(value = "imageUrl") String imgUrl,
                                  @RequestParam(value = "ownerId") String ownerId) {
        itemService.addItem(new Item(seqGen.getSequenceNumber(), productName, price, imgUrl, amount, Long.parseLong(ownerId)));
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/getAllItems")
    public ResponseEntity getAllItems() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @PostMapping("/sellItem")
    public ResponseEntity sellItem(@RequestParam(value = "id") int id, @RequestParam(value = "amount") int amount) {
        Item item = itemService.getItemById(id);
        if (item == null || item.getAmount() == 0) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (item.getAmount() > 0 && item.getAmount() < amount) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        double income = amount * item.getPrice();
        item.setAmount(item.getAmount() - amount);
        if(item.getAmount() == 0 ){
            itemService.deleteItem(id);
        }
        long ownerId = item.getOwnerId();
        Client owner = clientService.getClientById(ownerId);
        double ownerIncome = income * 0.9;
        transferToOwner(ownerIncome,owner.getAccount_number());
        
        return new ResponseEntity(HttpStatus.OK);

    }

    private void transferToOwner(double ownerIncome, int account_number) {
        return;
    }


}
