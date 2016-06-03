package ch.bfh.bti7081.s2016.purple.HealthVisitor.service;

import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.services.Geocoder;
import com.vaadin.tapio.googlemaps.client.LatLon;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by tgdflto1 on 03/06/16.
 */
public class GoogleMapsLatLong {
    private static GoogleMapsLatLong instance;

    private GoogleMapsLatLong() {
    }

    public static GoogleMapsLatLong getInstance() {
        if (instance == null) instance = new GoogleMapsLatLong();
        return instance;
    }

    public LatLon getLatLongByAddress(String address) {


        https:
//maps.googleapis.com/maps/api/geocode/json?address=STREET_ADDRESS
        return null;
    }


    private String getJSON(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            //JSONObject json = new JSONObject(jsonText);
            return "";
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
