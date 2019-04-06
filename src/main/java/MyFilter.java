import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/ans", servletNames = "AnsServlet", dispatcherTypes = DispatcherType.FORWARD)
public class MyFilter extends HttpFilter {


    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        ServletContext context = getServletContext();
        Map<String, User> users = (Map<String, User>) context.getAttribute("usersMap");
        if (users.containsKey(session.getId())) {
            session.setAttribute("username", users.get(request.getRequestedSessionId()).getUsername());
            chain.doFilter(request, response);
        } else {
            if (request.getSession().getAttribute("errorMessage") == null) {
                request.getSession().setAttribute("errorMessage", "Invalid login or password");
            }
            response.sendRedirect("/index.jsp?error");
        }
    }

    public void destroy() {
    }

}
