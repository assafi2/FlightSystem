package PersistantDomainObjects;

public interface POCO {


    // do not contain autoincremented id first attribute
    public String toRecordString() ;
    public String[] toStringArray() ;
}
