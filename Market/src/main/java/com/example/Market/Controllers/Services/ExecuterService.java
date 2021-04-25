package com.example.Market.Controllers.Services;

import com.example.Market.Entity.Client;
import com.example.Market.Entity.Item;
import com.example.Market.Entity.ReportEntity;
import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ExecuterService {


    @Autowired
    private ReportService reportService;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    public void runJob(int targetHour, int targetMin, int targetSec){
        Runnable wrapper = new Runnable() {
            @Override
            public void run() {
                GenerateExcelReport();
                runJob(targetHour,targetMin,targetSec);
            }
        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        scheduledExecutorService.schedule(wrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if(zonedNow.compareTo(zonedNextTarget) > 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    private void GenerateExcelReport()  {
        ReportEntity reportEntity = reportService.getReport();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("Report1");
        String[] columns = {"Items Sold","Income","Commission Amount","Unique sold Items Count","Unique logged users Count","All visitor count"};
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)16);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet1.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            Cell cell =  headerRow.createCell(i);
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
        }
        Row row = sheet1.createRow(1);
        row.createCell(0).setCellValue(reportEntity.getSoldItemsAmount());
        row.createCell(1).setCellValue(reportEntity.getSoldSumAmount());
        row.createCell(2).setCellValue(reportEntity.getCommissionAmount());
        row.createCell(3).setCellValue(reportEntity.getUniqueItemCount());
        row.createCell(4).setCellValue(reportEntity.getUniqueLoggedUserCount());
        row.createCell(5).setCellValue(reportEntity.getAllVisitedUserCount());
        for (int i = 0; i < columns.length; i++) {
            sheet1.autoSizeColumn(i);
        }
        Sheet sheet2 = workbook.createSheet("Sold Items");
        headerRow = sheet2.createRow(0);
        String[] sheet2Header = {"Item", "ClientId", "Client Mail"};
        for (int i = 0; i < sheet2Header.length; i++) {
            String column = sheet2Header[i];
            Cell cell =  headerRow.createCell(i);
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
        }
        List<Pair<Item, Client>> soldItems = reportEntity.getSoldItems();
        int numRow = 1;
        for(Pair<Item,Client> p : soldItems){
            Item item = p.getKey();
            Client client = p.getValue();
            Row sheet2Row = sheet2.createRow(numRow++);
            sheet2Row.createCell(0).setCellValue(item.getName());
            sheet2Row.createCell(1).setCellValue(client.getId());
            sheet2Row.createCell(2).setCellValue(client.getMail());
        }
        for (int i = 0; i < sheet2Header.length; i++) {
            sheet2.autoSizeColumn(i);
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("Report.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }


    }
}
