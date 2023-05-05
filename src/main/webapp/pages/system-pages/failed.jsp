<!--Header-->
<%@include file="/pages/includes/head.jsp" %>
<!--Header-->

<body>
<header>
    <!-- Navbar -->
    <%@include file="/pages/includes/navbar.jsp" %>
    <!-- Navbar -->

    <main>
        <div class="content-box m-2 d-flex align-items-center justify-content-center">
            <div class="card p-3 " style="width: 18rem;">
        <span class="d-flex align-items-center justify-content-center">
          <img src="<%= request.getContextPath() %>/img/systemicons/error.png" class="card-img-top w-75" alt="Error icon" />
       </span>
                <div class="card-body">
                    <h5 class="card-title text-danger">Failed!</h5>
                    <p class="card-text">${message}</p>
                </div>
                <div class="d-flex align-items-center justify-content-center">
                    <a href="<%= request.getContextPath() %>" class="btn btn-secondary">Homepage</a>
                    <a href="<%= request.getContextPath() %>/Auth/login" class="btn btn-primary ms-2">Retry</a>
                </div>
            </div>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="/pages/includes/footer.jsp" %>

</body>
