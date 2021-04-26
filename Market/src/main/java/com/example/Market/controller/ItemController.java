package com.example.Market.controller;

import com.example.Market.service.ClientService;
import com.example.Market.service.DBSequenceGenerator;
import com.example.Market.service.ItemService;
import com.example.Market.service.ReportService;
import com.example.Market.Entity.Client;
import com.example.Market.Entity.Item;
import com.example.Market.Entity.ReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")

public class ItemController {


    private final ItemService itemService;
    private final DBSequenceGenerator seqGen;
    private final ClientService clientService;
    private final ReportService reportService;

    @Autowired
    public ItemController(ClientService clientService, ReportService reportService, ItemService itemService, DBSequenceGenerator seqGen) {
        this.clientService = clientService;
        this.reportService = reportService;
        this.itemService = itemService;
        this.seqGen = seqGen;
    }

    @PostMapping("/addItem")
    public ResponseEntity addItem(@RequestParam(value = "name") String productName, @RequestParam(value = "amount") int amount,
                                  @RequestParam(value = "price") double price, @RequestParam(value = "imageUrl") String imgUrl,
                                  @RequestParam(value = "ownerId") String ownerId) {
        itemService.addItem(new Item(seqGen.getSequenceNumber(), productName, price, imgUrl, amount, Long.parseLong(ownerId)));
        ReportEntity reportEntity = reportService.getReport();
        if (reportEntity == null) {
            reportService.createEmptyRecord();
        }
        reportEntity.addItem(productName);
        reportService.updateReport(reportEntity);
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
        if (item.getAmount() == 0) {
            itemService.deleteItem(item);
        } else {
            itemService.save(item);
        }
        long ownerId = item.getOwnerId();
        Client owner = clientService.getClientById(ownerId);
        double ownerIncome = income * 0.9;
        transferToOwner(ownerIncome, owner.getAccount_number());
        ReportEntity reportEntity = reportService.getReport();
        if (reportEntity == null) {
            reportService.createEmptyRecord();
        }
        reportEntity.addSoldItem(item, owner);
        reportEntity.addUniqueSoldItem(item.getName());
        reportEntity.setSoldSumAmount(reportEntity.getSoldSumAmount() + income);
        reportEntity.setCommisionAmount(reportEntity.getCommissionAmount() + income * 0.1);
        reportEntity.setSoldItemsAmount(reportEntity.getSoldItemsAmount() + 1);
        reportService.updateReport(reportEntity);
        return new ResponseEntity(HttpStatus.OK);

    }

    private void transferToOwner(double ownerIncome, String account_number) {
        return;
    }


}
