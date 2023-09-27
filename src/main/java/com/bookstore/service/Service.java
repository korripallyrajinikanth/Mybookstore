package com.bookstore.service;

import com.bookstore.entity.Book;

import java.util.List;

public interface Service {
    public List<Book> savealldata(List<Book> book);

    public List<Book> getAllBooks();

    public void deleteBookById(int id);


    public Book getBookById(int id);

    void deleteAllBooks();

    Book updateBookById(int id, Book updatedEntity);
}
