package com.example.library.controller;

import com.example.library.dto.AuthorDTO;
import com.example.library.mapper.AuthorMapper;
import com.example.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors in the system")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors()
                .stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "Create a new author", description = "Add a new author to the system")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        return AuthorMapper.toDTO(authorService.createAuthor(AuthorMapper.toEntity(authorDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author", description = "Update the details of an existing author by ID")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return AuthorMapper.toDTO(authorService.updateAuthor(id, AuthorMapper.toEntity(authorDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author", description = "Delete an author from the system by ID")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
