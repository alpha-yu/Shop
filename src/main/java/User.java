public class User {
    private String _userName;
    private String _password;
    private int auth;

    private String UCIF;

    private String Uaddr;

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getUCIF() {
        return UCIF;
    }

    public void setUCIF(String UCIF) {
        this.UCIF = UCIF;
    }

    public String getUaddr() {
        return Uaddr;
    }

    public void setUaddr(String uaddr) {
        Uaddr = uaddr;
    }

    public void ShowMenu()
    {
        
    }
}
