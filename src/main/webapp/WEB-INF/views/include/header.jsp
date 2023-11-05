<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <title>JoinUs - More than Alone</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="keywords" />
    <meta content="" name="description" />

    <!-- Favicon -->
    <%--
    <link
      href="${PageContext.request.contextPath }/resources/img/favicon.ico"
      rel="icon"
    />
    --%>
    <link
      href="${PageContext.request.contextPath }/resources/img/faviconju.png"
      rel="icon"
    />

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="" />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500&amp;family=Roboto:wght@500;700;900&amp;display=swap"
      rel="stylesheet"
    />

    <!-- Icon Font Stylesheet -->
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
      rel="stylesheet"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />

    <!-- jQuery Library -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <!-- Libraries Stylesheet -->
    <link
      href="${PageContext.request.contextPath }/resources/lib/animate/animate.min.css"
      rel="stylesheet"
    />
    <link
      href="${PageContext.request.contextPath }/resources/lib/owlcarousel/assets/owl.carousel.min.css"
      rel="stylesheet"
    />
    <link
      href="${PageContext.request.contextPath }/resources/lib/lightbox/css/lightbox.min.css"
      rel="stylesheet"
    />

    <!-- Customized Bootstrap Stylesheet -->
    <link
      href="${PageContext.request.contextPath }/resources/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <!-- Template Stylesheet -->
    <link
      href="${PageContext.request.contextPath }/resources/css/style.css"
      rel="stylesheet"
    />

    <!-- hsb css -->
    <link
      href="${PageContext.request.contextPath }/resources/css/hsb.css"
      rel="stylesheet"
    />

    <!-- fontawesome -->
    <script
      src="https://kit.fontawesome.com/774827a7a5.js"
      crossorigin="anonymous"
    ></script>

    <style type="text/css">
      .service-item {
        height: 100%;
        cursor: pointer;
      }
      .service-icon {
        margin: 24px 24px 0 0;
      }
      .service-content {
        padding-top: 0.75rem;
      }
      .bottom-right {
        position: absolute;
        font-size: 1.25rem;
        right: 16px;
        bottom: 8px;
      }
      .top-left-content {
        position: absolute;
        font-size: 1.25rem;
        left: 40%;
        top: 40%;
      }
      .club_item {
        font-size: 1.25rem;
      }
      .club_name {
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        white-space: nowrap;
        display: inline-block;
      }
      .finished_time {
        margin-left: auto;
        text-align: right;
        white-space: nowrap;
      }
      #login,
      #logout {
        font-size: 30;
      }
  	  .ck-editor__editable { height: 400px; }
    </style>
  </head>

  <body>
    <!-- Spinner Start -->
    <div
      id="spinner"
      class="bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div
        class="spinner-border text-primary"
        style="width: 3rem; height: 3rem"
        role="status"
      >
        <span class="sr-only">Loading...</span>
      </div>
    </div>
    <!-- Spinner End -->

    <!-- Navbar Start -->
    <nav class="navbar navbar-expand-lg bg-white navbar-light sticky-top p-0 pb-2" style="box-shadow:0 0.125rem 0.25rem rgb(0 0 0 / 8%);">
      <a
        href="${PageContext.request.contextPath }/"
        class="navbar-brand d-flex align-items-center border-end px-4 px-lg-5"
      >
        <img
          src="${PageContext.request.contextPath }/resources/img/joinus.png"
          alt=""
          width="200"
          height="60"
        />
        <!--  <h2 class="m-0 text-primary">JOINUS</h2> -->
      </a>
      <div class="collapse navbar-collapse px-3" id="navbarCollapse">
        <div class="navbar-nav ms-auto p-4 p-lg-0">
          <a
            href="${PageContext.request.contextPath }/"
            class="nav-item nav-link"
            >Home</a
          >
          <a href="${PageContext.request.contextPath }/joinus" class="nav-item nav-link">JOINUS</a>
          <a
            href="${PageContext.request.contextPath }/club/clubList"
            class="nav-item nav-link"
            >CLUB</a
          >
          <a 
          	href="${PageContext.request.contextPath }/rental/partnerPlace" 
         	class="nav-item nav-link">PLACE</a>
          <a 
          	href="${PageContext.request.contextPath }/place/classList" 
         	class="nav-item nav-link">CLASS</a>
          <!--  <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Pages</a>
                    <div class="dropdown-menu bg-light m-0">
                        <a href="feature.html" class="dropdown-item">Feature</a>
                        <a href="quote.html" class="dropdown-item">Free Quote</a>
                        <a href="team.html" class="dropdown-item">Our Team</a>
                        <a href="testimonial.html" class="dropdown-item">Testimonial</a>
                        <a href="404.html" class="dropdown-item">404 Page</a> 
                    </div>
                </div>-->
          <c:if test="${!empty sessionScope.member }">
            <a
              href="${PageContext.request.contextPath }/member/mypage"
              class="nav-item nav-link"
              >MYPAGE</a
            >
          </c:if>
        </div>
        <c:if test="${empty sessionScope.member }">
          <a
            href="${PageContext.request.contextPath }/member/signin"
            id="login"
            class="btn btn-primary rounded-2 py-3 px-lg-5 d-none d-lg-block"
            >LOGIN<i class="fa-solid fa-arrow-right-to-bracket px-2"></i
          ></a>
        </c:if>
        <c:if test="${!empty sessionScope.member }">
          <a
            href="${PageContext.request.contextPath }/member/signout"
            id="logout"
            class="btn btn-primary rounded-2 py-3 px-lg-5 d-none d-lg-block"
            >LOGOUT<i class="fa-solid fa-arrow-right-to-bracket px-2"></i
          ></a>
        </c:if>
      </div>
    </nav>
    <!-- Navbar End -->
    <!-- Page Header Start -->
    <!--     <div class="container-fluid page-header py-5 mb-5">
      <div class="container py-5">
        <h1 class="display-3 text-white mb-3 animated slideInDown"></h1>
        <nav aria-label="breadcrumb animated slideInDown">
          <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a class="text-white" href="#"></a></li>
                    <li class="breadcrumb-item"><a class="text-white" href="#"></a></li>
                    <li class="breadcrumb-item text-white active" aria-current="page"></li>
          </ol>
        </nav>
      </div>
=======
<!--     Page Header Start
    <div class="container-fluid page-header py-5 mb-5">
        <div class="container py-5">
            <h1 class="display-3 text-white mb-3 animated slideInDown">JOINUS</h1>
            <nav aria-label="breadcrumb animated slideInDown">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a class="text-white" href="#">Home</a></li>
                    <li class="breadcrumb-item"><a class="text-white" href="#">Pages</a></li>
                    <li class="breadcrumb-item text-white active" aria-current="page">Services</li>
                </ol>
            </nav>
        </div>
>>>>>>> 789269ce0ed3037434a040b862425debf61078d4
    </div> -->
    <!-- Page Header End -->
  </body>
</html>
