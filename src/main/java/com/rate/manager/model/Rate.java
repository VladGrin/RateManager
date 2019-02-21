package com.rate.manager.model;

import lombok.Builder;
import lombok.Data;

//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@Builder
public class Rate {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String currencyName;
    private String bankCode;
    private String rateNBU;
    private String saleRate;
    private String purchaseRate;
    private String exchangeDate;

}
