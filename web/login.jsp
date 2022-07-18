<%-- 
    Document   : login
    Created on : Mar 3, 2022, 12:48:23 PM
    Author     : phuon
--%>

<%@page import="sample.env.env"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>

        <link rel="stylesheet" href="./style/css/signup.css"/>
        <link rel="stylesheet" href="./fonts/index.css"/>

        <link rel='stylesheet' href='./style/uicons-rs.css'>
        <link rel='stylesheet' href='./style/uicons-ss.css'>
    </head>
    <body>
        <form action="MainController" method="POST" class="form login">
            <h1 class="logo">GROCERY</h1>
            <small>Username</small>
            <div class="row">
                <div class="field">
                    <div>
                        <i class="fi fi-ss-user"></i>
                    </div>
                    <input name="userID" type="text" placeholder="Username"/>
                </div>
            </div>
            <small>Password</small>
            <div class="row">
                <div class="field">
                    <div>
                        <i class="fi fi-ss-lock"></i>
                    </div>
                    <input name="password" type="password" placeholder="Password"/>
                </div>
            </div>
            <div class="row">
                <%
                    if (request.getAttribute("ERROR") != null) {
                %>
                <p style="color: #DB4437"><%=request.getAttribute("ERROR")%></p>
                <%
                    }
                %>
            </div>
            <div class="sign-up">
                <button type="submit" name="action" value="Login">Sign in</button>
            </div>
            <div class="middle-dash">or sign in with</div>
            <div class="sign-up google">
                <div onclick="location.href = 'https://accounts.google.com/o/oauth2/auth?scope=profile email&redirect_uri=<%=env.REDIRECT_URI%>&response_type=code&client_id=<%=env.OAUTH_CLIENT_ID%>&approval_prompt=force'">
                    Google
                </div>
            </div>
            <h5>Not have an account? <a href="signUp.jsp">Register</a> now</h5>
        </form>
    </body>
</html>
