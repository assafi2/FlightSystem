package PersistantDomainObjects;

public class Customer implements POCO {

    public long id;
    public String first_name;
    public String last_name;
    public String address;
    public String phone_no;
    public String credit_card_no;
    public long user_id;

    public Customer() {
    }

    public Customer(long id, String first_name, String last_name,
                    String address, String phone_no, String creditCard_no, long user_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_no = phone_no;
        this.credit_card_no = creditCard_no;
        this.user_id = user_id;
    }

    public String toRecordString() {
        return "'%s','%s','%s','%s','%s',%s".formatted(first_name, last_name, address,
                phone_no, credit_card_no, user_id);
    }

    public String[] toStringArray() {
        String[] arr = {"'%s'".formatted(first_name), "'%s'".formatted(last_name),
                "'%s'".formatted(address), "'%s'".formatted(phone_no),
                "'%s'".formatted(credit_card_no), "%s".formatted(user_id),"%s".formatted(id)};
        return arr;
    }
}
