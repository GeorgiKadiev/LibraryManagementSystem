package com.example.library.mapper;

import com.example.library.dto.AuthorDTO;
import com.example.library.entity.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        return dto;
    }

    public static Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        return author;
    }
}
