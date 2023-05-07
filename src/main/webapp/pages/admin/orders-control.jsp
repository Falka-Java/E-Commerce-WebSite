<%@ page import="models.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Product" %>
<%@ page import="models.OrderProduct" %>
<!DOCTYPE html>
<html lang="en">

<!--Header-->
<%@include file="../includes/head.jsp"%>
<!--Header-->

<body>
<header>
  <!-- Navbar -->
  <%@include file="../includes/navbar.jsp"%>
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

                    <b>Order status:</b> <%if(orderProduct.getOrder().getStatus().equals("Pending")){
                    out.print("<span class=\"badge rounded-pill badge-warning\">Pending</span>");
                  }else
                    out.print("<span class=\"badge rounded-pill badge-secondary\">"+ orderProduct.getOrder().getStatus() +"</span>");
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
                  <div class="form-outline">
                    <input type="text" id="form12" class="form-control" />
                    <label class="form-label" for="form12">New status</label>
                  </div>
                  <button class="btn btn-secondary mt-3">Apply</button>

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
  <%@include file="../includes/footer.jsp"%>

</body>
</html>

