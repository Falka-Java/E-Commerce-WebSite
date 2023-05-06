package servlets;

import DAL.ProductsDAL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.CartProduct;
import models.Product;
import models.SessionProduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@WebServlet(name = "cart-servlet", urlPatterns = "/cart/*")
public class CartServlet extends HttpServlet {

    ProductsDAL productsDAL = new ProductsDAL();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (request.getPathInfo() == null)
            path = "default";

        switch (path) {
            case "default":
                getCartPage(request, response);
                break;
            case "/getproductscount":
                getProductsCount(request, response);
                break;
            default:
                response.sendError(404);
                break;
        }
    }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        try {
            if (path.equals("/addproduct"))
                handleAddProductToCartRequest(request, response);
            else
                response.sendError(404);
        } catch (Exception ex) {
            response.sendError(400);
        }

    }

    private void handleAddProductToCartRequest(HttpServletRequest request, HttpServletResponse response) {
        String bodyProductId = request.getParameter("id");
        int productId = Integer.parseInt(bodyProductId);

        HttpSession session = request.getSession(true);
        List<SessionProduct> cart = getCartFromSession(session);

        boolean added = false;
        for (SessionProduct sessionProduct : cart) {
            if (sessionProduct.getProductId()== productId) {
                sessionProduct.setQuantity(sessionProduct.getQuantity()+1);
                added=true;
                break;
            }
        }

        if(!added)
            cart.add(new SessionProduct(productId, 1));

        session.setAttribute("cart", cart);
        response.setStatus(200);
    }

private List<SessionProduct> getCartFromSession(HttpSession session){
    List<SessionProduct> cart = new LinkedList<>();
    if (session.getAttribute("cart") != null && session.getAttribute("cart") instanceof List)
        cart = (List<SessionProduct>) session.getAttribute("cart");
    return cart;
}

    private void getProductsCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        List<SessionProduct> cart = getCartFromSession(session);

        int count = 0;
        for (SessionProduct sessionProduct : cart)
             count += sessionProduct.getQuantity();

        String jsonResult = "{\"count\": " + count + "}";
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonResult);
        out.flush();

    }

    private void getCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Cart");
        List<Product> productList = productsDAL.getAllFeaturedProducts().stream().limit(4)
                .collect(Collectors.toList());
        request.setAttribute("featured-products", productList);

        HttpSession session = request.getSession(true);
        List<SessionProduct> sessionCart = getCartFromSession(session);

        List<CartProduct> cartProducts = new LinkedList<>();

        double totalPrice = 0;
        double discount = 0;
        for (SessionProduct product : sessionCart) {
            Optional<Product> optionalProduct = productsDAL.get(product.getProductId());
            if(optionalProduct.isPresent()) {
                cartProducts.add(new CartProduct(optionalProduct.get(), product.getQuantity()));
                totalPrice += optionalProduct.get().getProductPrice() * product.getQuantity();
                if(optionalProduct.get().getOriginalPrice().isPresent()){
                    discount += (optionalProduct.get().getOriginalPrice().getAsDouble() - optionalProduct.get().getProductPrice()) * product.getQuantity();
                }
            }
        }

        //cartProducts.add(new CartProduct(productsDAL.get(1).get(), 1));

        request.setAttribute("cart-products", cartProducts);


        request.setAttribute("total-price", totalPrice);
        request.setAttribute("discount", discount);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/Cart/cart.jsp");
        dispatcher.forward(request, response);
    }
}
