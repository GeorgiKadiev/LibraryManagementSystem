package com.example.library.repository;

import com.example.library.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
    @Query("SELECT t FROM BorrowTransaction t WHERE t.member.name = :memberName")
    List<BorrowTransaction> findTransactionsByMemberName(@Param("memberName") String memberName);

    @Query("SELECT t FROM BorrowTransaction t WHERE t.returnDate IS NULL AND t.borrowDate < :borrowDate")
    List<BorrowTransaction> findOverdueTransactions(@Param("borrowDate") LocalDate borrowDate);
}
