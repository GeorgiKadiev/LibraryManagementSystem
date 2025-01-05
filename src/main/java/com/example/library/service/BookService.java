package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.Genre;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book createBook(Book book, Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found with id: " + genreId));

        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            book.setIsbn(generateISBN());
        }

        book.setGenre(genre);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails, Long genreId) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> getBooksByGenreName(String genreName) {
        return bookRepository.findBooksByGenreName(genreName);
    }

    private String generateISBN() {
        StringBuilder isbn = new StringBuilder("978");
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            isbn.append(random.nextInt(10));
        }

        int checksum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            checksum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (checksum % 10)) % 10;
        isbn.append(checkDigit);

        return isbn.toString();
    }
}
