package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;
import services.AuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "auth-servlet", urlPatterns = "/Auth/*")
public class AuthServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/login")) {
            getLoginPage(request, response);
        } else if (path.equals("/registration")) {
            getRegistrationPage(request, response);
        }
      /* try {
            String path = request.getPathInfo();
            if (path.equals("/login")) {
                getLoginPage(request, response);
            } else if (path.equals("/registration")) {
                getRegistrationPage(request, response);
            } else {
                get404Page(request, response);
            }
        }catch(Exception ex) {
            get400Page(request, response);
        }*/
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/login")) {
            handleLoginRequest(request, response);
        } else if (path.equals("/registration")) {
            handleRegistrationRequest(request, response);
        } else if (path.equals("/logout")) {
            handleLogOutRequest(request, response);
        } else {
            get404Page(request, response);
        }
    }

    private void getLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/Authentication/login.jsp");
        dispatcher.forward(request, response);
    }

    private void getRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Registration");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/Authentication/registration.jsp");
        dispatcher.forward(request, response);
    }

    private void handleLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("emailInput");
        String password = request.getParameter("passwordInput");
        if (email == null || password == null) {
            getFailedResultPage(request, response, "Email or password is not provided!", "Auth/registration");
            return;
        }
        String rememberMeInput = request.getParameter("rememberMeCheckBox");
        boolean rememberMe = rememberMeInput != null;

        Optional<User> result = AuthenticationService.authenticate(email, password);
        if (result.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("session-user-email", result.get().getEmail());

            if (rememberMe) {
                Cookie cookie = new Cookie("user-email", result.get().getEmail());
                cookie.setMaxAge(3600 * 24); // 1 day
                cookie.setPath(request.getContextPath()+"/");
                response.addCookie(cookie);
            }

            getSuccessfulResultPage(request, response, "Login successful!");

        } else {
            getFailedResultPage(request, response, "Email or password is incorrect!","Auth/login");
        }
    }

    private void handleLogOutRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate(); //removes all sessions attributes bound to the session

        //Deleting cookies

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-email")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        //Returning home page
        response.sendRedirect(request.getContextPath());
    }

    private void handleRegistrationRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Getting parameters from request
        String nameInput = request.getParameter("nameInput");
        String surnameInput = request.getParameter("surnameInput");
        String emailInput = request.getParameter("emailInput");
        String passwordInput = request.getParameter("passwordInput");
        String repeatPasswordInput = request.getParameter("repeatPasswordInput");

        //Validating data
        if (nameInput == null || surnameInput == null || emailInput == null || passwordInput == null || repeatPasswordInput == null) {
            getFailedResultPage(request, response, "One or more fields are not provided!", "Auth/registration");
            return;
        }
        if (!passwordInput.equals(repeatPasswordInput)) {
            getFailedResultPage(request, response, "Passwords do not match!", "Auth/registration");
            return;
        }

        //Registering user
        String registrationResult = AuthenticationService.register(nameInput, surnameInput, emailInput, passwordInput);

        //Checking if registration was successful
        if (registrationResult == null) {
            //Logging user in
            HttpSession session = request.getSession(true);
            session.setAttribute("session-user-email", emailInput);

            //Returning successful result page
            getSuccessfulResultPage(request, response, "Registration successful!");
        } else
            getFailedResultPage(request, response, registrationResult, "Auth/registration");
    }

    private void getSuccessfulResultPage(HttpServletRequest request, HttpServletResponse response,
                                         String message) throws ServletException, IOException {
        request.setAttribute("title", "- Successful result");
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/system-pages/successful.jsp");
        dispatcher.forward(request, response);
    }

    private void getFailedResultPage(HttpServletRequest request, HttpServletResponse response, String message,
                                     String returnLink) throws ServletException, IOException {
        request.setAttribute("title", "- Operation failed");
        request.setAttribute("message", message);
        request.setAttribute("returnLink", returnLink);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/system-pages/failed.jsp");
        dispatcher.forward(request, response);
    }

    private void get404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 404 Page not found");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/system-pages/404.jsp");
        dispatcher.forward(request, response);
    }

    private void get400Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 400 Bad request");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/system-pages/400.jsp");
        dispatcher.forward(request, response);
    }
}
