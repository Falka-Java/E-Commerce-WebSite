package services;

import DAO.UserDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.util.Optional;

public class AuthenticationService {
    private static final UserDAO userDAO = new UserDAO();
    private final static int rememberMeCookieMaxAge = 3600 * 24; // 1 day

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

    public static Optional<User> loginUser(String login, String password,
                                           boolean rememberMe,
                                           HttpServletRequest request,  HttpServletResponse response, HttpSession session){
        Optional<User> optionalUser = authenticate(login, password);
        if(!optionalUser.isPresent())
            return Optional.empty();

        session.setAttribute("session-user-email", optionalUser.get().getEmail());
        session.setAttribute("session-user-password", optionalUser.get().getPw_hash());

        if(rememberMe){
            //Create cookies
            Cookie emailCookie = new Cookie("user-email", optionalUser.get().getEmail());
            Cookie passCookie = new Cookie("user-password", optionalUser.get().getPw_hash());

            //Set cookie age
            emailCookie.setMaxAge(rememberMeCookieMaxAge);
            passCookie.setMaxAge(rememberMeCookieMaxAge);

            //Set cookie path
            emailCookie.setPath(request.getContextPath()+"/");
            passCookie.setPath(request.getContextPath()+"/");

            //Add cookies to response
            response.addCookie(emailCookie);
            response.addCookie(passCookie);
        }

        return optionalUser;
    }

    public static void logOut(  HttpServletRequest request, HttpServletResponse response, HttpSession session){

        //Invalidate session
        session.invalidate();

        Cookie[] cookies = request.getCookies();
        //Delete cookies
        if (cookies != null) {
            boolean triggered = false;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-email")) {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath() + "/");
                    response.addCookie(cookie);
                    if(triggered)
                        break;
                    triggered = true;
                }
                if(cookie.getName().equals("user-password")){
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath() + "/");
                    response.addCookie(cookie);
                    if(triggered)
                        break;
                    triggered = true;
                }

            }
        }
    }

    public static Optional<User> getAuthenticatedUser(HttpServletRequest request, HttpSession session){

        String userEmail = (String)session.getAttribute("session-user-email");
        String userPassword = (String)session.getAttribute("session-user-password");
        if (userEmail == null || userPassword == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                boolean triggered = false;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user-email")) {
                        userEmail = cookie.getValue();
                        if(triggered)
                            break;
                        triggered = true;
                    }
                    if(cookie.getName().equals("user-password")){
                        userPassword = cookie.getValue();
                        if(triggered)
                            break;
                        triggered = true;
                    }

                }
            }
        }

        return userDAO.getByEmailAndHashedPassword(userEmail, userPassword);
    }

    public static boolean isAuthenticatedUserAdmin(HttpServletRequest request, HttpSession session){
        Optional<User> optionalUser = getAuthenticatedUser(request, session);
        if(!optionalUser.isPresent()) return false;
        User user = optionalUser.get();
        return user.getRoleId() == 2;
    }

}
