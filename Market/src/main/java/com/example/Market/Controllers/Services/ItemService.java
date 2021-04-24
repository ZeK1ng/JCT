package com.example.Market.Controllers.Services;

import com.example.Market.Model.Client;
import com.example.Market.Model.Item;

import com.example.Market.Repository.ClientRepository;
import com.example.Market.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemService {
    @Autowired
    private ItemRepository itemRep;
    @Autowired
    private ClientRepository clrep;
    public List<Item> getAllItems(){
        return itemRep.findAll();
    }

    public List<Item> getItemsByOwner(long ownerId){
        List<Item> result = null;
        List<Item> allItems = getAllItems();
        for (int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            if(item.getOwnerId() == ownerId){
                result.add(item);
            }
        }
        return result;
    }
    public int deleteByOwnerId(long ownerId) {
        if(clrep.findById(ownerId).isPresent()){
            List<Item> allItems = getAllItems();
            for (Item item:allItems){
                if(item.getOwnerId() == ownerId){
                    itemRep.deleteById(item.getId());
                    return 1;
                }
            }
        }
        return 0;
    }
    public void addItem(Item item){
        itemRep.save(item);
    }

}