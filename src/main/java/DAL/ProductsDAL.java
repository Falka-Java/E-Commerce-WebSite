package DAL;
import models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.function.Predicate;

public class ProductsDAL implements DAL<Product> {
    /**
     * Method that will return product by id
     *
     * @param id - id of the product
     * @return - Optional Product object, if product is not found returns Optional.empty()
     */
    @Override
    public Optional<Product> get(long id) {
        Product result = null;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM products WHERE id = ?");
            dbManager.prepareStatement();
            dbManager.getPstmt().setLong(1, id);

            try (ResultSet res = dbManager.executeQuery()) {
                List<Product> extractProducts = extractProducts(res);
                if(extractProducts.size()>0)
                    result = extractProducts.get(0);
            }

        }catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
        dbManager.closeAllConnections();
        }
            return Optional.ofNullable(result);
    }

    /**
     * Method that will return all products that are in the database
     *
     * @return - list of products that are in the database
     */
    @Override
    public List<Product> getAll() {
        List<Product> products = new LinkedList<>();
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM products");
            dbManager.prepareStatement();

            try (ResultSet res = dbManager.executeQuery()) {
                products = extractProducts(res);
            }
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return products;
    }

    /**
     * Method that will return all products that matches category id
     * Non interface method
     * @param categoryId - id of the category
     * @return - list of products
     */
    public List<Product> getByCategory(int categoryId) {
        List<Product> products = new LinkedList<>();
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM products WHERE categoryId = ?");
            dbManager.prepareStatement();
            dbManager.getPstmt().setLong(1, categoryId);

            try (ResultSet res = dbManager.executeQuery()) {
                products = extractProducts(res);
            }
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return products;
    }

    /**
     * Method that will return all products that are featured
     * @return - list of featured products
     */
    public List<Product> getAllFeaturedProducts(){
        List<Product> products = new LinkedList<>();
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            dbManager.setQuery("SELECT * FROM products WHERE isFeatured = 1");
            dbManager.prepareStatement();

            try (ResultSet res = dbManager.executeQuery()) {
                products = extractProducts(res);
            }
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return products;

    }

    private List<Product> extractProducts(ResultSet res) throws SQLException {
        List<Product> products = new LinkedList<>();
        while (res.next()) {
            //Getting product from the result set
            long productId = res.getLong("id");
            String name = res.getString("productName");
            String description = res.getString("productDescription");
            String productImagePath = res.getString("productImagePath");
            double productPrice = res.getDouble("productPrice");
            OptionalDouble originalPrice = OptionalDouble.empty();
            if(res.getDouble("originalPrice") != 0)
                originalPrice = OptionalDouble.of(res.getDouble("originalPrice"));

            int productQuantity = res.getInt("productQuantity");
            int dbCategoryId = res.getInt("categoryId");
            boolean isFeatured = res.getBoolean("isFeatured");

            //Adding product to the list of products
            Product product = new Product(productId, name, description, productImagePath,
                    productPrice, productQuantity, dbCategoryId, isFeatured);
            product.setOriginalPrice(originalPrice);
            products.add(product);
        }
        return products;
    }

    /**
     * Method that will add product to the database
     *
     * @param product - product that will be added to the database
     * @return - true if product is added, false if product is not added
     */
    @Override
    public boolean add(Product product) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try{
            dbManager.createConnection();
            //Sql query that add product to the database
            dbManager.setQuery("INSERT INTO products " +
                    "(productName, productDescription, productImagePath, productPrice, originalPrice, productQuantity, categoryId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setString(1, product.getProductName());
            dbManager.getPstmt().setString(2, product.getProductDescription());
            dbManager.getPstmt().setString(3, product.getProductImagePath());
            dbManager.getPstmt().setDouble(4, product.getProductPrice());

            if(product.getOriginalPrice().isPresent())
                dbManager.getPstmt().setDouble(5, product.getOriginalPrice().getAsDouble());
            else
                dbManager.getPstmt().setNull(5, Types.DOUBLE);
            dbManager.getPstmt().setInt(6, product.getProductQuantity());
            dbManager.getPstmt().setInt(7, product.getCategoryId());

            //Executing query
            int rowsCount = dbManager.executeUpdate();
            if(rowsCount > 0) success = true;

        }catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }

    /**
     * Method that will update product in the database
     *
     * @param productId - product id that will be updated
     * @param params  - params -> [0] - product name, [1] - product description, [2] - product image path,
     *                [3] - product price, [4] - product quantity, [5] - category id
     * @return - true if product is updated, false if product is not updated
     */
    @Override
    public boolean update(int productId, String[] params) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try{
            dbManager.createConnection();
            dbManager.setQuery("UPDATE products SET productName = ?, productDescription = ?, productImagePath = ?, " +
                    "productPrice = ?, originalPrice = ?, productQuantity = ?, categoryId = ? WHERE id = ?");
            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setString(1, params[0]);
            dbManager.getPstmt().setString(2, params[1]);
            dbManager.getPstmt().setString(3, params[3]);
            dbManager.getPstmt().setDouble(4, Double.parseDouble(params[4]));
            if(params[5]!=null)
                dbManager.getPstmt().setDouble(5, Double.parseDouble(params[5]));
            else
                dbManager.getPstmt().setNull(5, Types.DOUBLE);

            dbManager.getPstmt().setInt(6, Integer.parseInt(params[6]));
            dbManager.getPstmt().setInt(7, Integer.parseInt(params[7]));

            dbManager.getPstmt().setInt(8, productId);

            int rowsCount = dbManager.executeUpdate();
            if(rowsCount > 0) success = true;

        }catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return success;
    }

    /**
     * Method that will delete product from the database
     *
     * @param product - product that will be deleted
     * @return - true if product is deleted, false if product is not deleted
     */
    @Override
    public boolean delete(Product product) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try{
            dbManager.createConnection();
            //Sql query that deletes product from the database
            dbManager.setQuery("DELETE FROM products WHERE id = ?");
            dbManager.prepareStatement();
            //Setting parameters for the query
            dbManager.getPstmt().setLong(1, product.getId());

            //Executing query
            int rowsCount = dbManager.executeUpdate();
            if(rowsCount > 0) success =  true;

        }catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return success;
    }

    /**
     * Method that will search for products in the database
     *
     * @param filter - predicate that will be used to search for products
     * @return - list of products that match the predicate
     */
    @Override
    public List<Product> search(Predicate<Product> filter) {
        List<Product> results = new ArrayList<>();

        for (Product product : getAll()) {
            if (filter.test(product)) {
                results.add(product);
            }
        }
        return results;
    }
}
