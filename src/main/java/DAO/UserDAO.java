package DAO;

import DAL.UserDAL;
import models.User;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserDAO implements DAO<User> {
    //region Private fields
    private final UserDAL userDAL;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    //endregion

    //region Constructors

    public UserDAO() {
        userDAL = new UserDAL();
    }
    //endregion

    //region Public methods

    /**
     * Returns user by id
     *
     * @param id id of the user
     * @return Optional User object, if user is not found returns Optional.empty()
     */
    @Override
    public Optional<User> get(long id) {
        return userDAL.get(id);
    }

    /**
     * Returns all users
     *
     * @return list of users
     */
    @Override
    public List<User> getAll() {
        return userDAL.getAll();
    }

    /**
     * Method that adds user to the database and hashes user password
     * @param name user name
     * @param surname user surname
     * @param email user email
     * @param raw_password not hashed user password
     * @return true if user is added, false if not
     */

    public boolean add(String name, String surname, String email, String raw_password){
        //Hashing password
        String hashed_password = getMD5Hash(raw_password);

        //Checking if email is valid
        if(!EMAIL_PATTERN.matcher(email).matches()) return false;

        User user = new User(name, surname, email, hashed_password);
        return userDAL.add(user);
    }

    /**
     * Method that adds user to the database. Password must be hashed previously!
     * @param user user to add.
     * @return true if user is added, false if not
     */
    @Override
    public boolean add(User user) {
        return userDAL.add(user);
    }

    /**
     * Method that updates user in the database
     *
     * @param user   original user
     * @param params new user parameters(name, surname, email, pw_hash)
     *               Possible to provide fewer parameters, than required
     * @return true if user was updated, false if not, can also return false if nothing was changed
     */
    @Override
    public boolean update(User user, String[] params) {
        return userDAL.update(user, params);
    }

    @Override
    public boolean delete(User user) {
        return userDAL.delete(user);
    }

    //endregion

    //region Private methods
    /**
     * Method that hashes inputted string using MD5 algorithm
     * @param input string to hash
     * @return hashed string or null if an error occurred
     */

    private String getMD5Hash(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b : messageDigest){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e){
            System.out.println("Exception -> " + e.getMessage());
            return null;
        }
    }
    //endregion

}
