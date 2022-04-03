package BussinessServices;

public class LoginToken {

    enum Role {
        CUSTOMER,AIRLINE,ADMIN
    }
    long id ;
    String name ;
    Role role ;

    public boolean equals (LoginToken loginToken) {
        if ((this.id == loginToken.id) && (this.name.equals(loginToken.name))
                && (this.role == loginToken.role)) return true ;
        return false ;
    }

}