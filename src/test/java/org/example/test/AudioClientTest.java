package org.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioClientTest {

    private static final int NUM_CLIENTS = 10;
    private static final int GET_POST_RATIO = 2; // 2 GET requests for every 1 POST request
    private static final int TOTAL_REQUESTS = 100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_CLIENTS);

        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            executorService.submit(() -> {
                try {
                    int rand = (int) (Math.random() * GET_POST_RATIO);
                    String method = rand == 0 ? "GET" : "POST";
                    String url = "http://localhost:8080/audiosItem";

                    if (method.equals("GET")) {
                        // return a property value given an artist’s name
                        String artistName = "Tyler Swift";
                        String key = "track title";
                        url += "?artist name=" + artistName + "&key=" + key;
                    } else {
                        // store an audio item in the database
                        String artistName = "Justin Bieber";
                        String trackTitle = "Song 4";
                        int trackNumber = 4;
                        int year = 2023;
                        int reviewsCount = 400;
                        int copiesSold = 4000;
                        String postData = "artistName=" + artistName
                                + "&trackTitle=" + trackTitle
                                + "&trackNumber=" + trackNumber
                                + "&year=" + year
                                + "&reviewsCount=" + reviewsCount
                                + "&copiesSold=" + copiesSold;

                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.getOutputStream().write(postData.getBytes());
                        conn.connect();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String response = reader.readLine();
                        System.out.println(response);
                        reader.close();
                    }

                    // send the HTTP request and print the response
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod(method);
                    conn.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }

}
