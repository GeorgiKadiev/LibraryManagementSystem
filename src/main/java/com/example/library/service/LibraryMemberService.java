package com.example.library.service;

import com.example.library.dto.LibraryMemberDTO;
import com.example.library.entity.LibraryMember;
import com.example.library.mapper.LibraryMemberMapper;
import com.example.library.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<LibraryMemberDTO> getAllLibraryMembers() {
        return libraryMemberRepository.findAll()
                .stream()
                .map(LibraryMemberMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LibraryMemberDTO getLibraryMemberById(Long id) {
        LibraryMember member = libraryMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return LibraryMemberMapper.toDTO(member);
    }

    public LibraryMemberDTO createLibraryMember(LibraryMemberDTO libraryMemberDTO) {
        LibraryMember member = LibraryMemberMapper.toEntity(libraryMemberDTO);
        LibraryMember savedMember = libraryMemberRepository.save(member);
        return LibraryMemberMapper.toDTO(savedMember);
    }

    public LibraryMemberDTO updateLibraryMember(Long id, LibraryMemberDTO libraryMemberDTO) {
        LibraryMember existingMember = libraryMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        existingMember.setName(libraryMemberDTO.getName());
        existingMember.setEmail(libraryMemberDTO.getEmail());

        LibraryMember updatedMember = libraryMemberRepository.save(existingMember);
        return LibraryMemberMapper.toDTO(updatedMember);
    }

    public void deleteLibraryMember(Long id) {
        if (!libraryMemberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }
        libraryMemberRepository.deleteById(id);
    }
}
