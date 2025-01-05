package com.example.library.controller;

import com.example.library.dto.LibraryMemberDTO;
import com.example.library.service.LibraryMemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @GetMapping
    @Operation(summary = "Get all members", description = "Retrieve a list of all library members in the system")
    public List<LibraryMemberDTO> getAllMembers() {
        return libraryMemberService.getAllLibraryMembers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a member by ID", description = "Retrieve a library member by their ID")
    public LibraryMemberDTO getMemberById(@PathVariable Long id) {
        return libraryMemberService.getLibraryMemberById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new member", description = "Add a new library member to the system")
    public LibraryMemberDTO createMember(@RequestBody LibraryMemberDTO memberDTO) {
        return libraryMemberService.createLibraryMember(memberDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a member", description = "Update the details of an existing library member by ID")
    public LibraryMemberDTO updateMember(@PathVariable Long id, @RequestBody LibraryMemberDTO memberDTO) {
        return libraryMemberService.updateLibraryMember(id, memberDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member", description = "Delete a library member from the system by ID")
    public void deleteMember(@PathVariable Long id) {
        libraryMemberService.deleteLibraryMember(id);
    }
}
