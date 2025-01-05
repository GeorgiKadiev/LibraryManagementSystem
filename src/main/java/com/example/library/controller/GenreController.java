package com.example.library.controller;

import com.example.library.dto.GenreDTO;
import com.example.library.mapper.GenreMapper;
import com.example.library.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Operation(summary = "Get all genres", description = "Retrieve a list of all genres in the system")
    public List<GenreDTO> getAllGenres() {
        return genreService.getAllGenres()
                .stream()
                .map(GenreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "Create a new genre", description = "Add a new genre to the system")
    public GenreDTO createGenre(@RequestBody GenreDTO genreDTO) {
        return GenreMapper.toDTO(genreService.createGenre(GenreMapper.toEntity(genreDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing genre", description = "Update the details of an existing genre by ID")
    public GenreDTO updateGenre(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        return GenreMapper.toDTO(genreService.updateGenre(id, GenreMapper.toEntity(genreDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre", description = "Delete a genre from the system by ID")
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
}
