package com.example.Market.Controllers.Services;


import com.example.Market.Entity.ReportEntity;
import com.example.Market.Repository.ClientRepository;
import com.example.Market.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ItemService itemService;

    public void updateReport(ReportEntity reportEntity){
        reportRepository.save(reportEntity);
    }
    public ReportEntity getReport(int id){
        
        return reportRepository.findById(id).get();
    }
}
