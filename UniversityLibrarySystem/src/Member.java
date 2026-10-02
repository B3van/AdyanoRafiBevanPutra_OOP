import java.util.ArrayList;
import java.util.List;

public class Member {

    private String name;
    private String memberID;
    private List<LibraryItem> borrowedItems;

    public Member(String name, String memberID) {
        this.name = name;
        this.memberID = memberID;
        this.borrowedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
    }
}