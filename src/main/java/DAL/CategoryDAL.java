package DAL;
import models.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CategoryDAL implements DAL<Category> {
    public CategoryDAL() {
    }

    /**
     * Method that will return category by id
     * @param id - id of the category
     * @return - Optional Category object, if category is not found returns Optional.empty()
     */
    @Override
    public Optional<Category> get(long id) {
        Category result = null;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();

            //Sql query that will get all categories from the database with provided id
            dbManager.setQuery("SELECT * FROM categories WHERE id = ?");
            dbManager.prepareStatement();
            dbManager.getPstmt().setLong(1, id);

            try (ResultSet res = dbManager.executeQuery()) {
                //Getting category from the result set
                long userId = res.getLong("id");
                String name = res.getString("name");
                if (name != null)
                    result = new Category(userId, name);
            }


        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Method that will return all categories that are in the database
     * @return - list of categories
     */
    @Override
    public List<Category> getAll() {
        //Returning all categories that are in the database
        List<Category> categories = new LinkedList<>();
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();

            //SQL query that will get all users from the database
            dbManager.setQuery("SELECT * FROM categories");
            dbManager.prepareStatement();
            try (ResultSet res = dbManager.executeQuery()) {
                //Getting categories from the result set
                while (res.next()) {
                    long categoryId = res.getLong("id");
                    String name = res.getString("name");
                    categories.add(new Category(categoryId, name));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }

        return categories;
    }

    /**
     * Method that will add user into the database
     * @param category - category that will be added
     * @return - returns true if category is added successfully, otherwise returns false
     */
    @Override
    public boolean add(Category category) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();
            //Sql query that will add new category to the database
            dbManager.setQuery("INSERT INTO categories (name) VALUES (?)");
            dbManager.prepareStatement();
            dbManager.getPstmt().setString(1, category.getName());

            int rowCount = dbManager.executeUpdate();

            if (rowCount != 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }

    /**
     * Method that will update category in the database
     * @param categoryId - category id that will be updated
     * @param params - parameters that will be updated; params[0] - name
     * @return - true if category is updated, false otherwise
     */
    @Override
    public boolean update(int categoryId, String[] params) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        try {
            dbManager.createConnection();

            //SQl query that will update category in the database
            dbManager.setQuery("UPDATE categories SET name = ? WHERE id = ?");
            dbManager.prepareStatement();
            //Setting parameters
            dbManager.getPstmt().setString(1, params[0]);
            dbManager.getPstmt().setLong(2, categoryId);

            int rowCount = dbManager.executeUpdate();
            if (rowCount != 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }

    /**
     * Method that will delete category from the database
     * @param category - category that will be deleted
     * @return - true if category is deleted, false otherwise
     */
    @Override
    public boolean delete(Category category) {
        boolean success = false;
        DbManager dbManager = new DbManager();
        //Deleting category from the database
        try {
            dbManager.createConnection();

            //SQL query that will delete category from the database
            dbManager.setQuery("DELETE FROM categories WHERE id = ?");
            dbManager.prepareStatement();
            dbManager.getPstmt().setLong(1, category.getId());
            int rowCount = dbManager.executeUpdate();

            if(rowCount != 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            dbManager.closeAllConnections();
        }
        return success;
    }

    /**
     * Method that will search for categories in the database
     * @param filter - Predicate that will be used for searching
     * @return - list of categories that are found
     */
    @Override
    public List<Category> search(Predicate<Category> filter) {
        List<Category> results = new ArrayList<>();

        for (Category category : getAll()) {
            if (filter.test(category)) {
                results.add(category);
            }
        }
        return results;
    }

}
