<%-- 
    Document   : login
    Created on : Mar 3, 2022, 12:48:23 PM
    Author     : phuon
--%>

<%@page import="sample.env.env"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="sample.user.UserError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>

        <link rel="stylesheet" href="./style/css/signup.css"/>
        <link rel="stylesheet" href="./fonts/index.css"/>
        <link rel='stylesheet' href='https://cdn-uicons.flaticon.com/uicons-regular-straight/css/uicons-regular-straight.css'>
        <link rel='stylesheet' href='https://cdn-uicons.flaticon.com/uicons-solid-straight/css/uicons-solid-straight.css'>
    </head>
    <body>
        <%
            UserError userError = (UserError) request.getAttribute("USER_ERROR");
            if (userError == null) {
                userError = new UserError();
            }

            UserDTO validDTO = (UserDTO) request.getAttribute("VALID");
            if (validDTO == null) {
                validDTO = new UserDTO();
            }
        %>
        <form class="form" action="MainController" method="POST">
            <h1 class="logo">GROCERY</h1>
            <small>Full name <%=userError.getFullNameError()%></small>
            <div class="row">
                <div class="field">
                    <div>
                        <i class="fi fi-ss-user"></i>
                    </div>
                    <input value="<%=validDTO.getFullName()%>" name="fullname" type="text" placeholder="Fullname"/>
                </div>
            </div>
            <div class="col">
                <div>
                    <small>Username <%=userError.getUserIDError()%></small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-user"></i>
                            </div>
                            <input value="<%=validDTO.getUserID()%>" name="userID" type="text" placeholder="Username"/>
                        </div>
                    </div>
                    <small>Email<%=userError.getEmailError()%></small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-envelope"></i>
                            </div>
                            <input value="<%=validDTO.getEmail()%>" name="email" type="text" placeholder="Email"/>
                        </div>
                    </div>
                    <small>Password <%=userError.getRePasswordError()%></small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-lock"></i>
                            </div>
                            <input name="password" type="password" placeholder="Password"/>
                        </div>
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-lock"></i>
                            </div>
                            <input name="rePassword" type="password" placeholder="re-Password"/>
                        </div>
                    </div>
                </div>
                <div>
                    <small>Address</small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-marker"></i>
                            </div>
                            <input name="address" type="text" placeholder="Address"/>
                        </div>
                    </div>
                    <small>Phone number <%=userError.getPhoneError()%></small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-address-book"></i>
                            </div>
                            <input name="phone" type="text" placeholder="Phone number"/>
                        </div>
                    </div>
                    <small>Birthday <%=userError.getBirthdayError()%></small>
                    <div class="row">
                        <div class="field">
                            <div>
                                <i class="fi fi-ss-calendar"></i>
                            </div>
                            <input value="<%=validDTO.getBirthday()%>" name="birthday" type="date"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="sign-up">
                <button type="submit" name="action" value="SignUp">Sign up</button>
            </div>
            <div class="middle-dash">or sign in with</div>
            <div class="sign-up google">
                <div onclick="location.href='https://accounts.google.com/o/oauth2/auth?scope=profile email&redirect_uri=<%=env.REDIRECT_URI%>&response_type=code&client_id=<%=env.OAUTH_CLIENT_ID%>&approval_prompt=force'">
                    Google
                </div>
            </div>
            <h5>Already a member? <a href="login.jsp">Sign in</a> now</h5>
        </form>
        <!--        <div class="row">
                    <div class="middle-dash">or sign up with</div>
                </div>
                <div class="row">
                    <div class="sign-up google">
                        <button>Google</button>
                    </div>
                </div>
                <div class="row">
                    <h5>Already a member? <a href="login.jsp">Sign in</a> now</h5>
                </div>-->
    </div>
</div>
</body>
</html>
