package org.example.test;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.google.gson.Gson;
import org.example.model.Audio;
public class AudioTestClient {

    private static final String BASE_URL = "http://localhost:8080/audiosItem";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select an option:\n1. GET audio item property\n2. POST new audio item\n3. Quit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter artist name:");
                    String artistName = scanner.nextLine();
                    System.out.println("Enter property name:");
                    String propertyName = scanner.nextLine();
                    getAudioItemProperty(artistName, propertyName);
                    break;
                case "2":
                    System.out.println("Enter artist name:");
                    String newArtistName = scanner.nextLine();
                    System.out.println("Enter track title:");
                    String newTrackTitle = scanner.nextLine();
                    System.out.println("Enter album title:");
                    String newAlbumTitle = scanner.nextLine();
                    System.out.println("Enter track number:");
                    int newTrackNumber = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter year:");
                    int newYear = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter number of reviews:");
                    int newReviewsCount = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter number of copies sold:");
                    int newCopiesSold = Integer.parseInt(scanner.nextLine());
                    Audio newAudio = new Audio(newArtistName, newTrackTitle, newAlbumTitle, newTrackNumber, newYear, newReviewsCount, newCopiesSold);
                    postNewAudioItem(newAudio);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private static void getAudioItemProperty(String artistName, String propertyName) throws IOException {
        URL url = new URL(BASE_URL + "?artist%20name=" + artistName.replaceAll(" ", "%20") + "&key=" + propertyName.replaceAll(" ", "%20"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            System.out.println("Error: " + conn.getResponseMessage());
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private static void postNewAudioItem(Audio audio) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = new Gson().toJson(audio);
        conn.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));

        if (conn.getResponseCode() != 201) {
            System.out.println("Error: " + conn.getResponseMessage());
            return;
        }

        System.out.println("New audio item created successfully.");
    }
}
