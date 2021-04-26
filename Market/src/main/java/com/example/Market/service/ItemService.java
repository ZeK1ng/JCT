package com.example.Market.service;

import com.example.Market.Entity.Item;
import com.example.Market.repository.ClientRepository;
import com.example.Market.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemService {

    private final ItemRepository itemRep;

    private final ClientRepository clrep;

    @Autowired
    public ItemService(ItemRepository itemRep,ClientRepository clrep){
        this.itemRep=itemRep;
        this.clrep=clrep;
    }

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

    public Item getItemById(int id) {
        if(itemRep.findById(id).isPresent()){
            return itemRep.findById(id).get();
        }
        return null;
    }
    public void save(Item item){
        itemRep.save(item);
    }

    public void deleteItem(Item item){
        itemRep.delete(item);
    }

}
