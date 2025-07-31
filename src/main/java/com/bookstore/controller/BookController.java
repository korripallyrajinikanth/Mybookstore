package com.bookstore.controller;




import com.bookstore.entity.Book;

import com.bookstore.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class BookController {
	
	@Autowired
	private BookServiceImpl bookService;



	@GetMapping("/AvailableBooks")
	public List<Book> getAvailableBooks()
	{
		return bookService.getAllBooks();
	}
	@GetMapping ("/test")
	public String hi()
	{
		return  "Your application working fine";
	}

	@GetMapping("/getBookById/{id}")
	public Book getBookById(@PathVariable int id)
	{

		return bookService.getBookById(id);
	}
	@DeleteMapping("/deleteAllBooks")
	public String deleteAllBooks()
	{
		bookService.deleteAllBooks();
		return "You deleted all the books From the DATABASE";
	}
	@DeleteMapping("/deleteBookById/{id}")
	public String deleteBookById(@PathVariable int id)
	{

			bookService.deleteBookById(id);


		return " You deleted the book with id "  +id+ " You can't Undo this operation Man";
	}

	@PutMapping("/UpdateBookById/{id}")
	public ResponseEntity<Book> updateBookById(@PathVariable int id, @RequestBody Book updatedEntity) {
		Book result = bookService.updateBookById(id, updatedEntity);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/addBooks")
	public  List<Book> saving(@RequestBody List<Book> book)
	{

		return bookService.savealldata(book);
	}



}
