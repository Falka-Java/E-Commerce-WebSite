<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
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
      <table class="table align-middle mb-0 bg-white">
        <thead class="bg-light">
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>isFeatured</th>
          <th>Control</th>
        </tr>
        </thead>
        <tbody>

        <%
        List<Product> productList = (List<Product>) request.getAttribute("products");
        for (Product product : productList) {
        %>
        <tr>
          <td>
            <p class="fw-normal mb-1"><%=product.getId()%></p>
          </td>
          <td>
            <div class="d-flex align-items-center">
              <img
                      src="<%=request.getContextPath() +"/img/user-images/" + product.getProductImagePath()%>"
                      alt=""
                      style="width: 45px; height: 45px"
                      class="rounded-circle"
              />
              <div class="ms-3">
                <p class="fw-bold mb-1"><%=product.getProductName()%></p>
              </div>
            </div>
          </td>
          <td>
            <p class="fw-normal mb-1">S<%=product.getProductPrice()%></p>
          </td>
          <td>
            <p class="fw-normal mb-1">S<%=product.getProductQuantity()%></p>
          </td>
          <td>
            <% if (product.isFeatured()) { %>
            <p class="fw-normal mb-1">Yes</p>
            <% } else { %>
            <p class="fw-normal mb-1">No</p>
            <% } %>
          </td>
          <td>
            <button type="button" class="btn btn-danger btn-sm btn-rounded">
              Delete
            </button>
          </td>
        </tr>
  <% } %>
        </tbody>
      </table>
      <!-- /Content placeholder -->
    </div>
  </main>


  <!-- Footer -->
  <%@include file="../includes/footer.jsp"%>

</body>
</html>

