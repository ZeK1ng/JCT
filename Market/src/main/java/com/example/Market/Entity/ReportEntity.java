package com.example.Market.Entity;

import javafx.util.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "reportData")
public class ReportEntity {
    @Id
    private int id;
    private int soldItemsAmount;
    private double soldSumAmount;
    private double commissionAmount;
    private int allVisitedUserCount;
    private List<Pair<Item,Client>> soldItems;
    private Set<Long> uniqueUsers;
    private Set<String> uniqueSoldItems;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoldItemsAmount() {
        return soldItemsAmount;
    }

    public void setSoldItemsAmount(int soldItemsAmount) {
        this.soldItemsAmount = soldItemsAmount;
    }

    public double getSoldSumAmount() {
        return soldSumAmount;
    }

    public void setSoldSumAmount(double soldSumAmount) {
        this.soldSumAmount = soldSumAmount;
    }

    public double getCommissionAmount() { return commissionAmount; }

    public void setCommisionAmount(double commisionAmmount) { this.commissionAmount = commisionAmmount; }

    public int getUniqueItemCount() {
        if(uniqueSoldItems == null) return 0;
        return uniqueSoldItems.size();
    }

    public int getUniqueLoggedUserCount() {
        if(uniqueUsers == null) return 0;
        return uniqueUsers.size();
    }

    public int getAllVisitedUserCount() {
        return allVisitedUserCount;
    }


    public void setAllVisitedUserCount(int allUserCount) {
        this.allVisitedUserCount = allUserCount;
    }

    public List<Pair<Item, Client>> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<Pair<Item, Client>> soldItems) {
        this.soldItems = soldItems;
    }
    public void addUser(long id){
        if(this.uniqueUsers == null){
            this.uniqueUsers = new HashSet<>();
        }
        this.uniqueUsers.add(id);
    }
    public void addItem(String name){
        if(this.uniqueSoldItems == null){
            this.uniqueSoldItems = new HashSet<>();
        }
        this.uniqueSoldItems.add(name);
    }

    public void addSoldItem(Item item,Client client){
        Pair<Item,Client> p = new Pair<>(item,client);
        soldItems.add(p);
    }
    public void addUniqueSoldItem(String name){
        uniqueSoldItems.add(name);
    }

    public void setUniqueUsers(Set<Long> uniqueUsers) {
        this.uniqueUsers = uniqueUsers;
    }

    public void setUniqueSoldItems(Set<String> uniqueSoldItems) {
        this.uniqueSoldItems = uniqueSoldItems;
    }
}
