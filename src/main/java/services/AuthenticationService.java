package services;

import DAO.UserDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    public static Optional<User> getAuthenticatedUser(HttpServletRequest request, HttpSession session){

        String userEmail = (String)session.getAttribute("session-user-email");
        if (userEmail == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user-email")) {
                        userEmail = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return userDAO.getByEmail(userEmail);
    }

    public static boolean isAuthenticatedUserAdmin(HttpServletRequest request, HttpSession session){
        Optional<User> optionalUser = getAuthenticatedUser(request, session);
        if(!optionalUser.isPresent()) return false;
        User user = optionalUser.get();
        return user.getRoleId() == 2;
    }

}
