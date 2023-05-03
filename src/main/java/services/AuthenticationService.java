package services;

import DAO.UserDAO;
import models.User;

import java.util.Optional;

public class AuthenticationService {
    private static final UserDAO userDAO = new UserDAO();

    public static Optional<User> authenticate(String email, String password) {
        Optional<User> user = userDAO.getByEmail(email);

        //If user is not found, return empty optional
        if(!user.isPresent()) return Optional.empty();

        //If user is found, check if password is correct
        if (user.get().getPw_hash().equals(HashService.getMD5Hash(password))) {
            //If password is correct, return user
            return user;
        } else {
            //If not return empty optional
            return Optional.empty();
        }
    }
    /**
     * Method that adds user to the database and hashes user password
     * @param firstName user name
     * @param lastName user surname
     * @param email user email
     * @param password not hashed user password
     * @return error message or null if user is added
     */

    public static String register(String firstName, String lastName, String email, String password) {
        return userDAO.add(firstName, lastName, email, password);
    }

}
