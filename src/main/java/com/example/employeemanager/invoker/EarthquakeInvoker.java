package com.example.employeemanager.invoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Component
public class EarthquakeInvoker {

//    private String url = "http://61.56.9.234:80/svc-bin/streaming/";
//    private Logger logger = LoggerFactory.getLogger(EarthquakeInvoker.class);
//    public void connectEarthquake() {
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            HttpHeaders headers = createHttpHeaders("CO.TSMC_H", "1234abcd");
//            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//            interceptors.add(new LengthInterceptor());
//            restTemplate.setInterceptors( interceptors );
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//            logger.info("Result - status ( {} ) has body: {} ", response.getStatusCode(), response.hasBody());
//        } catch (Exception e) {
//            logger.error("ERROR: {}", e.getMessage());
//        }
//    }
//    private HttpHeaders createHttpHeaders(String user, String password) {
//        String notEncoded = user + ":" + password;
//        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "Basic " + encodedAuth);
//        return headers;
//    }
}
