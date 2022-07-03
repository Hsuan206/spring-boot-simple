package com.example.employeemanager.controller;

//import com.example.employeemanager.config.IRandomNumberGenerator;
//import com.example.employeemanager.config.IRandomNumberSerializer;
import com.example.employeemanager.config.RandNumber;
import com.example.employeemanager.invoker.EarthquakeInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping(value = "/data")
public class EarthquakeController {

    @Autowired
    private EarthquakeInvoker invoker;
    private Logger logger = LoggerFactory.getLogger(EarthquakeController.class);
    @GetMapping(value = {"/earthquake-data"})
    public void getEarthquakeData() throws Exception {
        try{
//            invoker.connectEarthquake();
        } catch (Exception e) {
            logger.error("ERROR: {}", e.getMessage());
        }
    }
//    public void getMultiple(
//            @RequestHeader("MAX_NUM_NUMS") int maxCount,
//            HttpServletRequest request,
//            HttpServletResponse response,
//            ServletOutputStream outputStream) throws Exception {
//
//        response.setStatus(HttpServletResponse.SC_OK);
//        String boundary = UUID.randomUUID().toString();
//        response.setContentType("multipart/x-mixed-replace;boundary=" + boundary);
//
//        RandNumber[] numbers = randomNumberGenerator.getRandomNumbers(maxCount);
//
//        for (int index = 0; index < maxCount; index++) {
//            outputStream.println("--" + boundary);
//            outputStream.println("Content-Type: " + randomNumberSerializer.getContentType());
//            outputStream.println();
//
//            outputStream.println(randomNumberSerializer.serializeRandomNumber(numbers[index]));
//
//            outputStream.println();
//        }
//        outputStream.println("--" + boundary + "--");
//        outputStream.flush();
//        outputStream.close();
//    }
}
