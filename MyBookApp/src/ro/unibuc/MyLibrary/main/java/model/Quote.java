package ro.unibuc.MyLibrary.main.java.model;

/**
 * @author antoneandreas
 */

public class Quote {

    private  int id;
    private String quote;
    private String author;

    public Quote() {}

    public Quote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    public int getId() {return id;}

    public String getAuthor() {return author;}

    public String getQuote() {return quote;}

    public void setAuthor(String author) {this.author = author;}

    public void setId(int id) {this.id = id;}

    public void setQuote(String quote) {this.quote = quote;}

}