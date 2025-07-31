package com.bookstore.repository;



import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    
    /**
     * Find books by name containing the keyword (case-insensitive)
     */
    List<Book> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find books by author containing the keyword (case-insensitive)
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Search books by name or author containing the keyword
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByNameOrAuthor(@Param("keyword") String keyword);
    
    /**
     * Check if a book exists by name and author (for duplicate prevention)
     */
    boolean existsByNameAndAuthor(String name, String author);
}
