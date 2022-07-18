<%-- 
    Document   : addminProductUpdate
    Created on : Mar 6, 2022, 4:51:12 PM
    Author     : phuon
--%>

<%@page import="sample.user.UserDTO"%>
<%@page import="sample.category.CategoryDTO"%>
<%@page import="sample.category.CategoryDAO"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="./style/css/custom-select.css"/>
        <link rel="stylesheet" href="./style/css/product.add.css"/>
        <link rel="stylesheet" href="./style/css/product.update.css"/>
        <link rel="stylesheet" href="./style/css/custom-scrollbar.css"/>

        <link rel="stylesheet" href="./fonts/index.css"/>

        <link rel='stylesheet' href='./style/uicons-rs.css'>
        <link rel='stylesheet' href='./style/uicons-ss.css'>

        <title>Admin Page</title>
    </head>
    <body>
        <%
            String search = (String) request.getAttribute("SEARCH");
            if (search == null) {
                search = "";
            }
        %>
        <nav>
            <div class="logo-container">
                <span class="logo">GROCERY</span>
            </div>
            <ul class="expand-menu" onclick="location.href = 'landing.jsp'">
                <li class="option">
                    <i class="fi fi-rs-home"></i>
                    <a>Home</a>
                </li>
                <ul class="list"></ul>
            </ul>
            <ul class="expand-menu">
                <li class="option">
                    <i class="fi fi-rs-shopping-cart-add"></i>
                    <a>Product</a>
                </li>
                <ul class="list">
                    <li onclick="location.href = 'productAdd.jsp'"><a>Add</a></li>
                    <li class="on-page"><a>Update</a></li>
                </ul>
            </ul>
            <ul class="expand-menu" onclick="location.href = 'MainController?action=Logout'">
                <li class="option">
                    <i class="fi fi-ss-sign-out-alt"></i>
                    <a>Logout</a>
                </li>
                <ul class="list"></ul>
            </ul>
        </nav>
        <div class="view">
            <div class="centered">
                <div class="header">
                    <form class="search-bar" action="MainController">
                        <input value="<%=search%>" name="search" placeholder="Product title" type="text"/>
                        <button type="submit" name="action" value="SearchProduct"><img src="./assets/search.svg"/></button>
                    </form>
                </div>
                <div class="body">
                    <%
                        List<ProductDTO> productList = (List<ProductDTO>) request.getAttribute("PRODUCT_LIST");

                        if (productList == null) {
                            productList = new ProductDAO().getAll(true);
                        }

                        if (productList.size() > 0) {
                            for (ProductDTO product : productList) {
                    %>
                    <form class="row" action="MainController" enctype="multipart/form-data" method="POST">
                        <input type="hidden" name="productID" value="<%=product.getProductID()%>"/>
                        <div class="col image">
                            <input accept="image/png, image/gif, image/jpeg" type="file" name="file"/>
                            <label class="image-label" id="imageDropLabel">
                                <img src="<%=product.getImage()%>" onerror="this.src='./assets/image-not-available.jpg'"/>
                            </label>
                        </div>
                        <div class="col">
                            <div class="row col-resp">
                                <div class="col col-resp">
                                    <small>Product title</small>
                                    <input id="productTitle" name="productName" placeholder="Product title" value="<%=product.getProductName()%>"/>
                                </div>
                                <div class="col col-resp">
                                    <small>Category</small>
                                    <div class="custom-select" style="width: 100%">
                                        <select name="category">
                                            <option value="Category">Category</option>
                                            <%
                                                CategoryDAO categoryDAO = new CategoryDAO();
                                                List<CategoryDTO> list = categoryDAO.getAll();
                                                for (int i = 0; i < list.size(); i++) {
                                            %>
                                            <option <%=product.getCategoryID() == i ? "selected" : ""%> value="<%=list.get(i).getCategoryName()%>"><%=list.get(i).getCategoryName()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col col-resp">
                                    <small>Import date</small>
                                    <input name="importDate" type="date" value="<%=product.getImportDate()%>"/>
                                </div>
                                <div class="col col-resp">
                                    <small>Expiry day</small>
                                    <input name="usingDate" data-type="number" placeholder="Expiry day" value="<%=product.getUsingDate()%>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="row">
                                <div class="col col-resp">
                                    <small>Price</small>
                                    <input name="price" value="<%=product.getPrice()%>" data-type="float" id="priceInput" type="text" placeholder="Price"/>
                                </div>
                                <div class="col col-resp">
                                    <small>Quantity</small>
                                    <input name="quantity" value="<%=product.getQuantity()%>" data-type="number" min="0" max="1000" placeholder="Quantity"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col col-resp">
                                    <small>Confirm</small>
                                    <button name="action" value="UpdateProduct" class="btn confirm-btn">Update</button>
                                </div>
                                <div class="col col-resp">
                                    <small>Delete</small>
                                    <button name="action" value="DeleteProduct" class="btn del-btn">Remove</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </div>

        <nav class="mobile">
            <a href="landing.jsp"><i class="fi fi-rs-home"></i></a>
            <a href="productAdd.jsp"><i class="fi fi-rs-shopping-cart-add"></i></a>
            <a href="productUpdate.jsp"><i class="fi fi-rs-shopping-cart-check"></i></a>
            <a><i class="fi fi-rs-user"></i></a>
        </nav>

        <div class="scrollbar-container">
            <div id="scroll-bar"></div>
        </div>
    </body>
    <script src="./script/plus-button.js"></script>
    <script src="./script/custom-select.js"></script>
    <script src="./script/typeNumber.js"></script>
    <script src="./script/admin.update.js"></script>
    <script src="./script/expand-menu.js"></script>
</html>
