package com.example.library.mapper;

import com.example.library.dto.LibraryMemberDTO;
import com.example.library.entity.LibraryMember;

public class LibraryMemberMapper {

    public static LibraryMemberDTO toDTO(LibraryMember member) {
        LibraryMemberDTO dto = new LibraryMemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        return dto;
    }

    public static LibraryMember toEntity(LibraryMemberDTO dto) {
        LibraryMember member = new LibraryMember();
        member.setId(dto.getId());
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        return member;
    }
}
