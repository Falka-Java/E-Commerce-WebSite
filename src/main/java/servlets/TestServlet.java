package servlets;

import java.io.*;

import DAL.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Category;
import models.Product;
import models.User;

@WebServlet(name = "test-servlet", value = "/test")
public class TestServlet extends HttpServlet {
    //region Private fields
    private String message;
    private final DAL<User> userDal;
    private final DAL<Category> categoryDAL;
    private final DAL<Product> productDAL;
    //endregion


    //region Constructors&Init
    public TestServlet(){
        userDal = new UserDAL();
        categoryDAL = new CategoryDAL();
        productDAL = new ProductsDAL();
    }

    public void init() {
        message = "Hello World!";
    }
    //endregion

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        for (User user : userDal.getAll()) {
            out.println("<h2>" + user + "</h2>");
        }
        for (Category category : categoryDAL.getAll()) {
            out.println("<h2>" + category + "</h2>");
        }

        for (Product product : productDAL.getAll()) {
            out.println("<h2>" + product + "</h2>");
        }

        out.println("</body></html>");
    }



    public void destroy() {
    }
}   