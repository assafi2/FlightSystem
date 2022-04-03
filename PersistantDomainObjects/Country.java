package PersistantDomainObjects;

public class Country implements POCO {

    public int id ;
    public String name ;

    public Country() {
    }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public String toRecordString() {
        return "'%s'".formatted(name) ;
    }

    public String[] toStringArray() {
        String[] arr = {"'%s'".formatted(name)};
        return arr;
    }
}
