package com.example.Market.Entity;

import javafx.util.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "reportData")
public class ReportEntity {
    @Id
    private int id;
    private int soldItemsAmount;
    private double soldSumAmount;
    private double commisionAmmount;
    private int uniqueItemCount;
    private int uniqueLoggedUserCount;
    private int allUserCount;
    private List<Pair<Item,Client>> soldItems;
    private Set<Long> uniqueUsers;

    public ReportEntity(int id, int soldItemsAmount, double soldSumAmount, double commisionAmmount, int uniqueItemCount, int uniqueLoggedUserCount, int allUserCount) {
        this.id = id;
        this.soldItemsAmount = soldItemsAmount;
        this.soldSumAmount = soldSumAmount;
        this.commisionAmmount = commisionAmmount;
        this.uniqueItemCount = uniqueItemCount;
        this.uniqueLoggedUserCount = uniqueLoggedUserCount;
        this.allUserCount = allUserCount;
    }

    public ReportEntity(int id, int soldItemsAmount, double soldSumAmount, double commisionAmmount, int uniqueItemCount, int uniqueLoggedUserCount, int allUserCount, List<Pair<Item, Client>> soldItems) {
        this.id = id;
        this.soldItemsAmount = soldItemsAmount;
        this.soldSumAmount = soldSumAmount;
        this.commisionAmmount = commisionAmmount;
        this.uniqueItemCount = uniqueItemCount;
        this.uniqueLoggedUserCount = uniqueLoggedUserCount;
        this.allUserCount = allUserCount;
        this.soldItems = soldItems;
    }

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

    public double getCommisionAmmount() {
        return commisionAmmount;
    }

    public void setCommisionAmmount(double commisionAmmount) {
        this.commisionAmmount = commisionAmmount;
    }

    public int getUniqueItemCount() {
        return uniqueItemCount;
    }

    public void setUniqueItemCount(int uniqueItemCount) {
        this.uniqueItemCount = uniqueItemCount;
    }

    public int getUniqueLoggedUserCount() {
        return uniqueLoggedUserCount;
    }

    public void setUniqueLoggedUserCount(int uniqueLoggedUserCount) {
        this.uniqueLoggedUserCount = uniqueLoggedUserCount;
    }

    public int getAllUserCount() {
        return allUserCount;
    }

    public void setAllUserCount(int allUserCount) {
        this.allUserCount = allUserCount;
    }

    public List<Pair<Item, Client>> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<Pair<Item, Client>> soldItems) {
        this.soldItems = soldItems;
    }
}
