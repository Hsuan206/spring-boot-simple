package com.example.employeemanager.controller;

import com.example.employeemanager.Service.ConfigService;
import com.example.employeemanager.model.Config;
import com.example.employeemanager.repository.ConfigRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@RestController
@RequestMapping("/api")
public class ConfigController {
    private final static Logger logger = LoggerFactory.getLogger(ConfigController.class);
    private final ConfigService configService;
    private final ConfigRepo configRepo;
    @Autowired
    public ConfigController(ConfigService configService, ConfigRepo configRepo) {
        this.configService = configService;
        this.configRepo = configRepo;
    }
    @GetMapping("/configs")
    public ResponseEntity<List<Config>> getAllConfigs () {
        List<Config> configs = configService.findAllConfigs();
        return new ResponseEntity<>(configs, HttpStatus.OK);
    }
    @GetMapping("/configs/{id}")
    public ResponseEntity<Config> getConfigById (@PathVariable("id") Long id) {
        Config config = configService.findConfigById(id);
        return new ResponseEntity<>(config, HttpStatus.OK);
    }
    @PostMapping("/configs")
    public ResponseEntity<Config> addConfig (@RequestBody Config config) {
        Config newConfig = configService.addConfig(config);
        return new ResponseEntity<>(newConfig, HttpStatus.CREATED); // 在 server 上創建東西
    }
    @PutMapping("/configs/{id}")
    public ResponseEntity<Config> updateConfig (@PathVariable("id") Long id, @RequestBody Config config) {
        Config updateConfig = configService.updateConfig(id, config);
        return new ResponseEntity<>(updateConfig, HttpStatus.OK);
    }
    @DeleteMapping("/configs/{id}")
    public ResponseEntity<?> deleteConfig (@PathVariable("id") Long id) {
        // 因為刪除不回傳任何東西不回傳所以打問號
        configService.deleteConfig(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/configs/upload")
    public ResponseEntity<?> uploadConfig (@RequestParam("Upload") MultipartFile file) {
        logger.debug("=====儲存檔案====");
        if (hasExcelFormat(file)) {
            try {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    logger.info(originalFilename);
                    List<Config> configs = excelToTutorials(file.getInputStream());
                    configRepo.saveAll(configs);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // static 不用實體化呼叫
    public static boolean hasExcelFormat(MultipartFile file) {
        String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        logger.debug("=====儲存檔案====");
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Config> excelToTutorials(InputStream is) {
        String SHEET = "Configs";
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet  = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Config> configs = new ArrayList<Config>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    logger.info(String.valueOf(rowNumber));
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Config config = new Config();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            config.setPhaseId(currentCell.getStringCellValue());
                            break;
                        case 1:
                            config.setLocId(currentCell.getStringCellValue());
                            break;
                        case 2:
                            config.setMatId(currentCell.getStringCellValue());
                            break;
                        case 3:
                            config.setBatchId(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                configs.add(config);

            }
            workbook.close();
            return configs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
