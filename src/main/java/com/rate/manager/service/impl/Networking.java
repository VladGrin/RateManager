package com.rate.manager.service.impl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

@Service
public class Networking{
    private URLConnection connection;
    private Scanner scanner;

    public String getRequest(String url) {
        try {
            connection = new URL(url).openConnection();
            scanner = new Scanner(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter("\\Z");
        return scanner.next();
    }
}
