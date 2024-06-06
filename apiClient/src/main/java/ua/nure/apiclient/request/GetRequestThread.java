package ua.nure.apiclient.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class GetRequestThread extends Thread {
    private final String url;
    private String response;
    private Map<String, String> headers;
    private final Object lock = new Object();

    public GetRequestThread(String url) {
        this.url = url;
    }

    public GetRequestThread(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int responseCode = connection.getResponseCode();
            BufferedReader in;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            synchronized (lock) {
                this.response = response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String getResponse() {
        synchronized (lock) {
            return response;
        }
    }
}
