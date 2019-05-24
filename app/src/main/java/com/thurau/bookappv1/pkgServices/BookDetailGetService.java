package com.thurau.bookappv1.pkgServices;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookDetailGetService extends AsyncTask<String, Void, String> {
    private static final String URL = "/BookServer/Library/BookDetail/";
    private static String ipHost = null;

    @Override
    protected String doInBackground(String... bookinfo) {
        boolean isError = false;
        java.net.URL url = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String content = null;

        try {
            url = new URL(ipHost + URL + bookinfo[0]);
            conn = (HttpURLConnection) url.openConnection();
            if (!conn.getResponseMessage().contains("OK")) {
                isError = true;
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            //get data from server
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
            if (isError) throw new Exception(conn.getResponseCode() + "; " + content);
        } catch (Exception e) {
            content = e.getMessage();
        } finally {
            try {
                reader.close();
                conn.disconnect();
            } catch (Exception ex) {
            }
        }
        return content;
    }
}
