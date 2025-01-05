package com.example.library.console;

import com.example.library.dto.*;
import com.example.library.mapper.*;
import com.example.library.service.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component
@Profile("!test")
public class ConsoleInterface {

    private final GenreService genreService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final LibraryMemberService libraryMemberService;
    private final BorrowTransactionService borrowTransactionService;

    public ConsoleInterface(GenreService genreService, BookService bookService, AuthorService authorService,
                            LibraryMemberService libraryMemberService, BorrowTransactionService borrowTransactionService) {
        this.genreService = genreService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.libraryMemberService = libraryMemberService;
        this.borrowTransactionService = borrowTransactionService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Manage Genres");
            System.out.println("2. Manage Books");
            System.out.println("3. Manage Authors");
            System.out.println("4. Manage Library Members");
            System.out.println("5. Manage Borrow Transactions");
            System.out.println("6. View All Data");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> manageGenres(scanner);
                case 2 -> manageBooks(scanner);
                case 3 -> manageAuthors(scanner);
                case 4 -> manageMembers(scanner);
                case 5 -> manageBorrowTransactions(scanner);
                case 6 -> viewAllData();
                case 7 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageGenres(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Genre Management ---");
            System.out.println("1. Add Genre");
            System.out.println("2. View All Genres");
            System.out.println("3. Update Genre");
            System.out.println("4. Delete Genre");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addGenre(scanner);
                case 2 -> viewAllGenres();
                case 3 -> updateGenre(scanner);
                case 4 -> deleteGenre(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addGenre(Scanner scanner) {
        System.out.print("Enter genre name: ");
        String name = scanner.nextLine();
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName(name);
        genreService.createGenre(GenreMapper.toEntity(genreDTO));
        System.out.println("Genre added successfully!");
    }

    private void viewAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres()
                .stream()
                .map(GenreMapper::toDTO)
                .toList();
        if (genres.isEmpty()) {
            System.out.println("No genres found.");
        } else {
            System.out.println("\n=== Genres ===");
            genres.forEach(genre -> System.out.println("ID: " + genre.getId() + ", Name: " + genre.getName()));
        }
    }

    private void updateGenre(Scanner scanner) {
        System.out.print("Enter genre ID to update: ");
        Long genreId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Enter new genre name: ");
        String name = scanner.nextLine();
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genreId);
        genreDTO.setName(name);
        genreService.updateGenre(genreId, GenreMapper.toEntity(genreDTO));
        System.out.println("Genre updated successfully!");
    }

    private void deleteGenre(Scanner scanner) {
        System.out.print("Enter genre ID to delete: ");
        Long genreId = scanner.nextLong();
        genreService.deleteGenre(genreId);
        System.out.println("Genre deleted successfully!");
    }

    private void manageBooks(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> viewAllBooks();
                case 3 -> updateBook(scanner);
                case 4 -> deleteBook(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter genre ID: ");
        Long genreId = scanner.nextLong();
        scanner.nextLine();

        String isbn = generateISBN();

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);

        try {
            bookService.createBook(BookMapper.toEntity(bookDTO), genreId);
            System.out.println("Book added successfully! ISBN: " + isbn);
        } catch (Exception e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }
    }

    private String generateISBN() {
        StringBuilder isbn = new StringBuilder("978");
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            isbn.append(random.nextInt(10));
        }

        int checksum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            checksum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (checksum % 10)) % 10;
        isbn.append(checkDigit);

