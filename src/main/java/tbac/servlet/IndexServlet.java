package tbac.servlet;

import tbac.util.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * index servlet
 *
 * @author kawasima
 */
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
            request.setAttribute("token", TokenUtil.createToken(request));
        } catch(GeneralSecurityException ex) {
            throw new ServletException(ex);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
