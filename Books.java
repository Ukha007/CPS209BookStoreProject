/**
 * @author 1KHANUMA
 */

// Books class stores all the info about a book and implements Comparable so we can sort by price
public class Books implements Comparable<Books> {

    private String name;
    private int id;
    private String type;
    private String genre;
    private double price;

    /* default constructor, used when setting fields one by one */
    public Books() { }

    /* overloaded constructor, lets us make a book in one line with all the info */
    public Books(String name, int id, String type, String genre, double price) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.genre = genre;
        this.price = price;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    /* toString just prints all the book info in one line so its easy to read */
    @Override
    public String toString() {
        return "Name: " + name + " | ID: " + id + " | Type: " + type
                + " | Genre: " + genre + " | Price: $" + String.format("%.2f", price);
    }

    /* equals compares two books by id, we use this so the cart can find and remove the right book */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Books other = (Books) obj;
        return this.id == other.id;
    }

    /* compareTo lets us sort books by price, this is what the ascending and descending buttons use */
    @Override
    public int compareTo(Books other) {
        return Double.compare(this.price, other.price);
    }
}
