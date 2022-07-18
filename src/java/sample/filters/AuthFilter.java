/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.user.UserDTO;

/**
 *
 * @author phuon
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final boolean debug = true;

    private final List<String> US_RESOURCE;
    private final List<String> AD_RESOURCE;
    private final List<String> NON_AUTH_RESOURCE;
    private final List<String> PAGE_RESOURCE;

    private static final String US = "US";
    private static final String AD = "AD";

    private static final String INDEX_PAGE = "landing.jsp";

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "Login";
    private static final String ADD_PRODUCT = "AddProduct";
    private static final String SEARCH_PRODUCT = "SearchProduct";
    private static final String UPDATE_PRODUCT = "UpdateProduct";
    private static final String DELETE_PRODUCT = "DeleteProduct";
    private static final String CHECK_OUT = "CheckOut";
    private static final String SIGN_UP = "SignUp";
    private static final String ADD_ITEM = "AddItem";
    private static final String GET_CART = "GetCart";
    private static final String UPDATE_CART = "UpdateCart";
    private static final String LOGOUT = "Logout";
    private static final String DELETE_CART = "DeleteCart";
    private static final String GOOGLE_AUTH = "AuthController";

    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthFilter() {
        US_RESOURCE = new ArrayList<>();
        AD_RESOURCE = new ArrayList<>();
        NON_AUTH_RESOURCE = new ArrayList<>();

        PAGE_RESOURCE = new ArrayList<>();

        PAGE_RESOURCE.addAll(Arrays.asList(
                "style", "script",
                "fonts", "assets"
        ));

        NON_AUTH_RESOURCE.addAll(Arrays.asList(
                "MainController",
                ERROR, LOGIN, SEARCH_PRODUCT,
                SIGN_UP, LOGOUT, GOOGLE_AUTH,
                "landing.jsp", "login.jsp",
                "signUp.jsp", ADD_ITEM
        ));

        US_RESOURCE.addAll(Arrays.asList(
                "MainController",
                ERROR, LOGIN, SEARCH_PRODUCT,
                SIGN_UP, LOGOUT, GOOGLE_AUTH,
                CHECK_OUT, ADD_ITEM, GET_CART,
                UPDATE_CART, DELETE_CART, GOOGLE_AUTH,
                "cart.jsp", "landing.jsp", "login.jsp",
                "signUp.jsp", "style", "script",
                "fonts", "assets"
        ));

        AD_RESOURCE.addAll(Arrays.asList(
                "MainController",
                ERROR, LOGIN, SEARCH_PRODUCT,
                SIGN_UP, LOGOUT, GOOGLE_AUTH,
                ADD_PRODUCT, UPDATE_PRODUCT,
                DELETE_PRODUCT, "productAdd.jsp",
                "productUpdate.jsp", "signUp.jsp",
                "landing.jsp", "login.jsp", "style",
                "script", "fonts", "assets"
        ));
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String uri = req.getRequestURI();
            int index = uri.lastIndexOf("/");

            String[] paths = uri.split("/");

            if (paths.length >= 3) {
                if (PAGE_RESOURCE.contains(paths[2])) {
                    chain.doFilter(request, response);
                } else {
                    String requestResource = uri.substring(index + 1);

                    if (requestResource.equals("MainController")) {
                        requestResource = req.getParameter("action");
                    }

                    if (requestResource == null) {
                        res.sendRedirect(INDEX_PAGE);
                    } else {
                        HttpSession session = req.getSession();
                        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

                        if (user == null) {
                            if (NON_AUTH_RESOURCE.contains(requestResource)) {
                                chain.doFilter(request, response);
                            } else {
                                res.sendRedirect(INDEX_PAGE);
                            }
                        } else if (AD.equals(user.getRoleID()) && AD_RESOURCE.contains(requestResource)) {
                            chain.doFilter(request, response);
                        } else if (US.equals(user.getRoleID()) && US_RESOURCE.contains(requestResource)) {
                            chain.doFilter(request, response);
                        } else {
                            res.sendRedirect(INDEX_PAGE);
                        }
                    }
                }
            } else {
                res.sendRedirect(INDEX_PAGE);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
