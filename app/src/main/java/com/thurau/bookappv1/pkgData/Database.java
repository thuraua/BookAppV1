package com.thurau.bookappv1.pkgData;

import com.google.gson.Gson;
import com.thurau.bookappv1.pkgServices.BookDetailGetService;

public class Database {
    private static Database db = null;
    private static String ipHost = "";


    private Database() {

    }

    public static Database newInstance(String ip) {
        if (db == null) {
            db = new Database();
        }
        ipHost = ip;
        return db;
    }

    public Book getBook(int id) throws Exception {
        Gson gson = new Gson();
        Book retBook;

        BookDetailGetService controller = new BookDetailGetService();
        BookDetailGetService.setIpHost(ipHost);

        String paras[] = new String[1];
        paras[0] = Integer.toString(id);
        controller.execute(paras);
        String strFromWebService = controller.get();
        try {
            retBook = gson.fromJson(strFromWebService, Book.class);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return retBook;

    }
}
