package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the system")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Add a new book to the system")
    public BookDTO createBook(@RequestBody BookDTO bookDTO, @RequestParam Long genreId) {
        return BookMapper.toDTO(bookService.createBook(BookMapper.toEntity(bookDTO), genreId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update the details of an existing book by ID")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO, @RequestParam Long genreId) {
        return BookMapper.toDTO(bookService.updateBook(id, BookMapper.toEntity(bookDTO), genreId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book from the system by ID")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
