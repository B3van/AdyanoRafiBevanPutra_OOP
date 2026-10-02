public class Magazine extends LibraryItem {

    private String publisher;
    private int issueNumber;

    public Magazine(String id, String title, String publisher, int issueNumber) {
        super(id, title);
        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String getDetails() {
        return "Magazine | ID: " + getId()
                + " | Title: " + getTitle()
                + " | Publisher: " + publisher
                + " | Issue: " + issueNumber;
    }
}