<%@ page import="models.Category" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
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
        <div class="content-box mt-4">
            <section class="">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <!-- content -->
                        <div class="col-lg-10">
                            <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                                <strong class="d-block py-2">Items list </strong>
                                <div class="ms-auto">
                                    <select class="form-select d-inline-block w-auto border pt-1" autocomplete="off"
                                            onchange="window.location.href='${pageContext.request.contextPath}/products?categoryId=' + this.value;">
                                        <option value="-1">Everything</option>


                                        <!-- Category filter -->
                                        <%
                                            List<Category> categoryList = (List<Category>) request.getAttribute("categories");
                                            int selectedId = (int) request.getAttribute("selectedCategoryId");
                                            for (Category category : categoryList) {%>
                                        <option <%
                                            if (category.getId() == selectedId) out.print("selected=\"selected\""); %>
                                                value="<%=category.getId()%>"><%=category.getName()%>
                                        </option>

                                        <%}%>

                                        <!-- Category filter end-->
                                    </select>
                                </div>
                            </header>

                            <div class="row">

                                <%
                                    List<Product> productsList = (List<Product>) request.getAttribute("products");

                                    if (productsList.size() == 0) {
                                %><h2 class="text-center text-danger">Products were not founded!</h2> <%
                                }
                                for (Product product : productsList) {%>
                                <div class="col-lg-4 col-md-6 col-sm-6 d-flex">
                                    <div class="card w-100 my-2 shadow-2-strong">
                                        <img src="<%= request.getContextPath() +"/img/user-images/" + product.getProductImagePath()%>"
                                             class="card-img-top p-2"/>
                                        <div class="card-body d-flex flex-column">
                                            <div class="d-flex flex-row">

                                                <h5 class="mb-1 me-1">$<%=product.getProductPrice()%>
                                                </h5>

                                                <%if (product.getOriginalPrice().isPresent()) { %>
                                                <span class="text-danger"><s>$<%=product.getOriginalPrice().getAsDouble()%></s></span>
                                                <%}%>
                                            </div>

                                            <p class="card-text"><%=product.getProductName()%>
                                            </p>

                                            <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                                <a href="#!" class="btn btn-primary shadow-0 me-1">Add to cart</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% }%>
                                <%--                                <div class="col-lg-4 col-md-6 col-sm-6 d-flex">--%>
                                <%--                                    <div class="card w-100 my-2 shadow-2-strong">--%>
                                <%--                                        <img src="https://bootstrap-ecommerce.com/bootstrap5-ecommerce/images/items/10.webp" class="card-img-top" />--%>
                                <%--                                        <div class="card-body d-flex flex-column">--%>
                                <%--                                            <div class="d-flex flex-row">--%>
                                <%--                                                <h5 class="mb-1 me-1">$34,50</h5>--%>
                                <%--                                                <span class="text-danger"><s>$49.99</s></span>--%>
                                <%--                                            </div>--%>
                                <%--                                            <p class="card-text">T-shirts with multiple colors, for men and lady</p>--%>
                                <%--                                            <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">--%>
                                <%--                                                <a href="#!" class="btn btn-primary shadow-0 me-1">Add to cart</a>--%>
                                <%--                                                <a href="#!" class="btn btn-light border icon-hover px-2 pt-2"><i class="fas fa-heart fa-lg text-secondary px-1"></i></a>--%>
                                <%--                                            </div>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>


                            </div>

                            <hr/>

                            <!-- Pagination -->
                            <nav aria-label="Page navigation example" class="d-flex justify-content-center mt-3">
                                <ul class="pagination">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                                    <li class="page-item">
                                        <a class="page-link" href="#" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                            <!-- Pagination -->
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../includes/footer.jsp" %>

</body>
</html>

