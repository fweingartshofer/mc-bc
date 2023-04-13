package at.fhooe;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Random;

public class Client {

    private static final String SERVER_URL = "http://localhost:7070/";
    private static final String HEADER_FORMAT = "%s;%s;%s;%s";

    public static void main(String[] args) throws Exception {
        request(SERVER_URL, 1, "GET");
        request(SERVER_URL + "greet", 2, "GET");
        request(SERVER_URL + "upload", 3, "POST");
    }

    private static void request(String urlString, int difficulty, String requestMethod) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Set the header
        String header = generateHeader(urlString, difficulty);
        con.setRequestProperty("HashREST", header);

        // Send the request
        con.setRequestMethod(requestMethod);
        int responseCode = con.getResponseCode();

        System.out.print(urlString + ": ");
        // Handle the response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("DONE!");
        } else {
            System.out.printf("Error: %s%n", responseCode);
        }
    }

    private static String generateHeader(String url, int difficulty) {
        long timestamp = Instant.now().getEpochSecond();
        String randomString = generateRandomString();
        int i = 1;
        String header = HEADER_FORMAT.formatted(timestamp, url, randomString, i);
        String hash = new HashedString(header).toString();

        while (!hash.startsWith("0".repeat(difficulty))) {
            i++;
            header = HEADER_FORMAT.formatted(timestamp, url, randomString, i);
            hash = new HashedString(header).toString();
        }

        return header;
    }

    private static String generateRandomString() {
        Random random = new Random();
        return Long.toString(random.nextLong(), 36);
    }
}

