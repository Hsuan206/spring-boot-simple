package com.example.employeemanager.invoker;

import com.example.employeemanager.controller.EarthquakeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LengthInterceptor implements ClientHttpRequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(LengthInterceptor.class);
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution ) throws IOException {

        ClientHttpResponse response = execution.execute( request, body );

        long length = response.getHeaders().getContentLength();
        logger.info("LENGTH: {}", length);

        // do something with length
        return response;
    }
}