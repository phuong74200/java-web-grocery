<%-- 
    Document   : admin.product.add
    Created on : Mar 3, 2022, 9:23:50 PM
    Author     : phuon
--%>

<%@page import="sample.utils.DateUtils"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="sample.utils.ValidUtils"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="sample.product.ProductError"%>
<%@page import="java.util.List"%>
<%@page import="sample.category.CategoryDTO"%>
<%@page import="sample.category.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="./style/css/custom-select.css"/>
        <link rel="stylesheet" href="./style/css/custom-scrollbar.css"/>
        <link rel="stylesheet" href="./style/css/product.add.css"/>

        <link rel="stylesheet" href="./fonts/index.css"/>
        
        <link rel='stylesheet' href='./style/uicons-rs.css'>
        <link rel='stylesheet' href='./style/uicons-ss.css'>

        <title>Admin Page</title>
    </head>
    <body>
        <%
            ProductError error = (ProductError) request.getAttribute("ERROR");
            if (error == null) {
                error = new ProductError();
            }

            ProductDTO validDTO = (ProductDTO) request.getAttribute("VALID");
            if (validDTO == null) {
                validDTO = new ProductDTO();
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
                    <li class="on-page"><a>Add</a></li>
                    <li onclick="location.href = 'productUpdate.jsp'"><a>Update</a></li>
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
            <div class="body">
                <div class="add">
                    <div class="col">
                        <form class="box" method="POST" action="MainController" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col">
                                    <p>Product title <small><%=error.getProductNameError()%></small></p>
                                    <input value="<%=validDTO.getProductName()%>" name="productName" id="productTitle" placeholder="Product title"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <p>Image <small><%=error.getImageError()%></small></p>
                                    <input name="file" id="imageDropZone" accept="image/png, image/gif, image/jpeg" type="file"/>
                                    <label for="imageDropZone" class="image-label" id="imageDropLabel">
                                        <span><b>Choose</b> an image or <b>drag</b> it here</span>
                                        <img/>
                                    </label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <p>Category <small><%=error.getCategoryIDError()%></small></p>
                                    <div class="custom-select" style="width: 100%">
                                        <select name="category">
                                            <option value="Category">Category</option>

                                            <%
                                                CategoryDAO categoryDAO = new CategoryDAO();
                                                List<CategoryDTO> list = categoryDAO.getAll();
                                                for (int i = 0; i < list.size(); i++) {
                                            %>
                                            <option value="<%=list.get(i).getCategoryName()%>"><%=list.get(i).getCategoryName()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <p>Import date <small><%=error.getImportDateError()%></small></p>
                                    <input value="<%=validDTO.getImportDate() == null ? "" : DateUtils.toHTMLDate(validDTO.getImportDate())%>" name="importDate" type="date"/>
                                </div>
                                <div class="col">
                                    <p>Expiry day <small><%=error.getUsingDateError()%></small></p>
                                    <input value="<%=validDTO.getUsingDate()%>" name="usingDate" data-type="number" placeholder="Expiry day"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <p>Price <small><%=error.getPriceError()%></small></p>
                                    <input value="<%=validDTO.getPrice()%>" name="price" value="14.00" data-type="float" id="priceInput" type="text" placeholder="Price"/>
                                </div>
                                <div class="col">
                                    <p>Quantity <small><%=error.getQuantityError()%></small></p>
                                    <input value="<%=validDTO.getQuantity()%>" name="quantity" value="0" data-type="number" min="0" max="1000" placeholder="Quantity"/>
                                </div>
                            </div>
                            <div class="row">
                                <button type="submit" class="add-product-button" name="action" value="AddProduct">Add product</button>
                            </div>
                        </form>
                    </div>
                    <div class="col preview">
                        <!--                        <div class="row"><h2>Preview</h2></div>-->
                        <div class="box preview">
                            <div class="row"><i class="fi fi-ss-apps pre-ico"></i></div>
                            <div class="row">
                                <div class="product">
                                    <div class="image">
                                        <img id="previewImg" src="./assets/image-not-available.jpg"/>
                                    </div>
                                    <div>
                                        <span class="sale-off" id="discount">10%</span>
                                        <p class="title" id="previewTitle">
                                            Product Title
                                        </p>
                                        <h6>
                                            <i class="fi fi-ss-checkbox"></i>
                                            <strong>In Stock</strong><a> - 1 kg</a>
                                        </h6>
                                        <p class="price" id="pricePreview">
                                            <del>$14</del>
                                            <bdi>$14.00</bdi>
                                        </p>
                                        <div class="plus">
                                            <div class="plus-ico left">-</div>
                                            <input type="text" value="0" min="5" max="10" inputmode="numeric">
                                            <div class="plus-ico right">+</div>
                                        </div>
                                        <div class="cart">
                                            <button><i class="fi fi-ss-shopping-cart-add"></i> Add to Cart</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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
    <script src="./script/admin.js"></script>
    <script src="./script/plus-button.js"></script>
    <script src="./script/custom-select.js"></script>
    <script src="./script/typeNumber.js"></script>
    <script src="./script/expand-menu.js"></script>
</html>
