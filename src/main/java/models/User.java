package models;

public class User {
    //region Private fields
    private int id;

    private String name;
    private String surname;
    //Email must be unique for user, because it's used for login
    private String email;
    // Password hash
    private String pw_hash;
    //endregion

    //region Setters/Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw_hash() {
        return pw_hash;
    }

    public void setPw_hash(String pw_hash) {
        this.pw_hash = pw_hash;
    }

    //endregion

    //region Constructors
    public User(String name, String surname, String email, String pw_hash) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pw_hash = pw_hash;
    }
    //endregion

    //region Overrides
    @Override
    public String toString(){
        return "User [id = " + id + ", name = " + name + " , surname = " + surname
                +  ", email = " + email + ", pw_hash = " + pw_hash + "]";
    }
    //endregion

}
