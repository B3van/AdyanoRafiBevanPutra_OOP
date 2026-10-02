import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {

    private ArrayList<LibraryItem> items;
    private HashMap<String, Member> members;

    public Library() {
        items = new ArrayList<>();
        members = new HashMap<>();
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addMember(Member member) {
        members.put(member.getMemberID(), member);
    }

    public Member findMember(String memberID) {
        return members.get(memberID);
    }

    public LibraryItem findItem(String itemID) {
        for (LibraryItem item : items) {
            if (item.getId().equalsIgnoreCase(itemID)) {
                return item;
            }
        }
        return null;
    }

    public void displayAllItems() {
        for (LibraryItem item : items) {
            System.out.println(item.getDetails());
        }
    }

    public void borrowItem(String memberID, String itemID) {

        Member member = findMember(memberID);
        LibraryItem item = findItem(itemID);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (item instanceof Book) {
            Book book = (Book) item;

            if (!book.isBorrowed()) {
                book.borrowItem();
                member.borrowItem(book);
                System.out.println(member.getName() + " borrowed " + book.getTitle());
            } else {
                System.out.println("Book is already borrowed.");
            }

        } else {
            System.out.println("This item cannot be borrowed.");
        }
    }

    public void returnItem(String memberID, String itemID) {

        Member member = findMember(memberID);
        LibraryItem item = findItem(itemID);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (item instanceof Book) {
            Book book = (Book) item;

            if (book.isBorrowed()) {
                book.returnItem();
                member.returnItem(book);
                System.out.println(member.getName() + " returned " + book.getTitle());
            } else {
                System.out.println("Book is not currently borrowed.");
            }
        }
    }

    public List<LibraryItem> getItems() {
        return items;
    }
}