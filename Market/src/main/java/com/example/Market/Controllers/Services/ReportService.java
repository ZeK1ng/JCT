package com.example.Market.Controllers.Services;


import com.example.Market.Entity.Client;
import com.example.Market.Entity.Item;
import com.example.Market.Entity.ReportEntity;
import com.example.Market.Repository.ClientRepository;
import com.example.Market.Repository.ReportRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service

public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemService;
    private int id = 1;
    public void updateReport(ReportEntity reportEntity){
        reportRepository.save(reportEntity);
    }

    public ReportEntity getReport(){
        if(reportRepository.findById(id).isPresent()){
            return reportRepository.findById(id).get();
        }
        return null;
    }

    public void deleteRecord(){
        reportRepository.deleteAll();
    }
    public void createEmptyRecord() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1);
        reportEntity.setSoldItems(new ArrayList<>());
        reportEntity.setCommisionAmount(0.0);
        reportEntity.setSoldItemsAmount(0);
        reportEntity.setSoldSumAmount(0.0);
        reportEntity.setUniqueUsers(new HashSet<>());
        reportEntity.setUniqueSoldItems(new HashSet<>());
        reportRepository.save(reportEntity);
    }
}
