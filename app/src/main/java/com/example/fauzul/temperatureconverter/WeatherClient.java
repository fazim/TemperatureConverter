package com.example.fauzul.temperatureconverter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?lat=";
    private static String LONG_URL = "&lon=";
    private static String API_KEY = "&appid=";

    public String getWeatherData(double latitude, double longitude) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL+latitude+LONG_URL+longitude+API_KEY)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);


            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            con.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();

            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
}