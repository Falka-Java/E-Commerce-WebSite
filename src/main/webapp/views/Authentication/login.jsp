<!DOCTYPE html>
<html lang="en">

<!--Header-->
<%@include file="../../includes/head.jsp"%>
<!--Header-->

<body>
<header>
    <!-- Navbar -->
    <%@include file="../../includes/navbar.jsp"%>
    <!-- Navbar -->
    <style>
        .divider:after,
        .divider:before {
            content: "";
            flex: 1;
            height: 1px;
            background: #eee;
        }
        .h-custom {
            height: calc(100% - 73px);
        }
        @media (max-width: 450px) {
            .h-custom {
                height: 100%;
            }
        }
    </style>
    <main>
        <div class="content-box">
            <!-- Content placeholder -->
            <section class="vh-100">
                <div class="container-fluid h-custom">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col-md-9 col-lg-6 col-xl-5">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp" class="img-fluid"
                                 alt="Sample image">
                        </div>
                        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                            <form method="post" action="${pageContext.request.contextPath}/Auth/login">

                                <div class="divider d-flex align-items-center my-4">
                                    <p class="text-center fw-bold mx-3 mb-0">Login</p>
                                </div>

                                <!-- Email input -->
                                <div class="form-outline mb-4">
                                    <input type="email" id="emailInput" class="form-control form-control-lg"
                                           placeholder="Enter a valid email address"
                                    name="emailInput"/>
                                    <label class="form-label" for="emailInput">Email address</label>
                                </div>

                                <!-- Password input -->
                                <div class="form-outline mb-3">
                                    <input type="password" id="passwordInput" class="form-control form-control-lg"
                                           placeholder="Enter password"
                                    name="passwordInput"/>
                                    <label class="form-label" for="passwordInput">Password</label>
                                </div>

                                <div class="d-flex justify-content-between align-items-center">
                                    <!-- Checkbox -->
                                    <div class="form-check mb-0">
                                        <input class="form-check-input me-2"
                                               name="rememberMeCheckBox"
                                               type="checkbox" value="" id="rememberMeCheckBox" />
                                        <label class="form-check-label" for="rememberMeCheckBox">
                                            Remember me
                                        </label>
                                    </div>
                                    <a href="#!" class="text-body">Forgot password?</a>
                                </div>

                                <div class="text-center text-lg-start mt-4 pt-2">
                                    <button type="submit" class="btn btn-primary btn-lg"
                                            style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button>
                                    <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account?
                                        <a href= "${pageContext.request.contextPath}/?page=register"
                                           class="link-danger">Register</a></p>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>

            </section>
            <!-- /Content placeholder -->
        </div>
    </main>


    <!-- Footer -->
    <%@include file="../../includes/footer.jsp"%>

</body>
</html>

