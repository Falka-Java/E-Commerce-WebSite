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
    <div class="content-box m-2 d-flex align-items-center justify-content-center">
      <div class="card p-3 " style="width: 18rem;">
        <span class="d-flex align-items-center justify-content-center">
          <img src="<%= request.getContextPath() %>/img/systemicons/success.png" class="card-img-top w-75" alt="Success icon" />
       </span>
        <div class="card-body">
          <h5 class="card-title text-success">Successful!</h5>
          <p class="card-text">${message}</p>
        </div>
        <a href="<%= request.getContextPath() %>" class="btn btn-primary">Homepage</a>
      </div>
    </div>
  </main>


  <!-- Footer -->
  <%@include file="../includes/footer.jsp"%>

</body>
</html>

