package com.example.employeemanager.controller;

import com.example.employeemanager.Service.ConfigService;
import com.example.employeemanager.model.Config;
import com.example.employeemanager.model.ConfigPK;
import com.example.employeemanager.repository.ConfigRepo;
import org.apache.catalina.connector.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ConfigController {
    private final static Logger logger = LoggerFactory.getLogger(ConfigController.class);
    private final ConfigService configService;
    private final ConfigRepo configRepo;
    @Autowired
    public ConfigController(@Autowired ConfigService configService, @Autowired ConfigRepo configRepo) {
        this.configService = configService;
        this.configRepo = configRepo;
    }
    @GetMapping("/configs")
    public ResponseEntity<List<Config>> getAllConfigs () {
        List<Config> configs = configService.findAllConfigs();
        return new ResponseEntity<>(configs, HttpStatus.OK);
    }
//    @GetMapping("/configs/{id}")
//    public ResponseEntity<Config> getConfigById (@PathVariable("id") Long id) {
//        Config config = configService.findConfigById(id);
//        return new ResponseEntity<>(config, HttpStatus.OK);
//    }
//    @PostMapping("/configs")
//    public ResponseEntity<Config> addConfig (@RequestBody Config config) {
//        Config newConfig = configService.addConfig(config);
//        return new ResponseEntity<>(newConfig, HttpStatus.CREATED); // 在 server 上創建東西
//    }
//    @PutMapping("/configs/{id}")
//    public ResponseEntity<Config> updateConfig (@PathVariable("id") Long id, @RequestBody Config config) {
//        Config updateConfig = configService.updateConfig(id, config);
//        return new ResponseEntity<>(updateConfig, HttpStatus.OK);
//    }
//    @DeleteMapping("/configs/{id}")
//    public ResponseEntity<?> deleteConfig (@PathVariable("id") Long id) {
//        // 因為刪除不回傳任何東西不回傳所以打問號
//        configService.deleteConfig(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    public static void checkMagazine(int[] arr) {
//        int pointer = 0;
//        int endIdx = 2;
//        int sum = 0;
//        int max_sum = -10000;
//        Map<List<Integer>, Number> hashMap = new HashMap<>();
//        for(int i = pointer; i<arr.length; i++) {
//            sum = 0;
//            if()
//            for(int j = endIdx; i<arr.length; j+=2) {
//                sum += arr[j];
//                if(sum>max_sum){
//                    max_sum = sum;
//                }
//            }
//
//        }
//    }

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
    public List<Config> excelToTutorials(InputStream is) {
        String SHEET = "Configs";
        try {
            // Workbook(Excel本體)、Sheet(內部頁面)、Row(頁面之列(橫的))、Cell(列內的元素)
            Workbook workbook = new XSSFWorkbook(is); //利用wb承接FileInputStream所讀取的檔案
            Sheet sheet  = workbook.getSheetAt(0); //讀取wb內的頁面
            Iterator<Row> rows = sheet.iterator(); //讀取頁面0的所有列
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
                Iterator<Cell> cellsInRow = currentRow.iterator(); //讀取列的所有欄位元素
                ConfigPK id = new ConfigPK(); //用PK寫法會直接蓋掉原本的資料(更新)
                Config config = new Config();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            id.setPhaseId(currentCell.getStringCellValue());
                            break;
                        case 1:
                            id.setLocId(currentCell.getStringCellValue());
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
                config.setId(id);
                configs.add(config);

//                List<Config> exists = configRepo.findConfigById(id);
//                if(exists.size() > 0){
//                    throw new DataIntegrityViolationException("Duplicate data");
//                }
//                checkExists(id);
            }
            workbook.close();
            return configs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    public void checkExists(ConfigPK id) throws DataIntegrityViolationException {
        List<Config> exists = configRepo.findConfigById(id);
        if(exists.size() > 0){
            throw new DataIntegrityViolationException("Duplicate data");
        }
    }

}

