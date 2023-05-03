package models;

public class Category {
    //region Private fields
    private long id;
    private String name;
    //endregion

    //region Constructors

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(){

    }

    //endregion

    //region Getters / Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

    //region Overload

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //endregion
}
