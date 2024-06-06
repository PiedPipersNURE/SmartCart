package ua.nure.apiclient.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * This is a tool that is used to make a universal request to an API.
 */
public class UniversalRequestThread extends Thread {
    protected final String url;
    protected final String requestBody;
    protected final String requestMethod;
    protected final Map<String, String> headers;
    protected final String contentType;
    protected String responseToken;
    protected final Object lock = new Object();

    public UniversalRequestThread(String url, String requestBody, String requestMethod, Map<String, String> headers, String contentType) {
        this.url = url;
        this.requestBody = requestBody;
        this.requestMethod = requestMethod;
        this.headers = headers;
        this.contentType = contentType;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Content-Type", contentType);

            // Set custom headers
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();
                os.close();
            }

            int responseCode = connection.getResponseCode();
            String responseString = getString(responseCode, connection);
            synchronized (lock) {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    responseToken = responseString;
                    System.out.println("Request successful. Response: " + responseToken);
                } else {
                    responseToken = null;
                    System.out.println("Request failed. Response code: " + responseCode);
                    System.out.println("Error response: " + responseString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getString(int responseCode, HttpURLConnection connection) throws IOException {
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

        String responseString = response.toString();
        return responseString;
    }

    public String getResponseToken() {
        synchronized (lock) {
            return responseToken;
        }
    }
}
