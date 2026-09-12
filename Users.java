/**
 * @author 1KHANUMA
 */

// Users class stores the login info for a registered user
public class Users {

    private String username;
    private String password;

    /* default constructor */
    public Users() {}

    /* overloaded constructor, lets us make a user in one line */
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    /* toString just shows the username, we hide the password so it doesnt show up anywhere */
    @Override
    public String toString() {
        return "User: " + username + " | Password: [hidden]";
    }

    /* equals checks if the username and password both match, used for login verification */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users other = (Users) obj;
        return this.username.equals(other.username) && this.password.equals(other.password);
    }

    /* validatePassword checks if what the user typed matches the stored password */
    public boolean validatePassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    /* getMemberInfo just returns the username in a simple format */
    public String getMemberInfo() {
        return "Member: " + username;
    }
}
