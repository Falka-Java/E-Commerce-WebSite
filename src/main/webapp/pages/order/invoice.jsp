<%@ page import="java.util.List" %>
<%@ page import="models.CartProduct" %>
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

    <main class="row d-flex justify-content-center">
        <div class="content-box col-lg-6 col-md-10 col-sm-12 mt-3">
            <%
                if(request.getAttribute("error")!=null){
                    out.print("<div class=\"alert alert-danger\" role=\"alert\">");
                    out.print(request.getAttribute("error"));
                    out.print("</div>");}
                else{
            %>

            <div class="card">
                <%
                    String firstName = (String) request.getAttribute("firstName");
                    String lastName = (String) request.getAttribute("lastName");
                    String date = (String) request.getAttribute("date");
                    double totalPrice = (double) request.getAttribute("totalPrice");
                %>

                <div class="card-body mx-4">
                    <div class="container">
                        <p class="my-5 mx-5" style="font-size: 30px;">Thank for your purchase</p>
                        <div class="row">
                            <ul class="list-unstyled">
                                <li class="text-black"><%=firstName%> <%=lastName%></li>
                               <li class="text-black mt-1"><%=date%></li>
                            </ul>
                            <hr>


                        </div>
                        <%
                            List<CartProduct> cartProducts = (List<CartProduct>) request.getAttribute("cart-products");
                            for (CartProduct product : cartProducts) {


                        %>

                        <div class="row">
                            <div class="col-xl-10">
                                <p><%=product.getProduct().getProductName()%></p>
                            </div>
                            <div class="col-xl-2">
                                <p class="float-end">$<%=product.getProduct().getProductPrice()%>
                                </p>
                            </div>
                            <hr>
                        </div>

                        <% }%>

                        <div class="row text-black">

                            <div class="col-xl-12">
                                <p class="float-end fw-bold">Total: $<%=totalPrice%>
                                </p>
                            </div>
                            <hr style="border: 2px solid black;">
                        </div>

                    </div>
                </div>
            </div>

            <%}%>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../includes/footer.jsp"%>

</body>
</html>

