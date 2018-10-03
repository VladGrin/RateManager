package com.rate.manager.service.impl;

import com.rate.manager.service.BuildRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BuildRequestImpl implements BuildRequest {

    @Override
    public String getRequestFromPB(String date) {
        String[] strings;
        String url;
        if(!date.equals("")){
            strings = date.split("-");
            String formatDate = strings[2] + "." + strings[1] + "." + strings[0];
            url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=" + formatDate;
        } else {
            url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=" +
                    new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        }
        return new Networking().getRequest(url);
    }

    @Override
    public String getRequestFromNBU(String date) {
        String url;
        if(!date.equals("")){
            url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=" +
                    date.replace("-","") + "&json";
        } else {
            url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        }
        return new Networking().getRequest(url);
    }
}
