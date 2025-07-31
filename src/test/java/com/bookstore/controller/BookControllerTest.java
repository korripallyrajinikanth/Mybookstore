package com.bookstore.controller;

import com.bookstore.dto.BookDTO;
import com.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        // Given
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Test Book");
        bookDTO.setAuthor("Test Author");
        bookDTO.setPrice(new BigDecimal("19.99"));

        BookDTO createdBook = new BookDTO();
        createdBook.setId(1);
        createdBook.setName("Test Book");
        createdBook.setAuthor("Test Author");
        createdBook.setPrice(new BigDecimal("19.99"));

        when(bookService.createBook(any(BookDTO.class))).thenReturn(createdBook);

        // When & Then
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Test Book"))
                .andExpect(jsonPath("$.data.author").value("Test Author"))
                .andExpect(jsonPath("$.data.price").value(19.99));
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        // Given
        BookDTO book1 = new BookDTO(1, "Book 1", "Author 1", new BigDecimal("15.99"));
        BookDTO book2 = new BookDTO(2, "Book 2", "Author 2", new BigDecimal("25.99"));
        List<BookDTO> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Book 1"))
                .andExpect(jsonPath("$.data[1].name").value("Book 2"));
    }

    @Test
    void getBookById_ShouldReturnBook() throws Exception {
        // Given
        BookDTO book = new BookDTO(1, "Test Book", "Test Author", new BigDecimal("19.99"));
        when(bookService.getBookById(1)).thenReturn(book);

        // When & Then
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Test Book"));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        // Given
        BookDTO updateRequest = new BookDTO();
        updateRequest.setName("Updated Book");
        updateRequest.setAuthor("Updated Author");
        updateRequest.setPrice(new BigDecimal("29.99"));

        BookDTO updatedBook = new BookDTO(1, "Updated Book", "Updated Author", new BigDecimal("29.99"));
        when(bookService.updateBook(eq(1), any(BookDTO.class))).thenReturn(updatedBook);

        // When & Then
        mockMvc.perform(put("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Updated Book"));
    }

    @Test
    void deleteBook_ShouldReturnSuccess() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book deleted successfully"));
    }

    @Test
    void searchBooks_ShouldReturnMatchingBooks() throws Exception {
        // Given
        BookDTO book = new BookDTO(1, "Spring Boot Guide", "John Doe", new BigDecimal("39.99"));
        List<BookDTO> searchResults = Arrays.asList(book);
        when(bookService.searchBooks("spring")).thenReturn(searchResults);

        // When & Then
        mockMvc.perform(get("/api/v1/books/search")
                .param("keyword", "spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Spring Boot Guide"));
    }

    @Test
    void healthCheck_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/books/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("OK"));
    }
}