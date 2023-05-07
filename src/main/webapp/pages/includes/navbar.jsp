<%@ page import="jakarta.servlet.http.Cookie" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/navbar-styling.css">


<%
    //Check if user is logged in
    String userEmail = (String) request.getSession().getAttribute("session-user-email");
    String userName = null;
    if (userEmail == null) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-email")) {
                    userEmail = cookie.getValue();
                    break;
                }
            }
        }
    }
    if(userEmail!=null)
        userName = userEmail.split("@")[0];

    pageContext.setAttribute("user-email", userEmail);

%>

<div class="p-3 text-center bg-white border-bottom">
    <div class="container">
        <div class="row gy-3">
            <!-- Left elements -->
            <div class="col-lg-2 col-sm-4 col-4">
                <a href="<%=request.getContextPath()%>" class="float-start">
                    <img src="https://mdbootstrap.com/img/logo/mdb-transaprent-noshadows.png" height="35" />
                </a>
            </div>
            <!-- Left elements -->

            <!-- Center elements -->
            <div class="order-lg-last col-lg-5 col-sm-8 col-8">
                <div class="d-flex float-end">
                    <% if (userEmail == null) { %>
                    <a href="<%= request.getContextPath() %>/Auth/login" class="me-1 border rounded py-1 px-3 nav-link d-flex align-items-center"> <i class="fas fa-user-alt m-1 me-md-2"></i><p class="d-none d-md-block mb-0">Sign in</p> </a>
                    <% } else { %>
                    <a class="me-1 border rounded py-1 px-3 nav-link d-flex align-items-center" href="<%= request.getContextPath() %>/order/orderslist">
                        <%=userName %>
                    </a>
                    <form method="post" action="<%= request.getContextPath() %>/Auth/logout">
                        <button class="me-1 border rounded py-1 px-3 nav-link d-flex align-items-center" type="submit">Logout</button>
                    </form>
                    <% } %>
                    <a href="${pageContext.request.contextPath}/cart" class="ms-2 border rounded py-1 px-3 nav-link d-flex align-items-center" > <i class="fas fa-shopping-cart m-1 me-md-2"></i><p class="d-none d-md-block mb-0">
                        <span class="badge rounded-pill badge-notification bg-success" id="cartCounter"></span>
                        <span class="ms-2"> My cart</span> </p> </a>
<%--                    <span class="numberCircle" id="cartCounter"></span>--%>
                </div>
            </div>
            <!-- Center elements -->

            <!-- Right elements -->
            <div class="col-lg-5 col-md-12 col-12">
                <div class="input-group float-center">
                    <div class="form-outline">
                        <input type="search" id="form1" class="form-control" />
                        <label class="form-label" for="form1">Search</label>
                    </div>
                    <button type="button" class="btn btn-primary shadow-0">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>
            <!-- Right elements -->
        </div>
    </div>
</div>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-white">
    <!-- Container wrapper -->
    <div class="container justify-content-center justify-content-md-between">
        <!-- Toggle button -->
        <button
                class="navbar-toggler border py-2 text-dark"
                type="button"
                data-mdb-toggle="collapse"
                data-mdb-target="#navbarLeftAlignExample"
                aria-controls="navbarLeftAlignExample"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Collapsible wrapper -->
        <div class="collapse navbar-collapse" id="navbarLeftAlignExample">
            <!-- Left elements -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link text-dark" aria-current="page"
                       href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="${pageContext.request.contextPath}/products">Products</a>
                </li>

                <!-- Navbar dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdown" role="button"
                       data-mdb-toggle="dropdown" aria-expanded="false">
                        Others
                    </a>
                    <!-- Dropdown menu -->
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li>
                            <a class="dropdown-item" href="#">Action</a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="#">Another action</a>
                        </li>
                        <li>
                            <hr class="dropdown-divider"/>
                        </li>
                        <li>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- End of left elements -->

        </div>
    </div>
    <!-- Container wrapper -->
</nav>
<!-- Navbar -->


<%@include file="../js/cart-items-script.jsp" %>
