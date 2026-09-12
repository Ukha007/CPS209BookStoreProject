/**
 * @author 1KHANUMA
 */

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

// main GUI window for the TMU BookStore. i made most of this in apache netbeans gui builder since its easier wit the drop/drag but then i saw that u cant have packages so i needed to move to vs.
// has 5 tabs: Administration, User Login, Shop, Checkout, and Help
// built using plain swing with a tabbed layout
public class NewJFrame extends JFrame {

    /* lists to store books and users */
    private ArrayList<Books> ListofBooks  = new ArrayList<Books>();
    private ArrayList<Books> CartAddition = new ArrayList<Books>();
    private ArrayList<Users> ListofUsers  = new ArrayList<Users>();

    /* this is a global variable which basically just stores the current searched book */
    private Books searchedBook = null;
    /* this is to store the current price of all the books */
    private double grandTotal = 0.0;
    /* update the discount price */
    private boolean discountApplied = false;

    /* shared UI components used across multiple methods */
    private JTextField     NameField, IDField, TypeField, GenreField, PriceField;
    private JTextArea      AdminArea;
    private JTextField     UsernameField;
    private JPasswordField PasswordField, ConfirmPasswordField;
    private JTextField     SearchField;
    private JTextArea      SearchResultArea;
    private JTextField     EmailPaymentField, PasswordPaymentField;
    private JTextField     AccountBalanceField, GrandTotalField;
    private JTextArea      CartArea;

    /* creates the main window and adds all the tabs */
    public NewJFrame() {
        setTitle("TMU BookStore");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Administration", buildAdminPanel());
        tabs.addTab("User Login",     buildLoginPanel());
        tabs.addTab("Shop",           buildShopPanel());
        tabs.addTab("Checkout",       buildCheckoutPanel());
        tabs.addTab("Help",           buildHelpPanel());

        add(tabs);
        setVisible(true);
    }

