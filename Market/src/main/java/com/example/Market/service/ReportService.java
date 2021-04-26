package com.example.Market.service;


import com.example.Market.Entity.ReportEntity;
import com.example.Market.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class ReportService {


    private final ReportRepository reportRepository;


    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;

    }
    @Transactional
    public void updateReport(ReportEntity reportEntity) {
        reportRepository.save(reportEntity);
    }

    public ReportEntity getReport() {
        int id = 1;
        if (reportRepository.findById(id).isPresent()) {
            return reportRepository.findById(id).get();
        }
        return null;
    }
    @Transactional
    public void deleteRecord() {
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
        updateReport(reportEntity);
    }
}
