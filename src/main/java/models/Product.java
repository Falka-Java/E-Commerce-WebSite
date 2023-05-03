package models;

public class Product {
    //region Private fields
    private long id;
    private String productName;
    private String productDescription;
    private String productImagePath;
    private double productPrice;
    private int productQuantity;
    private int categoryId;

    private boolean isFeatured;
    //endregion

    //region Constructors

    public Product(long id, String productName, String productDescription, String productImagePath,
                   double productPrice, int productQuantity, int categoryId, boolean isFeatured) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImagePath = productImagePath;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.categoryId = categoryId;
        this.isFeatured = isFeatured;
    }

    public Product( String productName, String productDescription, String productImagePath,
                   double productPrice, int productQuantity, int categoryId, boolean isFeatured) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImagePath = productImagePath;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.categoryId = categoryId;
        this.isFeatured = isFeatured;
    }
    public Product(){

    }

    //endregion

    //region Getters / Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    //endregion


    //region Overrides
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productImagePath='" + productImagePath + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", categoryId=" + categoryId +
                ", isFeatured=" + isFeatured +
                '}';
    }
    //endregion
}
