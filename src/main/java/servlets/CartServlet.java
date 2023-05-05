package servlets;

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
import java.util.stream.Collectors;

@WebServlet(name = "cart-servlet", urlPatterns = "/cart/*")
public class CartServlet extends HttpServlet{

    ProductsDAL productsDAL = new ProductsDAL();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getCartPage(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

    }

    private void getCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Cart");
        List<Product> productList = productsDAL.getAllFeaturedProducts().stream().limit(4)
                .collect(Collectors.toList());
        request.setAttribute("featured-products", productList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/Cart/cart.jsp");
        dispatcher.forward(request, response);
    }
}
