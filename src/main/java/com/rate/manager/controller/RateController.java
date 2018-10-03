package com.rate.manager.controller;

import com.rate.manager.model.Rate;
import com.rate.manager.service.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RateController {
    @Autowired
    private ResponseHandle responseHandle;

    @GetMapping
    public String main(@RequestParam(required = false, defaultValue = "") String code,
                       @RequestParam(required = false, defaultValue = "") String date,
                       Model model){
        List<Rate> rates;
        if (!date.isEmpty() && code != null && !code.isEmpty()) {
            rates = responseHandle.getRateByDateAndByCode(date, code);
        } else if (!date.isEmpty()) {
            rates = responseHandle.getRateByDate(date);
        } else {
            rates = responseHandle.getRateByDate("");
        }

        model.addAttribute("rates", rates);
        model.addAttribute("code", code);
        return "rate";
    }
}
