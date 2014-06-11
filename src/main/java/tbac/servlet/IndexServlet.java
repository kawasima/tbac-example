package tbac.servlet;

import tbac.util.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * index servlet
 *
 * @author kawasima
 */
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
