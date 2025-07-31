package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    
    /**
     * Create a new book
     */
    BookDTO createBook(BookDTO bookDTO);
    
    /**
     * Create multiple books
     */
    List<BookDTO> createBooks(List<BookDTO> bookDTOs);
    
    /**
     * Get all books
     */
    List<BookDTO> getAllBooks();
    
    /**
     * Get books with pagination
     */
    Page<BookDTO> getAllBooks(Pageable pageable);
    
    /**
     * Get book by ID
     */
    BookDTO getBookById(Integer id);
    
    /**
     * Update book by ID
     */
    BookDTO updateBook(Integer id, BookDTO bookDTO);
    
    /**
     * Delete book by ID
     */
    void deleteBook(Integer id);
    
    /**
     * Delete all books
     */
    void deleteAllBooks();
    
    /**
     * Search books by name or author
     */
    List<BookDTO> searchBooks(String keyword);
    
    /**
     * Check if book exists
     */
    boolean bookExists(Integer id);
}