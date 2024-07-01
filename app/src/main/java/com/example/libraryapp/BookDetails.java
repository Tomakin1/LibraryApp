package com.example.libraryapp;

import java.io.Serializable;

public class BookDetails implements Serializable {
    String bookName;
    int  image;

    public BookDetails(String bookName, int image) {
        this.bookName = bookName;
        this.image = image;
    }
}
