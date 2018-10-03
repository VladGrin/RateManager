package com.rate.manager.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rate.manager.model.Rate;
import com.rate.manager.service.BuildRequest;
import com.rate.manager.service.ResponseHandle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseHandleImpl implements ResponseHandle {

    private BuildRequest buildRequest = new BuildRequestImpl();

    private List<Rate> getRateFromPB(String date) {
        List<Rate> list = new ArrayList<>();

        String request = buildRequest.getRequestFromPB(date);
        JsonObject object = new JsonParser().parse(request).getAsJsonObject();
        String exchangeDate = object.get("date").getAsString();

        JsonArray exchangeRate = object.get("exchangeRate").getAsJsonArray();
        for (int i = 0; i < exchangeRate.size(); i++) {
            JsonElement bankCode = exchangeRate.get(i).getAsJsonObject().get("currency");
            String bankCodeString = (bankCode != null) ? bankCode.getAsString() : "none";

            JsonElement saleRate = exchangeRate.get(i).getAsJsonObject().get("saleRate");
            String saleRateString = (saleRate != null) ? saleRate.getAsString() : "none";

            JsonElement purchaseRate = exchangeRate.get(i).getAsJsonObject().get("purchaseRate");
            String purchaseRateString = (purchaseRate != null) ? purchaseRate.getAsString() : "none";

            Rate ratePB = Rate.builder()
                    .bankCode(bankCodeString)
                    .saleRate(saleRateString)
                    .purchaseRate(purchaseRateString)
                    .exchangeDate(exchangeDate)
                    .build();
            list.add(ratePB);
        }
        return list;
    }

    private List<Rate> getRateFromNBU(String date) {
        List<Rate> list = new ArrayList<>();

        String request = buildRequest.getRequestFromNBU(date);

        JsonArray array = new JsonParser().parse(request).getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonElement currencyName = array.get(i).getAsJsonObject().get("txt");
            String currencyNameString = (currencyName != null) ? currencyName.getAsString() : "none";

            JsonElement bankCode = array.get(i).getAsJsonObject().get("cc");
            String bankCodeString = (bankCode != null) ? bankCode.getAsString() : "none";

            JsonElement rateNBU = array.get(i).getAsJsonObject().get("rate");
            String rateNBUString = (rateNBU != null) ? rateNBU.getAsString() : "none";

            Rate rate = Rate.builder()
                    .currencyName(currencyNameString)
                    .bankCode(bankCodeString)
                    .rateNBU(rateNBUString)
                    .build();
            list.add(rate);
        }
        return list;
    }

    @Override
    public List<Rate> getRateByDateAndByCode(String date, String code) {
        return getRateByDate(date).stream().filter(x->x.getBankCode().equals(code)).collect(Collectors.toList());
    }

    @Override
    public List<Rate> getRateByDate(String date) {
        List<Rate> list = new ArrayList<>();

        List<Rate> rateFromPB = getRateFromPB(date);
        List<Rate> rateFromNBU = getRateFromNBU(date);
        for (int i = 0; i < rateFromPB.size(); i++) {
            Rate ratePB = rateFromPB.get(i);
            for (int j = 0; j < rateFromNBU.size(); j++) {
                if (ratePB.getBankCode().equals( rateFromNBU.get(j).getBankCode())) {
                    Rate rateNBU = rateFromNBU.get(j);
                    Rate rate = Rate.builder().currencyName(rateNBU.getCurrencyName())
                            .bankCode(rateNBU.getBankCode())
                            .rateNBU(rateNBU.getRateNBU())
                            .saleRate(ratePB.getSaleRate())
                            .purchaseRate(ratePB.getPurchaseRate())
                            .exchangeDate(ratePB.getExchangeDate())
                            .build();
                    list.add(rate);
                    break;
                }
            }
        }
        return list;
    }
}
