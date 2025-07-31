package com.bookstore.mapper;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setPrice(book.getPrice());
        return dto;
    }
    
    public Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        return book;
    }
    
    public List<BookDTO> toDTOList(List<Book> books) {
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<Book> toEntityList(List<BookDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromDTO(BookDTO dto, Book book) {
        if (dto.getName() != null) {
            book.setName(dto.getName());
        }
        if (dto.getAuthor() != null) {
            book.setAuthor(dto.getAuthor());
        }
        if (dto.getPrice() != null) {
            book.setPrice(dto.getPrice());
        }
    }
}