
<%@ page import="jakarta.servlet.http.Cookie" %>

<%
  String user = (String)request.getSession().getAttribute("user-email");
  if(user==null){
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
      for(Cookie cookie : cookies){
        if(cookie.getName().equals("user-email")){
          user = cookie.getValue();
          break;
        }
      }
    }
  }
  pageContext.setAttribute("user-email", user);

%>

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
            aria-label="Toggle navigation"
    >
      <i class="fas fa-bars"></i>
    </button>

    <!-- Collapsible wrapper -->
    <div class="collapse navbar-collapse" id="navbarLeftAlignExample">
      <!-- Left links -->
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link text-dark" aria-current="page" href= "${pageContext.request.contextPath}/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Categories</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Hot offers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Gift boxes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Projects</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Menu item</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-dark" href="#">Menu name</a>
        </li>
        <!-- Navbar dropdown -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdown" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
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
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a class="dropdown-item" href="#">Something else here</a>
            </li>
          </ul>
        </li>
      </ul>
      <!-- Left links -->
      <div class="d-flex align-items-center">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

          <% if (user==null) { %>
          <li class="nav-item">
            <a class="nav-link text-dark" href= "<%= request.getContextPath() %>/Auth/login">Login</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-dark" href= "<%= request.getContextPath() %>/Auth/registration">Register</a>
          </li>
          <% } else { %>



            <li class="nav-item">
              <a class="nav-link" href="Auth?page=profile">
                Welcome, <% { out.print(user); } %>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="Auth?page=signout">
                Logout
              </a>
            </li>
          <% } %>

        </ul>
      </div>

    </div>
  </div>
  <!-- Container wrapper -->
</nav>
<!-- Navbar -->