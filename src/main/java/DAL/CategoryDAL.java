package DAL;

import models.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CategoryDAL implements DAL<Category> {
    public CategoryDAL() {
    }

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

            try(ResultSet res = dbManager.executeQuery()) {
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
            try(ResultSet res = dbManager.executeQuery()) {
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

    @Override
    public boolean add(Category category) {
        return false;
    }

    @Override
    public boolean update(Category category, String[] params) {
        return false;
    }

    @Override
    public boolean delete(Category category) {
        return false;
    }

    @Override
    public List<Category> search(Predicate<Category> filter) {
        return null;
    }

}
