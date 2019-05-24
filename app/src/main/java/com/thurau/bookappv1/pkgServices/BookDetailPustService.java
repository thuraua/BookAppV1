package com.thurau.bookappv1.pkgServices;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.thurau.bookappv1.pkgData.Book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookDetailPustService extends AsyncTask<BookDetailPustService.COMMAND, Void, String> {
    private static final String URL = "/BookServer/Library/BookDetail/";
    private static String ipHost = null;

    public enum COMMAND {POST, PUT}

    private Book book = null;

    public static void setIpHost(String ip) {
        ipHost = ip;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    protected String doInBackground(COMMAND... command) {
        java.net.URL url = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = null;
        Gson gson = new Gson();

        try {
            url = new URL(ipHost + URL);
            conn = (HttpURLConnection) url.openConnection();
            switch (command[0]) {
                case POST:
                    conn.setRequestMethod("POST");
                    break;
                case PUT:
                    conn.setRequestMethod("PUT");
                    break;
            }
            conn.setRequestProperty("Content-Type", "application/json");
            writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            writer.write(gson.toJson(book));
            writer.flush();
            writer.close();
            //if necessary read error message
            if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = conn.getResponseCode() + "; " + sb.toString();
            } else {
                content = "responsecode: " + conn.getResponseCode();
            }

        } catch (Exception e) {
            content = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                writer.close();
                conn.disconnect();
            } catch (Exception ex) {
            }
        }
        return content;
    }
}
