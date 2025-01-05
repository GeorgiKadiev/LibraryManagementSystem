-- Create the database
CREATE DATABASE LibraryManagement;
USE LibraryManagement;

-- Create Genre table
CREATE TABLE Genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Author table
CREATE TABLE Author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Book table
CREATE TABLE Book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    genre_id INT,
    FOREIGN KEY (genre_id) REFERENCES Genre(id) ON DELETE SET NULL
);

-- Create LibraryMember table
CREATE TABLE LibraryMember (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create BorrowTransaction table
CREATE TABLE BorrowTransaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES LibraryMember(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES Book(id) ON DELETE CASCADE
);

-- Insert demo data into Genre
INSERT INTO Genre (name) VALUES
('Fiction'),
('Non-Fiction'),
('Science Fiction'),
('Fantasy'),
('Biography');

-- Insert demo data into Author
INSERT INTO Author (name) VALUES
('J.K. Rowling'),
('George Orwell'),
('J.R.R. Tolkien'),
('Isaac Asimov'),
('Jane Austen');

-- Insert demo data into Bookborrowtransaction
INSERT INTO Book (title, isbn, genre_id) VALUES
('Harry Potter and the Sorcerer\'s Stone', '9780439708180', 1),
('1984', '9780451524935', 3),
('The Hobbit', '9780547928227', 4),
('Foundation', '9780553293357', 3),
('Pride and Prejudice', '9781503290563', 5);

-- Insert demo data into LibraryMember
INSERT INTO LibraryMember (name, email) VALUES
('Alice Johnson', 'alice@example.com'),
('Bob Smith', 'bob@example.com'),
('Charlie Brown', 'charlie@example.com');

-- Insert demo data into BorrowTransaction
INSERT INTO BorrowTransaction (member_id, book_id, borrow_date, return_date) VALUES
(1, 1, '2024-01-01', NULL), -- Alice borrowed Harry Potter
(2, 2, '2024-01-05', '2024-01-15'), -- Bob borrowed 1984 and returned it
(3, 3, '2024-01-10', NULL); -- Charlie borrowed The Hobbit
