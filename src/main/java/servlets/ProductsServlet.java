package servlets;

import DAL.ProductsDAL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "products-servlet", urlPatterns = "/products/*")
public class ProductsServlet extends HttpServlet {

    ProductsDAL productsDAL = new ProductsDAL();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getPathInfo();
            if(path == null)
                path="default";

            switch (path){
                case "default":
                    getProductsListPage(request, response);
                    break;

                default:
                    get404Page(request, response);
                    break;
            }
        }catch(Exception ex) {
            get400Page(request, response);
        }

    }

    private void getProductsListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Products");
        request.setAttribute("products", productsDAL.getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/Products/products-list.jsp");
        dispatcher.forward(request, response);
    }


    private void get404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getContextPath() + "/errors/404";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void get400Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getContextPath() + "/errors/400";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
