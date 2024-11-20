import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface AccountingSystem {
    void issueBook(Book book, Reader reader);
    void returnBook(Book book, Reader reader);
}

interface Catalog {
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByGenre(String genre);
}

class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, Genre: %s, ISBN: %s, Available: %b", title, author, genre, isbn, isAvailable);
    }
}

class Reader {
    private String firstName;
    private String lastName;
    private String ticketNumber;

    public Reader(String firstName, String lastName, String ticketNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketNumber = ticketNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}

class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void issueBook(Book book, Reader reader, Accounting accounting) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            accounting.issueBook(book, reader);
            System.out.println(reader.getFullName() + " has borrowed " + book.getTitle());
        } else {
            System.out.println("The book " + book.getTitle() + " is not available.");
        }
    }

    public void returnBook(Book book, Reader reader, Accounting accounting) {
        book.setAvailable(true);
        accounting.returnBook(book, reader);
        System.out.println(reader.getFullName() + " has returned " + book.getTitle());
    }
}

class LibraryCatalog implements Catalog {
    private List<Book> books;

    public LibraryCatalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    @Override
    public List<Book> searchByGenre(String genre) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
}

class Accounting implements AccountingSystem {
    private List<String> issuedBooksLog;

    public Accounting() {
        this.issuedBooksLog = new ArrayList<>();
    }

    @Override
    public void issueBook(Book book, Reader reader) {
        String logEntry = String.format("%s borrowed '%s' on %s", reader.getFullName(), book.getTitle(), new Date());
        issuedBooksLog.add(logEntry);
    }

    @Override
    public void returnBook(Book book, Reader reader) {
        String logEntry = String.format("%s returned '%s' on %s", reader.getFullName(), book.getTitle(), new Date());
        issuedBooksLog.add(logEntry);
    }

    public void printLog() {
        System.out.println("Issued Books Log:");
        for (String entry : issuedBooksLog) {
            System.out.println(entry);
        }
    }
}

public class LibraryManagementSystemtask2 {
    public static void main(String[] args) {
        LibraryCatalog catalog = new LibraryCatalog();
        Accounting accounting = new Accounting();
        Librarian librarian = new Librarian("Erco");

        Book book1 = new Book("Voina i mir", "Lev Tolstoi", "Roman", "1");
        Book book2 = new Book("Abay joly", "Muhtar Aueezov", "Biography", "2");
        catalog.addBook(book1);
        catalog.addBook(book2);

        Reader reader = new Reader("Manat", "Murat", "1");

        librarian.issueBook(book1, reader, accounting);
        librarian.returnBook(book1, reader, accounting);

        accounting.printLog();
    }
}