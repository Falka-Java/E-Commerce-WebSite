<%@ page import="models.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Product" %>
<%@ page import="models.OrderProduct" %>
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
            <!-- Content placeholder -->
            <main>
                <div class="content-box m-4">
                    <div class="row row-cols-1 row-cols-md-3 g-4">
                        <%
                            List<OrderProduct> orderList = request.getAttribute("productOrders") == null ? new ArrayList<>() : (List<OrderProduct>) request.getAttribute("productOrders");


                            if (orderList.size() == 0) {
                        %>
                        <div class="alert alert-warning" role="alert">
                            No orders found!
                        </div>
                        <% } else {
                            for (OrderProduct orderProduct : orderList) {
                        %>

                        <div class="col">

                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title ">Order: #<%=orderProduct.getOrder().getId()%>
                                    </h5>
                                    <p class="card-text">

                                        <b>Order status:</b> <%
                                        if (orderProduct.getOrder().getStatus().equals("Pending")) {
                                            out.print("<span class=\"badge rounded-pill badge-warning\">Pending</span>");
                                        }else if(orderProduct.getOrder().getStatus().equals("Delivered")){
                                            out.print("<span class=\"badge rounded-pill badge-success\">Delivered</span>");
                                        }else
                                            out.print("<span class=\"badge rounded-pill badge-secondary\">" + orderProduct.getOrder().getStatus() + "</span>");
                                    %><br>

                                        <b>Order date:</b> <%=orderProduct.getOrder().getCreationDate()%><br>

                                        <b>Customer name: </b><span class="badge rounded-pill badge-secondary">
              <%=orderProduct.getOrder().getUserFirstName()%> <%=orderProduct.getOrder().getUserLastName()%></span>

                                        <br>

                                        <b>Delivery address:</b><br>
                                        <span class="badge rounded-pill badge-secondary">
                                    <%=orderProduct.getOrder().getAddress1()%>, <%=orderProduct.getOrder().getAddress2()%>
                                        </span><br>
                                        <span class="badge rounded-pill badge-secondary">
              <%=orderProduct.getOrder().getCountry()%>, <%=orderProduct.getOrder().getState()%> <%=orderProduct.getOrder().getZip()%>
                                </span>

                                        <br>
                                        <span class="badge rounded-pill badge-dark">Total: <%=orderProduct.getOrder().getTotalSum()%>$</span>

                                    </p>

                                    <form method="post">
                                        <div class="form-outline">
                                            <input type="hidden" name="orderId" value="<%=orderProduct.getOrder().getId()%>"/>
                                            <input type="text" name="status-input" id="form12" class="form-control"/>
                                            <label class="form-label" for="form12">New status</label>
                                        </div>

                                        <button type="submit" class="btn btn-secondary mt-3">Apply</button>
                                    </form>

                                    <div class="accordion" id="accordion-<%=orderProduct.getOrder().getId()%>">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="headingOne">
                                                <button
                                                        class="accordion-button"
                                                        type="button"
                                                        data-mdb-toggle="collapse"
                                                        data-mdb-target="#collapse1-<%=orderProduct.getOrder().getId()%>"
                                                        aria-expanded="true"
                                                        aria-controls="collapseOne"
                                                >
                                                    Products
                                                </button>
                                            </h2>
                                            <div id="collapse1-<%=orderProduct.getOrder().getId()%>"
                                                 class="accordion-collapse collapse" aria-labelledby="headingOne"
                                                 data-mdb-parent="#accordionExample">
                                                <div class="accordion-body">

                                                    <%

                                                        for (CartProduct cartProduct : orderProduct.getProductList()) {
                                                    %>


                                                    <ul class="list-group list-group-light">
                                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                                            <div class="d-flex align-items-center">
                                                                <img src="<%= request.getContextPath() +"/img/user-images/" + cartProduct.getProduct().getProductImagePath() %>"
                                                                     alt="" style="width: 45px; height: 45px"
                                                                     class="rounded-circle"/>
                                                                <div class="ms-3">
                                                                    <p class="fw-bold mb-1"><%=cartProduct.getProduct().getProductName()%>
                                                                    </p>
                                                                    <p class="text-muted mb-0">
                                                                        Qty: <%=cartProduct.getQuantity()%>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <span class="badge rounded-pill badge-dark">$<%=cartProduct.getProduct().getProductPrice()%></span>
                                                        </li>
                                                    </ul>

                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <% }
                        } %>
                    </div>
                </div>
            </main>
            <!-- /Content placeholder -->
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../includes/footer.jsp" %>

</body>
</html>

