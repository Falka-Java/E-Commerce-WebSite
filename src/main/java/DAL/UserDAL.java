package DAL;

import data.DbProvider;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

//Todo: Make UserDAL work with DbManager
public class UserDAL implements DAL<User> {
    private Connection conn;
    private ResultSet res;
    private PreparedStatement pstmt;
    private String query;

    public UserDAL() {
        conn = null;
        res = null;
        pstmt = null;
        query = "";
    }

    /**
     * Returns user by id
     * @param id id of the user
     * @return Optional User object, if user is not found returns Optional.empty()
     */
    @Override
    public Optional<User> get(long id) {
        User result = null;
        try {
            conn = DbProvider.getMySqlConnection();

            //SQL query that will get all users from the database with provided id
            query = "SELECT * FROM users WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            res = pstmt.executeQuery();

            //Getting user from the result set
            long userId = res.getLong("id");
            String name = res.getString("name");
            String surname = res.getString("surname");
            String email = res.getString("email");
            String pw_hash = res.getString("pw_hash");
            if(name != null && surname != null && email != null && pw_hash != null)
                result = new User(userId, name, surname, email, pw_hash);

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            closeAllConnections();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Returns all users
     * @return list of users
     */
    @Override
    public List<User> getAll() {
        //Returning all users that are in the database
        List<User> users = new LinkedList<>();
        try {
            conn = DbProvider.getMySqlConnection();

            //SQL query that will get all users from the database
            query = "SELECT * FROM users";
            pstmt = conn.prepareStatement(query);
            res = pstmt.executeQuery();

            //Getting users from the result set
            while (res.next()) {
                long userId = res.getLong("id");
                String name = res.getString("name");
                String surname = res.getString("surname");
                String email = res.getString("email");
                String pw_hash = res.getString("pw_hash");
                users.add(new User(userId, name, surname, email, pw_hash));
            }

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            closeAllConnections();
        }

        return users;
    }


    /**
     * Method that adds user to the database
     * @param user user to add
     * @return true if user is added, false if not
     */
    @Override
    public boolean add(User user) {
        boolean success = false;
        try {
            conn = DbProvider.getMySqlConnection();

            //SQL query that will add user to the database
            query = "INSERT INTO users (name, surname, email, pw_hash) VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPw_hash());
            int rowCount = pstmt.executeUpdate();
            if (rowCount != 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            closeAllConnections();
        }
        return success;
    }


    /**
     * Method that updates user in the database
     * @param userId original user id
     * @param params new user parameters(name, surname, email, pw_hash)
     *               Possible to provide fewer parameters, than required
     * @return true if user was updated, false if not, can also return false if nothing was changed
     */
    @Override
    public boolean update(int userId, String[] params) {
        boolean success = false;
        try {
            conn = DbProvider.getMySqlConnection();

            //SQl query that will update user in the database
            query = "UPDATE users SET name = ?, surname = ?, email = ?, pw_hash = ? WHERE id = ?";

            pstmt = conn.prepareStatement(query);
            for (int i = 0; i<params.length; i++){
                pstmt.setString(i+1, params[i]);
            }
            pstmt.setLong(5, userId);

            int rowCount = pstmt.executeUpdate();
            if (rowCount != 0) success = true;
        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            closeAllConnections();
        }
        return success;
    }

    /**
     * Method that deletes user from the database
     * @param user user to delete
     * @return true if user was deleted, false if not
     */
    @Override
    public boolean delete(User user) {
        boolean success = false;
        //Deleting user from the database
        try {
            conn = DbProvider.getMySqlConnection();

            //SQL query that will delete user from the database
            query = "DELETE FROM users WHERE id = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, user.getId());
            int rowCount = pstmt.executeUpdate();
            if(rowCount != 0) success = true;

        } catch (SQLException ex) {
            System.out.println("SQL-Exception -> " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception -> " + ex.getMessage());
        } finally {
            closeAllConnections();
        }
        return success;
    }

    /**
     * Search based in Predicate
     * @param filter - Predicate to filter by
     * @return List of users that match the predicate
     */
    @Override
    public List<User> search(Predicate<User> filter) {
        List<User> results = new ArrayList<>();

        for (User user : getAll()) {
            if (filter.test(user)) {
                results.add(user);
            }
        }
        return results;
    }

    /**
     * Method that closes all connections
     */
    private void closeAllConnections() {
        try {
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
            if (res != null) {
                res.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
