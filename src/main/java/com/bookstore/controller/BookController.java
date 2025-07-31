package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.dto.BookDTO;
import com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Management", description = "REST API for managing books in the bookstore")
public class BookController {
    
    private final BookService bookService;

    @Operation(summary = "Create a new book", description = "Add a new book to the bookstore")
    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO.getName());
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", createdBook));
    }

    @Operation(summary = "Create multiple books", description = "Add multiple books to the bookstore")
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<BookDTO>>> createBooks(@Valid @RequestBody List<BookDTO> bookDTOs) {
        log.info("Creating {} books", bookDTOs.size());
        List<BookDTO> createdBooks = bookService.createBooks(bookDTOs);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Books created successfully", createdBooks));
    }

    @Operation(summary = "Get all books", description = "Retrieve all books from the bookstore")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
        log.info("Fetching all books");
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.success("Books retrieved successfully", books));
    }

    @Operation(summary = "Get books with pagination", description = "Retrieve books with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<BookDTO>>> getAllBooksPaginated(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookDTO> booksPage = bookService.getAllBooks(pageable);
        
        return ResponseEntity.ok(ApiResponse.success("Books retrieved successfully", booksPage));
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable Integer id) {
        log.info("Fetching book with ID: {}", id);
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success("Book retrieved successfully", book));
    }

    @Operation(summary = "Update book", description = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(
            @PathVariable Integer id, 
            @Valid @RequestBody BookDTO bookDTO) {
        log.info("Updating book with ID: {}", id);
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", updatedBook));
    }

    @Operation(summary = "Delete book", description = "Delete a specific book by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Integer id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }

    @Operation(summary = "Delete all books", description = "Delete all books from the bookstore")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllBooks() {
        log.info("Deleting all books");
        bookService.deleteAllBooks();
        return ResponseEntity.ok(ApiResponse.success("All books deleted successfully", null));
    }

    @Operation(summary = "Search books", description = "Search books by name or author")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDTO>>> searchBooks(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        log.info("Searching books with keyword: {}", keyword);
        List<BookDTO> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", books));
    }

    @Operation(summary = "Check if book exists", description = "Check if a book exists by its ID")
    @GetMapping("/{id}/exists")
    public ResponseEntity<ApiResponse<Boolean>> bookExists(@PathVariable Integer id) {
        boolean exists = bookService.bookExists(id);
        return ResponseEntity.ok(ApiResponse.success("Check completed", exists));
    }

    @Operation(summary = "Health check", description = "Simple health check endpoint")
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Book service is running", "OK"));
    }
}
