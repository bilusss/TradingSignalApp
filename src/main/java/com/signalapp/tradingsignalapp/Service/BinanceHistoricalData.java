package com.signalapp.tradingsignalapp.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

public class BinanceHistoricalData {
    @Value("${binance.apiKey}")
    private String apiKey;

    @Value("${binance.secretKey}")
    private String secretKey;


    @Bean
    public static ArrayList<String>  HistoricalData(String pair, String timeline){

        String url = "https://api.binance.com/api/v3/uiKlines?symbol="+pair+"&interval="+timeline;
        ArrayList<String> PairData = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        try{
            String response = restTemplate.getForObject(url, String.class);

            JsonNode rootnode = com.fasterxml.jackson.databind.json.JsonMapper.builder()
                    .build()
                    .readTree(response);

//            for () {//CONTINUE HERE
//                PairData.add(rootnode.get(pair).asText());
//            }

        }catch (Exception err){
            System.err.println("Chart generate error code: " +err.getMessage());
        }
        return PairData;
    }
}
