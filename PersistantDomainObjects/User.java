package PersistantDomainObjects;

public class User implements POCO{

    public long id ;
    public String username ;
    public String password ;
    public String email ;
    public int user_role;

    public User() {
    }

    public User(long id, String username, String password, String email, int userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.user_role = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + user_role +
                '}';
    }
    public String toRecordString() {
        return "'%s','%s','%s',%s".formatted(username,password,email, user_role) ;
    }

    // capture values into suitable SQL values in an array
    public String[] toStringArray() {
        String[] arr =  {"'%s'".formatted(username),"'%s'".formatted(password),
                             "'%s'".formatted(email),"%s".formatted(user_role)} ;
        return arr ;
    }
}
