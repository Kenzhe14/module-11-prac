import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum UserType {
    READER,
    LIBRARIAN
}

class Book {
    private String title;
    private String isbn;
    private List<Author> authors;
    private int publicationYear;
    private boolean availabilityStatus;

    public Book(String title, String isbn, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.authors = new ArrayList<>();
        this.availabilityStatus = true;
    }

    public void changeAvailabilityStatus(boolean status) {
        this.availabilityStatus = status;
    }

    public String getBookInfo() {
        return String.format("Title: %s, ISBN: %s, Year: %d, Available: %b", title, isbn, publicationYear, availabilityStatus);
    }

    public boolean isAvailable() {
        return availabilityStatus;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }
}

abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected UserType userType;

    public abstract void register();
    public abstract void login();
}

class Reader extends User {
    public Reader(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = UserType.READER;
    }

    public void borrowBook(Book book) throws Exception {
        if (!book.isAvailable()) {
            throw new Exception("Book is not available.");
        }
        book.changeAvailabilityStatus(false);
        System.out.println(name + " borrowed " + book.getBookInfo());
    }

    public void returnBook(Book book) {
        book.changeAvailabilityStatus(true);
        System.out.println(name + " returned " + book.getBookInfo());
    }

    @Override
    public void register() {
        System.out.println(name + " registered as a Reader.");
    }

    @Override
    public void login() {
        System.out.println(name + " logged in as a Reader.");
    }
}

class Librarian extends User {
    public Librarian(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = UserType.LIBRARIAN;
    }

    public void addBook(Book book, Library library) {
        library.addBook(book);
        System.out.println(name + " added " + book.getBookInfo());
    }

    public void editBook(Book book) {
        System.out.println(name + " edited " + book.getBookInfo());
    }

    public void removeBook(Book book, Library library) {
        library.removeBook(book);
        System.out.println(name + " removed " + book.getBookInfo());
    }

    @Override
    public void register() {
        System.out.println(name + " registered as a Librarian.");
    }

    @Override
    public void login() {
        System.out.println(name + " logged in as a Librarian.");
    }
}

class Loan {
    private Book book;
    private Reader reader;
    private Date loanDate;
    private Date returnDate;

    public Loan(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.loanDate = new Date();
    }

    public void issueLoan() {
        book.changeAvailabilityStatus(false);
        System.out.println("Loan issued for " + book.getBookInfo() + " to " + reader.name);
    }

    public void returnBook() {
        book.changeAvailabilityStatus(true);
        returnDate = new Date();
        System.out.println("Book returned: " + book.getBookInfo() + " by " + reader.name);
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> searchBooks(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookInfo().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void registerUser (User user) {
        users.add(user);
        user.register();
    }
}

class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }
}

class Report {
    public void generateBookPopularityReport() {
        System.out.println("Generating book report");
    }

    public void generateReaderActivityReport() {
        System.out.println("Generating reader report");
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Librarian librarian = new Librarian(1, "Erko", "erko@gmail.com");
        librarian.register();
        librarian.login();

        Book book1 = new Book("Voina i mir", "1", 1998);
        Author author1 = new Author("Lev Tolstoi");
        book1.addAuthor(author1);
        librarian.addBook(book1, library);

        Reader reader = new Reader(2, "Nari", "nari@gmail.com");
        library.registerUser (reader);
        reader.login();

        try {
            reader.borrowBook(book1);
            reader.returnBook(book1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Report report = new Report();
        report.generateBookPopularityReport();
        report.generateReaderActivityReport();
    }
}