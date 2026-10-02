public class Book extends LibraryItem implements Borrowable {

    private String author;
    private String isbn;
    private boolean borrowed;

    public Book(String id, String title, String author, String isbn) {
        super(id, title);
        this.author = author;
        this.isbn = isbn;
        this.borrowed = false;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    @Override
    public void borrowItem() {
        if (!borrowed) {
            borrowed = true;
            System.out.println(getTitle() + " has been borrowed.");
        } else {
            System.out.println(getTitle() + " is already borrowed.");
        }
    }

    @Override
    public void returnItem() {
        if (borrowed) {
            borrowed = false;
            System.out.println(getTitle() + " has been returned.");
        } else {
            System.out.println(getTitle() + " was not borrowed.");
        }
    }

    @Override
    public String getDetails() {
        return "Book | ID: " + getId()
                + " | Title: " + getTitle()
                + " | Author: " + author
                + " | ISBN: " + isbn;
    }
}