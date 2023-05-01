package servlets;

import java.io.*;

import DAL.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;

@WebServlet(name = "test-servlet", value = "/test")
public class TestServlet extends HttpServlet {
    //region Private fields
    private String message;
    private final DAL<User> userDal;
    //endregion


    //region Constructors&Init
    public TestServlet(){
        userDal = new UserDAL();
    }

    public void init() {
        message = "Hello World!";
    }
    //endregion

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        for (User user : userDal.getAll()) {
            out.println("<h2>" + user + "</h2>");
        }

        out.println("</body></html>");
    }

    public void destroy() {
    }
}   