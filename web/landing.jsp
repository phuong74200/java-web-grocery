<%-- 
    Document   : landing
    Created on : Mar 1, 2022, 11:56:05 PM
    Author     : phuon
--%>

<%@page import="sample.user.UserDTO"%>
<%@page import="sample.category.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="./assets/favicon.ico">
        <title>Grocery</title>

        <link rel="stylesheet" href="./style/css/landing.css"/>
        <link rel="stylesheet" href="./fonts/index.css"/>
        <link rel="stylesheet" href="./style/css/custom-scrollbar.css"/>
        <link rel="stylesheet" href="./style/css/custom-select.css"/>

        <link rel='stylesheet' href='./style/uicons-rs.css'>
        <link rel='stylesheet' href='./style/uicons-ss.css'>
    </head>
    <body>
        <%
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

            if (user == null) {
                user = new UserDTO();
            }

            List<ProductDTO> productList = (List<ProductDTO>) request.getAttribute("PRODUCT_LIST");

            String search = (String) request.getAttribute("SEARCH");

            if (search == null) {
                search = "";
            }
        %>

        <div class="scrollbar-container">
            <div id="scroll-bar"></div>
        </div>
        <nav>
            <div class="container">
                <div class="logo-container">
                    <h1 class="logo"><a href="landing.jsp">GROCERY</a></h1>
                </div>
                <form class="search-bar" action="MainController">
                    <input value="<%=search%>" name="search" placeholder="Product title" type="text"/>
                    <button type="submit" name="action" value="SearchProduct"><img src="./assets/search.svg"/></button>
                </form>
                <div class="btns-container">
                    <a
                        href="MainController?action=AddProduct"
                        style="display: <%=!user.getRoleID().equals("AD") ? "none" : "block"%>">
                        <i class="fi fi-ss-layout-fluid button round"></i>
                    </a>
                    <a 
                        href="MainController?action=GetCart" 
                        style="display: <%=!user.getRoleID().equals("US") ? "none" : "block"%>">
                        <i class="fi fi-ss-shopping-cart button round"></i>
                    </a>
                    <a 
                        href="login.jsp" 
                        style="display: <%=user.getUserID().equals("") ? "block" : "none"%>">
                        <i class="fi fi-ss-user button round"></i>
                    </a>
                    <a 
                        href="MainController?action=Logout" 
                        style="display: <%=user.getUserID().equals("") ? "none" : "block"%>">
                        <i class="fi fi-rs-sign-out-alt button round"></i>
                    </a>
                </div>
            </div>
        </nav>

        <div class="container blank">
        </div>

        <%
            if (productList == null) {
        %>
        <div class="carousel" data-interval="5000">
            <div class="slide">
                <div class="item">
                    <img src="https://klbtheme.com/groci/wp-content/uploads/2018/08/slider1.jpg"/>
                </div>
                <div class="item">
                    <img src="https://klbtheme.com/groci/wp-content/uploads/2018/08/slider2.jpg"/>
                </div>
            </div>
            <div class="arrow-container">
                <i class="fi fi-ss-angle-left button round"></i>
                <i class="fi fi-ss-angle-right button round"></i>
            </div>
            <div class="slide-count">
                <div class="sc-item"></div>
                <div class="sc-item"></div>
            </div>
        </div>
        <%
            }
        %>

        <div class="container shop">
            <div class="products-grid">
                <%
                    if (productList == null) {
                        productList = new ProductDAO().getAll(false);
                    }

                    if (productList.size() > 0) {
                        for (ProductDTO product : productList) {
                %>
                <form class="product" action="MainController" method="POST">
                    <input type="hidden" name="productID" value="<%=product.getProductID()%>"/>
                    <div class="image">
                        <img src="<%=product.getImage()%>" onerror="this.src='./assets/image-not-available.jpg'"/>
                    </div>
                    <div>
                        <span class="sale-off"><%=new CategoryDAO().getNameByID(product.getCategoryID())%></span>
                        <p class="title">
                            <%=product.getProductName()%>
                        </p>
                        <h6>
                            <i class="fi fi-ss-checkbox"></i>
                            <strong>In Stock</strong><a> - 1 kg</a>
                        </h6>
                        <p class="price">
                            <bdi>$<%=product.getPrice()%></bdi>
                        </p>
                        <div class="plus">
                            <div class="plus-ico left">-</div>
                            <input name="quantity" type="text" value="0" inputmode="numeric">
                            <div class="plus-ico right">+</div>
                        </div>
                        <div class="cart">
                            <button name="action" value="AddItem"><i class="fi fi-ss-shopping-cart-add"></i> Add to Cart</button>
                        </div>
                    </div>
                </form>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <div class="footer">
            <h3>Copy right © PhuongMT</h3>
        </div>

        <nav class="mobile">
            <a href="landing.jsp"><i class="fi fi-rs-home"></i></a>
            <a 
                href="productAdd.jsp"
                style="display: <%=!user.getRoleID().equals("AD") ? "none" : "flex"%>"
                >
                <i class="fi fi-rs-shopping-cart-add"></i>
            </a>
            <a 
                href="productUpdate.jsp"
                style="display: <%=!user.getRoleID().equals("AD") ? "none" : "flex"%>"
                ><i class="fi fi-rs-shopping-cart-check"></i>
            </a>
            <a 
                href="MainController?action=GetCart"
                style="display: <%=!user.getRoleID().equals("US") ? "none" : "flex"%>"
                ><i class="fi fi-ss-shopping-cart"></i>
            </a>
            <a 
                href="MainController?action=Logout"
                style="display: <%=!user.getRoleID().equals("") ? "flex" : "none"%>"
                ><i class="fi fi-ss-sign-out-alt"></i>
            </a>
            <a 
                href="login.jsp"
                style="display: <%=user.getRoleID().equals("") ? "flex" : "none"%>"
                ><i class="fi fi-ss-user"></i>
            </a>
        </nav>

    </body>
    <script src="./script/carousel.js"></script>
    <script src="./script/plus-button.js"></script>
    <script src="./script/custom-select.js"></script>
</html>
