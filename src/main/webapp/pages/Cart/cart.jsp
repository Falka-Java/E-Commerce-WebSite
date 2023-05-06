<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="models.CartProduct" %>
<!DOCTYPE html>
<html lang="en">

<!--Header-->
<%@include file="../includes/head.jsp" %>
<!--Header-->

<body>
<header>
    <!-- Navbar -->
    <%@include file="../includes/navbar.jsp" %>
    <!-- Navbar -->

    <main>
        <div class="content-box">
            <section class="bg-light my-5">
                <div class="container">
                    <div class="row">
                        <!-- cart -->
                        <div class="col-lg-9">
                            <div class="card border shadow-0">
                                <div class="m-4">
                                    <h4 class="card-title mb-4">Your shopping cart</h4>
                                    <div class="row gy-3 mb-4">

                                        <%
                                            List<CartProduct> cartProducts = (List<CartProduct>) request.getAttribute("cart-products");
                                            for (CartProduct product : cartProducts) {%>

                                        <div class="row gy-3 mb-4">
                                            <div class="col-lg-5">
                                                <div class="me-lg-5">
                                                    <div class="d-flex">
                                                        <img src="<%= request.getContextPath() +"/img/user-images/" + product.getProduct().getProductImagePath()%>"
                                                             class="border rounded me-3"
                                                             style="width: 96px; height: 96px;"/>
                                                        <div class="">
                                                            <a href="#"
                                                               class="nav-link"><%=product.getProduct().getProductName()%>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2 col-sm-6 col-6 ">
                                                <div class="form-outline">
                                                    <input min="0" max="20" type="number" id="typeNumber"
                                                           class="form-control" value="<%=product.getQuantity()%>"/>
                                                    <label class="form-label" for="typeNumber">Amount</label>
                                                </div>
                                                <a href="#"
                                                    class="btn btn-secondary border icon-hover-danger mt-3">
                                                    Apply</a>
                                            </div>


                                            <div class="col-lg col-sm-6 d-flex justify-content-sm-center justify-content-md-start justify-content-lg-center justify-content-xl-end mb-2">
                                                <div class="">
                                                    <text class="h6">
                                                        $<%= Math.round(product.getQuantity() * product.getProduct().getProductPrice() * 100) / 100%>
                                                    </text>
                                                    <br/>
                                                    <small class="text-muted text-nowrap">
                                                        $<%=product.getProduct().getProductPrice()%> / per
                                                        item </small>
                                                </div>

                                                <div class="float-md-end me-4">
                                                    <a href="#"
                                                       class="btn btn-light border text-danger icon-hover-danger">
                                                        Remove</a>
                                                </div>
                                            </div>
                                        </div>
                                        <%} %>
                                    </div>
                                </div>

                                <div class="border-top pt-4 mx-4 mb-4">
                                    <p><i class="fas fa-truck text-muted fa-lg"></i> Free Delivery within 1-2 weeks</p>
                                    <p class="text-muted">
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                                        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
                                        nostrud exercitation ullamco laboris nisi ut
                                        aliquip
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!-- cart -->
                        <!-- summary -->

                        <%
                            double totalPrice = (double) request.getAttribute("total-price");
                            double discount  = (double) request.getAttribute("discount");

                        %>
                        <div class="col-lg-3">
                            <div class="card shadow-0 border">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <p class="mb-2">Total price:</p>
                                        <p class="mb-2">$<%=totalPrice + discount%></p>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <p class="mb-2">Discount:</p>
                                        <p class="mb-2 text-success">$<%=discount%></p>
                                    </div>
                                    <hr/>
                                    <div class="d-flex justify-content-between">
                                        <p class="mb-2">Total price:</p>
                                        <p class="mb-2 fw-bold">$<%=totalPrice%></p>
                                    </div>

                                    <div class="mt-3">
                                        <a href="#" class="btn btn-success w-100 shadow-0 mb-2"> Make Purchase </a>
                                        <a href="${pageContext.request.contextPath}/products/"
                                           class="btn btn-light w-100 border mt-2"> Back to shop </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- summary -->
                    </div>
                </div>
            </section>
            <!-- cart + summary -->
            <section>
                <div class="container my-5">
                    <header class="mb-4">
                        <h3>Recommended items</h3>
                    </header>

                    <div class="row">

                        <%
                            List<Product> productsList = (List<Product>) request.getAttribute("featured-products");

                            for (Product product : productsList) {%>

                        <div class="col-lg-3 col-md-6 col-sm-6">
                            <div class="card px-4 border shadow-0 mb-4 mb-lg-0">
                                <a href="#" class="">
                                    <img src="<%= request.getContextPath() +"/img/user-images/" + product.getProductImagePath()%>"
                                         class="card-img-top rounded-2"/>
                                </a>
                                <div class="card-body d-flex flex-column pt-3 border-top">
                                    <a href="#" class="nav-link"><%=product.getProductName()%>
                                    </a>
                                    <div class="price-wrap mb-2">
                                        <strong class="">$<%=product.getProductPrice()%>
                                        </strong>
                                        <%if (product.getOriginalPrice().isPresent()) { %>
                                        <del class="">$<%=product.getOriginalPrice().getAsDouble()%>
                                        </del>
                                        <%}%>
                                    </div>
                                    <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                        <a onclick="addItemToCart(<% out.print(product.getId()); %>); updateButtonStyle('addItem-<% out.print(product.getId()); %>');"
                                           id="addItem-<% out.print(product.getId()); %>"
                                           class="btn btn-outline-primary w-100">Add to cart</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <% }%>
                    </div>
                </div>
            </section>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../includes/footer.jsp" %>

</body>
</html>

