package ua.nure.apiclient.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JWTRequestThread extends Thread {
    private final String url;
    private final String requestBody;

    public JWTRequestThread(String url, String requestBody) {
        this.url = url;
        this.requestBody = requestBody;
    }

    @Override
    public void run() {
        try {
            URL apiUrl = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jwtToken = response.toString();
                System.out.println("Received JWT token: " + jwtToken);
            } else {
                System.out.println("Failed to receive JWT token. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
