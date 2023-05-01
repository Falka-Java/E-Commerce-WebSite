package services;

import DAO.UserDAO;
import models.User;

import java.util.Optional;

public class AuthenticationService {
    private static  UserDAO userDAO = new UserDAO();

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
}
