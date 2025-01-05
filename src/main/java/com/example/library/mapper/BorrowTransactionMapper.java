package com.example.library.mapper;

import com.example.library.dto.BorrowTransactionDTO;
import com.example.library.entity.BorrowTransaction;

import java.time.format.DateTimeFormatter;

public class BorrowTransactionMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static BorrowTransactionDTO toDTO(BorrowTransaction transaction) {
        BorrowTransactionDTO dto = new BorrowTransactionDTO();
        dto.setId(transaction.getId());
        dto.setMemberName(transaction.getMember().getName());
        dto.setBookTitle(transaction.getBook().getTitle());
        dto.setBorrowDate(transaction.getBorrowDate().format(DATE_FORMATTER));
        dto.setReturnDate(transaction.getReturnDate() != null ? transaction.getReturnDate().format(DATE_FORMATTER) : "Not returned");
        return dto;
    }
}
