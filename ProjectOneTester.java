/**
 * @author 1KHANUMA
 */

/*
 * TMU BookStore - CPS209 Project
 *
 * This program is a bookstore simulation for TMU so its easier for students to pay for books online.
 * It has two types of users, admins and customers. Admins can add, edit, delete and display books in the
 * inventory. Customers can register an account, search for books, add and remove
 * items from a cart, apply a 10 percent discount if they are a actual user whos in the system,
 * and complete a purchase with tax applied.
 *
 * Classes:
 * Books: stores all the info about a physical book like name, id, type, genre
 *   and price. It uses Comparable so books can be sorted by price.
 *   It overrides toString and equals() which compares books by id.
 *
 * EBook: a subclass of Books that represents a digital book instead of physical. It inherits
 *   everything from Books and adds a file format and download link.
 *   It overrides tostring and has two extra methods.
 *
 * Users: stores the username and password for a registered customer.
 *   It overrides tostring which hides the password, and equals() which
 *   checks both the username and password match.
 *
 * NewJFrame: the main GUI window with 5 tabs adminstraution, User Login,
 *   Shop, Checkout, a nd Help. It uses ArrayLists for the inventory and cart.
 *
 * The inventory and cart both use ArrayList which is from the java framework.
 * Collections.sort() is used to sort books by price using comparable.
 */
public class ProjectOneTester {

    public static void main(String args[]) {

        // this is just like a quick demo of the EBook class before the GUI opens
        // EBook is a subclass of Books so it inherits everything and adds a file format and download link
        System.out.println("===== TMU BookStore - EBook Demo =====\n");

        // the download links here are just fake file paths to show where a digital copy would be prob be stored
        // in a real system this would be an actual path or URL, but for this demo its just a like a placeholder
        EBook e1 = new EBook("Harry Potter", 201, "Fiction", "Fantasy Magic", 9.99, "PDF", "/files/ebooks/cleancode.pdf");
        EBook e2 = new EBook("The Land of Stories", 202, "Fiction", "Fantasy Adventure", 11.49, "EPUB", "/files/ebooks/pragprog.epub");

        // toString is overridden in EBook to also show the format and download link
        System.out.println(e1.toString());
        System.out.println(e2.toString());

        // sendDownloadLink simulates what would happen when a customer buys an ebook
        // in a real system it would email them the actual file or link, here it just prints it out
        System.out.println("\n" + e1.sendDownloadLink("randomstudent@torontomu.ca"));

        // isFormat checks what file type the ebook is
        System.out.println("\nIs Harry Potter a PDF? " + e1.isFormat("PDF"));
        System.out.println("Is The Land of Stories a PDF? " + e2.isFormat("PDF"));

        System.out.println("\n===== Opening The BookStore GUI =====\n");

        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            
        }

        // create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
}
