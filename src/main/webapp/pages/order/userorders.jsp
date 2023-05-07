<%@ page import="models.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
        <div class="content-box m-4">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <%
                    List<Order> orderList = request.getAttribute("orders") == null ? new ArrayList<>() : (List<Order>) request.getAttribute("orders");

                    if (orderList.size() == 0) {
                %>
                <div class="alert alert-warning" role="alert">
                    No orders found!
                </div>
                <% } else {
                    for (Order order : orderList) {
                %>

                <div class="col">

                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Order: #<%=order.getId()%>
                            </h5>
                            <p class="card-text">

                                <b>Order status:</b> <%if(order.getStatus().equals("Pending")){
                                    out.print("<span class=\"badge rounded-pill badge-warning\">Pending</span>");
                                }else if(order.getStatus().equals("Delivered")){
                                out.print("<span class=\"badge rounded-pill badge-success\">Delivered</span>");
                                }else
                                    out.print("<span class=\"badge rounded-pill badge-secondary\">"+ order.getStatus() +"</span>");
                            %><br>

                                <b>Order date:</b> <%=order.getCreationDate()%><br>

                                <b>Customer name: </b><span class="badge rounded-pill badge-secondary">
              <%=order.getUserFirstName()%> <%=order.getUserLastName()%></span>

                                <br>

                                <b>Delivery address:</b><br>
                                <span class="badge rounded-pill badge-secondary">
                                    <%=order.getAddress1()%>, <%=order.getAddress2()%>
                                        </span><br>
                                <span class="badge rounded-pill badge-secondary">
              <%=order.getCountry()%>, <%=order.getState()%> <%=order.getZip()%>
                                </span>

                                <br>
                                <span class="badge rounded-pill badge-dark">Total: <%=order.getTotalSum()%>$</span>

                            </p>
                        </div>
                    </div>
                </div>
                <% }
                } %>
            </div>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../includes/footer.jsp" %>

</body>
</html>

