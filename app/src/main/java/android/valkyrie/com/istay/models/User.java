package android.valkyrie.com.istay.models;

public class User {

    private String user_id;
    private String user_type;
    private String full_name;
    private String email;
    private String address;

    public User() {
    }

    public User(String user_id, String user_type, String full_name, String email, String address) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.full_name = full_name;
        this.email = email;
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}

