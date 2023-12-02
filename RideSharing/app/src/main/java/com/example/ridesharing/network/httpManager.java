package com.example.ridesharing.network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class httpManager implements Callable<String>{
    private final String urlAddress;
    private HttpURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    public httpManager(String urlAddress) {
       this.urlAddress = urlAddress;
    }
    private String process() {
        try {
            connection = (HttpURLConnection) new URL(urlAddress).openConnection();
            bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line= bufferedReader.readLine()) != null){
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return null;
    }
    @Override
    public String call() {
        return process();
    }
}
