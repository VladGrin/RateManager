package com.rate.manager.service;

import com.rate.manager.model.Rate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseHandle {

    List<Rate> getRateByDateAndByCode(String date, String code);

    List<Rate> getRateByDate(String date);
}
