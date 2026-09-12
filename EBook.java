/**
 * @author 1KHANUMA
 */

// EBook extends Books because an ebook is a kind of book but digital
// it has extra fields for the file format and download link
public class EBook extends Books {

    private String fileFormat;   // for example like a PDF, EPUB, or MOBI
    private String downloadLink; // the link to download the ebook

    // default constructor 
    public EBook() {
        super(); //ssj4 
    }

    // overloaded constructor, passes the shared stuff to Books then sets the ebook only fields 
    public EBook(String name, int id, String type, String genre, double price,
                 String fileFormat, String downloadLink) {
        super(name, id, type, genre, price);
        this.fileFormat = fileFormat;
        this.downloadLink = downloadLink;
    }

    public String getFileFormat() {return fileFormat;}

    public void setFileFormat(String fileFormat) {this.fileFormat = fileFormat;}

    public String getDownloadLink() {return downloadLink;}

    public void setDownloadLink(String downloadLink) {this.downloadLink = downloadLink;}

    // toString adds the format and download link on top of the regular book toString 
    @Override
    public String toString() {
        return super.toString() + " | Format: " + fileFormat + " | Link: " + downloadLink;
    }

    // sends the download link to an email this only makes sense for an ebook so its here 
    public String sendDownloadLink(String email) {
        return "Download link for \"" + getName() + "\" sent to: " + email
                + "\nLink: " + downloadLink;
    }

    // checks if the ebook matches a certain format like PDF or EPUB 
    public boolean isFormat(String format) {
        return this.fileFormat.equalsIgnoreCase(format);
    }
}
