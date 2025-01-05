package com.example.library.service;

import com.example.library.entity.BorrowTransaction;
import com.example.library.entity.LibraryMember;
import com.example.library.entity.Book;
import com.example.library.repository.BorrowTransactionRepository;
import com.example.library.repository.LibraryMemberRepository;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowTransactionService {

    private final BorrowTransactionRepository borrowTransactionRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final BookRepository bookRepository;

    public BorrowTransactionService(BorrowTransactionRepository borrowTransactionRepository,
                                    LibraryMemberRepository libraryMemberRepository,
                                    BookRepository bookRepository) {
        this.borrowTransactionRepository = borrowTransactionRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
    }

    public List<BorrowTransaction> getAllTransactions() {
        return borrowTransactionRepository.findAll();
    }

    public BorrowTransaction createTransaction(Long memberId, Long bookId, LocalDate borrowDate) {
        LibraryMember member = libraryMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setMember(member);
        transaction.setBook(book);
        transaction.setBorrowDate(borrowDate);
        return borrowTransactionRepository.save(transaction);
    }

    public List<BorrowTransaction> findOverdueTransactions(LocalDate date) {
        return borrowTransactionRepository.findOverdueTransactions(date);
    }

    public void completeTransaction(Long id, LocalDate returnDate) {
        BorrowTransaction transaction = borrowTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transaction.setReturnDate(returnDate);
        borrowTransactionRepository.save(transaction);
    }
}
