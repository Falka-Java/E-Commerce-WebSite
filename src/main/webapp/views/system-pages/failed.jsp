<!--Header-->
<%@include file="/includes/head.jsp" %>
<!--Header-->

<body>
<header>
    <!-- Navbar -->
    <%@include file="/includes/navbar.jsp" %>
    <!-- Navbar -->

    <main>
        <div class="content-box">
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Failed!</h5>
                    <p class="card-text">${message}</p>
                </div>
            </div>
        </div>
    </main>


    <!-- Footer -->
    <%@include file="/includes/footer.jsp" %>

</body>
</html>

