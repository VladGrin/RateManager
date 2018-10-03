package com.rate.manager.service;

import org.springframework.stereotype.Service;

@Service
public interface BuildRequest {

    String getRequestFromPB(String date);

    String getRequestFromNBU(String date);
}
