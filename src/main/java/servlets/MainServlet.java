package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;
import services.AuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "main-servlet", urlPatterns = "")
public class MainServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String page = request.getParameter("page");
            if(page == null)
                page = "default";
            switch(page) {
                case "default":
                    getHomePage(request,response);
                    break;
                case "login":
                    getLoginPage(request,response);
                    break;
                default:
                    get404Page(request,response);

            }
        }catch(Exception ex) {
            get400Page(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("emailInput");
        String password = request.getParameter("passwordInput");
        if(email == null || password == null) {
            getFailedResultPage(request, response, "Email or password is not provided!");
            return;
        }
        String rememberMeInput = request.getParameter("rememberMeCheckBox");
        boolean rememberMe = false;
        if(rememberMeInput != null)
            rememberMe = true;

        Optional<User> result = AuthenticationService.authenticate(email, password);
        if(result.isPresent()){
            HttpSession session = request.getSession(true);
            session.setAttribute("user-email", result.get().getEmail());

            if(rememberMe) {
                Cookie cookie = new Cookie("user-email", result.get().getEmail());
                cookie.setMaxAge(3600* 24); // 1 day
                response.addCookie(cookie);
            }

            getSuccessfulResultPage(request, response, "Login successful!");

        }else {
            getFailedResultPage(request, response, "Email or password is incorrect!");
        }
    }

    private void getHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Homepage");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void getLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("Views/Authentication/login.jsp");
        dispatcher.forward(request, response);
    }

    private void get404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 404 Page not found");
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/system-pages/404.jsp");
        dispatcher.forward(request, response);
    }
    private void get400Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 400 Bad request");
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/system-pages/400.jsp");
        dispatcher.forward(request, response);
    }

    private void getSuccessfulResultPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("title", "- Successful result");
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Views/system-pages/successful.jsp");
        dispatcher.forward(request, response);
    }
    private void getFailedResultPage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("title", "- Operation failed");
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Views/system-pages/failed.jsp");
        dispatcher.forward(request, response);
    }

}
