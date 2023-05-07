package servlets;

import DAL.OrdersDAL;
import DAL.ProductsDAL;
import DAO.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.*;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;
import services.AuthenticationService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "orders-servlet", urlPatterns = "/order/*")

public class OrdersServlet extends HttpServlet {

    private ProductsDAL productsDAL = new ProductsDAL();
    private UserDAO userDAO = new UserDAO();
    private OrdersDAL ordersDAL = new OrdersDAL();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/orderslist")) {
            getOrdersList(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path.equals("/placeorder")) {
            handlePlaceOrder(request, response); //Adds order and returns invoice page
        }
    }

    private void getOrdersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Optional<User> optionalUser = AuthenticationService.getAuthenticatedUser(request, session);
        if(!optionalUser.isPresent()){
            try {
                response.sendError(401);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        User user = optionalUser.get();
        List<Order> orders = ordersDAL.search(o->o.getUserId() == user.getId());
        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/order/userorders.jsp");

        dispatcher.forward(request, response);
    }

    private void handlePlaceOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Getting data from form
        String firstName = request.getParameter("nameInput");
        String lastName = request.getParameter("surnameInput");
        String address1 = request.getParameter("address1Input");
        String address2 = request.getParameter("address2Input");
        String country = request.getParameter("countryInput");
        String city = request.getParameter("cityInput");
        String zipInput = request.getParameter("zipInput");

        int zip = Integer.parseInt(zipInput);

        if(firstName == null || lastName == null || address1 ==null || address2 == null
        || country == null || city == null){
            request.setAttribute("error", "Please fill all fields");
            getInvoicePage(request, response);
            return;
        }

        HttpSession session = request.getSession(true);

        //Getting user from session
        User user;
        Optional<User> optionalUser = AuthenticationService.getAuthenticatedUser(request, session);
        if(!optionalUser.isPresent()){
            response.sendError(401);
            return;
        }
        user = optionalUser.get();

        //Getting cart items from session
        List<SessionProduct> sessionCart = getCartFromSession(session);
        List<CartProduct> cartProducts = new LinkedList<>();
        List<Integer> productIds = new LinkedList<>();

        double totalPrice = 0;
        double discount = 0;
        for (SessionProduct product : sessionCart) {
            Optional<Product> optionalProduct = productsDAL.get(product.getProductId());
            if (optionalProduct.isPresent()) {

                for(int i = 0; i < product.getQuantity(); i++)
                    productIds.add((int) optionalProduct.get().getId());

                cartProducts.add(new CartProduct(optionalProduct.get(), product.getQuantity()));

                //CAlculating total price
                totalPrice += optionalProduct.get().getProductPrice() * product.getQuantity();

                //Calculating discount
                if (optionalProduct.get().getOriginalPrice().isPresent()) {
                    discount += (optionalProduct.get().getOriginalPrice().getAsDouble() - optionalProduct.get().getProductPrice()) * product.getQuantity();
                }
            }
        }

        //Adding order to database
        java.util.Date utilDate = new java.util.Date(); // create a new util Date object with the current time
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        Order order = new Order(user.getId(), sqlDate, firstName, lastName, address1, address2, country, city, zip, totalPrice,"Pending");
        boolean result = ordersDAL.addWithProducts(order, productIds);

        if(result){
            //Clearing cart
            session.setAttribute("cart", new LinkedList<SessionProduct>());
        }else{
            request.setAttribute("error", "Something went wrong, please try again later");
        }

        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("date", utilDate.toString());
        request.setAttribute("totalPrice", totalPrice);

        request.setAttribute("cart-products", cartProducts);

        getInvoicePage(request, response);

    }
    private List<SessionProduct> getCartFromSession(HttpSession session) {
        List<SessionProduct> cart = new LinkedList<>();
        if (session.getAttribute("cart") != null && session.getAttribute("cart") instanceof List)
            cart = (List<SessionProduct>) session.getAttribute("cart");
        return cart;
    }

    private void getInvoicePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "Invoice");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../pages/order/invoice.jsp");
        dispatcher.forward(request, response);
    }
}