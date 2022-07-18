<%-- 
    Document   : cart
    Created on : Mar 9, 2022, 4:49:22 PM
    Author     : phuon
--%>

<%@page import="sample.user.UserDTO"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.orderDetail.OrderDetailDTO"%>
<%@page import="sample.category.CategoryDAO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cart Page</title>

        <link rel="stylesheet" href="./style/css/cart.css"/>
        <link rel="stylesheet" href="./fonts/index.css"/>

        <link rel="stylesheet" href="./fonts/index.css"/>

        <link rel='stylesheet' href='./style/uicons-rs.css'>
        <link rel='stylesheet' href='./style/uicons-ss.css'>
    </head>
    <body>
        <%
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

            if (user == null) {
                user = new UserDTO();
            }

        %>
        <nav>
            <div class="container nav">
                <div class="logo-container">
                    <a href="landing.jsp"><h1 class="logo">GROCERY</h1></a>
                </div>
                <div></div>
                <div class="btns-container">
                    <a href="MainController?action=GetCart" style="display: <%=user.getUserID().equals("") ? "none" : "block"%>"><i class="fi fi-ss-shopping-cart button round"></i></a>
                    <a href="login.jsp" style="display: <%=user.getUserID().equals("") ? "block" : "none"%>"><i class="fi fi-ss-user button round"></i></a>
                    <a href="MainController?action=Logout" style="display: <%=user.getUserID().equals("") ? "none" : "block"%>"><i class="fi fi-rs-sign-out-alt button round"></i></a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col v-center h-center">Image</div>
                <div>Description</div>
                <div>Quantity</div>
                <div class="col v-center h-center">Price</div>
                <div class="col v-center h-center">Remove</div>
                <div class="col v-center h-center">Update</div>
                <div class="col v-center h-center">Total</div>
            </div>
            <%

                List<ProductDTO> list = (List<ProductDTO>) request.getAttribute("CART_LIST");
                List<OrderDetailDTO> infoList = (List<OrderDetailDTO>) request.getAttribute("CART_INFO");
                List<OrderDetailDTO> outStock = (List<OrderDetailDTO>) request.getAttribute("OUT_STOCK");

                ProductDAO productDAO = new ProductDAO();

                int orderID = -1;

                if (request.getAttribute("ORDER_ID") != null) {
                    orderID = (int) request.getAttribute("ORDER_ID");
                }

                CategoryDAO categoryDAO = new CategoryDAO();

                float total = 0;

                if (list != null && list.size() == 0) {
            %>
            <h2 class="no-product">You have no product</h2>
            <%
            } else if (list == null) {
            %>
            <h2 class="no-product">You have no product</h2>
            <%
                }

                String outOfStock = "";

                if (outStock != null && outStock.size() > 0) {
                    for (OrderDetailDTO detail : outStock) {
                        outOfStock += productDAO.getByID(detail.getProductID()).getProductName() + ";";
                    }
            %>
            <p style="text-align: center;">These products: <%=outOfStock%> can't be order due to out of stock</p>
            <%
                }

                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        ProductDTO product = list.get(i);
                        OrderDetailDTO info = infoList.get(i);
                        total += info.getQuantity() * product.getPrice();
            %>
            <form class="row">
                <input type="hidden" name="detailID" value="<%=info.getDetailID()%>"/>
                <input type="hidden" name="orderID" value="<%=info.getDetailID()%>"/>
                <div class="image">
                    <img src="<%=product.getImage()%>" onerror="this.src='./assets/image-not-available.jpg'"/>
                </div>
                <div class="col v-center des">
                    <span><%=product.getProductName()%></span>
                    <small><%=categoryDAO.getNameByID(product.getCategoryID())%></small>
                </div>
                <div class="col v-center plus-cont">
                    <div class="plus">
                        <div class="plus-ico left">-</div>
                        <input name="quantity" type="text" value="<%=info.getQuantity()%>" inputmode="numeric">
                        <div class="plus-ico right">+</div>
                    </div>
                </div>
                <div class="col v-center h-center">
                    <span>$<%=product.getPrice()%></span>
                </div>
                <div class="col v-center h-center remove">
                    <button name="action" value="DeleteCart"><i class="fi fi-rs-cross"></i></button>
                </div>
                <div class="col v-center h-center remove">
                    <button name="action" value="UpdateCart"><i class="fi fi-rs-refresh"></i></button>
                </div>
                <div class="col v-center h-center">
                    <span>$<%=info.getQuantity() * product.getPrice()%></span>
                </div>
            </form>
            <%
                    }
                }
            %>
            <%
                if (list != null) {
                    if (list.size() > 0) {
            %>
            <%
                if (request.getAttribute("INVALID_CAPTCHA") != null) {
            %>
            <p style="text-align: right; color: red;"><%=request.getAttribute("INVALID_CAPTCHA")%></p>
            <%
                }
            %>
            <form class="final-row" action="MainController">
                <input type="hidden" name="orderID" value="<%=orderID%>"/>
                <div class="total">
                    <span>Total</span>
                    <span>$<%=String.format("%.2f", total)%></span>
                </div>
                <div class="g-recaptcha"
                     data-sitekey="6LfQjdAeAAAAANv_077XuFGUiSGOmu6tBPb1oarK"></div>
                <button name="action" value="CheckOut">Check Out</button>
            </form>
            <%
                    }
                }
            %>
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
    <script src="./script/plus-button.js"></script>
    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</html>
