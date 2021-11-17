package com.example.employeemanager.Service;

import com.example.employeemanager.model.Config;
import com.example.employeemanager.repository.ConfigRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ConfigService {
//    Logger logger;
//    private final ConfigRepo configRepo;
//    @Autowired
//    public ConfigService(ConfigRepo configRepo) {
//        this.configRepo = configRepo;
//    }
//
//    public List<Config> findAllConfigs() {
//        return configRepo.findAll();
//    }
//    public Config addConfig(Config config) {
//        return configRepo.save(config);
//    }
//    public Config updateConfig(Long id, Config config) {
//        Config newConfig = new Config();
//        newConfig.setId(id);
//        newConfig.setPhaseId(config.getPhaseId());
//        newConfig.setLocId(config.getLocId());
//        newConfig.setMatId(config.getMatId());
//        newConfig.setBatchId(config.getBatchId());
//        return configRepo.save(newConfig);
//    }
//
//    public void deleteConfig(Long id) {
//        try {
//            Optional<Config> config = configRepo.findConfigById(id);
//            configRepo.deleteById(id);
//        } catch (Exception e) {
//            logger.warning("File not found, resetting score.");
//        }
//    }
//
//    public Config findConfigById(Long id) {
//        return configRepo.findConfigById(id)
//                .orElseThrow(()-> new UserNotFoundException("not found id"));
//    }
//
//    public void saveFile(MultipartFile file) {
//        try {
//            logger.info("HELLO");
//            List<Config> configs = excelToTutorials(file.getInputStream());
//            logger.warning(configs.get(0).getPhaseId());
//            configRepo.saveAll(configs);
//        } catch (IOException e) {
//            throw new RuntimeException("fail to store excel data: " + e.getMessage());
//        }
//    }
//    public static List<Config> excelToTutorials(InputStream is) {
//        String SHEET = "Configs";
//        try {
//            Workbook workbook = new XSSFWorkbook(is);
//            Sheet sheet = workbook.getSheet(SHEET);
//            Iterator<Row> rows = sheet.iterator();
//            List<Config> configs = new ArrayList<Config>();
//
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//                // skip header
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//                Iterator<Cell> cellsInRow = currentRow.iterator();
//                Config config = new Config();
//                int cellIdx = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//                    switch (cellIdx) {
//                        case 0:
//                            config.setPhaseId(currentCell.getStringCellValue());
//                            break;
//                        case 1:
//                            config.setLocId(currentCell.getStringCellValue());
//                            break;
//                        case 2:
//                            config.setMatId(currentCell.getStringCellValue());
//                            break;
//                        case 3:
//                            config.setBatchId(currentCell.getStringCellValue());
//                            break;
//                        default:
//                            break;
//                    }
//                    cellIdx++;
//                }
//                configs.add(config);
//            }
//            workbook.close();
//            return configs;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//    }
}
