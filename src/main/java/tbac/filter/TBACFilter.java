package tbac.filter;

import tbac.util.TokenUtil;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token based access control filter.
 *
 * @author kawasima
 */
public class TBACFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if (!TokenUtil.validToken((HttpServletRequest) servletRequest)) {
            httpResponse.sendError(403);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
