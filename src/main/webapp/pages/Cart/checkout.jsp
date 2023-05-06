<%@ page import="models.User" %>
<%@ page import="models.CartProduct" %>
<%@ page import="java.util.List" %>
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
            <div class="container">
                <!-- Heading -->
                <h2 class="my-5 text-center">Checkout</h2>

                <%
                    if (request.getAttribute("isEmpty") != null) {
                        out.print("<div class=\"alert alert-danger\" role=\"alert\">");
                        out.print("Cart is empty!");
                        out.print("</div>");
                    } else {
                        User userModel = (User) request.getAttribute("current-user");
                        if (userModel == null) {
                            out.print("<div class=\"alert alert-danger\" role=\"alert\">");
                            out.print("You are not authorized!");
                            out.print("</div>");
                        }
                %>
                <!--Grid row-->
                <div class="row">
                    <!--Grid column-->
                    <div class="col-md-8 mb-4">
                        <!--Card-->
                        <form method="post" action="${pageContext.request.contextPath}/order/placeorder">
                            <div class="card p-4">
                                <!--Grid row-->
                                <div class="row mb-3">
                                    <!--Grid column-->
                                    <div class="col-md-6 mb-2">
                                        <!--firstName-->
                                        <div class="form-outline">
                                            <input type="text" id="nameInput" name="nameInput" class="form-control"
                                                   value="<%=userModel.getName()%>"
                                            />
                                            <label class="form-label" for="nameInput">First name</label>
                                        </div>
                                    </div>
                                    <!--Grid column-->

                                    <!--Grid column-->
                                    <div class="col-md-6 mb-2">
                                        <!--lastName-->
                                        <div class="form-outline">
                                            <input type="text" id="surnameInput" name="surnameInput"
                                                   class="form-control"
                                                   value="<%=userModel.getSurname()%>"
                                            />
                                            <label class="form-label" for="surnameInput">Last name</label>
                                        </div>
                                    </div>
                                    <!--Grid column-->
                                </div>
                                <!--Grid row-->


                                <!--email-->
                                <p class="mb-0">
                                    Email
                                </p>
                                <div class="form-outline mb-4">
                                    <input type="email" class="form-control disabled"
                                           placeholder="youremail@example.com"
                                           aria-label="youremail@example.com" aria-describedby="basic-addon1"
                                           disabled
                                           value="<%=userModel.getEmail()%>"
                                    />
                                </div>

                                <!--address-->
                                <p class="mb-0">
                                    Address
                                </p>
                                <div class="form-outline mb-4">
                                    <input type="text" name="address1Input" class="form-control" placeholder="1234 Main St"
                                           aria-label="1234 Main St" aria-describedby="basic-addon1"/>
                                </div>

                                <!--address-2-->
                                <p class="mb-0">
                                    Address 2 (optional)
                                </p>
                                <div class="form-outline mb-4">
                                    <input type="text" name="address2Input" class="form-control" placeholder="Apartment"
                                           aria-label="Apartment" aria-describedby="basic-addon1"/>
                                </div>

                                <!--Grid row-->
                                <div class="row">
                                    <!--Grid column-->
                                    <div class="col-lg-4 col-md-12 mb-4">
                                        <p class="mb-0">
                                            Country
                                        </p>
                                        <div class="form-outline mb-4">
                                            <input type="text" name="countryInput" class="form-control" placeholder="Ukraine"
                                                   aria-label="Ukraine" aria-describedby="basic-addon1"/>
                                        </div>
                                    </div>
                                    <!--Grid column-->

                                    <!--Grid column-->
                                    <div class="col-lg-4 col-md-12 mb-4">
                                        <p class="mb-0">
                                            State
                                        </p>
                                        <div class="form-outline mb-4">
                                            <input type="text" name="cityInput" class="form-control" placeholder="Kyiv"
                                                   aria-label="Kyiv" aria-describedby="basic-addon1"/>
                                        </div>
                                    </div>
                                    <!--Grid column-->

                                    <!--Grid column-->
                                    <div class="col-lg-4 col-md-12 mb-4">
                                        <p class="mb-0">
                                            Zip
                                        </p>
                                        <div class="form-outline">
                                            <input type="text" name="zipInput" class="form-control"/>
                                        </div>
                                    </div>
                                    <!--Grid column-->
                                </div>
                                <!--Grid row-->

                                <hr/>

                                <div class="my-3">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               id="flexRadioDefault1" checked/>
                                        <label class="form-check-label" for="flexRadioDefault1"> Invoice </label>
                                    </div>
                                </div>
                                <hr class="mb-4"/>
                                <button class="btn btn-success" type="submit">Checkout</button>
                            </div>
                        </form>
                        <!--/.Card-->
                    </div>
                    <!--Grid column-->

                    <!--Grid column-->
                    <div class="col-md-4 mb-4">

                        <%
                            double totalSum = (double) request.getAttribute("total-price");
                            double discount = (double) request.getAttribute("discount");
                            List<CartProduct> productList = (List<CartProduct>) request.getAttribute("cart-products");

                            int totalItems = 0;
                            for (CartProduct cartProduct : productList)
                                totalItems = totalItems + (cartProduct.getQuantity());

                        %>

                        <!-- Heading -->
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                            <span class="badge rounded-pill badge-primary"><%=totalItems%></span>
                        </h4>

                        <!-- Cart -->

                        <ul class="list-group mb-3">
                            <% for (CartProduct product : productList) { %>
                            <li class="list-group-item d-flex justify-content-between">
                                <div>
                                    <h6 class="my-0"><%=product.getProduct().getProductName()%>
                                    </h6>
                                    <small class="text-muted">Qty: <%=product.getQuantity()%>
                                    </small>
                                </div>
                                <span class="text-muted">$<%=product.getProduct().getProductPrice() * product.getQuantity()%></span>
                            </li>
                            <% } %>
                            <li class="list-group-item d-flex justify-content-between bg-light">
                                <div class="text-success">
                                    <h6 class="my-0">Discount</h6>
                                </div>
                                <span class="text-success">-$<%=discount%></span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total (USD)</span>
                                <strong>$<%=totalSum%>
                                </strong>
                            </li>
                        </ul>
                        <!-- Cart -->

                    </div>
                    <!--Grid column-->
                </div>
                <!--Grid row-->

                <% } %>
            </div>

        </div>
    </main>
    <!--Main layout-->

    <!-- Footer -->
    <%@include file="../includes/footer.jsp" %>

</body>
</html>

