package com.thurau.bookappv1.pkgData;

import com.google.gson.Gson;
import com.thurau.bookappv1.pkgServices.BookDetailGetService;
import com.thurau.bookappv1.pkgServices.BookDetailPustService;

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

    public String insertBook(Book book) throws Exception {
        BookDetailPustService controller = new BookDetailPustService();
        BookDetailPustService.setIpHost(ipHost);
        controller.setBook(book);
        controller.execute(BookDetailPustService.COMMAND.POST);
        return controller.get();
    }

    public String updateBook(Book book) throws Exception {
        BookDetailPustService controller = new BookDetailPustService();
        BookDetailPustService.setIpHost(ipHost);
        controller.setBook(book);
        controller.execute(BookDetailPustService.COMMAND.PUT);
        return controller.get();
    }
}