    /* sets up the administration tab with all the input fields and buttons */
    private JPanel buildAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("ADMINISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("MS Gothic", Font.BOLD, 36));
        panel.add(title, BorderLayout.NORTH);

        // left side input fields
        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(14, 8, 14, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        NameField  = new JTextField(22);
        IDField    = new JTextField(22);
        TypeField  = new JTextField(22);
        GenreField = new JTextField(22);
        PriceField = new JTextField(22);

        String[]     labels = {"Book Name :", "Book ID :", "Type :", "Genre :", "Price :"};
        JTextField[] inputs = {NameField, IDField, TypeField, GenreField, PriceField};

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.ITALIC | Font.BOLD, 16));
            fields.add(lbl, gc);
            gc.gridx = 1; gc.weightx = 1;
            inputs[i].setPreferredSize(new Dimension(280, 32));
            fields.add(inputs[i], gc);
        }

        // right side buttons and display area
        JPanel right   = new JPanel(new BorderLayout(5, 5));
        right.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        JButton AddButton     = styledButton("ADD");
        JButton DeleteButton  = styledButton("DELETE");
        JButton DisplayButton = styledButton("DISPLAY");
        JButton EditButton    = styledButton("EDIT");
        for (JButton b : new JButton[]{AddButton, DeleteButton, DisplayButton, EditButton}) {
            b.setPreferredSize(new Dimension(100, 32));
            buttons.add(b);
        }

        AdminArea = new JTextArea();
        AdminArea.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        AdminArea.setEditable(false);
        AdminArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(AdminArea);

        right.add(buttons, BorderLayout.NORTH);
        right.add(scroll,  BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fields, right);
        split.setDividerLocation(420);
        split.setResizeWeight(0.35);
        split.setBorder(null);
        panel.add(split, BorderLayout.CENTER);

        AddButton.addActionListener(e     -> AddButtonActionPerformed());
        DeleteButton.addActionListener(e  -> DeleteButtonActionPerformed());
        DisplayButton.addActionListener(e -> DisplayButtonActionPerformed());
        EditButton.addActionListener(e    -> EditButtonActionPerformed());

        return panel;
    }

    // sets up the registration tab where new users can sign up 
    private JPanel buildLoginPanel() {
        // outer panel centers everything on screen
        JPanel panel = new JPanel(new GridBagLayout());

        // inner panel holds all the content with a fixed width
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setPreferredSize(new Dimension(750, 500));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(25, 15, 25, 15);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("REGISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("MS Gothic", Font.BOLD, 42));
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.CENTER;
        inner.add(title, gc);
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.WEST;

        UsernameField        = new JTextField(28);
        PasswordField        = new JPasswordField(28);
        ConfirmPasswordField = new JPasswordField(28);

        // set a fixed size for the fields
        for (JTextField f : new JTextField[]{UsernameField, PasswordField, ConfirmPasswordField}) {
            f.setPreferredSize(new Dimension(370, 40));
        }

        String[]     lblTxt = {"PHONE / EMAIL / USERNAME :", "PASSWORD :", "RE ENTER PASSWORD :"};
        JComponent[] flds   = {UsernameField, PasswordField, ConfirmPasswordField};

        for (int i = 0; i < lblTxt.length; i++) {
            gc.gridx = 0; gc.gridy = i + 1; gc.weightx = 0;
            JLabel lbl = new JLabel(lblTxt[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            inner.add(lbl, gc);
            gc.gridx = 1; gc.weightx = 1;
            inner.add(flds[i], gc);
        }

        JButton confirm = new JButton("CONFIRM");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirm.setPreferredSize(new Dimension(202, 55));
        confirm.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        gc.gridx = 0; gc.gridy = 4; gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        inner.add(confirm, gc);

        confirm.addActionListener(e -> ConfirmUserButtonActionPerformed());
        panel.add(inner);
        return panel;
    }

    // sets up the shop tab where users can search for books and manage their cart 
    private JPanel buildShopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("TMU BookStore", SwingConstants.CENTER);
        title.setFont(new Font("MS Gothic", Font.BOLD, 36));
        panel.add(title, BorderLayout.NORTH);

        // search bar at the top of the book shop area.
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel searchLbl = new JLabel("Search :");
        searchLbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        SearchField = new JTextField(30);
        JButton SearchButton = new JButton("Find");
        SearchButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        searchBar.add(searchLbl);
        searchBar.add(SearchField);
        searchBar.add(SearchButton);

        SearchResultArea = new JTextArea();
        SearchResultArea.setEditable(false);
        SearchResultArea.setBackground(new Color(242, 242, 242));
        JScrollPane resultScroll = new JScrollPane(SearchResultArea);

        JPanel centerArea = new JPanel(new BorderLayout(10, 10));
        centerArea.add(searchBar,    BorderLayout.NORTH);
        centerArea.add(resultScroll, BorderLayout.CENTER);

        // options panel on the left side
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setBackground(new Color(235, 235, 235));
        options.setBorder(new EmptyBorder(10, 15, 10, 15));
        options.setPreferredSize(new Dimension(200, 0));

        JButton DisplayAllBooks  = shopButton("Display");
        JButton RemoveFromCart   = shopButton("Remove");
        JButton AddToCart        = shopButton("Add");
        JButton AscendingButton  = shopButton("Ascending");
        JButton DescendingButton = shopButton("Descending");

        Component[] optItems = {
            bold18Label("Options:"),        bold18Label("Display All Books?"),
            DisplayAllBooks,                bold18Label("Remove From Cart"),
            RemoveFromCart,                 bold18Label("Add To Cart?"),
            AddToCart,                      bold18Label("Display By:"),
            AscendingButton,                bold18Label("Or"),
            DescendingButton
        };
        for (Component c : optItems) {
            options.add(c);
            options.add(Box.createVerticalStrut(10));
        }

        panel.add(options,    BorderLayout.WEST);
        panel.add(centerArea, BorderLayout.CENTER);

        SearchButton.addActionListener(e     -> SearchButtonActionPerformed());
        DisplayAllBooks.addActionListener(e  -> DisplayAllBooksActionPerformed());
        AddToCart.addActionListener(e        -> AddToCartActionPerformed());
        RemoveFromCart.addActionListener(e   -> RemoveFromCartActionPerformed());
        AscendingButton.addActionListener(e  -> AscendingButtonActionPerformed());
        DescendingButton.addActionListener(e -> DescendingButtonActionPerformed());

        return panel;
    }

    // sets up the checkout tab with the cart, discount, payment and receipt 
    private JPanel buildCheckoutPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(new Font("MS Gothic", Font.BOLD, 36));
        panel.add(title, BorderLayout.NORTH);

        // cart display on the right side
        JPanel cartPanel = new JPanel(new BorderLayout(5, 5));
        JLabel cartTitle = new JLabel("Items In Cart");
        cartTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        CartArea = new JTextArea();
        CartArea.setEditable(false);
        CartArea.setBackground(UIManager.getColor("Panel.background"));
        JScrollPane cartScroll = new JScrollPane(CartArea);
        cartScroll.setPreferredSize(new Dimension(500, 400));
        JButton ClearCart = new JButton("EMPTY CART");
        ClearCart.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ClearCart.setBackground(new Color(204, 204, 204));
        cartPanel.add(cartTitle,  BorderLayout.NORTH);
        cartPanel.add(cartScroll, BorderLayout.CENTER);
        cartPanel.add(ClearCart,  BorderLayout.SOUTH);

        // discount panel where users enter their login for the 10% discount
        JPanel discountPanel = new JPanel(new GridBagLayout());
        discountPanel.setBackground(new Color(235, 235, 235));
        discountPanel.setBorder(BorderFactory.createTitledBorder("Enter Login For A Discount Of 10%:"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        EmailPaymentField    = new JTextField(18);
        PasswordPaymentField = new JTextField(18);
        JButton DiscountButton = new JButton("Apply Discount");
        DiscountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        gc.gridx = 0; gc.gridy = 0; discountPanel.add(new JLabel("Username:"), gc);
        gc.gridx = 1;               discountPanel.add(EmailPaymentField, gc);
        gc.gridx = 0; gc.gridy = 1; discountPanel.add(new JLabel("Password:"), gc);
        gc.gridx = 1;               discountPanel.add(PasswordPaymentField, gc);
        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.CENTER;
        discountPanel.add(DiscountButton, gc);

        // payment panel where users enter their balance and pay
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBackground(new Color(235, 235, 235));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment"));
        gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        GrandTotalField     = new JTextField(16);
        AccountBalanceField = new JTextField(16);
        JButton PayButton     = new JButton("Place Your Order");
        JButton RecieptButton = new JButton("Print Reciept");
        PayButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        RecieptButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        gc.gridx = 0; gc.gridy = 0; paymentPanel.add(new JLabel("Grand Total + HST :"), gc);
        gc.gridx = 1;               paymentPanel.add(GrandTotalField, gc);
        gc.gridx = 0; gc.gridy = 1; paymentPanel.add(new JLabel("Enter Account Balance:"), gc);
        gc.gridx = 1;               paymentPanel.add(AccountBalanceField, gc);
        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2; gc.fill = GridBagConstraints.HORIZONTAL;
        paymentPanel.add(PayButton, gc);
        gc.gridy = 3; paymentPanel.add(RecieptButton, gc);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(discountPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(paymentPanel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(cartPanel, BorderLayout.CENTER);

        ClearCart.addActionListener(e      -> ClearCartActionPerformed());
        DiscountButton.addActionListener(e -> DiscountButtonActionPerformed());
        PayButton.addActionListener(e      -> PayButtonActionPerformed());
        RecieptButton.addActionListener(e  -> RecieptButtonActionPerformed());

        return panel;
    }

    // sets up the help tab 
    private JPanel buildHelpPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(20, 20, 20, 20);

        JLabel title = new JLabel("HELP", SwingConstants.CENTER);
        title.setFont(new Font("MS Gothic", Font.BOLD, 36));
        gc.gridy = 0; panel.add(title, gc);

        JButton HelpButton = new JButton("Display Help Information");
        HelpButton.setFont(new Font("MS Gothic", Font.BOLD, 24));
        HelpButton.setBackground(new Color(204, 204, 204));
        HelpButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        gc.gridy = 1; panel.add(HelpButton, gc);

        HelpButton.addActionListener(e -> HelpButtonActionPerformed());
        return panel;
    }

    // adds a new book to the inventory using the values from the input fields 
    private void AddButtonActionPerformed() {
        try {
            Books obj = new Books();
            obj.setId(Integer.parseInt(IDField.getText().trim()));
            obj.setName(NameField.getText().trim());
            obj.setType(TypeField.getText().trim());
            obj.setGenre(GenreField.getText().trim());
            obj.setPrice(Double.parseDouble(PriceField.getText().trim()));
            ListofBooks.add(obj);
            AdminArea.setText("Added Successfully");
        } catch (NumberFormatException ex) {
            AdminArea.setText("Error: Please enter a valid ID and Price.");
        }
    }

    // removes the most recently added book from the inventory 
    private void DeleteButtonActionPerformed() {
        if (!ListofBooks.isEmpty()) {
            ListofBooks.remove(ListofBooks.size() - 1);
            AdminArea.setText("Latest book removed successfully.");
        } else {
            AdminArea.setText("No books to remove.");
        }
    }

    // displays all books currently in the inventory in the admin text area 
    private void DisplayButtonActionPerformed() {
        AdminArea.setText("");
        for (Books book : ListofBooks) {
            AdminArea.append("Name: "  + book.getName()  + "\n");
            AdminArea.append("ID: "    + book.getId()    + "\n");
            AdminArea.append("Type: "  + book.getType()  + "\n");
            AdminArea.append("Genre: " + book.getGenre() + "\n");
            AdminArea.append("Price: " + book.getPrice() + "\n");
            AdminArea.append("---------------------------------\n");
        }
    }

    // edits an existing book by matching the id from the id field 
    private void EditButtonActionPerformed() {
        try {
            int idToEdit = Integer.parseInt(IDField.getText().trim());
            boolean found = false;
            for (Books book : ListofBooks) {
                if (book.getId() == idToEdit) {
                    book.setName(NameField.getText().trim());
                    book.setType(TypeField.getText().trim());
                    book.setGenre(GenreField.getText().trim());
                    book.setPrice(Double.parseDouble(PriceField.getText().trim()));
                    AdminArea.setText("Book with ID " + idToEdit + " Updated Successfully.");
                    found = true;
                    break;
                }
            }
            if (!found) AdminArea.setText("Book with ID " + idToEdit + " Not Found.");
        } catch (NumberFormatException ex) {
            AdminArea.setText("Error: Please enter a valid ID and Price.");
        }
    }

    // confirms the registration, checks the fields are filled and saves to Members.txt 
    private void ConfirmUserButtonActionPerformed() {
        String username        = UsernameField.getText().trim();
        String password        = new String(PasswordField.getPassword());
        String confirmPassword = new String(ConfirmPasswordField.getPassword());

        // this is for the messages if the user didn't or did input the right fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Error: Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // create object from Users class
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);

        // add the user to the list of users
        ListofUsers.add(user);

        // save user details to the .txt file
        try (FileWriter writer = new FileWriter("Members.txt", true)) {
            writer.write("Username: " + username + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("----------------------------------------\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Could not save user details to file.", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // clear the user fields
        UsernameField.setText("");
        PasswordField.setText("");
        ConfirmPasswordField.setText("");
    }

    // searches for a book by name or id and shows the result, also saves it so we can add it to cart 
    private void SearchButtonActionPerformed() {
        String searchInput = SearchField.getText().trim();
        boolean found = false;
        // book is object Books is class
        for (Books book : ListofBooks) {
            
            if (book.getName().equalsIgnoreCase(searchInput) || String.valueOf(book.getId()).equals(searchInput)) {
                // display the found book in the search result area
                SearchResultArea.setText(
                    "Name: "  + book.getName()  + "\n" +
                    "ID: "    + book.getId()    + "\n" +
                    "Type: "  + book.getType()  + "\n" +
                    "Genre: " + book.getGenre() + "\n" +
                    "Price: " + book.getPrice() + "\n" +
                    "---------------------------------\n");
                searchedBook = book;
                found = true;
                break;
            }
        }
        if (!found) {
            SearchResultArea.setText("No product found with the given name or ID.");
            searchedBook = null;
        }
    }

    // displays all books in the shop search result area 
    private void DisplayAllBooksActionPerformed() {
        StringBuilder allBooks = new StringBuilder();
        for (Books book : ListofBooks) {
            allBooks.append("Name: " + book.getName() + "\n");
            allBooks.append("ID: " + book.getId() + "\n");
            allBooks.append("Type: " + book.getType() + "\n");
            allBooks.append("Genre: " + book.getGenre() + "\n");
            allBooks.append("Price: " + book.getPrice() + "\n");
            allBooks.append("---------------------------------\n");
        }
        SearchResultArea.setText(allBooks.toString());
    }

    // adds the currently searched book to the cart and updates the grand total with tax 
    private void AddToCartActionPerformed() {
        if (searchedBook != null) {
            // add the searched book to the CartAddition list
            CartAddition.add(searchedBook);

            // calculate grand total with tax
            grandTotal += searchedBook.getPrice();
            double totalWithTax = grandTotal * 1.13; // canadian 13% TAXXXX
            totalWithTax = Math.round(totalWithTax * 100.0) / 100.0; // round to 2 decimal places

            // add book details to the CartArea
            CartArea.append("Name: "  + searchedBook.getName()  + "\n");
            CartArea.append("ID: "    + searchedBook.getId()    + "\n");
            CartArea.append("Type: "  + searchedBook.getType()  + "\n");
            CartArea.append("Genre: " + searchedBook.getGenre() + "\n");
            CartArea.append("Price: " + searchedBook.getPrice() + "\n");
            CartArea.append("---------------------------------\n");

            // display the grand total in the text field
            GrandTotalField.setText("$" + totalWithTax);

            JOptionPane.showMessageDialog(this, "Successfully added item to the cart.", "Item Added", JOptionPane.INFORMATION_MESSAGE);
        } else {
            SearchResultArea.setText("No product selected to add to the cart.");
        }
    }

    // removes the currently searched book from the cart and recalculates the total 
    private void RemoveFromCartActionPerformed() {
        if (searchedBook != null) {
            if (CartAddition.contains(searchedBook)) {
                CartAddition.remove(searchedBook);

                grandTotal -= searchedBook.getPrice();
                double totalWithTax = grandTotal * 1.13;
                totalWithTax = Math.round(totalWithTax * 100.0) / 100.0; // rounds to two decimal places

                // update the grand total field
                GrandTotalField.setText("Total (with tax): $" + totalWithTax);

                CartArea.setText(""); // clear the CartArea
                for (Books book : CartAddition) {
                    CartArea.append("Name: "  + book.getName()  + "\n");
                    CartArea.append("ID: "    + book.getId()    + "\n");
                    CartArea.append("Type: "  + book.getType()  + "\n");
                    CartArea.append("Genre: " + book.getGenre() + "\n");
                    CartArea.append("Price: " + book.getPrice() + "\n");
                    CartArea.append("---------------------------------\n");
                }
                JOptionPane.showMessageDialog(this, "Item removed from the cart.", "Item Removed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "The selected item is not in the cart.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No product selected to remove from the cart.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // bubble sort method code the difference with descending is the < sign 
    private void AscendingButtonActionPerformed() {
        int n = ListofBooks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // compare prices with greater than less than
                if (ListofBooks.get(j).getPrice() > ListofBooks.get(j + 1).getPrice()) {
                    // swap the prices if not in ascending order
                    Books temp = ListofBooks.get(j);
                    ListofBooks.set(j, ListofBooks.get(j + 1));
                    ListofBooks.set(j + 1, temp);
                }
            }
        }
        // display the sorted results with price
        SearchResultArea.setText("Books sorted by price (ascending):\n");
        for (Books book : ListofBooks)
            SearchResultArea.append("Name: " + book.getName() + ", Price: $" + book.getPrice() + "\n");
        JOptionPane.showMessageDialog(this, "Books are now displayed in ascending order by price.", "Sorting Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    // bubble sort methodd code 
    private void DescendingButtonActionPerformed() {
        int n = ListofBooks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // compare prices with less than greater than
                if (ListofBooks.get(j).getPrice() < ListofBooks.get(j + 1).getPrice()) {
                    // swap if not in descending order
                    Books temp = ListofBooks.get(j);
                    ListofBooks.set(j, ListofBooks.get(j + 1));
                    ListofBooks.set(j + 1, temp);
                }
            }
        }
        // display sorted results
        SearchResultArea.setText("Books sorted by price (descending):\n");
        for (Books book : ListofBooks)
            SearchResultArea.append("Name: " + book.getName() + ", Price: $" + book.getPrice() + "\n");
        JOptionPane.showMessageDialog(this, "Books are now displayed in descending order by price.", "Sorting Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    // empties the cart and resets the grand total 
    private void ClearCartActionPerformed() {
        CartArea.setText("");
        CartAddition.clear();
        grandTotal = 0.0;
        GrandTotalField.setText("0.00");
        JOptionPane.showMessageDialog(this, "Cart emptied successfully!", "Cart Emptied", JOptionPane.INFORMATION_MESSAGE);
    }

    // checks the username and password wit Members.txt, if it matches gives a 10% discount 
    private void DiscountButtonActionPerformed() {
        String enteredEmail    = EmailPaymentField.getText().trim();
        String enteredPassword = PasswordPaymentField.getText().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // check if the email and password match a registered user from the members.txt file
        boolean isRegistered = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Members.txt"));
            String line;
            String storedUsername = "";
            String storedPassword = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: "))       storedUsername = line.substring(10).trim();
                else if (line.startsWith("Password: "))  storedPassword = line.substring(10).trim();

                if (!storedUsername.isEmpty() && !storedPassword.isEmpty()) {
                    if (storedUsername.equals(enteredEmail) && storedPassword.equals(enteredPassword)) {
                        isRegistered = true;
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading user data from file.", "File Error", JOptionPane.ERROR_MESSAGE);
        }

        if (isRegistered) {
            // apply a 10% discount to the grand total 
            grandTotal = grandTotal * 0.90;
            grandTotal = Math.round(grandTotal * 100.0) / 100.0;
            discountApplied = true; // set the flag to true if da discount has been applied. i wonder if the ta will read this.
            GrandTotalField.setText("Total (with discount): $" + grandTotal);
            JOptionPane.showMessageDialog(this, "Discount applied! Your new total is $" + grandTotal, "Discount Applied", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password. No discount applied.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // this handles the payment, checks if the balance covers the total and tehn saves the receipt to a file
    private void PayButtonActionPerformed() {
        try {
            double accountBalance = Double.parseDouble(AccountBalanceField.getText().trim());

            if (grandTotal <= 0) {
                JOptionPane.showMessageDialog(this, "The cart is empty or total is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // apply the discount if it's been applied
            double discountedTotal = grandTotal;
            if (discountApplied) {
                discountedTotal = grandTotal * 0.90; // apply 10% discount
                discountedTotal = Math.round(discountedTotal * 100.0) / 100.0;
            }

            if (accountBalance >= discountedTotal) {
                double remainingBalance = accountBalance - discountedTotal;
                remainingBalance = Math.round(remainingBalance * 100.0) / 100.0;

                JOptionPane.showMessageDialog(this, "Payment successful! Remaining balance: $" + remainingBalance, "Payment Successful", JOptionPane.INFORMATION_MESSAGE);

                // save transaction details
                String enteredEmail = EmailPaymentField.getText().trim();

                try (FileWriter writer = new FileWriter("TransactionDetails.txt", true)) {
                    writer.write("Transaction Details:\n");
                    writer.write("Email: " + enteredEmail + "\n");
                    writer.write("Cart Items:\n");
                    writer.write(CartArea.getText());
                    writer.write("Total Price (with discount): $" + discountedTotal + "\n");
                    writer.write("Account Balance: $" + accountBalance + "\n");
                    writer.write("Remaining Balance: $" + remainingBalance + "\n");
                    writer.write("----------------------------------------\n");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error saving transaction details.", "File Error", JOptionPane.ERROR_MESSAGE);
                }

                // reset fields after payment
                CartArea.setText("");
                CartAddition.clear();
                AccountBalanceField.setText("");
                GrandTotalField.setText("");
                grandTotal = 0;
                discountApplied = false;
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance. You need $" + (discountedTotal - accountBalance) + " more.", "Payment Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the account balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // reads the transaction details from the payment transaction file
    private void RecieptButtonActionPerformed() {
        StringBuilder receiptContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("TransactionDetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                receiptContent.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading the transaction details file.", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextArea receiptArea = new JTextArea(receiptContent.toString());
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        // sets a specific height for the scrollpane
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    // displays a help dialog and saves the help text to the  text file called help.txt. 
    private void HelpButtonActionPerformed() {
        String helpText = "Welcome to the Program!\n\n"
                + "ADMINISTRATION.\n"
                + "1. Input the Book Name,ID and other attributes in the text boxes.\n"
                + "2. Add the book to the store inventory using the add button.\n"
                + "3. Remove the latest book added to the store using the delete button.\n"
                + "4. Edit a certain book if it has the same ID then press the edit button to finalize.\n"
                + "USER LOGIN.\n"
                + "1. Input email/phone/username to the Phone/Email text box.\n"
                + "2. Enter password to the password text box.\n"
                + "3. Re enter password to the password text box (must be the exact same casesensitive).\n"
                + "4. Register by pressing the confirm button.\n"
                + "SHOP.\n"
                + "1. Use the 'FIND' button to search for products.\n"
                + "2. Use the Display button to show every item in store.\n"
                + "3. After pressing the find button use the Add to cart button and to remove use the Remove button.\n"
                + "4. Display the prices in order of price using the ascending / descending buttons.\n"
                + "Checkout.\n"
                + "1. Items in cart will appear on the right in a box and you can empty the cart by pressing the button underneath.\n"
                + "2. For a store discount input your username and email and then click on Apply Discount.\n"
                + "3. The grand total will update as you add books, enter your account balance to pay.\n"
                + "4. Press the pay button to confirm (price must be lower than account balance).\n"
                + "4. Press the receipt button to show the transaction details.\n\n"
                + "Thank you for using the program!";

        // create a text area and set the help text der cuz yeah.
        JTextArea helpArea = new JTextArea(helpText);
        helpArea.setEditable(false); 

        JScrollPane scrollPane = new JScrollPane(helpArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 450));

        JOptionPane.showMessageDialog(this, scrollPane, "Help", JOptionPane.INFORMATION_MESSAGE);

        try (FileWriter writer = new FileWriter("help.txt")) {
            writer.write(helpText);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving help content to file.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // makes a styled button for the admin tab 
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        return btn;
    }

    // thids makes a styled button for the shop panel 
    private JButton shopButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn.setBackground(new Color(204, 204, 204));
        btn.setMaximumSize(new Dimension(160, 35));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        return btn;
    }

    // makes a bold label for the shop options panel 
    private JLabel bold18Label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

}
