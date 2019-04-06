import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;


public class MyFilter extends HttpFilter {


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            ServletContext context = request.getServletContext();
            Map<String, User> users = (Map<String, User>) context.getAttribute("usersMap");

            // достает из hashmap'ы на сервере объект, описывающий этого пользователя ???
            session.setAttribute("username", users.get(session.getId()).getUsername());
            chain.doFilter(request, response);
        }
        else { // JSESSION_ID нет
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/index.jsp");
        }
    }

    public void destroy() {
    }

}