        return isbn.toString();
    }


    private void viewAllBooks() {
        List<BookDTO> books = bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\n=== Books ===");
            books.forEach(book -> System.out.println(
                    "ID: " + book.getId() +
                            ", Title: " + book.getTitle() +
                            ", ISBN: " + book.getIsbn() +
                            ", Genre: " + book.getGenreName()
            ));
        }
    }

    private void updateBook(Scanner scanner) {
        System.out.print("Enter book ID to update: ");
        Long bookId = scanner.nextLong();
        scanner.nextLine();

        BookDTO existingBook;
        try {
            existingBook = BookMapper.toDTO(bookService.getBookById(bookId));
        } catch (Exception e) {
            System.out.println("Book not found: " + e.getMessage());
            return;
        }

        System.out.print("Enter new book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter new genre ID: ");
        String genreIdInput = scanner.nextLine();

        if (!title.isEmpty()) {
            existingBook.setTitle(title);
        }
        if (!isbn.isEmpty()) {
            existingBook.setIsbn(isbn);
        }

        Long genreId = existingBook.getGenreId();
        if (!genreIdInput.isEmpty()) {
            try {
                genreId = Long.parseLong(genreIdInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid genre ID. Keeping the current genre.");
            }
        }

        try {
            bookService.updateBook(bookId, BookMapper.toEntity(existingBook), genreId);
            System.out.println("Book updated successfully!");
        } catch (Exception e) {
            System.out.println("Failed to update book: " + e.getMessage());
        }
    }



    private void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        Long bookId = scanner.nextLong();
        scanner.nextLine();

        try {
            bookService.deleteBook(bookId);
            System.out.println("Book deleted successfully!");
        } catch (Exception e) {
            System.out.println("Failed to delete book: " + e.getMessage());
        }
    }


    private void manageAuthors(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Author Management ---");
            System.out.println("1. Add Author");
            System.out.println("2. View All Authors");
            System.out.println("3. Update Author");
            System.out.println("4. Delete Author");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addAuthor(scanner);
                case 2 -> viewAllAuthors();
                case 3 -> updateAuthor(scanner);
                case 4 -> deleteAuthor(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addAuthor(Scanner scanner) {
        System.out.print("Enter author name: ");
        String name = scanner.nextLine();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(name);
        authorService.createAuthor(AuthorMapper.toEntity(authorDTO));
        System.out.println("Author added successfully!");
    }

    private void viewAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors()
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
        if (authors.isEmpty()) {
            System.out.println("No authors found.");
        } else {
            System.out.println("\n=== Authors ===");
            authors.forEach(author -> System.out.println("ID: " + author.getId() + ", Name: " + author.getName()));
        }
    }

    private void updateAuthor(Scanner scanner) {
        System.out.print("Enter author ID to update: ");
        Long authorId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Enter new author name: ");
        String name = scanner.nextLine();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(authorId);
        authorDTO.setName(name);
        authorService.updateAuthor(authorId, AuthorMapper.toEntity(authorDTO));
        System.out.println("Author updated successfully!");
    }

    private void deleteAuthor(Scanner scanner) {
        System.out.print("Enter author ID to delete: ");
        Long authorId = scanner.nextLong();
        authorService.deleteAuthor(authorId);
        System.out.println("Author deleted successfully!");
    }

    private void manageMembers(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Library Member Management ---");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addMember(scanner);
                case 2 -> viewAllMembers();
                case 3 -> updateMember(scanner);
                case 4 -> deleteMember(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addMember(Scanner scanner) {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();

        LibraryMemberDTO memberDTO = new LibraryMemberDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);

        try {
            LibraryMemberDTO createdMember = libraryMemberService.createLibraryMember(memberDTO);
            System.out.println("Member added successfully! ID: " + createdMember.getId());
        } catch (Exception e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    private void viewAllMembers() {
        List<LibraryMemberDTO> members = libraryMemberService.getAllLibraryMembers();
        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            System.out.println("\n=== Library Members ===");
            members.forEach(member -> System.out.println(
                    "ID: " + member.getId() +
                            ", Name: " + member.getName() +
                            ", Email: " + member.getEmail()));
        }
    }

    private void updateMember(Scanner scanner) {
        System.out.print("Enter member ID to update: ");
        Long memberId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Enter new member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new member email: ");
        String email = scanner.nextLine();

        LibraryMemberDTO memberDTO = new LibraryMemberDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);

        try {
            LibraryMemberDTO updatedMember = libraryMemberService.updateLibraryMember(memberId, memberDTO);
            System.out.println("Member updated successfully! ID: " + updatedMember.getId());
        } catch (Exception e) {
            System.out.println("Error updating member: " + e.getMessage());
        }
    }

    private void deleteMember(Scanner scanner) {
        System.out.print("Enter member ID to delete: ");
        Long memberId = scanner.nextLong();

        try {
            libraryMemberService.deleteLibraryMember(memberId);
            System.out.println("Member deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting member: " + e.getMessage());
        }
    }


    private void manageBorrowTransactions(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Borrow Transaction Management ---");
            System.out.println("1. Borrow a Book");
            System.out.println("2. View All Transactions");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> borrowBook(scanner);
                case 2 -> viewAllTransactions();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void borrowBook(Scanner scanner) {
        System.out.print("Enter member ID: ");
        Long memberId = scanner.nextLong();
        System.out.print("Enter book ID: ");
        Long bookId = scanner.nextLong();

        borrowTransactionService.createTransaction(memberId, bookId, LocalDate.now());
        System.out.println("Book borrowed successfully!");
    }

    private void viewAllTransactions() {
        List<BorrowTransactionDTO> transactions = borrowTransactionService.getAllTransactions()
                .stream()
                .map(BorrowTransactionMapper::toDTO)
                .toList();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n=== Borrow Transactions ===");
            transactions.forEach(transaction -> System.out.println(
                    "Transaction ID: " + transaction.getId() +
                            ", Member: " + transaction.getMemberName() +
                            ", Book: " + transaction.getBookTitle() +
                            ", Borrow Date: " + transaction.getBorrowDate() +
                            ", Return Date: " + (transaction.getReturnDate() != null ? transaction.getReturnDate() : "Not Returned")));
        }
    }

    private void viewAllData() {
        System.out.println("\n--- All Data ---");
        viewAllGenres();
        viewAllBooks();
        viewAllAuthors();
        viewAllMembers();
        viewAllTransactions();
    }
}
