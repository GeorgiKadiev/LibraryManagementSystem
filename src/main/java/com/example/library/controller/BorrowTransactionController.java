package com.example.library.controller;

import com.example.library.dto.BorrowTransactionDTO;
import com.example.library.mapper.BorrowTransactionMapper;
import com.example.library.service.BorrowTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class BorrowTransactionController {

    private final BorrowTransactionService borrowTransactionService;

    public BorrowTransactionController(BorrowTransactionService borrowTransactionService) {
        this.borrowTransactionService = borrowTransactionService;
    }

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve a list of all borrow transactions in the system")
    public List<BorrowTransactionDTO> getAllTransactions() {
        return borrowTransactionService.getAllTransactions()
                .stream()
                .map(BorrowTransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Add a new borrow transaction to the system")
    public BorrowTransactionDTO createTransaction(@RequestParam Long memberId, @RequestParam Long bookId) {
        return BorrowTransactionMapper.toDTO(borrowTransactionService.createTransaction(memberId, bookId, LocalDate.now()));
    }
}
