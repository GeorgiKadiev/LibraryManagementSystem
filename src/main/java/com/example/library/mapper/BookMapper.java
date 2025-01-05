package com.example.library.mapper;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setGenreName(book.getGenre() != null ? book.getGenre().getName() : null);
        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        return book;
    }
}
