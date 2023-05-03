package models;

public class Category {
    //region Private fields
    private long id;
    private String categoryName;
    //endregion

    //region Constructors

    public Category(long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    //endregion
}
