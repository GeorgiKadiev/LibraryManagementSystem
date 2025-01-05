package com.example.library.repository;

import com.example.library.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    @Query("SELECT m FROM LibraryMember m WHERE m.email = :email")
    LibraryMember findByEmail(@Param("email") String email);
}
