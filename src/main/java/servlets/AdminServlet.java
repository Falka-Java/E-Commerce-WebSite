package servlets;

import DAL.OrdersDAL;
import DAL.ProductsDAL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.OrderProduct;
import services.AuthenticationService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "admin-servlet", urlPatterns = "/admin/*")
public class AdminServlet extends HttpServlet {

    ProductsDAL productsDAL = new ProductsDAL();
    OrdersDAL ordersDAL = new OrdersDAL();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(AuthenticationService.isAuthenticatedUserAdmin(request, request.getSession())) {

            String path = request.getPathInfo();
            if (path.equals("/orders-control")) {
                getOrdersControl(request, response);
            } else if (path.equals("/products-control")) {
                getProductsControl(request, response);
            }

        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(AuthenticationService.isAuthenticatedUserAdmin(request, request.getSession())) {

            String path = request.getPathInfo();
            if (path.equals("/orders-control")) {
                handleChangeStatus(request, response);

            } else {
                response.sendRedirect("/login");
            }
        }
    }

    private void handleChangeStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String statusInput = request.getParameter("status-input");

            ordersDAL.get(orderId).ifPresent(order -> {
                order.setStatus(statusInput);
                ordersDAL.update(orderId, order);
            });
            getOrdersControl(request, response);
    }

    private void getOrdersControl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "orders control");
        List<OrderProduct> orderProducts = new LinkedList<>();
        ordersDAL.getAll().forEach(order -> {
            orderProducts.add(new OrderProduct(order, productsDAL.getByOrderId((int) order.getId())));
        });
        request.setAttribute("productOrders", orderProducts);
        request.getRequestDispatcher("../pages/admin/orders-control.jsp").forward(request, response);
    }

    private void getProductsControl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "products control");
        request.setAttribute("products", productsDAL.getAll());
        request.getRequestDispatcher("../pages/admin/products-control.jsp").forward(request, response);
    }



}
