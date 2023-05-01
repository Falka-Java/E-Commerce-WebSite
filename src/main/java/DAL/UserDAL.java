package DAL;

import data.DbProvider;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDAL implements DAL<User> {
    private Connection conn;
    private DbProvider dbProvider;
    private ResultSet res;
    private PreparedStatement pstmt;
    private String query;

    public UserDAL(){
        conn = null;
        res = null;
        pstmt = null;
        query = "";
    }

    @Override
    public Optional<User> get(long id) {
        User result = null;
        try {
            conn = dbProvider.getMySqlConnection();

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

            result = new User(name, surname, email, pw_hash);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }catch (ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }finally {
            closeAllConnections();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<User> getAll() {
        //Returning all users that are in the database
        List<User> users = new LinkedList<User>();
        try{
            conn = DbProvider.getMySqlConnection();

            //SQL query that will get all users from the database
            query = "SELECT * FROM users";
            pstmt = conn.prepareStatement(query);
            res = pstmt.executeQuery();

            //Getting users from the result set
            while(res.next()){
                long userId = res.getLong("id");
                String name = res.getString("name");
                String surname = res.getString("surname");
                String email = res.getString("email");
                String pw_hash = res.getString("pw_hash");

                users.add(new User(name, surname, email, pw_hash));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }catch (ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }finally {
            closeAllConnections();
        }

        return users;
    }


    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }

    private void closeAllConnections(){
        try {
            if(pstmt!=null)
                pstmt.close();
            if(conn!=null)
                conn.close();
            if(res!=null){
                res.close();
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


}
