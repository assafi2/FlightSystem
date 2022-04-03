package PersistantDomainObjects;

public class Administrator implements POCO{

    public int id ;
    public String first_name;
    public String last_name;
    public long user_id;

    public Administrator() {
    }

    public Administrator(int id, String firstName, String lastName, long userId) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.user_id = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + id +
                ", firstName='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", userId=" + user_id +
                '}';
    }

    public String toRecordString() {
        return "'%s','%s',%s".formatted(first_name, last_name, user_id) ;
    }

    public String[] toStringArray() {
        String[] arr = {"'%s'".formatted(first_name), "%s".formatted(last_name),
                "%s".formatted(user_id)};
        return arr;
    }

}
