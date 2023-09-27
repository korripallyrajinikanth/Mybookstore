package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class BookServiceImpl implements com.bookstore.service.Service {
	
	@Autowired
	private BookRepository bRepo;

	@Override
	public List<Book> getAllBooks() {
		return bRepo.findAll();
	}

	@Override
	@Transactional
	public void deleteBookById(int id) {


			Book entity = bRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Entity with ID " + id + " not found"));

		bRepo.delete(entity);

	}

	@Override
	public Book getBookById(int id) {
		return bRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity with ID " + id + " not found"));
	}

	@Override
	public void deleteAllBooks() {
		bRepo.deleteAll();
	}

	@Override
	public Book updateBookById(int id, Book updatedEntity) {
		Book existingEntity = bRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found"));

		// Update the existing entity with the new data
		existingEntity.setName(updatedEntity.getName());
		existingEntity.setAuthor(updatedEntity.getAuthor());
		existingEntity.setPrice(updatedEntity.getPrice());


		return bRepo.save(existingEntity);
	}

	@Override
	public List<Book> savealldata(List<Book> book) {
		return (List<Book>)bRepo.saveAll(book);
	}


}
