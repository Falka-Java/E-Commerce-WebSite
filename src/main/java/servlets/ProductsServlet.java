package servlets;

import DAL.CategoryDAL;
import DAL.ProductsDAL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "products-servlet", urlPatterns = "/products/*")
public class ProductsServlet extends HttpServlet {

    ProductsDAL productsDAL = new ProductsDAL();
    CategoryDAL categoryDAL = new CategoryDAL();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProductsListPage(request, response);

        /*try {
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
        }*/

    }

    private void getProductsListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = 0;
        List<Product> productList;
        String GETCategoryId = request.getParameter("categoryId");
        if(GETCategoryId!=null) {
            categoryId = Integer.parseInt(GETCategoryId);

            if(categoryId == -1)
                productList = productsDAL.getAll();
            else
                productList = productsDAL.getByCategory(categoryId);
        }else
            productList = productsDAL.getAll();


        request.setAttribute("title", "- Products");
        request.setAttribute("products", productList);
        request.setAttribute("categories", categoryDAL.getAll());
        request.setAttribute("selectedCategoryId",categoryId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/Products/products-list.jsp");
        dispatcher.forward(request, response);
    }


    private void get404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/errors/404";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void get400Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/errors/400";
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
