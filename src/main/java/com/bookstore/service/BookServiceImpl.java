package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO.getName());
        
        // Check for duplicates
        if (bookRepository.existsByNameAndAuthor(bookDTO.getName(), bookDTO.getAuthor())) {
            throw new IllegalArgumentException("Book with same name and author already exists");
        }
        
        Book book = bookMapper.toEntity(bookDTO);
        book.setId(null); // Ensure new entity
        Book savedBook = bookRepository.save(book);
        
        log.info("Book created successfully with ID: {}", savedBook.getId());
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> createBooks(List<BookDTO> bookDTOs) {
        log.info("Creating {} books", bookDTOs.size());
        
        List<Book> books = bookMapper.toEntityList(bookDTOs);
        books.forEach(book -> book.setId(null)); // Ensure new entities
        
        List<Book> savedBooks = bookRepository.saveAll(books);
        log.info("Successfully created {} books", savedBooks.size());
        
        return bookMapper.toDTOList(savedBooks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        log.info("Fetching all books");
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDTOList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        log.info("Fetching books with pagination: page={}, size={}", 
                 pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Integer id) {
        log.info("Fetching book with ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {
        log.info("Updating book with ID: {}", id);
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        
        // Check for duplicates (excluding current book)
        if (!existingBook.getName().equals(bookDTO.getName()) || 
            !existingBook.getAuthor().equals(bookDTO.getAuthor())) {
            if (bookRepository.existsByNameAndAuthor(bookDTO.getName(), bookDTO.getAuthor())) {
                throw new IllegalArgumentException("Book with same name and author already exists");
            }
        }
        
        bookMapper.updateEntityFromDTO(bookDTO, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        
        log.info("Book updated successfully with ID: {}", updatedBook.getId());
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Integer id) {
        log.info("Deleting book with ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
        
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with ID: {}", id);
    }

    @Override
    public void deleteAllBooks() {
        log.info("Deleting all books");
        long count = bookRepository.count();
        bookRepository.deleteAll();
        log.info("Successfully deleted {} books", count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooks(String keyword) {
        log.info("Searching books with keyword: {}", keyword);
        
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }
        
        List<Book> books = bookRepository.searchByNameOrAuthor(keyword.trim());
        return bookMapper.toDTOList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bookExists(Integer id) {
        return bookRepository.existsById(id);
    }
}
